package top.ibase4j.core.base;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 用于显示请求的路径与请求参数
 * 在项目Spring-servlet.xml中配置切面
 *
 * @author simon
 */
@Component
@Aspect
@Order(-1)
public class RequestMonitor {
    private static Logger logger = LoggerFactory.getLogger(RequestMonitor.class);

    @Pointcut(value = "execution(* com.xmyy..*.web..*.*(..))")
    public void controller() {
    }

    private static String[] types = {"java.lang.Integer", "java.lang.Double",
            "java.lang.Float", "java.lang.Long", "java.lang.Short",
            "java.lang.Byte", "java.lang.Boolean", "java.lang.Char",
            "java.lang.String", "int", "double", "long", "short", "byte",
            "boolean", "char", "float"};
    private static List<String> TYPELIST;

    static {
        TYPELIST = new ArrayList<>();
        TYPELIST = Arrays.asList(types);
    }

    @Before(value = "controller()")
    public void doBefore(JoinPoint joinPoint) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
            String qid = sdf.format(new Date());

            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();

            logger.info("[" + qid + " Request] " + request.getRequestURI() + " | " + joinPoint.getSignature().getDeclaringType().getSimpleName() + "." + joinPoint.getSignature().getName());

            String[] params = ((CodeSignature) joinPoint.getStaticPart().getSignature()).getParameterNames();
            Object[] args = joinPoint.getArgs();
            int i = 0;
            StringBuilder sb = new StringBuilder();
            for (Object arg : args) {
                if (arg != null) {
                    Class<?> clazz = arg.getClass();
                    String typeName = arg.getClass().getTypeName();
                    if (TYPELIST.contains(typeName)) {
                        sb.append(params[i]).append(":").append(arg).append(" | ");
                    } else {
                        if (clazz.getName().startsWith("com.xmyy")) {
                            Field[] fields = clazz.getDeclaredFields();
                            for (Field field : fields) {
                                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                                Method getMethod = pd.getReadMethod(); //获得get方法
                                Object value = getMethod.invoke(arg);
                                sb.append(field.getName()).append(":").append(value).append(" | ");
                            }
                        }
                    }
                    i++;
                }
            }
            logger.info("[" + qid + " Params] " + sb.toString());
        } catch (Throwable e) {
            logger.error("RequestMonitorAspect ERROR:", e);
        }

    }
}
