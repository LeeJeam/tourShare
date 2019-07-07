package com.xmyy.search.service;

public interface PackerImportService {
    int fullImportPackersToIndex(final int size);
    int incrementImportPackersToIndex(final int size);
    boolean rebuildIndex();
}
