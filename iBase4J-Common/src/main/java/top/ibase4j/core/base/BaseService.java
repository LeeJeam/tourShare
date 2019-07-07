package top.ibase4j.core.base;

import top.ibase4j.core.support.Pagination;

import java.util.List;
import java.util.Map;

/**
 * 业务逻辑层基类接口
 *
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:19:19
 */
public interface BaseService<T extends BaseModel> {

    /**
     * 修改/新增
     * @param record
     * @return
     */
    T update(T record);

    /**
     * 逻辑批量删除
     * @param ids
     * @param userId
     */
    void del(List<Long> ids, Long userId);

    /**
     * 逻辑删除单条
     * @param id
     * @param userId
     */
    void del(Long id, Long userId);

    /**
     * 物理删除单条
     * @param id
     */
    void delete(Long id);

    /**
     * 物理删除单条
     * @param t
     * @return
     */
    Integer deleteByEntity(T t);

    /**
     * 物理删除
     * @param columnMap
     * @return
     */
    Integer deleteByMap(Map<String, Object> columnMap);

    /**
     * 根据id查询实体
     * @param id
     * @return
     */
    T queryById(Long id);

    /**
     * 根据参数分页查询
     * @param params
     * @return
     */
    Pagination<T> query(Map<String, Object> params);

    /**
     * 根据参数查询列表
     * @param params
     * @return
     */
    List<T> queryList(Map<String, Object> params);

    /**
     * 根据Id查询列表(默认类型T)
     * @param ids
     * @return
     */
    List<T> queryList(List<Long> ids);

    /**
     * 根据Id查询列表(cls返回类型Class)
     * @param ids
     * @param cls
     * @param <K>
     * @return
     */
    <K> List<K> queryList(List<Long> ids, Class<K> cls);

    /**
     * 根据实体参数查询
     * @param entity
     * @return
     */
    List<T> queryList(T entity);

    /**
     * 根据实体参数查询一条记录
     * @param entity
     * @return
     */
    T selectOne(T entity);

    /**
     * 修改所有字段/新增
     * @param record
     * @return
     */
    T updateAllColumn(T record);

    /**
     * 批量修改所有字段/新增
     * @param entityList
     * @return
     */
    boolean updateAllColumnBatch(List<T> entityList);

    /**
     * 批量修改所有字段/新增
     * @param entityList
     * @param batchSize
     * @return
     */
    boolean updateAllColumnBatch(List<T> entityList, int batchSize);

    /**
     * 批量修改/新增
     * @param entityList
     * @return
     */
    boolean updateBatch(List<T> entityList);

    /**
     * 批量修改/新增
     * @param entityList
     * @param batchSize
     * @return
     */
    boolean updateBatch(List<T> entityList, int batchSize);

    /**
     * 批量修改/新增，并返回列表
     * @param entityList
     * @return
     */
    List<T> updateBatchThenReturn(List<T> entityList);

    /**
     * 获取enable_=0的数据总数
     * @return
     */
    Integer getTotalCount();
}
