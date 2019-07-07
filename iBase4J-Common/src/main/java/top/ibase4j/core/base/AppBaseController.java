package top.ibase4j.core.base;

import top.ibase4j.core.Constants;

import javax.servlet.http.HttpServletRequest;

public abstract class AppBaseController<T extends BaseModel, S extends BaseService<T>> extends BaseController<T, S> {

    /**
     * 经过TokenFilter的url(非白名单),使用此方法获取用户ID
     * TokenFilter对SESSID做了处理，将ID存入了CURRENT_USER中
     */
    @Override
    protected Long getCurrUser(HttpServletRequest request) {
        Object id = request.getAttribute(Constants.CURRENT_USER);
        return id == null ? null : Long.parseLong(id.toString());
    }

}
