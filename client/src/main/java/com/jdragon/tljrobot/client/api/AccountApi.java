package com.jdragon.tljrobot.client.api;

import com.alibaba.fastjson.JSONObject;
import com.jdragon.tljrobot.client.api.body.AccountDto;
import com.jdragon.tljrobot.client.config.HttpAddr;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.entry.ArticleDto;
import com.jdragon.tljrobot.client.entry.History;
import com.jdragon.tljrobot.client.entry.History2;
import com.jdragon.tljrobot.client.entry.HistoryDto;
import com.jdragon.tljrobot.client.entry.TypingMatchVO;
import com.jdragon.tljrobot.client.event.online.TypeNumManagerThread;
import com.jdragon.tljrobot.tljutils.response.table.PageTable;
import com.jdragon.tljrobot.tljutils.zFeign.ZFeign;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p></p>
 * <p>create time: 2021/12/28 23:24 </p>
 *
 * @author : Jdragon
 */
@ZFeign(baseUrl = HttpAddr.SERVER_ADDR_NEW, basePath = "/account", depth = "result", fallback = GlobalFallback.class)
public interface AccountApi {
    @PostMapping("/login")
    String login(@RequestBody AccountDto accountDto);

    @PostMapping("/register")
    String register(@RequestBody AccountDto accountDto);

    @GetMapping("/type/info")
    JSONObject getMyInfo();

    @GetMapping("/type/history")
    PageTable<History> getMyTypeHistory(@RequestParam(Constant.PAGE_NUM) Integer pageNum,
                                        @RequestParam(Constant.PAGE_SIZE) Integer pageSize);

    @PostMapping("/type/changeNum")
    TypeNumManagerThread.NumTemp changeNum(@RequestBody TypeNumManagerThread.NumTemp numTemp);

    @GetMapping("/match/today")
    TypingMatchVO getTodayMatch(@RequestParam("mobile") Boolean mobile);

    @PostMapping("/match/uploadTljMatchAch")
    String uploadTljMatchAch(@RequestBody History history);

    @GetMapping("/match/getPCTljMatchAchByDate")
    PageTable<History2> getPCTljMatchAchByDate(@RequestParam("date") Date date,
                                               @RequestParam("matchType") Integer matchType,
                                               @RequestParam("mobile") Boolean mobile);

    @PostMapping("/history/uploadHistoryAndArticle")
    String uploadHistoryAndArticle(@RequestBody HistoryDto historyDto);

    @GetMapping("/article/getHistoryArticle")
    ArticleDto getArticleById(@RequestParam("articleId") Integer articleId);

}
