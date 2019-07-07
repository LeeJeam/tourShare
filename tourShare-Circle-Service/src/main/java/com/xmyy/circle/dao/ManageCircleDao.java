package com.xmyy.circle.dao;

import com.xmyy.circle.vo.CircleTopPageParam;
import com.xmyy.circle.vo.CircleTopPageResult;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ManageCircleDao {
    int countCircleTopPage(CircleTopPageParam param);

    List<CircleTopPageResult> listCircleTopPage(CircleTopPageParam param, RowBounds rowBounds);
}
