package cn.master.track.enums;

import cn.master.track.exception.ErrorInfo;

/**
 * @author Created by 11's papa on 2021/09/03
 * @version 1.0.0
 */
public enum ExceptionEnum implements ErrorInfo {
    // 数据操作错误定义
    SUCCESS("200", "成功!"),
    BODY_NOT_MATCH("400", "请求的数据格式不符!"),
//    SIGNATURE_NOT_MATCH("401", "请求的数字签名不匹配!"),
    NOT_FOUND("404", "未找到该资源!"),
    INTERNAL_SERVER_ERROR("500", "服务器内部错误!"),
    SERVER_BUSY("503", "服务器正忙，请稍后再试!");
    /**
     * 错误码
     */
    private final String resultCode;

    /**
     * 错误描述
     */
    private final String resultMsg;

    ExceptionEnum(String code, String resultMessage) {
        this.resultCode = code;
        this.resultMsg = resultMessage;
    }

    @Override
    public String getResultCode() {
        return resultCode;
    }

    @Override
    public String getResultMsg() {
        return resultMsg;
    }
}
