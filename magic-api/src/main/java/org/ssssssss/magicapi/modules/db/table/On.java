package org.ssssssss.magicapi.modules.db.table;

import org.ssssssss.script.annotation.Comment;

import java.beans.Transient;
import java.util.function.Function;

/**
 * 连表API的On
 *
 * @author tom
 */
public class On extends WhereBase implements TableInterface, JoinInterface {

    public On(JoinedTable joinedTable) {
        this(joinedTable, true);
    }

    public On(JoinedTable joinedTable, boolean needOn) {
        super(joinedTable, needOn);
    }

    @Override
    @Comment("克隆")
    public On clone() {
        return (On) super.clone();
    }

    @Override
    @Transient
    public NamedTable getNamedTable() {
        return ((JoinedTable) tableBase).namedTable;
    }

    @Comment("拼接where")
    public Where where() {
        return getNamedTable().where();
    }

    @Comment("逻辑过滤")
    public On logic() {
        super.logic();
        return this;
    }

    @Override
    @Comment("过滤`null`的参数")
    public On notNull() {
        return notNull(true);
    }

    @Override
    @Comment("过滤`blank`的参数")
    public On notBlank() {
        return notBlank(true);
    }

    @Override
    @Comment("是否过滤`null`的参数")
    public On notNull(boolean flag) {
        super.notNull(flag);
        return this;
    }

    @Override
    @Comment("是否过滤`blank`的参数")
    public On notBlank(boolean flag) {
        super.notBlank(flag);
        return this;
    }

    @Override
    @Comment("等于`=`,如：`eq('name', '老王') ---> name = '老王'`")
    public On eq(@Comment(name = "column", value = "数据库中的列名") String column,
                 @Comment(name = "value", value = "值") Object value) {
        return eq(true, column, value);
    }

    @Override
    @Comment("等于`=`,如：`eq('name', '老王') ---> name = '老王'`")
    public On eq(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                 @Comment(name = "column", value = "数据库中的列名") String column,
                 @Comment(name = "value", value = "值") Object value) {
        super.eq(condition, column, value);
        return this;
    }

    @Override
    @Comment("等于`=`,如：`eq2('t.userId', 'u.id') ---> t.userId = u.id`")
    public On eq2(@Comment(name = "column1", value = "数据库中的列名1") String column1,
                  @Comment(name = "column2", value = "数据库中的列名2") String column2) {
        return eq2(true, column1, column2);
    }

    @Override
    @Comment("等于`=`,如：`eq2('t.userId', 'u.id') ---> t.userId = u.id`")
    public On eq2(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                  @Comment(name = "column1", value = "数据库中的列名1") String column1,
                  @Comment(name = "column2", value = "数据库中的列名2") String column2) {
        super.eq2(condition, column1, column2);
        return this;
    }

    @Override
    @Comment("不等于`<>`,如：`ne('name', '老王') ---> name <> '老王'`")
    public On ne(@Comment(name = "column", value = "数据库中的列名") String column,
                 @Comment(name = "value", value = "值") Object value) {
        return ne(true, column, value);
    }

    @Override
    @Comment("不等于`<>`,如：`ne('name', '老王') ---> name <> '老王'`")
    public On ne(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                 @Comment(name = "column", value = "数据库中的列名") String column,
                 @Comment(name = "value", value = "值") Object value) {
        super.ne(condition, column, value);
        return this;
    }

    @Override
    @Comment("不等于`<>`,如：`ne2('t.name', 'u.name') ---> t.name <> u.name`")
    public On ne2(@Comment(name = "column1", value = "数据库中的列名1") String column1,
                  @Comment(name = "column2", value = "数据库中的列名2") String column2) {
        return ne2(true, column1, column2);
    }

    @Override
    @Comment("不等于`<>`,如：`ne2('t.name', 'u.name') ---> t.name <> u.name`")
    public On ne2(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                  @Comment(name = "column1", value = "数据库中的列名1") String column1,
                  @Comment(name = "column2", value = "数据库中的列名2") String column2) {
        super.ne2(condition, column1, column2);
        return this;
    }

    @Override
    @Comment("between(true, true, 't.createTime', '2023-01-01', '2023-01-31', true, false) ---> t.create_time >= '2023-01-01' and t.create_time < '2023-01-31'")
    public On between(
            @Comment(name = "startCon", value = "判断表达式，当为true时拼接条件") boolean startCon,
            @Comment(name = "endCon", value = "判断表达式，当为true时拼接条件") boolean endCon,
            @Comment(name = "column", value = "数据库中的列名") String column,
            @Comment(name = "startVal", value = "开始值") Object startVal,
            @Comment(name = "endVal", value = "结束值") Object endVal,
            @Comment(name = "startContain", value = "包含开始值") boolean startContain,
            @Comment(name = "endContain", value = "包含结束值") boolean endContain
    ) {
        super.between(startCon, endCon, column, startVal, endVal, startContain, endContain);
        return this;
    }

    @Override
    @Comment("between('t.createTime', '2023-01-01', '2023-01-31', true, false) ---> t.create_time >= '2023-01-01' and t.create_time < '2023-01-31'")
    public On between(
            @Comment(name = "column", value = "数据库中的列名") String column,
            @Comment(name = "startVal", value = "开始值") Object startVal,
            @Comment(name = "endVal", value = "结束值") Object endVal,
            @Comment(name = "startContain", value = "包含开始值") boolean startContain,
            @Comment(name = "endContain", value = "包含结束值") boolean endContain
    ) {
        return between(true,true,column,startVal,endVal,startContain,endContain);
    }

    @Override
    @Comment("between(true, true, 't.createTime', '2023-01-01', '2023-01-31') ---> t.create_time >= '2023-01-01' and t.create_time <= '2023-01-31'")
    public On between(
            @Comment(name = "startCon", value = "判断表达式，当为true时拼接条件") boolean startCon,
            @Comment(name = "endCon", value = "判断表达式，当为true时拼接条件") boolean endCon,
            @Comment(name = "column", value = "数据库中的列名") String column,
            @Comment(name = "startVal", value = "开始值") Object startVal,
            @Comment(name = "endVal", value = "结束值") Object endVal
    ) {
        return between(startCon,endCon,column,startVal,endVal,true,true);
    }

    @Override
    @Comment("between('t.createTime', '2023-01-01', '2023-01-31') ---> t.create_time >= '2023-01-01' and t.create_time <= '2023-01-31'")
    public On between(
            @Comment(name = "column", value = "数据库中的列名") String column,
            @Comment(name = "startVal", value = "开始值") Object startVal,
            @Comment(name = "endVal", value = "结束值") Object endVal
    ) {
        return between(true,true,column,startVal,endVal,true,true);
    }

    @Override
    @Comment("等于`=`,如：`notExists(true, 'table_name', 'tn', namedWhere -> namedWhere.eq2('tn.aId','a.id').eq('tn.name','老王'), true) ---> not exists(select 1 from table_name as tn namedWhere tn.a_id=a.id and tn.user_id='老王' and tn.del_flag<>1`")
    public On notExists(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                        @Comment(name = "tableName", value = "表名") String tableName,
                        @Comment(name = "alias", value = "别名") String alias,
                        @Comment(name = "function", value = "关联条件") Function<WhereBase, WhereBase> function,
                        @Comment(name = "useLogic", value = "使用逻辑删除") boolean useLogic) {
        exists(true, condition, tableName, alias, function, useLogic);
        return this;
    }

    @Override
    @Comment("等于`=`,如：`exists('table_name', 'tn', namedWhere -> namedWhere.eq2('tn.aId','a.id').eq('tn.name','老王'), true) ---> exists(select 1 from table_name as tn namedWhere tn.a_id=a.id and tn.user_id='老王' and tn.del_flag<>1`")
    public On notExists(@Comment(name = "tableName", value = "表名") String tableName,
                        @Comment(name = "alias", value = "别名") String alias,
                        @Comment(name = "function", value = "关联条件") Function<WhereBase, WhereBase> function,
                        @Comment(name = "useLogic", value = "使用逻辑删除") boolean useLogic) {
        return notExists(true, tableName, alias, function, useLogic);
    }

    @Override
    @Comment("等于`=`,如：`exists(true, 'table_name', 'tn', namedWhere -> namedWhere.eq2('tn.aId','a.id').eq('tn.name','老王')) ---> exists(select 1 from table_name as tn namedWhere tn.a_id=a.id and tn.user_id='老王'`")
    public On notExists(@Comment(name = "tableName", value = "表名") String tableName,
                        @Comment(name = "alias", value = "别名") String alias,
                        @Comment(name = "function", value = "关联条件") Function<WhereBase, WhereBase> function) {
        return notExists(true, tableName, alias, function, false);
    }

    @Override
    @Comment("等于`=`,如：`exists(true, 'table_name', 'tn', namedWhere -> namedWhere.eq2('tn.aId','a.id').eq('tn.name','老王'), true) ---> exists(select 1 from table_name as tn namedWhere tn.a_id=a.id and tn.user_id='老王' and tn.del_flag<>1`")
    public On exists(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                     @Comment(name = "tableName", value = "表名") String tableName,
                     @Comment(name = "alias", value = "别名") String alias,
                     @Comment(name = "function", value = "关联条件") Function<WhereBase, WhereBase> function,
                     @Comment(name = "useLogic", value = "使用逻辑删除") boolean useLogic) {
        exists(false, condition, tableName, alias, function, useLogic);
        return this;
    }

    @Override
    @Comment("等于`=`,如：`exists('table_name', 'tn', namedWhere -> namedWhere.eq2('tn.aId','a.id').eq('tn.name','老王'), true) ---> exists(select 1 from table_name as tn namedWhere tn.a_id=a.id and tn.user_id='老王' and tn.del_flag<>1`")
    public On exists(@Comment(name = "tableName", value = "表名") String tableName,
                     @Comment(name = "alias", value = "别名") String alias,
                     @Comment(name = "function", value = "关联条件") Function<WhereBase, WhereBase> function,
                     @Comment(name = "useLogic", value = "使用逻辑删除") boolean useLogic) {
        return exists(true, tableName, alias, function, useLogic);
    }

    @Override
    @Comment("等于`=`,如：`exists('table_name', 'tn', namedWhere -> namedWhere.eq2('tn.aId','a.id').eq('tn.name','老王')) ---> exists(select 1 from table_name as tn namedWhere tn.a_id=a.id and tn.user_id='老王'`")
    public On exists(@Comment(name = "tableName", value = "表名") String tableName,
                     @Comment(name = "alias", value = "别名") String alias,
                     @Comment(name = "function", value = "关联条件") Function<WhereBase, WhereBase> function) {
        return exists(true, tableName, alias, function, false);
    }

    @Override
    @Comment("小于`<`,如：`lt('age', 18) ---> age < 18")
    public On lt(@Comment(name = "column", value = "数据库中的列名") String column,
                 @Comment(name = "value", value = "值") Object value) {
        return lt(true, column, value);
    }

    @Override
    @Comment("小于`<`,如：`lt('age', 18) ---> age < 18")
    public On lt(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                 @Comment(name = "column", value = "数据库中的列名") String column,
                 @Comment(name = "value", value = "值") Object value) {
        super.lt(condition, column, value);
        return this;
    }

    @Override
    @Comment("小于`<`,如：`lt2('t.age', 'u.age') ---> t.age < u.age")
    public On lt2(@Comment(name = "column1", value = "数据库中的列名1") String column1,
                  @Comment(name = "column2", value = "数据库中的列名2") String column2) {
        return lt2(true, column1, column2);
    }

    @Override
    @Comment("小于`<`,如：`lt2('t.age', 'u.age') ---> t.age < u.age")
    public On lt2(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                  @Comment(name = "column1", value = "数据库中的列名1") String column1,
                  @Comment(name = "column2", value = "数据库中的列名2") String column2) {
        super.lt2(condition, column1, column2);
        return this;
    }

    @Override
    @Comment("小于等于`<=`,如：`lte('age', 18) ---> age <= 18")
    public On lte(@Comment(name = "column", value = "数据库中的列名") String column,
                  @Comment(name = "value", value = "值") Object value) {
        return lte(true, column, value);
    }

    @Override
    @Comment("小于等于`<=`,如：`lte('age', 18) ---> age <= 18")
    public On lte(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                  @Comment(name = "column", value = "数据库中的列名") String column,
                  @Comment(name = "value", value = "值") Object value) {
        super.lte(condition, column, value);
        return this;
    }

    @Override
    @Comment("小于等于`<=`,如：`lte2('t.age', 'u.age') ---> t.age <= u.age")
    public On lte2(@Comment(name = "column1", value = "数据库中的列名1") String column1,
                   @Comment(name = "column2", value = "数据库中的列名2") String column2) {
        return lte2(true, column1, column2);
    }

    @Override
    @Comment("小于等于`<=`,如：`lte2('t.age', 'u.age') ---> t.age <= u.age")
    public On lte2(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                   @Comment(name = "column1", value = "数据库中的列名1") String column1,
                   @Comment(name = "column2", value = "数据库中的列名2") String column2) {
        super.lte2(condition, column1, column2);
        return this;
    }

    @Override
    @Comment("大于`>`,如：`get('age', 18) ---> age > 18")
    public On gt(@Comment(name = "column", value = "数据库中的列名") String column,
                 @Comment(name = "value", value = "值") Object value) {
        return gt(true, column, value);
    }

    @Override
    @Comment("大于`>`,如：`get('age', 18) ---> age > 18")
    public On gt(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                 @Comment(name = "column", value = "数据库中的列名") String column,
                 @Comment(name = "value", value = "值") Object value) {
        super.gt(condition, column, value);
        return this;
    }

    @Override
    @Comment("大于`>`,如：`get('t.age', 't.age') ---> t.age > u.age")
    public On gt2(@Comment(name = "column1", value = "数据库中的列名1") String column1,
                  @Comment(name = "column2", value = "数据库中的列名2") String column2) {
        return gt2(true, column1, column2);
    }

    @Override
    @Comment("大于`>`,如：`get('t.age', 't.age') ---> t.age > u.age")
    public On gt2(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                  @Comment(name = "column1", value = "数据库中的列名1") String column1,
                  @Comment(name = "column2", value = "数据库中的列名2") String column2) {
        super.gt2(condition, column1, column2);
        return this;
    }

    @Override
    @Comment("大于等于`>=`,如：`get('age', 18) ---> age >= 18")
    public On gte(@Comment(name = "column", value = "数据库中的列名") String column,
                  @Comment(name = "value", value = "值") Object value) {
        return gte(true, column, value);
    }

    @Override
    @Comment("大于等于`>=`,如：`get('age', 18) ---> age >= 18")
    public On gte(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                  @Comment(name = "column", value = "数据库中的列名") String column,
                  @Comment(name = "value", value = "值") Object value) {
        super.gte(condition, column, value);
        return this;
    }

    @Override
    @Comment("大于等于`>=`,如：`get('t.age', 'u.age') ---> t.age >= u.age")
    public On gte2(@Comment(name = "column1", value = "数据库中的列名1") String column1,
                   @Comment(name = "column2", value = "数据库中的列名2") String column2) {
        return gte2(true, column1, column2);
    }

    @Override
    @Comment("大于等于`>=`,如：`get('t.age', 'u.age') ---> t.age >= u.age")
    public On gte2(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                   @Comment(name = "column1", value = "数据库中的列名1") String column1,
                   @Comment(name = "column2", value = "数据库中的列名2") String column2) {
        super.gte2(condition, column1, column2);
        return this;
    }

    @Override
    @Comment("`in`,如：`in('age', [1,2,3]) ---> age in (1,2,3)")
    public On in(@Comment(name = "column", value = "数据库中的列名") String column,
                 @Comment(name = "value", value = "值") Object value) {
        return in(true, column, value);
    }

    @Override
    @Comment("`in`,如：`in('age', [1,2,3]) ---> age in (1,2,3)")
    public On in(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                 @Comment(name = "column", value = "数据库中的列名") String column,
                 @Comment(name = "value", value = "值") Object value) {
        super.in(condition, column, value);
        return this;
    }

    @Override
    @Comment("`not in`,如：`notIn('age', [1,2,3]) ---> age not in (1,2,3)")
    public On notIn(@Comment(name = "column", value = "数据库中的列名") String column,
                    @Comment(name = "value", value = "值") Object value) {
        return notIn(true, column, value);
    }

    @Override
    @Comment("`not in`,如：`notIn('age', [1,2,3]) ---> age not in (1,2,3)")
    public On notIn(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                    @Comment(name = "column", value = "数据库中的列名") String column,
                    @Comment(name = "value", value = "值") Object value) {
        super.notIn(condition, column, value);
        return this;
    }

    @Override
    @Comment("`like`,如：`like('name', '%王%') ---> name like '%王%'")
    public On like(@Comment(name = "column", value = "数据库中的列名") String column,
                   @Comment(name = "value", value = "值") Object value) {
        return like(true, column, value);
    }

    @Override
    @Comment("`like`,如：`like('name', '%王%') ---> name like '%王%'")
    public On like(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                   @Comment(name = "column", value = "数据库中的列名") String column,
                   @Comment(name = "value", value = "值") Object value) {
        super.like(condition, column, value);
        return this;
    }

    @Override
    @Comment("`not like`,如：`notLike('name', '%王%') ---> name not like '%王%'")
    public On notLike(@Comment(name = "column", value = "数据库中的列名") String column,
                      @Comment(name = "value", value = "值") Object value) {
        return notLike(true, column, value);
    }

    @Override
    @Comment("`not like` ,如：`notLike('name', '%王%') ---> name not like '%王%'")
    public On notLike(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                      @Comment(name = "column", value = "数据库中的列名") String column,
                      @Comment(name = "value", value = "值") Object value) {
        super.notLike(condition, column, value);
        return this;
    }

    @Override
    @Comment("`is null`,如：`isNull('name') ---> name is null")
    public On isNull(@Comment(name = "column", value = "数据库中的列名") String column) {
        return isNull(true, column);
    }

    @Override
    @Comment("`is null`,如：`isNull('name') ---> name is null")
    public On isNull(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                     @Comment(name = "column", value = "数据库中的列名") String column) {
        super.isNull(condition, column);
        return this;
    }

    @Override
    @Comment("`is not null`,如：`isNotNull('name') ---> name is not null")
    public On isNotNull(@Comment(name = "column", value = "数据库中的列名") String column) {
        return isNotNull(true, column);
    }

    @Override
    @Comment("`is not null`,如：`isNotNull('name') ---> name is not null")
    public On isNotNull(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                        @Comment(name = "column", value = "数据库中的列名") String column) {
        super.isNotNull(condition, column);
        return this;
    }

    @Override
    @Comment("拼接`or`")
    public On or() {
        super.or();
        return this;
    }

    @Override
    @Comment("拼接`and`")
    public On and() {
        super.and();
        return this;
    }

    @Override
    @Comment("`and`嵌套，如and(it => it.eq('name','李白').ne('status','正常') --> and (name = '李白' and status <> '正常')")
    public On and(@Comment(name = "function", value = "回调函数") Function<Object[], WhereBase> function) {
        return and(true, function);
    }

    @Override
    @Comment("`and`嵌套，如and(it => it.eq('name','李白').ne('status','正常') --> and (name = '李白' and status <> '正常')")
    public On and(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                  @Comment(name = "function", value = "回调函数") Function<Object[], WhereBase> function) {
        super.and(condition, function);
        return this;
    }

    @Override
    @Comment("拼接`order by xxx asc/desc`")
    public On orderBy(@Comment(name = "column", value = "要排序的列") String column,
                         @Comment(name = "sort", value = "`asc`或`desc`") String sort) {

        this.getNamedTable().orderBy(column, sort);
        return this;
    }

    @Override
    @Comment("拼接`order by xxx asc`")
    public On orderBy(@Comment(name = "column", value = "要排序的列") String column) {
        return orderBy(column, "asc");
    }

    @Override
    @Comment("拼接`order by xxx desc`")
    public On orderByDesc(@Comment(name = "column", value = "要排序的列") String column) {
        return orderBy(column, "desc");
    }

    @Override
    @Comment("拼接`group by`")
    public On groupBy(@Comment("要分组的列") String... columns) {
        this.getNamedTable().groupBy(columns);
        return this;
    }

    @Override
    protected String getSql() {
        remove();
        if (isEmpty()) {
            return "";
        }
        return (needWhere ? " on " : "") + String.join(" ", tokens);
    }

    @Override
    @Transient
    @Comment("拼接join")
    public JoinedTable join(boolean condition, String joinType, String tableName, String alias) {
        return getNamedTable().join(condition, joinType, tableName, alias);
    }
}
