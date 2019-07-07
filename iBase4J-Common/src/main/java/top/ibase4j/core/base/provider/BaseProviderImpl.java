package top.ibase4j.core.base.provider;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import top.ibase4j.core.Constants;
import top.ibase4j.core.util.InstanceUtil;

public abstract class BaseProviderImpl implements ApplicationContextAware, BaseProvider {
    protected static Logger logger = LoggerFactory.getLogger(BaseProviderImpl.class);
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Parameter execute(Parameter parameter) {
        String no = parameter.getNo();
        logger.info("{} request：{}", no, JSON.toJSONString(parameter));
        Object service = applicationContext.getBean(parameter.getService());
        try {
            String method = parameter.getMethod();
            Object[] param = parameter.getParam();
            Object result = InstanceUtil.invokeMethod(service, method, param);
            Parameter response = new Parameter(result);
            logger.info("{} response：{}", no, JSON.toJSONString(response));
            return response;
        } catch (Exception e) {
            logger.error(no + " " + Constants.Exception_Head, e);
            throw e;
        }
    }

    @Override
    public Object execute(String service, String method, Object... parameters) {
        logger.info("{}.{} request：{}", service, method, JSON.toJSONString(parameters));
        Object owner = applicationContext.getBean(service);
        try {
            Object result = InstanceUtil.invokeMethod(owner, method, parameters);
            Parameter response = new Parameter(result);
            logger.info("{}.{} response：{}", service, method, JSON.toJSONString(response));
            return response;
        } catch (Exception e) {
            logger.error(service + "." + method + " " + Constants.Exception_Head, e);
            throw e;
        }
    }
}
