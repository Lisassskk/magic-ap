package org.ssssssss.magicapi.modules.db.table;

import org.ssssssss.script.annotation.Comment;

/**
 * 连表操作API
 *
 * @author tom
 */
public class JoinedTable extends TableBase {

    protected final NamedTable namedTable;

    On on = new On(this);

    /**
     * Join类型
     */
    String joinType;

    public JoinedTable(NamedTable namedTable, String tableName, String alias) {
        super(tableName, alias, namedTable.sqlModule,
                namedTable.rowMapColumnMapper, namedTable.namedTableInterceptors);
        this.namedTable = namedTable;
    }

    @Override
    @Comment("克隆")
    public JoinedTable clone() {
        JoinedTable joinedTable = (JoinedTable) super.clone();
        joinedTable.on = this.on == null ? null : this.on.clone();
        return joinedTable;
    }

    @Comment("拼接on")
    public On on() {
        return on;
    }
}
