package com.jdragon.tljrobot.tljutils.zFeign;

/**
 * <p></p>
 * <p>create time: 2021/12/29 1:04 </p>
 *
 * @author : Jdragon
 */
public class ZFeignFallbackImpl implements ZFeignFallback{
    @Override
    public Object fallback(HttpException httpException) {
        // do nothing
        return null;
    }
}
