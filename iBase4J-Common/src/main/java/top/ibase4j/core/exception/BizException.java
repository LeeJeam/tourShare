package top.ibase4j.core.exception;

import top.ibase4j.core.support.HttpCode;

/**
 * 业务自定义异常
 *
 * @author wangzejun
 */
public class BizException extends RuntimeException {

    /** 异常信息代码 */
    private Integer code;

    /** 异常信息 */
    private String msg;

    public BizException(String msg) {
        this.code = HttpCode.BAD_REQUEST.value();
        this.msg = msg;
    }

    public BizException(Integer code, String msg) {
        this.code = code == null ? HttpCode.BAD_REQUEST.value() : code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}