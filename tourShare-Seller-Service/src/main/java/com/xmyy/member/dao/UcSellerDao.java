package com.xmyy.member.dao;

import com.xmyy.common.vo.MemberInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 买手操作持久层
 * </p>
 *
 * @author wangzejun
 * @since 2018-09-20
 */
public interface UcSellerDao {

    List<MemberInfo> getManySellerInfos(@Param("ids") List<Long>  sellerIds);
}
