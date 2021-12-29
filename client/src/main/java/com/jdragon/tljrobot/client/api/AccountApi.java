package com.jdragon.tljrobot.client.api;

import com.alibaba.fastjson.JSONObject;
import com.jdragon.tljrobot.client.api.body.AccountDto;
import com.jdragon.tljrobot.client.config.HttpAddr;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.entry.History;
import com.jdragon.tljrobot.client.entry.TypingMatchVO;
import com.jdragon.tljrobot.client.event.online.TypeNumManagerThread;
import com.jdragon.tljrobot.tljutils.response.table.PageTable;
import com.jdragon.tljrobot.tljutils.zFeign.ZFeign;
import org.springframework.web.bind.annotation.*;

/**
 * <p></p>
 * <p>create time: 2021/12/28 23:24 </p>
 *
 * @author : Jdragon
 */
@ZFeign(baseUrl = HttpAddr.SERVER_ADDR_NEW, depth = "result", fallback = GlobalFallback.class)
public interface AccountApi {
    @PostMapping("/account/login")
    String login(@RequestBody AccountDto accountDto);

    @PostMapping("/account/register")
    String register(@RequestBody AccountDto accountDto);

    @GetMapping("/account/type/info")
    JSONObject getMyInfo();

    @GetMapping("/account/type/history")
    PageTable<History> getMyTypeHistory(@RequestParam(Constant.PAGE_NUM) Integer pageNum,
                                        @RequestParam(Constant.PAGE_SIZE) Integer pageSize);

    @PostMapping("/account/type/changeNum")
    TypeNumManagerThread.NumTemp changeNum(@RequestBody TypeNumManagerThread.NumTemp numTemp);

    @GetMapping("/account/match/today")
    TypingMatchVO getTodayMatch(@RequestParam("mobile") Boolean mobile);
}
