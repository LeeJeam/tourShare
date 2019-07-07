package com.xmyy.search.core;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.util.CollectionUtils;
import top.ibase4j.core.support.Pagination;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Simon on 2018/8/28.
 */
public class PaginationUtils {

    public static <T> Pagination<T> from(AggregatedPage<T> results, Pageable pageable){
        List<T> content = results.getContent();
        int totalPages = results.getTotalPages();
        long totalElements = results.getTotalElements();

        Pagination<T> page = new Pagination<>();
        page.setRecords(content);
        page.setPages(totalPages);
        page.setTotal((int)totalElements);
        page.setCurrent(pageable.getPageNumber());
        page.setSize(pageable.getPageSize());
        return page;
    }

    public static <T> void contentWrapper(AggregatedPage<T> results,Consumer<T> consumer){
        List<T> content = results.getContent();
        if(!CollectionUtils.isEmpty(content)){
            for (T t : content) {
                consumer.accept(t);
            }
        }
    }

}
