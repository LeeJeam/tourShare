package com.xmyy.order.hystrix.command;

import com.netflix.hystrix.*;
import com.xmyy.demand.service.DgDemandOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryDemandByOrderIdCommand extends HystrixCommand<Object> {

    @Autowired
    private DgDemandOrderService demandOrderService;

    private Long id;

    public QueryDemandByOrderIdCommand() {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("OrderServiceGroup")));
    }

    public QueryDemandByOrderIdCommand(Long id) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("OrderServiceGroup")) //分组名称，必须
                .andCommandKey(HystrixCommandKey.Factory.asKey("QueryDemandByOrderIdCommand")) //HystrixCommand的名字，如果未定义，则使用类名作为Key
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(2000) //线程池隔离的超时时间，默认1s
                        .withCircuitBreakerErrorThresholdPercentage(50) //容错率，周期内(10s)请求错误率超过进入open状态，默认50%
                )
//                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("serviceThreadPool")) //线程池名称，如果不设置则使用HystrixCommandGroupKey作为线程池名称，即同一个Group下使用同一个线程池
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(10)) //线程池大小，默认10
        );

        this.id = id;
    }

    @Override
    protected String getFallback() {
        return "系统忙，请稍后再试";
    }

    @Override
    protected Object run() {
        return demandOrderService.queryByOrderId(id);
    }
}
