package top.ibase4j.core.base;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.enums.SqlMethod;
import com.baomidou.mybatisplus.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.toolkit.ReflectionKit;
import com.codingapi.tx.annotation.TxTransaction;
import org.apache.commons.lang3.RandomUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.support.dbcp.HandleDataSource;
import top.ibase4j.core.util.DataUtil;
import top.ibase4j.core.util.InstanceUtil;
import top.ibase4j.core.util.PropertiesUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 业务逻辑层基类实现类
 *
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:19:19
 */
public abstract class BaseServiceImpl<T extends BaseModel, M extends BaseMapper<T>> implements BaseService<T> {
    protected static Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);
    @Autowired
    protected M mapper;

    private int maxThread = PropertiesUtil.getInt("db.reader.list.maxThread", 50);
    private int threadSleep = PropertiesUtil.getInt("db.reader.list.threadWait", 5);
    private ExecutorService executorService = Executors.newFixedThreadPool(maxThread);

    /**
     * 分页查询参数封装
     */
    @SuppressWarnings({"unchecked"})
    public static Page<Long> getPage(Map<String, Object> params) {
        int current = 1;
        int size = 10;
        String orderBy = "id_", sortAsc = null, openSort = "Y";
        if (DataUtil.isNotEmpty(params.get("pageNumber"))) {
            current = Integer.parseInt(params.get("pageNumber").toString());
        }
        if (DataUtil.isNotEmpty(params.get("pageIndex"))) {
            current = Integer.parseInt(params.get("pageIndex").toString());
        }
        if (DataUtil.isNotEmpty(params.get("pageSize"))) {
            size = Integer.parseInt(params.get("pageSize").toString());
        }
        if (DataUtil.isNotEmpty(params.get("limit"))) {
            size = Integer.parseInt(params.get("limit").toString());
        }
        if (DataUtil.isNotEmpty(params.get("offset"))) {
            current = Integer.valueOf(params.get("offset").toString()) / size + 1;
        }
        if (DataUtil.isNotEmpty(params.get("sort"))) {
            orderBy = (String) params.get("sort");
            params.remove("sort");
        }
        if (DataUtil.isNotEmpty(params.get("orderBy"))) {
            orderBy = (String) params.get("orderBy");
            params.remove("orderBy");
        }
        if (DataUtil.isNotEmpty(params.get("sortAsc"))) {
            sortAsc = (String) params.get("sortAsc");
            params.remove("sortAsc");
        }
        if (DataUtil.isNotEmpty(params.get("openSort"))) {
            openSort = (String) params.get("openSort");
            params.remove("openSort");
        }
        Object filter = params.get("filter");
        if (filter != null) {
            params.putAll(JSON.parseObject(filter.toString(), Map.class));
        }
        if (size == -1) {
            Page<Long> page = new Page<>();
            page.setOrderByField(orderBy);
            page.setAsc("Y".equals(sortAsc));
            page.setOpenSort("Y".equals(openSort));
            return page;
        }
        Page<Long> page = new Page<>(current, size, orderBy);
        page.setAsc("Y".equals(sortAsc));
        page.setOpenSort("Y".equals(openSort));
        return page;
    }

    @Override
    @Transactional
    public void del(List<Long> ids, Long userId) {
        for (Long id : ids) {
            del(id, userId);
        }
    }

    @Override
    @Transactional
    public void del(Long id, Long userId) {
        try {
            T record = this.getById(id);
            record.setEnable(0);
            record.setUpdateTime(new Date());
            record.setUpdateBy(userId);
            mapper.updateById(record);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        try {
            mapper.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public Integer deleteByEntity(T t) {
        Wrapper<T> wrapper = new EntityWrapper<T>(t);
        return mapper.delete(wrapper);
    }

    @Override
    @Transactional
    public Integer deleteByMap(Map<String, Object> columnMap) {
        return mapper.deleteByMap(columnMap);
    }

    /**
     * 根据Id查询(默认类型Map)
     */
    public Pagination<Map<String, Object>> getPageMap(final Page<Long> ids) {
        if (ids != null) {
            Pagination<Map<String, Object>> page = new Pagination<>(ids.getCurrent(), ids.getSize());
            page.setTotal(ids.getTotal());
            final List<Map<String, Object>> records = InstanceUtil.newArrayList();
            for (int i = 0; i < ids.getRecords().size(); i++) {
                records.add(null);
            }
            final Map<Integer, Object> thread = InstanceUtil.newConcurrentHashMap();
            final String datasource = HandleDataSource.getDataSource();
            for (int i = 0; i < ids.getRecords().size(); i++) {
                final int index = i;
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        HandleDataSource.putDataSource(datasource);
                        try {
                            records.set(index, InstanceUtil.transBean2Map(getById(ids.getRecords().get(index))));
                        } finally {
                            thread.put(index, 0);
                        }
                    }
                });
            }
            while (thread.size() < records.size()) {
                try {
                    Thread.sleep(threadSleep);
                } catch (InterruptedException e) {
                    logger.error("", e);
                }
            }
            page.setRecords(records);
            return page;
        }
        return new Pagination<>();
    }

    @Override
    public Pagination<T> query(Map<String, Object> params) {
        Page<Long> page = getPage(params);
        page.setRecords(mapper.selectIdPage(page, params));
        return getPage(page);
    }

    @Override
    public List<T> queryList(Map<String, Object> params) {
        if (DataUtil.isEmpty(params.get("orderBy"))) {
            params.put("orderBy", "id_");
        }
        List<Long> ids = mapper.selectIdPage(params);
        List<T> list = queryList(ids);
        return list;
    }

    @Override
    public T queryById(Long id) {
        return getById(id);
    }

    @Override
    public List<T> queryList(T params) {
        List<Long> ids = mapper.selectIdPage(params);
        List<T> list = queryList(ids);
        return list;
    }

    @Override
    public List<T> queryList(final List<Long> ids) {
        List<T> list = InstanceUtil.newArrayList();
        EntityWrapper<T> wrapper = new EntityWrapper<>();
        if (ids == null) {
            return list;
        } else if (ids.size() == 1) {
            T record = getById(ids.get(0));
            list.add(record);
        } else {
            wrapper.in("id_", ids);
            list = mapper.selectList(wrapper);
        }
        return list;
    }

    @Override
    public <K> List<K> queryList(final List<Long> ids, final Class<K> cls) {
        final List<K> list = InstanceUtil.newArrayList();
        EntityWrapper<T> wrapper = new EntityWrapper<>();
        if (ids == null) {
            return list;
        } else if (ids.size() == 1) {
            T t = getById(ids.get(0));
            K k = InstanceUtil.to(t, cls);
            list.add(k);
        } else {
            wrapper.in("id_", ids);
            List<T> tList = mapper.selectList(wrapper);
            tList.stream().map(t -> InstanceUtil.to(t, cls)).forEach(list::add);
        }
        return list;
    }

    @Override
    public T selectOne(T entity) {
        T t = mapper.selectOne(entity);
        return t;
    }

    @Override
    @Transactional
    @TxTransaction
    public T update(T record) {
        record.setUpdateTime(new Date());
        if (record.getId() == null) {
            record.setCreateTime(new Date());
            mapper.insert(record);
        } else {
            mapper.updateById(record);
        }
        return record;
    }

    @Override
    @Transactional
    public T updateAllColumn(T record) {
        record.setUpdateTime(new Date());
        if (record.getId() == null) {
            record.setCreateTime(new Date());
            mapper.insert(record);
        } else {
            mapper.updateAllColumnById(record);
        }
        return record;
    }

    @Override
    @Transactional
    public boolean updateAllColumnBatch(List<T> entityList) {
        return updateAllColumnBatch(entityList, 30);
    }

    @Override
    @Transactional
    public boolean updateAllColumnBatch(List<T> entityList, int batchSize) {
        return updateBatch(entityList, batchSize, false);
    }

    @Override
    @Transactional
    @TxTransaction
    public boolean updateBatch(List<T> entityList) {
        return updateBatch(entityList, 30);
    }

    @Override
    @Transactional
    public boolean updateBatch(List<T> entityList, int batchSize) {
        return updateBatch(entityList, batchSize, true);
    }

    @SuppressWarnings("unchecked")
    protected Class<T> currentModelClass() {
        return ReflectionKit.getSuperClassGenricType(getClass(), 0);
    }

    /**
     * @param params
     * @param cls
     * @return
     */
    protected <P> Pagination<P> query(Map<String, Object> params, Class<P> cls) {
        Page<Long> page = getPage(params);
        page.setRecords(mapper.selectIdPage(page, params));
        return getPage(page, cls);
    }

    /**
     * @param millis
     */
    protected void sleep(int millis) {
        try {
            Thread.sleep(RandomUtils.nextLong(10, millis));
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    /**
     * <p>
     * 批量操作 SqlSession
     * </p>
     */
    protected SqlSession sqlSessionBatch() {
        return SqlHelper.sqlSessionBatch(currentModelClass());
    }

    /**
     * 获取SqlStatement
     *
     * @param sqlMethod
     * @return
     */
    protected String sqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethod.getMethod());
    }

    /**
     * 根据Id查询(默认类型T)
     */
    private T getById(Long id) {
        return mapper.selectById(id);
    }

    /**
     * 根据Id批量查询(默认类型T)
     */
    protected Pagination<T> getPage(final Page<Long> ids) {
        if (ids != null) {
            Pagination<T> page = new Pagination<T>(ids.getCurrent(), ids.getSize());
            page.setTotal(ids.getTotal());
            final List<T> records = InstanceUtil.newArrayList();
            for (int i = 0; i < ids.getRecords().size(); i++) {
                records.add(null);
            }
            final Map<Integer, Object> thread = InstanceUtil.newConcurrentHashMap();
            final String datasource = HandleDataSource.getDataSource();
            for (int i = 0; i < ids.getRecords().size(); i++) {
                final int index = i;
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        HandleDataSource.putDataSource(datasource);
                        try {
                            records.set(index, getById(ids.getRecords().get(index)));
                        } finally {
                            thread.put(index, 0);
                        }
                    }
                });
            }
            while (thread.size() < records.size()) {
                try {
                    Thread.sleep(threadSleep);
                } catch (InterruptedException e) {
                    logger.error("", e);
                }
            }
            page.setRecords(records);
            return page;
        }
        return new Pagination<T>();
    }

    /**
     * 根据Id查询(cls返回类型Class)
     */
    private <K> Pagination<K> getPage(final Page<Long> ids, final Class<K> cls) {
        if (ids != null) {
            Pagination<K> page = new Pagination<K>(ids.getCurrent(), ids.getSize());
            page.setTotal(ids.getTotal());
            final List<K> records = InstanceUtil.newArrayList();
            for (int i = 0; i < ids.getRecords().size(); i++) {
                records.add(null);
            }
            final Map<Integer, Object> thread = InstanceUtil.newConcurrentHashMap();
            final String datasource = HandleDataSource.getDataSource();
            for (int i = 0; i < ids.getRecords().size(); i++) {
                final int index = i;
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        HandleDataSource.putDataSource(datasource);
                        try {
                            T t = getById(ids.getRecords().get(index));
                            K k = InstanceUtil.to(t, cls);
                            records.set(index, k);
                        } finally {
                            thread.put(index, 0);
                        }
                    }
                });
            }
            while (thread.size() < records.size()) {
                try {
                    Thread.sleep(threadSleep);
                } catch (InterruptedException e) {
                    logger.error("", e);
                }
            }
            page.setRecords(records);
            return page;
        }
        return new Pagination<K>();
    }

    private boolean updateBatch(List<T> entityList, int batchSize, boolean selective) {
        if (CollectionUtils.isEmpty(entityList)) {
            throw new IllegalArgumentException("Error: entityList must not be empty");
        }
        try (SqlSession batchSqlSession = sqlSessionBatch()) {
            for (int i = 0; i < entityList.size(); i++) {
                if (selective) {
                    update(entityList.get(i));
                } else {
                    updateAllColumn(entityList.get(i));
                }
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
            }
            batchSqlSession.flushStatements();
        } catch (Throwable e) {
            throw new MybatisPlusException("Error: Cannot execute insertOrUpdateBatch Method. Cause", e);
        }
        return true;
    }

    public List<T> updateBatchThenReturn(List<T> entityList) {
        updateBatch(entityList, 30, true);
        return entityList;
    }

    public Integer getTotalCount() {
        EntityWrapper<T> ew = new EntityWrapper<>();
        ew.eq("enable_", 1);
        return mapper.selectCount(ew);
    }

}
