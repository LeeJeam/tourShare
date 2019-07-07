package com.xmyy.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xmyy.common.EnumConstants;
import com.xmyy.search.core.config.EsConfig;
import com.xmyy.search.dao.PackerDao;
import com.xmyy.search.repository.PackerRepository;
import com.xmyy.search.service.PackerImportService;
import com.xmyy.search.vo.PackerData;
import com.xmyy.search.vo.PackerDoc;
import com.xmyy.search.vo.SearchLogDoc;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 背包客ES导入  服务实现类
 *
 * @author zlp
 */
@Service(interfaceClass = PackerImportService.class)
public class PackerServiceImpl implements PackerImportService {

    private static final String PACKER_LAST_INDEX_TIME = EnumConstants.AdminDicType.PACKER_LAST_INDEX_TIME.getValue();

    @Resource
    private SearchCommonServiceImpl searchCommonService;
    @Autowired
    private PackerRepository packerRepository;
    @Autowired
    protected EsConfig esConfig;
    @Autowired
    protected IndexMappingServiceImpl indexMappingServiceImpl;
    @Autowired
    private PackerDocServiceImpl packerDocService;
    @Autowired
    private PackerDao packerDao;
    @Autowired
    private SearchLogServiceImpl logService;

    public String getIndexName() {
        return esConfig.getSellerdataIndexName();
    }

    public int incrementImportPackersToIndex(final int size) {
        return searchCommonService.doWithLastOperationTime(PACKER_LAST_INDEX_TIME, lastIndexTime -> {
            if (lastIndexTime == null) {
                return fullImportPackersToIndex(size);
            } else {
                return importPackersToIndex(size, lastIndexTime);
            }
        });
    }


    public int fullImportPackersToIndex(final int size) {
        return importPackersToIndex(size, null);
    }


    public boolean rebuildIndex() {
        return indexMappingServiceImpl.rebuildIndex(getIndexName());
    }

    /****
     * 导入索引
     * @param size          每次导入数据量
     * @param lastIndexTime 如果上一次索引更新时间不为null，则按上一次索引更新时间查找买手
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public int importPackersToIndex(final int size, Date lastIndexTime) {
        int total = packerDocService.countValidPackers(lastIndexTime);
        return this.searchCommonService.doInBatchIfNecessary(size, total, (batchSize, index) -> {
            return importPackersToIndex(batchSize, index, lastIndexTime);
        });
    }

    @Transactional(readOnly = true)
    public int importPackersToIndex(int batchSize, int batchIndex, Date lastIndexTime) {
        // 查询
        List<PackerData> batchPackers = findValidPackers(batchSize, batchIndex, lastIndexTime);
        PackerServiceImpl.ImportDataContext dataContext = createImportDataContext(true, batchPackers);
        List<PackerDoc> packerDocs = mapToPackerDocs(dataContext);
        // 保存
        saveDocs(packerDocs);

        Date now = new Date();
        List<SearchLogDoc> logs = new ArrayList<>();
        packerDocs.parallelStream().forEach(s -> {

            SearchLogDoc log = new SearchLogDoc();
            log.setType("MEMBER");
            log.setCreateAt(now);

            if (!StringUtils.isBlank(s.getNickName())) {
                log.setId(s.getId().toString());
                log.setKeyword(s.getNickName());
                log.setSearchword(s.getNickName());
                logs.add(log);
            }

            if (!StringUtils.isBlank(s.getRealName())) {
                log.setId(s.getId().toString());
                log.setKeyword(s.getRealName());
                log.setSearchword(s.getRealName());
                logs.add(log);
            }


            if (!StringUtils.isBlank(s.getLiveCountry())) {
                log.setKeyword(s.getLiveCountry());
                log.setSearchword(s.getLiveCountry());
                logs.add(log);
            }/*

            if(!StringUtils.isBlank(s.getOftenPlace())) {
                log.setKeyword(s.getOftenPlace());
                log.setSearchword(s.getOftenPlace());
                logs.add(log);
            }

            if(!StringUtils.isBlank(s.getClassifyTags())) {
                log.setKeyword(s.getClassifyTags());
                log.setSearchword(s.getClassifyTags());
                logs.add(log);
            }*/
        });

        logService.saveList(logs);
        return packerDocs.size();
    }

    protected List<PackerData> findValidPackers(int batchSize, int batchIndex, Date lastIndexTime) {
        RowBounds rowBounds = new RowBounds(batchSize * batchIndex, batchSize);
        return packerDao.findValidPackers(lastIndexTime, rowBounds);
    }

    protected List<PackerDoc> mapToPackerDocs(PackerServiceImpl.ImportDataContext dataContext) {
        return dataContext.batchPackers.stream()
                .map(p -> mapToPackerDoc(p, new PackerDoc(), dataContext))
                .collect(Collectors.toList());
    }

    protected PackerDoc mapToPackerDoc(PackerData packerData, PackerDoc doc, PackerServiceImpl.ImportDataContext dataContext) {
        BeanUtils.copyProperties(packerData, doc);

        return doc;
    }

    private void saveDocs(List<PackerDoc> packerDocs) {
        if (packerDocs.isEmpty())
            return;
        packerRepository.saveAll(packerDocs);
    }

    protected PackerServiceImpl.ImportDataContext createImportDataContext(boolean batch, List<PackerData> batchPackers) {
        PackerServiceImpl.ImportDataContext dataContext = new PackerServiceImpl.ImportDataContext(batch, batchPackers);
        if (batch) {

        }
        return dataContext;
    }

    public class ImportDataContext {
        private boolean batch;
        final private List<PackerData> batchPackers;

        public ImportDataContext(boolean batch, List<PackerData> batchPackers) {
            super();
            this.batch = batch;
            this.batchPackers = batchPackers;
        }

        public boolean isBatch() {
            return batch;
        }


        public List<PackerData> getbatchPackers() {
            return batchPackers;
        }
    }
}
