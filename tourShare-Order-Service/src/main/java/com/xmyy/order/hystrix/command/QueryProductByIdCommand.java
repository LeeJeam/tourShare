package com.xmyy.order.hystrix.command;

import com.netflix.hystrix.*;
import com.xmyy.product.model.PtProduct;
import com.xmyy.product.service.PtProductService;
import org.springframework.stereotype.Component;
import top.ibase4j.core.exception.BizException;

@Component
public class QueryProductByIdCommand extends HystrixCommand<Object> {

    private PtProductService ptProductService;

    private Long id;

    public QueryProductByIdCommand() {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("OrderServiceGroup")));
    }

    public QueryProductByIdCommand(PtProductService ptProductService, Long id) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("OrderServiceGroup")) //分组名称，必须
                .andCommandKey(HystrixCommandKey.Factory.asKey("QueryProductByIdCommand")) //HystrixCommand的名字，如果未定义，则使用类名作为Key
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(10000) //线程池隔离的超时时间，默认1s
                        .withCircuitBreakerErrorThresholdPercentage(50) //容错率，周期内(10s)请求错误率超过进入open状态，默认50%
                )
//                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("QueryProductByIdCommandPool")) //线程池名称，如果不设置则使用HystrixCommandGroupKey作为线程池名称，即同一个Group下使用同一个线程池
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(10)) //线程池大小，默认10
        );

        this.ptProductService = ptProductService;
        this.id = id;
    }

    @Override
    protected String getFallback() {
        throw new BizException("系统忙，请稍后再试");
    }

    @Override
    protected PtProduct run() {
        return ptProductService.queryById(id);
    }
}
