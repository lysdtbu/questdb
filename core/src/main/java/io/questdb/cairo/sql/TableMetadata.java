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

import io.questdb.cairo.TableToken;
import io.questdb.std.QuietCloseable;

public interface TableMetadata extends RecordMetadata, QuietCloseable {
    /**
     * The returned value defines how many row IDs to store in a single storage block on disk
     * for an indexed column. Fewer blocks used to store row IDs achieves better performance.
     *
     * @param columnIndex numeric index of the column
     * @return number of row IDs per block
     */
    int getIndexValueBlockCapacity(int columnIndex);

    /**
     * The returned value defines how many row IDs to store in a single storage block on disk
     * for an indexed column. Fewer blocks used to store row IDs achieves better performance.
     *
     * @param columnName name of the column
     * @return number of row IDs per block
     */
    default int getIndexValueBlockCapacity(CharSequence columnName) {
        return getIndexValueBlockCapacity(getColumnIndex(columnName));
    }

    int getMaxUncommittedRows();

    long getMetadataVersion();

    long getO3MaxLag();

    int getPartitionBy();

    int getTableId();

    TableToken getTableToken();

    /**
     * Writing index for the column
     *
     * @param columnIndex column index
     * @return writing index
     */
    int getWriterIndex(int columnIndex);

    /**
     * @param columnIndex numeric index of the column
     * @return true if column is part of deduplication key used in inserts.
     */
    boolean isDedupKey(int columnIndex);

    boolean isWalEnabled();
}
