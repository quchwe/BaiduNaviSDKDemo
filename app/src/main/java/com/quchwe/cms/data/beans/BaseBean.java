package com.quchwe.cms.data.beans;

/**
 * Created by quchwe on 2016/11/26 0026.
 * 网络通讯基类 errCode 0:成功1111123
 */

public class BaseBean<T> {
    public static final int SUCCESS_CODE = 0;
    public static final String SUCCESS_STRING = "ok";
    public static final int ERROR_CODE = -1;
    public static final int OTHER_CODE = 1;
    public static final int USER_TOKEN_EXPIRED = 42002;
    public static final String SYSTEM_ERROR = "system error";
    int errCode;
    String errMsg;
    T resultInfo;

    public T getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(T resultInfo) {
        this.resultInfo = resultInfo;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
