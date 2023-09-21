package org.ssssssss.magicapi.modules.db.table;

/**
 * 数据库操作常量
 * @author tangchen
 */
public interface DbConstant {

    String AS = " AS ";

    String JOIN = "JOIN";

    String LEFT = "LEFT";

    String RIGHT = "RIGHT";

    String INNER = "INNER";

    String SPACE = " ";

    String DOT = ".";


    /**
     * " LEFT JOIN "
     */
    String LEFT_JOIN = SPACE + LEFT + SPACE + JOIN + SPACE;

    /**
     * " RIGHT JOIN "
     */
    String RIGHT_JOIN = SPACE + RIGHT + SPACE + JOIN + SPACE;

    /**
     * " INNER JOIN "
     */
    String INNER_JOIN = SPACE + INNER + SPACE + JOIN + SPACE;
}
