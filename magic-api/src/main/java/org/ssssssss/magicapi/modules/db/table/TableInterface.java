package org.ssssssss.magicapi.modules.db.table;

import org.ssssssss.magicapi.modules.db.model.PageResult;
import org.ssssssss.script.annotation.Comment;
import org.ssssssss.script.runtime.RuntimeContext;

import java.beans.Transient;
import java.util.List;
import java.util.Map;

/**
 * 表查询操作接口
 *
 * @author tom
 */
public interface TableInterface {

    @Transient
    NamedTable getNamedTable();

    @Comment("拼接`order by xxx asc/desc`")
    default TableInterface orderBy(@Comment(name = "column", value = "要排序的列") String column,
                                   @Comment(name = "sort", value = "`asc`或`desc`") String sort) {
        this.getNamedTable().orderBy(column, sort);
        return this;
    }

    @Comment("拼接`order by xxx asc`")
    default TableInterface orderBy(@Comment(name = "column", value = "要排序的列") String column) {
        return orderBy(column, "asc");
    }

    @Comment("拼接`order by xxx desc`")
    default TableInterface orderByDesc(@Comment(name = "column", value = "要排序的列") String column) {
        return orderBy(column, "desc");
    }

    @Comment("拼接`group by`")
    default TableInterface groupBy(@Comment("要分组的列") String... columns) {
        this.getNamedTable().groupBy(columns);
        return this;
    }

    @Comment("执行分页查询")
    default PageResult page(RuntimeContext runtimeContext) {
        return getNamedTable().page(runtimeContext);
    }

    @Comment("执行分页查询，分页条件手动传入")
    default PageResult page(RuntimeContext runtimeContext,
                            @Comment(name = "limit", value = "限制条数") long limit,
                            @Comment(name = "offset", value = "跳过条数") long offset) {
        return getNamedTable().page(runtimeContext, limit, offset);
    }

    @Comment("执行select查询")
    default List<Map<String, Object>> select(RuntimeContext runtimeContext) {
        return getNamedTable().select(runtimeContext);
    }

    @Comment("执行selectOne查询")
    default Map<String, Object> selectOne(RuntimeContext runtimeContext) {
        return getNamedTable().selectOne(runtimeContext);
    }

    @Comment("查询条数")
    default int count(RuntimeContext runtimeContext) {
        return getNamedTable().count(runtimeContext);
    }

    @Comment("查询是否存在")
    default boolean exists(RuntimeContext runtimeContext) {
        return getNamedTable().exists(runtimeContext);
    }
}
