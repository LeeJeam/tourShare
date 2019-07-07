package top.ibase4j.core.support;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class Pagination<T> implements Serializable {
    public Pagination() {
        this.offset = NO_ROW_OFFSET;
        this.limit = NO_ROW_LIMIT;
    }

    public Pagination(int current, int size) {
        this(current, size, true);
    }

    public Pagination(int current, int size, boolean searchCount) {
        this(current, size, searchCount, true);
    }

    public Pagination(int current, int size, boolean searchCount, boolean openSort) {
        this.offset = offsetCurrent(current, size);
        this.limit = size;
        if (current > 1) {
            this.current = current;
        }
        this.size = size;
        this.searchCount = searchCount;
        this.openSort = openSort;
    }

    public Pagination(int current, int size, String orderByField) {
        this(current, size);
        this.setOrderByField(orderByField);
    }

    public Pagination(int current, int size, String orderByField, boolean isAsc) {
        this(current, size, orderByField);
        this.setAsc(isAsc);
    }

    public static final int NO_ROW_OFFSET = 0;
    public static final int NO_ROW_LIMIT = Integer.MAX_VALUE;

    private final int offset;
    private final int limit;
    /**
     * 总数
     */
    private int total;

    /**
     * 每页显示条数，默认 10
     */
    private int size = 10;

    /**
     * 总页数
     */
    private int pages;

    /**
     * 当前页
     */
    private int current = 1;

    /**
     * 查询总记录数（默认 true）
     */
    private boolean searchCount = true;

    /**
     * 开启排序（默认 true） 只在代码逻辑判断 并不截取sql分析
     *
     * @see com.baomidou.mybatisplus.mapper.SqlHelper#fillWrapper
     **/
    private boolean openSort = true;

    /**
     * 优化 Count Sql 设置 false 执行 select count(1) from (listSql)
     */
    private boolean optimizeCountSql = true;

    /**
     * <p>
     * SQL 排序 ASC 集合
     * </p>
     */
    private List<String> ascs;
    /**
     * <p>
     * SQL 排序 DESC 集合
     * </p>
     */
    private List<String> descs;

    /**
     * 是否为升序 ASC（ 默认： true ）
     *
     * @see #ascs
     * @see #descs
     */
    private boolean isAsc = true;

    /**
     * <p>
     * SQL 排序 ORDER BY 字段，例如： id DESC（根据id倒序查询）
     * </p>
     * <p>
     * DESC 表示按倒序排序(即：从大到小排序)<br>
     * ASC 表示按正序排序(即：从小到大排序)
     *
     * @see #ascs
     * @see #descs
     * </p>
     */
    private String orderByField;
    /**
     * 查询数据列表
     */
    private List<T> records = Collections.emptyList();

    /**
     * 查询参数（ 不会传入到 xml 层，这里是 Controller 层与 service 层传递参数预留 ）
     */
    private Map<String, Object> condition;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public boolean isSearchCount() {
        return searchCount;
    }

    public void setSearchCount(boolean searchCount) {
        this.searchCount = searchCount;
    }

    public boolean isOpenSort() {
        return openSort;
    }

    public void setOpenSort(boolean openSort) {
        this.openSort = openSort;
    }

    public boolean isOptimizeCountSql() {
        return optimizeCountSql;
    }

    public void setOptimizeCountSql(boolean optimizeCountSql) {
        this.optimizeCountSql = optimizeCountSql;
    }

    public List<String> getAscs() {
        return ascs;
    }

    public void setAscs(List<String> ascs) {
        this.ascs = ascs;
    }

    public List<String> getDescs() {
        return descs;
    }

    public void setDescs(List<String> descs) {
        this.descs = descs;
    }

    public boolean isAsc() {
        return isAsc;
    }

    public void setAsc(boolean isAsc) {
        this.isAsc = isAsc;
    }

    public String getOrderByField() {
        return orderByField;
    }

    public void setOrderByField(String orderByField) {
        this.orderByField = orderByField;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public List<T> getRecords() {
        return records;
    }

    public Pagination<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    @Transient
    public Map<String, Object> getCondition() {
        return condition;
    }

    public Pagination<T> setCondition(Map<String, Object> condition) {
        this.condition = condition;
        return this;
    }

    /**
     * <p>
     * 计算当前分页偏移量
     * </p>
     *
     * @param current 当前页
     * @param size    每页显示数量
     * @return
     */
    public int offsetCurrent(int current, int size) {
        if (current > 0) {
            return (current - 1) * size;
        }
        return 0;
    }

    /**
     * <p>
     * Pagination 分页偏移量
     * </p>
     */
    public int offsetCurrent(Pagination<T> page) {
        if (null == page) {
            return 0;
        }
        return offsetCurrent(page.getCurrent(), page.getSize());
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "offset=" + offset +
                ", limit=" + limit +
                ", total=" + total +
                ", size=" + size +
                ", pages=" + pages +
                ", current=" + current +
                ", records=" + records +
                '}';
    }
}
