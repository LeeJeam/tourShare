package top.ibase4j.core.base.provider;

/**
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:19:19
 */
public interface BaseProvider {
	Parameter execute(Parameter parameter);
	
	Object execute(String service, String method, Object...parameters);
}
