package org.ssssssss.magicapi.modules.db.table;

import org.ssssssss.script.annotation.Comment;

import java.beans.Transient;

/**
 * 关联表操作接口
 *
 * @author tom
 */
public interface JoinInterface {

    @Transient
    @Comment("拼接join")
    JoinedTable join(boolean condition, String joinType, String tableName, String alias);

    @Comment("拼接innerJoin")
    default JoinedTable innerJoin(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                                  @Comment(name = "tableName", value = "表名") String tableName,
                                  @Comment(name = "alias", value = "别名") String alias) {
        return join(condition, DbConstant.INNER_JOIN, tableName, alias);
    }

    @Comment("拼接innerJoin")
    default JoinedTable innerJoin(@Comment(name = "tableName", value = "表名") String tableName,
                                  @Comment(name = "alias", value = "别名") String alias) {
        return join(true, DbConstant.INNER_JOIN, tableName, alias);
    }

    @Comment("拼接leftJoin")
    default JoinedTable leftJoin(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                                 @Comment(name = "tableName", value = "表名") String tableName,
                                 @Comment(name = "alias", value = "别名") String alias) {
        return join(condition, DbConstant.LEFT_JOIN, tableName, alias);
    }

    @Comment("拼接leftJoin")
    default JoinedTable leftJoin(@Comment(name = "tableName", value = "表名") String tableName,
                                 @Comment(name = "alias", value = "别名") String alias) {
        return join(true, DbConstant.LEFT_JOIN, tableName, alias);
    }

    @Comment("拼接rightJoin")
    default JoinedTable rightJoin(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                                  @Comment(name = "tableName", value = "表名") String tableName,
                                  @Comment(name = "alias", value = "别名") String alias) {
        return join(condition, DbConstant.RIGHT_JOIN, tableName, alias);
    }

    @Comment("拼接rightJoin")
    default JoinedTable rightJoin(@Comment(name = "tableName", value = "表名") String tableName,
                                  @Comment(name = "alias", value = "别名") String alias) {
        return join(true, DbConstant.RIGHT_JOIN, tableName, alias);
    }
}
