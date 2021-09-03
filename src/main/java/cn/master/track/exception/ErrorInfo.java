package cn.master.track.exception;

/**
 * @author Created by 11's papa on 2021/09/03
 * @version 1.0.0
 */
public interface ErrorInfo {
    /**
     * 错误码
     *
     * @return java.lang.String
     */
    String getResultCode();

    /**
     * 错误描述
     *
     * @return java.lang.String
     */
    String getResultMsg();
}
