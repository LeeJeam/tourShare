package top.ibase4j.core.exception;

import top.ibase4j.core.support.HttpCode;

/**
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:19:19
 */
@SuppressWarnings("serial")
public class ConflictException extends BaseException {

	public ConflictException() {
	}

	public ConflictException(Throwable ex) {
		super(ex);
	}

	public ConflictException(String message) {
		super(message);
	}

	public ConflictException(String message, Throwable ex) {
		super(message, ex);
	}

	protected HttpCode getCode() {
		return HttpCode.CONFLICT;
	}
}