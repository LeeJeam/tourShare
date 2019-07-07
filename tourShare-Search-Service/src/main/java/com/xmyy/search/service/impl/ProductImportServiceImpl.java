package com.xmyy.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xmyy.common.EnumConstants;
import com.xmyy.product.model.PtProduct;
import com.xmyy.product.service.PtBrandService;
import com.xmyy.product.service.PtCategoryService;
import com.xmyy.product.service.PtProductService;
import com.xmyy.search.core.config.EsConfig;
import com.xmyy.search.dao.ProductDao;
import com.xmyy.search.repository.ProductRepository;
import com.xmyy.search.service.ProductImportService;
import com.xmyy.search.vo.ProductData;
import com.xmyy.search.vo.ProductDoc;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商品ES导入  服务实现类
 *
 * @author simon
 */
@Service(interfaceClass = ProductImportService.class)
public class ProductImportServiceImpl implements ProductImportService {

    @Resource
    ProductDao productDao;
    @Resource
    PtProductService ptProductService;
    @Resource
    PtCategoryService ptCategoryService;
    @Resource
    PtBrandService ptBrandService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    protected EsConfig esConfig;
    @Autowired
    protected IndexMappingServiceImpl indexMappingServiceImpl;
    @Resource
    private SearchCommonServiceImpl searchCommonService;

    private final static int BATCH_SIZE = 100;

    /**
     * 全导
     *
     * @return
     */
    @Override
    public Integer fullImport() {
        int total = productDao.countValidProducts(null);
        if (total == 0) {
            return 0;
        }
        return batchImport(BATCH_SIZE, total, null);
    }

    /**
     * 增量导入
     *
     * @return
     */
    @Override
    public Integer incrementImport() {
        Date lastIndexTime = searchCommonService.getLastOperationTime(EnumConstants.AdminDicType.PRODUCT_LAST_INDEX_TIME.getValue());
        int total = productDao.countValidProducts(lastIndexTime);
        if (total == 0) {
            return 0;
        }
        return batchImport(BATCH_SIZE, total, lastIndexTime);
    }

    /**
     * 根据商品ID导入
     *
     * @param id
     * @return
     */
    @Override
    public Integer importByProductId(Long id) {
        PtProduct ptProduct = ptProductService.queryById(id);
        if (ptProduct != null) {
            Map<Long, String> categoryNameMap = ptCategoryService.getCategoryNameMap();
            Map<Long, String> brandNameMap = ptBrandService.getBrandNameMap();
            ProductData productData = InstanceUtil.to(ptProduct, ProductData.class);
            productData.setCategoryName(categoryNameMap.get(productData.getPtCategoryId()));
            productData.setCategoryName2(categoryNameMap.get(productData.getPtCategoryId2()));
            productData.setBrandName(brandNameMap.get(productData.getPtBrandId()));
            ProductDoc doc = InstanceUtil.to(productData, ProductDoc.class);
            productRepository.save(doc);
            return 1;
        }
        return 0;
    }

    public Integer batchImport(Integer size, Integer total, Date lastIndexTime) {
        Map<Long, String> categoryNameMap = ptCategoryService.getCategoryNameMap();
        Map<Long, String> brandNameMap = ptBrandService.getBrandNameMap();
        return this.searchCommonService.doInBatchIfNecessary(size, total,
                (batchSize, batchCurrent) -> doImport(batchSize, batchCurrent, lastIndexTime, categoryNameMap, brandNameMap));
    }

    public int doImport(int batchSize, int batchCurrent, Date lastIndexTime, Map<Long, String> categoryNameMap, Map<Long, String> brandNameMap) {
        RowBounds rb = new RowBounds(batchCurrent * batchSize, batchSize);
        List<ProductData> list = productDao.findValidProducts(lastIndexTime, rb);

        for (ProductData productData : list) {
            productData.setCategoryName(categoryNameMap.get(productData.getPtCategoryId()));
            productData.setCategoryName2(categoryNameMap.get(productData.getPtCategoryId2()));
            productData.setBrandName(brandNameMap.get(productData.getPtBrandId()));
        }

        List<ProductDoc> docList;
        if (!CollectionUtils.isEmpty(list)) {
            docList = list.stream().map(v -> InstanceUtil.to(v, ProductDoc.class)).collect(Collectors.toList());
            productRepository.saveAll(docList);
            return docList.size();
        }
        return 0;
    }

}
