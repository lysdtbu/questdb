/*******************************************************************************
 *     ___                  _   ____  ____
 *    / _ \ _   _  ___  ___| |_|  _ \| __ )
 *   | | | | | | |/ _ \/ __| __| | | |  _ \
 *   | |_| | |_| |  __/\__ \ |_| |_| | |_) |
 *    \__\_\\__,_|\___||___/\__|____/|____/
 *
 *  Copyright (c) 2014-2019 Appsicle
 *  Copyright (c) 2019-2023 QuestDB
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 ******************************************************************************/

package io.questdb.cairo;

import io.questdb.cairo.sql.ColumnMetadata;
import io.questdb.cairo.sql.RecordMetadata;
import org.jetbrains.annotations.Nullable;

public class TableColumnMetadata extends ColumnMetadata {
    private final int writerIndex;
    private int indexValueBlockCapacity;
    private boolean isDedupKey;

    public TableColumnMetadata(String name, int type) {
        this(name, type, null);
    }

    public TableColumnMetadata(String name, int type, @Nullable RecordMetadata metadata) {
        this(name, type, false, 0, false, metadata, -1, false);
        // Do not allow using this constructor for symbol types.
        // Use version where you specify symbol table parameters
        assert !ColumnType.isSymbol(type);
    }

    public TableColumnMetadata(
            String name,
            int type,
            boolean indexFlag,
            int indexValueBlockCapacity,
            boolean symbolTableStatic,
            @Nullable RecordMetadata metadata
    ) {
        this(name, type, indexFlag, indexValueBlockCapacity, symbolTableStatic, metadata, -1, false);
    }

    public TableColumnMetadata(
            String name,
            int type,
            boolean indexed,
            int indexValueBlockCapacity,
            boolean symbolTableStatic,
            @Nullable RecordMetadata metadata,
            int writerIndex,
            boolean dedupKeyFlag
    ) {
        super(name, type, indexed, symbolTableStatic, metadata);
        this.indexValueBlockCapacity = indexValueBlockCapacity;
        this.writerIndex = writerIndex;
        this.isDedupKey = dedupKeyFlag;
    }

    public int getIndexValueBlockCapacity() {
        return indexValueBlockCapacity;
    }

    public int getWriterIndex() {
        return writerIndex;
    }

    public boolean isDedupKey() {
        return isDedupKey;
    }

    public boolean isDeleted() {
        return getColumnType() < 0;
    }

    public void markDeleted() {
        columnType = -Math.abs(columnType);
    }

    public void setDedupKeyFlag(boolean dedupKeyFlag) {
        isDedupKey = dedupKeyFlag;
    }

    public void setIndexValueBlockCapacity(int indexValueBlockCapacity) {
        this.indexValueBlockCapacity = indexValueBlockCapacity;
    }
}
