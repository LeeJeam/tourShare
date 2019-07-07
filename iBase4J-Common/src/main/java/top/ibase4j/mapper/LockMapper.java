package top.ibase4j.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import top.ibase4j.model.Lock;

public interface LockMapper extends BaseMapper<Lock> {

	void cleanExpiredLock();

}
