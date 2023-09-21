package org.ssssssss.magicapi.modules.db.table;

import org.apache.commons.lang3.StringUtils;
import org.ssssssss.magicapi.core.model.Attributes;
import org.ssssssss.magicapi.modules.db.SQLModule;
import org.ssssssss.magicapi.modules.db.inteceptor.NamedTableInterceptor;
import org.ssssssss.script.annotation.Comment;

import java.beans.Transient;
import java.util.List;
import java.util.function.Function;

/**
 * 表操作基类
 *
 * @author tom
 */
public class TableBase extends Attributes<Object> {

    String tableName;

    String alias;

    SQLModule sqlModule;

    String primary;

    String logicDeleteColumn;

    Object logicDeleteValue;

    boolean useLogic = false;

    Function<String, String> rowMapColumnMapper;

    List<NamedTableInterceptor> namedTableInterceptors;

    public TableBase(String tableName, String alias, SQLModule sqlModule, Function<String, String> rowMapColumnMapper,
                     List<NamedTableInterceptor> namedTableInterceptors) {
        this.tableName = tableName;
        this.alias = alias;
        this.sqlModule = sqlModule;
        this.rowMapColumnMapper = rowMapColumnMapper;
        this.namedTableInterceptors = namedTableInterceptors;
        this.logicDeleteColumn = sqlModule.getLogicDeleteColumn();
        String deleteValue = sqlModule.getLogicDeleteValue();
        this.logicDeleteValue = deleteValue;
        if (deleteValue != null) {
            boolean isString = deleteValue.startsWith("'") || deleteValue.startsWith("\"");
            if (isString && deleteValue.length() > 2) {
                this.logicDeleteValue = deleteValue.substring(1, deleteValue.length() - 1);
            } else {
                try {
                    this.logicDeleteValue = Integer.parseInt(deleteValue);
                } catch (NumberFormatException e) {
                    this.logicDeleteValue = deleteValue;
                }
            }
        }
    }

    protected TableBase() {
    }

    @Override
    @Comment("克隆")
    public TableBase clone() {
        TableBase tableBase = new TableBase();
        tableBase.tableName = this.tableName;
        tableBase.alias = this.alias;
        tableBase.sqlModule = this.sqlModule;
        tableBase.primary = this.primary;
        tableBase.logicDeleteValue = this.logicDeleteValue;
        tableBase.logicDeleteColumn = this.logicDeleteColumn;
        tableBase.rowMapColumnMapper = this.rowMapColumnMapper;
        tableBase.namedTableInterceptors = this.namedTableInterceptors;
        tableBase.properties = this.properties;
        return tableBase;
    }


    /**
     * 获取别名
     *
     * @return 名
     */
    @Transient
    String getAlisName(String name) {
        if (StringUtils.isNotEmpty(alias)) {
            return name + DbConstant.AS + alias;
        }
        return name;
    }


    /**
     * 获取别名
     *
     * @return 名
     */
    @Transient
    String withAlis(String name) {
        if (StringUtils.isNotEmpty(alias)) {
            return alias + DbConstant.DOT + name;
        }
        return name;
    }


    /**
     * 获取查询的表名
     *
     * @return 表名
     */
    @Transient
    public String getTableName() {
        return tableName;
    }

    /**
     * 设置表名
     *
     * @param tableName 表名
     */
    @Transient
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * 获取SQL模块
     */
    @Transient
    public SQLModule getSqlModule() {
        return sqlModule;
    }

    /**
     * 获取主键列
     */
    @Transient
    public String getPrimary() {
        return primary;
    }

    /**
     * 获取逻辑删除列
     */
    @Transient
    public String getLogicDeleteColumn() {
        return logicDeleteColumn;
    }

    /**
     * 获取逻辑删除值
     */
    @Transient
    public Object getLogicDeleteValue() {
        return logicDeleteValue;
    }

    /**
     * 是否设逻辑了逻辑删除
     */
    @Transient
    public boolean isUseLogic() {
        return useLogic;
    }

    /**
     * 设置是否使用逻辑删除
     */
    @Transient
    public void setUseLogic(boolean useLogic) {
        this.useLogic = useLogic;
    }
}
