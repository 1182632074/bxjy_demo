package com.bxjy.demo.entity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by wsy on 12/8/2017.
 */
public class Result {

    private int errorCode = -1;         // 返回错误码;-1：系统错误；0：成功

    private String message = "";// 错误信息

    private Map<String, Object> resultValue = new LinkedHashMap<>();  //返回字段值sss

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResultValue(String fieldName, Object fieldValue) {
        this.resultValue.put(fieldName, fieldValue);
    }

    public Map<String, Object> getResultValue() {
        return resultValue;
    }
}
