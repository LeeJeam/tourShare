package com.xmyy.manage.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.xmyy.common.EnumConstants;
import com.xmyy.manage.mapper.AdminDicMapper;
import com.xmyy.manage.model.AdminDic;
import com.xmyy.manage.service.AdminDicService;
import com.xmyy.manage.vo.ManageAdminDicPageParam;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.support.Pagination;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 数据字典明细表  服务实现类
 *
 * @author zlp
 */
@Service(interfaceClass = AdminDicService.class)
public class AdminDicServiceImpl extends BaseServiceImpl<AdminDic, AdminDicMapper> implements AdminDicService {

    @Transactional
    public AdminDic insert(AdminDic dic) {
        this.mapper.insert(dic);
        return dic;
    }


    @Transactional(readOnly = true)
    public Set<String> findHotWords(String type, String code, String keywords) {
        EntityWrapper<AdminDic> ew = new EntityWrapper<>();
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        if (!StringUtils.isBlank(code)) ew.in("code_", code.split(","));
        ew.eq("type_", type);
        if (!StringUtils.isBlank(keywords)) ew.like("code_text", "%" + keywords + "%");
        List<AdminDic> sensitiveList = mapper.selectList(ew);

        if (CollectionUtils.isEmpty(sensitiveList)) return Sets.newHashSet();

        List<String> sens = sensitiveList.parallelStream().map(s -> {
            return s.getCodeText();
        }).collect(Collectors.toCollection(ArrayList::new));

        return Sets.newHashSet(sens);

    }

    @Transactional(readOnly = true)
    public Pagination<AdminDic> list(ManageAdminDicPageParam param) {
        //封装查询参数
        EntityWrapper<AdminDic> ew = new EntityWrapper();

        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());

        if (param.getType() != null) ew.eq("type_", param.getType());
        if (!StringUtils.isBlank(param.getCode())) ew.eq("code_", param.getCode());
        if (!StringUtils.isBlank(param.getCodeText())) ew.like("code_text", "%" + param.getCodeText() + "%");

        ew.orderDesc(Lists.newArrayList("type_", "sort_no"));

        List<AdminDic> list = null;

        int count = mapper.selectCount(ew);
        if (count > 0) {
            //分页查询，封装订单信息
            RowBounds rb = new RowBounds((param.getCurrent() - 1) * param.getSize(), param.getSize());
            list = mapper.selectPage(rb, ew);

        }

        Pagination<AdminDic> page = new Pagination<>();
        page.setRecords(list);
        page.setCurrent(param.getCurrent());
        page.setSize(param.getSize());
        page.setTotal(count);


        return page;
    }

    @Override
    @Transactional
    public Object save(AdminDic param) {
        AdminDic dic = null;
        if (param.getId() != null) {
            dic = super.queryById(param.getId());

            if (dic == null) {
                return "字典项不存在";
            }

            if (dic.getEditable() != null && dic.getEditable().intValue() != EnumConstants.YesOrNo.YES.getValue().intValue()) {
                return "该项不可编辑";
            }
        }

        BeanUtils.copyProperties(param, dic);
        super.update(dic);
        return null;
    }
}
