package cn.master.track.exception;

import cn.master.track.enums.ExceptionEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Created by 11's papa on 2021/09/02
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
public class ResponseResult {

    private String code;
    private String message;
    private Object result;

    public static ResponseResult success(Object data) {
        final ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(ExceptionEnum.SUCCESS.getResultCode());
        responseResult.setMessage(ExceptionEnum.SUCCESS.getResultMsg());
        responseResult.setResult(data);
        return responseResult;
    }

    public static ResponseResult error(String code, String message) {
        final ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(code);
        responseResult.setMessage(message);
        responseResult.setResult(null);
        return responseResult;
    }
}
