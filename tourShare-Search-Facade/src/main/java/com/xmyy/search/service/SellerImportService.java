package com.xmyy.search.service;

public interface SellerImportService {
    int fullImportSellersToIndex(final int size);
    int incrementImportSellersToIndex(final int size);
    boolean rebuildIndex();
}
