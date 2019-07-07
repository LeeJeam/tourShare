package com.xmyy.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xmyy.common.EnumConstants;
import com.xmyy.search.core.config.EsConfig;
import com.xmyy.search.dao.SellerDao;
import com.xmyy.search.repository.SellerRepository;
import com.xmyy.search.service.SellerImportService;
import com.xmyy.search.vo.SearchLogDoc;
import com.xmyy.search.vo.SellerData;
import com.xmyy.search.vo.SellerDoc;
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
 * 买手ES导入  服务实现类
 *
 * @author zlp
 */
@Service(interfaceClass = SellerImportService.class)
public class SellerServiceImpl implements SellerImportService {
    private static final String SELLER_LAST_INDEX_TIME = EnumConstants.AdminDicType.SELLER_LAST_INDEX_TIME.getValue();

    @Resource
    private SellerDao sellerDao;
    @Resource
    private SellerDocServiceImpl sellerDocService;
    @Resource
    private SearchCommonServiceImpl searchCommonService;
    @Autowired
    private SellerRepository selllerRepository;
    @Autowired
    protected EsConfig esConfig;
    @Autowired
    protected IndexMappingServiceImpl indexMappingServiceImpl;
    @Autowired
    private SearchLogServiceImpl logService;

    public String getIndexName() {
        return esConfig.getSellerdataIndexName();
    }

    public String getPackerIndexName() {
        return esConfig.getPackerdataIndexName();
    }


    public int incrementImportSellersToIndex(final int size) {
        return searchCommonService.doWithLastOperationTime(SELLER_LAST_INDEX_TIME, lastIndexTime -> {
            if (lastIndexTime == null) {
                return fullImportSellersToIndex(size);
            } else {
                return importSellersToIndex(size, lastIndexTime);
            }
        });
    }


    public int fullImportSellersToIndex(final int size) {
        return importSellersToIndex(size, null);
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
    public int importSellersToIndex(final int size, Date lastIndexTime) {
        int total = sellerDocService.countValidSellers(lastIndexTime);
        return this.searchCommonService.doInBatchIfNecessary(size, total, (batchSize, index) -> {
            return importSellersToIndex(batchSize, index, lastIndexTime);
        });
    }

    /**
     * @param batchSize
     * @param batchIndex
     * @param lastIndexTime
     * @return
     */
    @Transactional(readOnly = true)
    public int importSellersToIndex(int batchSize, int batchIndex, Date lastIndexTime) {
        // 查询
        List<SellerData> batchSellers = findValidSellers(batchSize, batchIndex, lastIndexTime);
        ImportDataContext dataContext = createImportDataContext(true, batchSellers);
        List<SellerDoc> sellerDocs = mapToSellerDocs(dataContext);
        // 保存
        saveDocs(sellerDocs);

        Date now = new Date();
        List<SearchLogDoc> logs = new ArrayList<>();
        sellerDocs.parallelStream().forEach(s -> {

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

        return sellerDocs.size();
    }

    private void saveDocs(List<SellerDoc> sellerDocs) {
        if (sellerDocs.isEmpty())
            return;
        selllerRepository.saveAll(sellerDocs);
    }

    /**
     * @param dataContext
     * @return
     */
    protected List<SellerDoc> mapToSellerDocs(ImportDataContext dataContext) {
        return dataContext.batchSellers.stream()
                .map(p -> mapToSellerDoc(p, new SellerDoc(), dataContext))
                .collect(Collectors.toList());
    }

    /**
     * @param sellerData
     * @param doc
     * @param dataContext
     * @return
     */
    protected SellerDoc mapToSellerDoc(SellerData sellerData, SellerDoc doc, ImportDataContext dataContext) {
        BeanUtils.copyProperties(sellerData, doc);
        mapAutoCompletion(doc);

        return doc;
    }


    private void mapAutoCompletion(SellerDoc doc) {
        //List<String> inputs = new ArrayList<String>();

        // Completion comp = new Completion(inputs.toArray(new String[0]));
        //doc.setAutoCompletion(comp);
    }

    /****
     * 查找所有买手
     * @param batchSize
     * @param batchIndex
     * @param lastIndexTime
     * @return
     */
    protected List<SellerData> findValidSellers(int batchSize, int batchIndex, Date lastIndexTime) {
        RowBounds rowBounds = new RowBounds(batchSize * batchIndex, batchSize);
        return sellerDao.findValidSellers(lastIndexTime, rowBounds);
    }

    protected ImportDataContext createImportDataContext(boolean batch, List<SellerData> batchSellers) {
        ImportDataContext dataContext = new ImportDataContext(batch, batchSellers);
        if (batch) {

        }
        return dataContext;
    }

    public class ImportDataContext {
        private boolean batch;
        final private List<SellerData> batchSellers;

        public ImportDataContext(boolean batch, List<SellerData> batchSellers) {
            super();
            this.batch = batch;
            this.batchSellers = batchSellers;
        }

        public boolean isBatch() {
            return batch;
        }


        public List<SellerData> getBatchSellers() {
            return batchSellers;
        }
    }
}
