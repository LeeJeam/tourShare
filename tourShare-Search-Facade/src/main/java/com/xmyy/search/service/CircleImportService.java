package com.xmyy.search.service;

public interface CircleImportService {
    int fullImportCirclesToIndex(final int size);
    int incrementImportCirclesToIndex(final int size);
    boolean rebuildIndex();
}
