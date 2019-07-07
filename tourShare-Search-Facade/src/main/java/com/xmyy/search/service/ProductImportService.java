package com.xmyy.search.service;


/**
 * Created by Simon on 2018/7/20.
 */
public interface ProductImportService {

    Integer fullImport();

    Integer incrementImport();

    Integer importByProductId(Long id);

}
