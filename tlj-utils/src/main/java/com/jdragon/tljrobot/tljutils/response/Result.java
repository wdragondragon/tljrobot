package com.jdragon.tljrobot.tljutils.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.18 17:45
 * @Description: 返回结果构造类
 */
@Data
@ToString
@Accessors(chain = true)
@NoArgsConstructor
public class Result<T> implements Serializable {

    /**
     * 编号.
     */
    private Long code;

    /**
     * 信息.
     */
    private String message;

    /**
     * 结果数据
     */
    private T result;

    @JsonIgnore
    private transient static Object defaultData = new Object();


    public Result(Long code,String message,T result){
        this.code = code;
        this.message = message;
        this.result = result;
    }
    public Result(ICode resultCode, T result){
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.result = result;
    }

    public static Result<Object> success(){
        return success(defaultData);
    }

    public static Result<String> success(String message){
        return new Result<>(ResultCode.NORMAL.getCode(),message,message);
    }
    public static <T> Result<T> success(T result) {
        return new Result<>(ResultCode.NORMAL, result);
    }
    public static <T> Result<T> success(String message, T result) {
        return new Result<>(ResultCode.NORMAL.getCode(), message,result);
    }


    public static Result<Object> error(){
        return error(defaultData);
    }
    public static Result<String> error(String message) {
        return new Result<>(ResultCode.SYSTEM_ERROR.getCode(),message,message);
    }
    public static <T> Result<T> error(T result) {
        return new Result<>(ResultCode.SYSTEM_ERROR, result);
    }
    public static <T> Result<T> error(String message, T result) {
        return new Result<>(ResultCode.SYSTEM_ERROR.getCode(), message,result);
    }

    public static <T> Result<T> build(ICode resultCode, T result) {
        return new Result<>(resultCode,result);
    }
    public static <T> Result<T> build(Long code, String message, T result) {
        return new Result<>(code,message,result);
    }

    public static <T> Result<T> authFail(T result){
        return build(ResultCode.AUTH_FAIL,result);
    }

    public static <T> Result<T> permissionsNotEnough(T result) {
        return build(ResultCode.PERMISSIONS_NOT_ENOUGH,result);
    }

    public static <T> Result<T> paramsError(T result) {
        return build(ResultCode.PARAMS_ERROR, result);
    }
    public static <T> Result<T> unKnowError(T result){
        return build(ResultCode.SYSTEM_UN_KNOW_ERROR,result);
    }
    /**
     * 判断是否成功： 依据 Wrapper.SUCCESS_CODE == this.code
     *
     * @return code =200,true;否则 false.
     */
    @JsonIgnore
    public boolean result() {
        return ResultCode.NORMAL.getCode().equals(code);
    }
}
