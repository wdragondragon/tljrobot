package com.jdragon.tljrobot.tljutils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Accessors(chain = true)
@ToString
public class Result implements Serializable {

    /**
     * 成功码.
     */
    public static final int SUCCESS_CODE = 200;

    /**
     * 成功信息.
     */
    public static final String SUCCESS_MESSAGE = "操作成功";

    /**
     * 错误码.
     */
    public static final int ERROR_CODE = 500;

    /**
     * 错误信息.
     */
    public static final String ERROR_MESSAGE = "内部异常";

    /**
     * 错误码：参数非法
     */
    public static final int ILLEGAL_ARGUMENT_CODE_ = 100;

    /**
     * 错误信息：参数非法
     */
    public static final String ILLEGAL_ARGUMENT_MESSAGE = "参数非法";

    /**
     * 编号.
     */
    private int code;

    /**
     * 信息.
     */
    private String message;

    /**
     * 结果数据
     */
    private Object result;

    public Result(int code, String message) {
        this(code, message, null);
    }

    public Result(int code, String message, Object result) {
       this.code = code;
       this.message = message;
       this.result = result;
    }

    public static Result success(String message) {
        return new Result(SUCCESS_CODE, message, null);
    }

    public static Result error(String message) {
        return new Result(ERROR_CODE, message, null);
    }


    /**
     * 判断是否成功： 依据 Wrapper.SUCCESS_CODE == this.code
     *
     * @return code =200,true;否则 false.
     */
    @JsonIgnore
    public boolean success() {
        return Result.SUCCESS_CODE == code;
    }

    @JsonIgnore
    public boolean error() {
        return !success();
    }
}
