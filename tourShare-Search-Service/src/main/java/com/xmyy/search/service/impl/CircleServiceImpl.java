package com.xmyy.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xmyy.common.EnumConstants;
import com.xmyy.search.core.config.EsConfig;
import com.xmyy.search.dao.CircleDao;
import com.xmyy.search.repository.CircleRepository;
import com.xmyy.search.service.CircleImportService;
import com.xmyy.search.vo.CircleData;
import com.xmyy.search.vo.CircleDoc;
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
 * 动态ES导入  服务实现类
 *
 * @author zlp
 */
@Service(interfaceClass = CircleImportService.class)
public class CircleServiceImpl implements CircleImportService {
    private static final String Circle_LAST_INDEX_TIME = EnumConstants.AdminDicType.CIRCLE_LAST_INDEX_TIME.getValue();

    @Resource
    private CircleDao circleDao;
    @Resource
    private CircleDocServiceImpl circleDocService;
    @Resource
    private SearchCommonServiceImpl searchCommonService;
    @Autowired
    private CircleRepository circleRepository;
    @Autowired
    protected EsConfig esConfig;
    @Autowired
    protected IndexMappingServiceImpl indexMappingServiceImpl;
    @Autowired
    private SearchLogServiceImpl logService;

    public String getIndexName() {
        return esConfig.getCircledataIndexName();
    }

    public String getPackerIndexName() {
        return esConfig.getPackerdataIndexName();
    }


    public int incrementImportCirclesToIndex(final int size) {
        return searchCommonService.doWithLastOperationTime(Circle_LAST_INDEX_TIME, lastIndexTime -> {
            if (lastIndexTime == null) {
                return fullImportCirclesToIndex(size);
            } else {
                return importCirclesToIndex(size, lastIndexTime);
            }
        });
    }


    public int fullImportCirclesToIndex(final int size) {
        return importCirclesToIndex(size, null);
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
    public int importCirclesToIndex(final int size, Date lastIndexTime) {
        int total = circleDocService.countValidCircles(lastIndexTime);
        return this.searchCommonService.doInBatchIfNecessary(size, total, (batchSize, index) -> {
            return importCirclesToIndex(batchSize, index, lastIndexTime);
        });
    }

    /**
     * @param batchSize
     * @param batchIndex
     * @param lastIndexTime
     * @return
     */
    @Transactional(readOnly = true)
    public int importCirclesToIndex(int batchSize, int batchIndex, Date lastIndexTime) {
        // 查询
        List<CircleData> batchCircles = findValidCircles(batchSize, batchIndex, lastIndexTime);
        ImportDataContext dataContext = createImportDataContext(true, batchCircles);
        List<CircleDoc> circleDocs = mapToCircleDocs(dataContext);
        // 保存
        saveDocs(circleDocs);

        Date now = new Date();
        List<SearchLogDoc> logs = new ArrayList<>();
        circleDocs.parallelStream().forEach(s -> {

            SearchLogDoc log = new SearchLogDoc();
            log.setType("CRICLE");
            log.setCreateAt(now);

           /* if(!StringUtils.isBlank(s.getClassifyTags())) {
                log.setKeyword(s.getClassifyTags());
                log.setSearchword(s.getClassifyTags());
                logs.add(log);
            }*/

            if (!StringUtils.isBlank(s.getTitle())) {
                log.setKeyword(s.getTitle());
                log.setSearchword(s.getTitle());
                logs.add(log);
            }
        });

        logService.saveList(logs);

        return circleDocs.size();
    }

    private void saveDocs(List<CircleDoc> circleDocs) {
        if (circleDocs.isEmpty())
            return;
        circleRepository.saveAll(circleDocs);
    }

    /**
     * @param dataContext
     * @return
     */
    protected List<CircleDoc> mapToCircleDocs(ImportDataContext dataContext) {
        return dataContext.batchCircles.stream()
                .map(p -> mapToCircleDoc(p, new CircleDoc(), dataContext))
                .collect(Collectors.toList());
    }

    /**
     * @param
     * @param doc
     * @param dataContext
     * @return
     */
    protected CircleDoc mapToCircleDoc(CircleData circleData, CircleDoc doc, ImportDataContext dataContext) {
        BeanUtils.copyProperties(circleData, doc);
        mapAutoCompletion(doc);

        return doc;
    }


    private void mapAutoCompletion(CircleDoc doc) {
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
    protected List<CircleData> findValidCircles(int batchSize, int batchIndex, Date lastIndexTime) {
        RowBounds rowBounds = new RowBounds(batchSize * batchIndex, batchSize);
        return circleDao.findValidCircles(lastIndexTime, rowBounds);
    }

    protected ImportDataContext createImportDataContext(boolean batch, List<CircleData> batchCircles) {
        ImportDataContext dataContext = new ImportDataContext(batch, batchCircles);
        if (batch) {

        }
        return dataContext;
    }

    public class ImportDataContext {
        private boolean batch;
        final private List<CircleData> batchCircles;

        public ImportDataContext(boolean batch, List<CircleData> batchCircles) {
            super();
            this.batch = batch;
            this.batchCircles = batchCircles;
        }

        public boolean isBatch() {
            return batch;
        }


        public List<CircleData> getBatchCircles() {
            return batchCircles;
        }
    }
}
