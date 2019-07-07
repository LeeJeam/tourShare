package com.xmyy.manage.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Sets;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.util.IOUtil;
import com.xmyy.common.util.SensitivewordFilter;
import com.xmyy.manage.dao.SensitiveDao;
import com.xmyy.manage.mapper.WsSensitiveMapper;
import com.xmyy.manage.model.WsSensitive;
import com.xmyy.manage.service.WsSensitiveService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.util.ExcelReaderUtil;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 敏感词  服务实现类
 *
 * @author zlp
 */
@Service(interfaceClass = WsSensitiveService.class)
@CacheConfig(cacheNames = "wsSensitive")
public class WsSensitiveServiceImpl extends BaseServiceImpl<WsSensitive, WsSensitiveMapper> implements WsSensitiveService {

    @Resource
    private SensitiveDao sensitiveDao;

    @Transactional(readOnly = true)
    public List<String> findSensitives() {
        EntityWrapper<WsSensitive> ew = new EntityWrapper<>();
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        List<WsSensitive> sensitiveList = mapper.selectList(ew);

        if (CollectionUtils.isEmpty(sensitiveList)) return InstanceUtil.newArrayList();

        return sensitiveList.parallelStream().map(s -> s.getContent()).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * 检查敏感词并返回
     *
     * @return
     */
    @Override
    @Transactional
    public Set<String> findSensitives(String content) {
        if (StringUtils.isBlank(content)) return InstanceUtil.newHashSet();
        List<String> sensitives = findSensitives();
        if (CollectionUtils.isEmpty(sensitives)) return InstanceUtil.newHashSet();
        SensitivewordFilter filter = new SensitivewordFilter(Sets.newHashSet(sensitives));
        Set<String> sensitiveWord = filter.getSensitiveWord(content, 2);

        return sensitiveWord;
    }

    /**
     * 剔除敏感字
     *
     * @param words
     */
    @Transactional(readOnly = true)
    public void removeSensitive(List<String> words) {
        List<String> sensitivesl = findSensitives();
        if (CollectionUtils.isEmpty(sensitivesl)) return;
        Set<String> sensitives = Sets.newHashSet(sensitivesl);
        words.removeIf(sensitives::contains);
    }


    @Override
    @Transactional
    public Object importWord(String fileName, WsSensitive sensitive, byte[] inputStream) {
        logger.info("本次导入的敏感词文件为：" + fileName);
        List<WsSensitive> sensitives = new ArrayList<>();
        Date now = new Date();
        sensitive.setCreateTime(now);
        sensitive.setUpdateTime(now);

        if (fileName.endsWith(".txt")) {
            ByteArrayInputStream bis = null;
            InputStreamReader reader = null;
            BufferedReader bufferedReader = null;
            try {
                bis = new ByteArrayInputStream(inputStream);
                reader = new InputStreamReader(bis, "utf8");
                bufferedReader = new BufferedReader(reader);
                String txt;

                while ((txt = bufferedReader.readLine()) != null) {    //读取文件，将文件内容放入到set中
                    WsSensitive sen = InstanceUtil.to(sensitive, WsSensitive.class);
                    sen.setContent(txt);
                    sensitives.add(sen);
                }
            } catch (Exception e) {
                return "敏感词导入错误：" + e.getMessage();
            } finally {
                IOUtil.close(bufferedReader, reader, bis);
            }

        } else if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
            File temp = null;
            FileOutputStream fos = null;
            try {

                temp = File.createTempFile("mingan", fileName.substring(fileName.lastIndexOf(".")));
                fos = new FileOutputStream(temp);
                fos.write(inputStream);
                List<String[]> txts = ExcelReaderUtil.excelToArrayList(temp.getAbsolutePath(), null);
                txts.parallelStream().forEach(t -> {
                    WsSensitive sen = InstanceUtil.to(sensitive, WsSensitive.class);
                    sen.setContent(t[0]);
                    sensitives.add(sen);
                });

            } catch (Exception e) {
                return "敏感词导入错误：" + e.getMessage();
            } finally {
                IOUtil.close(fos);
            }

        }

        if (CollectionUtils.isEmpty(sensitives)) {
            return "没有导入数据";
        }

        //分批插入 S
        Pagination<WsSensitive> page = new Pagination<WsSensitive>();
        page.setTotal(sensitives.size());
        page.setCurrent(1);
        page.setSize(100);
        page.setRecords(sensitives);
        logger.info("总页数：" + page.getPages());

        int count = (int) (page.getTotal() / (long) page.getSize());
        if (page.getTotal() % (long) page.getSize() > 0L)
            count++;
        page.setPages(count);

        //分批发送，间隔100
        for (int i = 0, size = page.getPages(); i < size; i++) {
            int fromIndex = (page.getCurrent() - 1) * page.getSize();
            int toIndex = page.getCurrent() * page.getSize();
            if (page.getTotal() < toIndex) toIndex = (int) page.getTotal();

            logger.info("页码：" + page.getCurrent() + "fromIndex:" + fromIndex + "toIndex:" + toIndex);
            List<WsSensitive> its = sensitives.subList(fromIndex, toIndex);
            this.sensitiveDao.insertSensitiveBatch(its);

            page.setCurrent(page.getCurrent() + 1);
        }
        return null;
    }
}
