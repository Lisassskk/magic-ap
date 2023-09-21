package org.ssssssss.magicapi.modules.db.table;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.ssssssss.script.annotation.Comment;
import org.ssssssss.script.functions.StreamExtension;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * 表API的Where基类
 *
 * @author tom
 */
public class WhereBase {

    protected final List<String> tokens = new ArrayList<>();

    protected final List<Object> params = new ArrayList<>();

    protected final TableBase tableBase;

    protected final boolean needWhere;

    protected boolean notNull = false;

    protected boolean notBlank = false;

    public WhereBase(TableBase tableBase) {
        this(tableBase, true);
    }

    public WhereBase(TableBase tableBase, boolean needWhere) {
        this.tableBase = tableBase;
        this.needWhere = needWhere;
    }

    @Transient
    @Comment("克隆")
    public WhereBase clone() {
        WhereBase whereOrOn = new WhereBase(this.tableBase, this.needWhere);
        whereOrOn.tokens.addAll(this.tokens);
        whereOrOn.params.addAll(this.params);
        whereOrOn.notNull = this.notNull;
        whereOrOn.notBlank = this.notBlank;
        return whereOrOn;
    }

    @Transient
    @Comment("过滤`null`的参数")
    public WhereBase notNull() {
        return notNull(true);
    }

    @Comment("过滤`blank`的参数")
    public WhereBase notBlank() {
        return notBlank(true);
    }

    @Transient
    @Comment("是否过滤`null`的参数")
    WhereBase notNull(boolean flag) {
        notNull = flag;
        return this;
    }

    @Transient
    @Comment("是否过滤`blank`的参数")
    WhereBase notBlank(boolean flag) {
        notBlank = flag;
        return this;
    }

    @Transient
    @Comment("使用逻辑删除")
    WhereBase logic() {
        tableBase.useLogic = true;
        return this;
    }

    @Transient
    @Comment("等于`=`,如：`eq('name', '老王') ---> name = '老王'`")
    WhereBase eq(@Comment(name = "column", value = "数据库中的列名") String column,
                 @Comment(name = "value", value = "值") Object value) {
        return eq(true, column, value);
    }

    @Transient
    @Comment("等于`=`,如：`eq('name', '老王') ---> name = '老王'`")
    WhereBase eq(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                 @Comment(name = "column", value = "数据库中的列名") String column,
                 @Comment(name = "value", value = "值") Object value) {
        if (condition && filterNullAndBlank(value)) {
            tokens.add(tableBase.rowMapColumnMapper.apply(column));
            if (value == null) {
                append(" is null");
            } else {
                params.add(value);
                append(" = ?");
            }
            appendAnd();
        }
        return this;
    }

    @Transient
    @Comment("等于`=`,如：`eq2('t.userId', 'u.id') ---> t.userId = u.id`")
    WhereBase eq2(@Comment(name = "column1", value = "数据库中的列名1") String column1,
                  @Comment(name = "column2", value = "数据库中的列名2") String column2) {
        return eq2(true, column1, column2);
    }

    @Transient
    @Comment("等于`=`,如：`eq2('t.userId', 'u.id') ---> t.userId = u.id`")
    WhereBase eq2(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                  @Comment(name = "column1", value = "数据库中的列名1") String column1,
                  @Comment(name = "column2", value = "数据库中的列名2") String column2) {
        return append2(condition, column1, " = ", column2);
    }

    @Transient
    @Comment("不等于`<>`,如：`ne('name', '老王') ---> name <> '老王'`")
    WhereBase ne(@Comment(name = "column", value = "数据库中的列名") String column,
                 @Comment(name = "value", value = "值") Object value) {
        return ne(true, column, value);
    }

    @Transient
    @Comment("不等于`<>`,如：`ne('name', '老王') ---> name <> '老王'`")
    WhereBase ne(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                 @Comment(name = "column", value = "数据库中的列名") String column,
                 @Comment(name = "value", value = "值") Object value) {
        if (condition && filterNullAndBlank(value)) {
            append(tableBase.rowMapColumnMapper.apply(column));
            if (value == null) {
                append("is not null");
            } else {
                params.add(value);
                append("<> ?");
            }
            appendAnd();
        }
        return this;
    }

    @Transient
    @Comment("不等于`<>`,如：`ne2('t.name', 'u.name') ---> t.name <> u.name`")
    WhereBase ne2(@Comment(name = "column1", value = "数据库中的列名1") String column1,
                  @Comment(name = "column2", value = "数据库中的列名2") String column2) {
        return ne2(true, column1, column2);
    }

    @Transient
    @Comment("不等于`<>`,如：`ne2('t.name', 'u.name') ---> t.name <> u.name`")
    WhereBase ne2(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                  @Comment(name = "column1", value = "数据库中的列名1") String column1,
                  @Comment(name = "column2", value = "数据库中的列名2") String column2) {
        return append2(condition, column1, " <> ", column2);
    }

    @Transient
    @Comment("between('t.createTime', '2023-01-01', '2023-01-31', true, false) ---> t.create_time >= '2023-01-01' and t.create_time < '2023-01-31'")
    WhereBase between(
            @Comment(name = "column", value = "数据库中的列名") String column,
            @Comment(name = "startVal", value = "开始值") Object startVal,
            @Comment(name = "endVal", value = "结束值") Object endVal,
            @Comment(name = "startContain", value = "包含开始值") boolean startContain,
            @Comment(name = "endContain", value = "包含结束值") boolean endContain
    ) {
        return between(true,true,column,startVal,endVal,startContain,endContain);
    }

    @Transient
    @Comment("between(true, true, 't.createTime', '2023-01-01', '2023-01-31') ---> t.create_time >= '2023-01-01' and t.create_time <= '2023-01-31'")
    WhereBase between(
            @Comment(name = "startCon", value = "判断表达式，当为true时拼接条件") boolean startCon,
            @Comment(name = "endCon", value = "判断表达式，当为true时拼接条件") boolean endCon,
            @Comment(name = "column", value = "数据库中的列名") String column,
            @Comment(name = "startVal", value = "开始值") Object startVal,
            @Comment(name = "endVal", value = "结束值") Object endVal
    ) {
        return between(startCon,endCon,column,startVal,endVal,true,true);
    }

    @Transient
    @Comment("between('t.createTime', '2023-01-01', '2023-01-31') ---> t.create_time >= '2023-01-01' and t.create_time <= '2023-01-31'")
    WhereBase between(
            @Comment(name = "column", value = "数据库中的列名") String column,
            @Comment(name = "startVal", value = "开始值") Object startVal,
            @Comment(name = "endVal", value = "结束值") Object endVal
    ) {
        return between(true,true,column,startVal,endVal,true,true);
    }

    @Transient
    @Comment("between(true, true, 't.createTime', '2023-01-01', '2023-01-31', true, false) ---> t.create_time >= '2023-01-01' and t.create_time < '2023-01-31'")
    WhereBase between(
            @Comment(name = "startCon", value = "判断表达式，当为true时拼接条件") boolean startCon,
            @Comment(name = "endCon", value = "判断表达式，当为true时拼接条件") boolean endCon,
            @Comment(name = "column", value = "数据库中的列名") String column,
            @Comment(name = "startVal", value = "开始值") Object startVal,
            @Comment(name = "endVal", value = "结束值") Object endVal,
            @Comment(name = "startContain", value = "包含开始值") boolean startContain,
            @Comment(name = "endContain", value = "包含结束值") boolean endContain
    ) {
        append(startCon,column,startContain ? ">=" : ">",startVal).append(endCon,column,endContain ? "<=" : "<",endVal);
        return this;
    }

    @Transient
    @Comment("等于`=`,如：`notExists(true, 'table_name', 'tn', namedWhere -> namedWhere.eq2('tn.aId','a.id').eq('tn.name','老王'), true) ---> not exists(select 1 from table_name as tn namedWhere tn.a_id=a.id and tn.user_id='老王' and tn.del_flag<>1`")
    WhereBase notExists(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                        @Comment(name = "tableName", value = "表名") String tableName,
                        @Comment(name = "alias", value = "别名") String alias,
                        @Comment(name = "function", value = "关联条件") Function<WhereBase, WhereBase> function,
                        @Comment(name = "useLogic", value = "使用逻辑删除") boolean useLogic) {
        return exists(true, condition, tableName, alias, function, useLogic);
    }

    @Transient
    @Comment("等于`=`,如：`notExists('table_name', 'tn', namedWhere -> namedWhere.eq2('tn.aId','a.id').eq('tn.name','老王'), true) ---> not exists(select 1 from table_name as tn namedWhere tn.a_id=a.id and tn.user_id='老王' and tn.del_flag<>1`")
    WhereBase notExists(@Comment(name = "tableName", value = "表名") String tableName,
                        @Comment(name = "alias", value = "别名") String alias,
                        @Comment(name = "function", value = "关联条件") Function<WhereBase, WhereBase> function,
                        @Comment(name = "useLogic", value = "使用逻辑删除") boolean useLogic) {
        return notExists(true, tableName, alias, function, useLogic);
    }

    @Transient
    @Comment("等于`=`,如：`notExists(true, 'table_name', 'tn', namedWhere -> namedWhere.eq2('tn.aId','a.id').eq('tn.name','老王')) ---> not exists(select 1 from table_name as tn namedWhere tn.a_id=a.id and tn.user_id='老王'`")
    WhereBase notExists(@Comment(name = "tableName", value = "表名") String tableName,
                        @Comment(name = "alias", value = "别名") String alias,
                        @Comment(name = "function", value = "关联条件") Function<WhereBase, WhereBase> function) {
        return notExists(true, tableName, alias, function, false);
    }

    @Transient
    @Comment("等于`=`,如：`exists(true, 'table_name', 'tn', namedWhere -> namedWhere.eq2('tn.aId','a.id').eq('tn.name','老王'), true) ---> exists(select 1 from table_name as tn namedWhere tn.a_id=a.id and tn.user_id='老王' and tn.del_flag<>1`")
    WhereBase exists(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                     @Comment(name = "tableName", value = "表名") String tableName,
                     @Comment(name = "alias", value = "别名") String alias,
                     @Comment(name = "function", value = "关联条件") Function<WhereBase, WhereBase> function,
                     @Comment(name = "useLogic", value = "使用逻辑删除") boolean useLogic) {
        return exists(false, condition, tableName, alias, function, useLogic);
    }

    @Transient
    @Comment("等于`=`,如：`exists('table_name', 'tn', namedWhere -> namedWhere.eq2('tn.aId','a.id').eq('tn.name','老王'), true) ---> exists(select 1 from table_name as tn namedWhere tn.a_id=a.id and tn.user_id='老王' and tn.del_flag<>1`")
    WhereBase exists(@Comment(name = "tableName", value = "表名") String tableName,
                     @Comment(name = "alias", value = "别名") String alias,
                     @Comment(name = "function", value = "关联条件") Function<WhereBase, WhereBase> function,
                     @Comment(name = "useLogic", value = "使用逻辑删除") boolean useLogic) {
        return exists(true, tableName, alias, function, useLogic);
    }

    @Transient
    @Comment("等于`=`,如：`exists('table_name', 'tn', namedWhere -> namedWhere.eq2('tn.aId','a.id').eq('tn.name','老王')) ---> exists(select 1 from table_name as tn namedWhere tn.a_id=a.id and tn.user_id='老王'`")
    WhereBase exists(@Comment(name = "tableName", value = "表名") String tableName,
                     @Comment(name = "alias", value = "别名") String alias,
                     @Comment(name = "function", value = "关联条件") Function<WhereBase, WhereBase> function) {
        return exists(true, tableName, alias, function, false);
    }

    @Comment("存在子查询")
    WhereBase exists(boolean notFlag, boolean condition, String tableName, String alias,
                     Function<WhereBase, WhereBase> function,
                     boolean useLogic) {
        if (condition) {
            if (notFlag) {
                append("not");
            }
            append("exists(select 1 from");
            append(tableName);
            if (StringUtils.isNotEmpty(alias)) {
                append("as");
                append(alias);
            }
            WhereBase whereBase = new WhereBase(tableBase);
            function.apply(whereBase);
            if (!whereBase.isEmpty()) {
                whereBase.and();
            }
            whereBase.ne(useLogic,
                    (StringUtils.isNotEmpty(alias) ? (alias + ".") : "") + tableBase.logicDeleteColumn,
                    tableBase.logicDeleteValue);
            append(whereBase.getSql());
            params.addAll(whereBase.getParams());
            append(")");
            appendAnd();
        }
        return this;
    }

    @Transient
    @Comment("小于`<`,如：`lt('age', 18) ---> age < 18")
    WhereBase lt(@Comment(name = "column", value = "数据库中的列名") String column,
                 @Comment(name = "value", value = "值") Object value) {
        return lt(true, column, value);
    }

    @Transient
    @Comment("小于`<`,如：`lt('age', 18) ---> age < 18")
    WhereBase lt(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                 @Comment(name = "column", value = "数据库中的列名") String column,
                 @Comment(name = "value", value = "值") Object value) {
        return append(condition, column, " < ?", value);
    }

    @Transient
    @Comment("小于`<`,如：`lt2('t.age', 'u.age') ---> t.age < u.age")
    WhereBase lt2(@Comment(name = "column1", value = "数据库中的列名1") String column1,
                  @Comment(name = "column2", value = "数据库中的列名2") String column2) {
        return lt2(true, column1, column2);
    }

    @Transient
    @Comment("小于`<`,如：`lt2('t.age', 'u.age') ---> t.age < u.age")
    WhereBase lt2(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                  @Comment(name = "column1", value = "数据库中的列名1") String column1,
                  @Comment(name = "column2", value = "数据库中的列名2") String column2) {
        return append2(condition, column1, " < ", column2);
    }

    @Transient
    @Comment("小于等于`<=`,如：`lte('age', 18) ---> age <= 18")
    WhereBase lte(@Comment(name = "column", value = "数据库中的列名") String column,
                  @Comment(name = "value", value = "值") Object value) {
        return lte(true, column, value);
    }

    @Transient
    @Comment("小于等于`<=`,如：`lte('age', 18) ---> age <= 18")
    WhereBase lte(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                  @Comment(name = "column", value = "数据库中的列名") String column,
                  @Comment(name = "value", value = "值") Object value) {
        return append(condition, column, " <= ?", value);
    }

    @Transient
    @Comment("小于等于`<=`,如：`lte2('t.age', 'u.age') ---> t.age <= u.age")
    WhereBase lte2(@Comment(name = "column1", value = "数据库中的列名1") String column1,
                   @Comment(name = "column2", value = "数据库中的列名2") String column2) {
        return lte2(true, column1, column2);
    }

    @Transient
    @Comment("小于等于`<=`,如：`lte2('t.age', 'u.age') ---> t.age <= u.age")
    WhereBase lte2(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                   @Comment(name = "column1", value = "数据库中的列名1") String column1,
                   @Comment(name = "column2", value = "数据库中的列名2") String column2) {
        return append2(condition, column1, " <= ", column2);
    }

    @Transient
    @Comment("大于`>`,如：`get('age', 18) ---> age > 18")
    WhereBase gt(@Comment(name = "column", value = "数据库中的列名") String column,
                 @Comment(name = "value", value = "值") Object value) {
        return gt(true, column, value);
    }

    @Transient
    @Comment("大于`>`,如：`get('age', 18) ---> age > 18")
    WhereBase gt(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                 @Comment(name = "column", value = "数据库中的列名") String column,
                 @Comment(name = "value", value = "值") Object value) {
        return append(condition, column, " > ?", value);
    }

    @Transient
    @Comment("大于`>`,如：`get('t.age', 't.age') ---> t.age > u.age")
    WhereBase gt2(@Comment(name = "column1", value = "数据库中的列名1") String column1,
                  @Comment(name = "column2", value = "数据库中的列名2") String column2) {
        return gt2(true, column1, column2);
    }

    @Transient
    @Comment("大于`>`,如：`get('t.age', 't.age') ---> t.age > u.age")
    WhereBase gt2(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                  @Comment(name = "column1", value = "数据库中的列名1") String column1,
                  @Comment(name = "column2", value = "数据库中的列名2") String column2) {
        return append2(condition, column1, " > ", column2);
    }

    @Transient
    @Comment("大于等于`>=`,如：`get('age', 18) ---> age >= 18")
    WhereBase gte(@Comment(name = "column", value = "数据库中的列名") String column,
                  @Comment(name = "value", value = "值") Object value) {
        return gte(true, column, value);
    }

    @Transient
    @Comment("大于等于`>=`,如：`get('age', 18) ---> age >= 18")
    WhereBase gte(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                  @Comment(name = "column", value = "数据库中的列名") String column,
                  @Comment(name = "value", value = "值") Object value) {
        return append(condition, column, " >= ?", value);
    }

    @Transient
    @Comment("大于等于`>=`,如：`get('t.age', 'u.age') ---> t.age >= u.age")
    WhereBase gte2(@Comment(name = "column1", value = "数据库中的列名1") String column1,
                   @Comment(name = "column2", value = "数据库中的列名2") String column2) {
        return gte2(true, column1, column2);
    }

    @Transient
    @Comment("大于等于`>=`,如：`get('t.age', 'u.age') ---> t.age >= u.age")
    WhereBase gte2(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                   @Comment(name = "column1", value = "数据库中的列名1") String column1,
                   @Comment(name = "column2", value = "数据库中的列名2") String column2) {
        return append2(condition, column1, " >= ", column2);
    }

    @Transient
    @Comment("`in`,如：`in('age', [1,2,3]) ---> age in (1,2,3)")
    WhereBase in(@Comment(name = "column", value = "数据库中的列名") String column,
                 @Comment(name = "value", value = "值") Object value) {
        return in(true, column, value);
    }

    @Transient
    @Comment("`in`,如：`in('age', [1,2,3]) ---> age in (1,2,3)")
    WhereBase in(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                 @Comment(name = "column", value = "数据库中的列名") String column,
                 @Comment(name = "value", value = "值") Object value) {
        if (condition && value != null) {
            List<Object> objects = StreamExtension.arrayLikeToList(value);
            if (!objects.isEmpty()) {
                append(tableBase.rowMapColumnMapper.apply(column));
                append(" in (");
                append(String.join(",", Collections.nCopies(objects.size(), "?")));
                append(")");
                appendAnd();
                params.addAll(objects);
            }
        }
        return this;
    }

    @Transient
    @Comment("`not in`,如：`notIn('age', [1,2,3]) ---> age not in (1,2,3)")
    WhereBase notIn(@Comment(name = "column", value = "数据库中的列名") String column,
                    @Comment(name = "value", value = "值") Object value) {
        return notIn(true, column, value);
    }

    @Transient
    @Comment("`not in`,如：`notIn('age', [1,2,3]) ---> age not in (1,2,3)")
    WhereBase notIn(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                    @Comment(name = "column", value = "数据库中的列名") String column,
                    @Comment(name = "value", value = "值") Object value) {
        if (condition && value != null) {
            List<Object> objects = StreamExtension.arrayLikeToList(value);
            if (!objects.isEmpty()) {
                append(tableBase.rowMapColumnMapper.apply(column));
                append("not in (");
                append(String.join(",", Collections.nCopies(objects.size(), "?")));
                append(")");
                appendAnd();
                params.addAll(objects);
            }
        }
        return this;
    }

    @Transient
    @Comment("`like`,如：`like('name', '%王%') ---> name like '%王%'")
    WhereBase like(@Comment(name = "column", value = "数据库中的列名") String column,
                   @Comment(name = "value", value = "值") Object value) {
        return like(true, column, value);
    }

    @Transient
    @Comment("`like`,如：`like('name', '%王%') ---> name like '%王%'")
    WhereBase like(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                   @Comment(name = "column", value = "数据库中的列名") String column,
                   @Comment(name = "value", value = "值") Object value) {
        return append(condition, column, "like ?", value);
    }

    @Transient
    @Comment("`not like`,如：`notLike('name', '%王%') ---> name not like '%王%'")
    WhereBase notLike(@Comment(name = "column", value = "数据库中的列名") String column,
                      @Comment(name = "value", value = "值") Object value) {
        return notLike(true, column, value);
    }

    @Transient
    @Comment("`not like` ,如：`notLike('name', '%王%') ---> name not like '%王%'")
    WhereBase notLike(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                      @Comment(name = "column", value = "数据库中的列名") String column,
                      @Comment(name = "value", value = "值") Object value) {
        return append(condition, column, "not like ?", value);
    }

    @Transient
    @Comment("`is null`,如：`isNull('name') ---> name is null")
    WhereBase isNull(@Comment(name = "column", value = "数据库中的列名") String column) {
        return isNull(true, column);
    }

    @Transient
    @Comment("`is null`,如：`isNull('name') ---> name is null")
    WhereBase isNull(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                     @Comment(name = "column", value = "数据库中的列名") String column) {
        if (condition) {
            append(tableBase.rowMapColumnMapper.apply(column));
            append("is null");
            appendAnd();
        }
        return this;
    }

    @Transient
    @Comment("`is not null`,如：`isNotNull('name') ---> name is not null")
    WhereBase isNotNull(@Comment(name = "column", value = "数据库中的列名") String column) {
        return isNotNull(true, column);
    }

    @Transient
    @Comment("`is not null`,如：`isNotNull('name') ---> name is not null")
    WhereBase isNotNull(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                        @Comment(name = "column", value = "数据库中的列名") String column) {
        if (condition) {
            append(tableBase.rowMapColumnMapper.apply(column));
            append("is not null");
            appendAnd();
        }
        return this;
    }

    @Transient
    @Comment("拼接`or`")
    WhereBase or() {
        appendOr();
        return this;
    }

    @Transient
    @Comment("拼接`and`")
    WhereBase and() {
        appendAnd();
        return this;
    }

    @Transient
    @Comment("`and`嵌套，如and(it => it.eq('name','李白').ne('status','正常') --> and (name = '李白' and status <> '正常')")
    WhereBase and(@Comment(name = "function", value = "回调函数") Function<Object[], WhereBase> function) {
        return and(true, function);
    }

    @Transient
    @Comment("`and`嵌套，如and(it => it.eq('name','李白').ne('status','正常') --> and (name = '李白' and status <> '正常')")
    WhereBase and(@Comment(name = "condition", value = "判断表达式，当为true时拼接条件") boolean condition,
                  @Comment(name = "function", value = "回调函数") Function<Object[], WhereBase> function) {
        if (condition) {
            WhereBase expr = function.apply(new Object[]{new WhereBase(this.tableBase, false)});
            this.params.addAll(expr.params);
            String sql = expr.getSql();
            if (StringUtils.isNotBlank(sql)) {
                append("(");
                append(sql);
                append(")");
                appendAnd();
            }
        }
        return this;
    }

    @Transient
    void appendAnd() {
        remove();
        tokens.add("and");
    }

    @Transient
    void appendOr() {
        remove();
        tokens.add("or");
    }

    List<Object> getParams() {
        return params;
    }

    void remove() {
        int size = tokens.size();
        while (size > 0) {
            String token = tokens.get(size - 1);
            if ("and".equalsIgnoreCase(token) || "or".equalsIgnoreCase(token)) {
                tokens.remove(size - 1);
                size--;
            } else {
                break;
            }
        }
        while (size > 0) {
            String token = tokens.get(0);
            if ("and".equalsIgnoreCase(token) || "or".equalsIgnoreCase(token)) {
                tokens.remove(0);
                size--;
            } else {
                break;
            }
        }
    }

    boolean isEmpty() {
        return tokens.isEmpty();
    }

    @Transient
    void append(String value) {
        tokens.add(value);
    }

    @Transient
    void append(String sql, Object value) {
        tokens.add(sql);
        params.add(value);
    }

    protected String getSql() {
        throw new NotImplementedException("获取sql方法未实现！");
    }

    boolean filterNullAndBlank(Object value) {
        if (notNull && value == null) {
            return false;
        }
        return !notBlank || !StringUtils.isEmpty(Objects.toString(value, ""));
    }

    WhereBase append(boolean append, String column, String condition, Object value) {
        if (append && filterNullAndBlank(value)) {
            append(tableBase.rowMapColumnMapper.apply(column));
            append(condition);
            appendAnd();
            params.add(value);
        }
        return this;
    }

    WhereBase append2(boolean append, String column1, String condition, String column2) {
        if (append) {
            append(tableBase.rowMapColumnMapper.apply(column1));
            append(condition);
            append(tableBase.rowMapColumnMapper.apply(column2));
            appendAnd();
        }
        return this;
    }
}
