package com.jdragon.tljrobot.tljutils.zFeign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.08.01 10:32
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpException extends RuntimeException{
    private String message;

    public HttpException(Throwable e){
        super(e);
    }
}
