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

package io.questdb.cairo.sql;

import io.questdb.cairo.ColumnType;
import io.questdb.cairo.GenericRecordMetadata;
import io.questdb.griffin.PlanSink;
import io.questdb.griffin.Plannable;
import org.jetbrains.annotations.Nullable;

public class ColumnMetadata implements Plannable {
    @Nullable
    private final RecordMetadata metadata;
    private final boolean symbolTableStatic;
    protected String columnName;
    protected int columnType;
    private boolean indexed;

    public ColumnMetadata(String columnName, int columnType) {
        this(columnName, columnType, null);
    }

    public ColumnMetadata(String columnName, int columnType, @Nullable RecordMetadata metadata) {
        this(columnName, columnType, false, false, metadata);
        // Do not allow using this constructor for symbol types.
        // Use version where you specify symbol table parameters
        assert !ColumnType.isSymbol(columnType);
    }

    public ColumnMetadata(
            String columnName,
            int columnType,
            boolean indexed,
            boolean symbolTableStatic,
            @Nullable RecordMetadata metadata
    ) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.indexed = indexed;
        this.symbolTableStatic = symbolTableStatic;
        this.metadata = GenericRecordMetadata.copyOf(metadata);
    }

    public String getColumnName() {
        return columnName;
    }

    public int getColumnType() {
        return columnType;
    }

    @Nullable
    public RecordMetadata getMetadata() {
        return metadata;
    }

    public boolean isIndexed() {
        return indexed;
    }

    public boolean isSymbolTableStatic() {
        return symbolTableStatic;
    }

    public void setIndexed(boolean value) {
        this.indexed = value;
    }

    @Override
    public void toPlan(PlanSink sink) {
        sink.val(columnName);
    }
}
