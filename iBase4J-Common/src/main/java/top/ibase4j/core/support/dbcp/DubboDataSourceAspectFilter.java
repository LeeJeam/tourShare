package top.ibase4j.core.support.dbcp;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * dubbo拦截器，切换数据库读写分离，项目未用到
 *
 * @author ShenHuaJie
 * @since 2018年4月24日 下午2:13:13
 */
@Activate(group = Constants.PROVIDER)
public class DubboDataSourceAspectFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(DubboDataSourceAspectFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String service = invoker.getInterface().getSimpleName();
        String method = invocation.getMethodName();
        HandleDataSource.setDataSource(service, method);
        Result result = invoker.invoke(invocation);
        logger.info(service + "." + method + "=>end.");
        return result;
    }
}
