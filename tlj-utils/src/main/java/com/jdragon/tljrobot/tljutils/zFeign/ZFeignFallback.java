package com.jdragon.tljrobot.tljutils.zFeign;

/**
 * <p></p>
 * <p>create time: 2021/12/29 0:47 </p>
 *
 * @author : Jdragon
 */
public interface ZFeignFallback {
    void fallback(HttpException httpException);
}
