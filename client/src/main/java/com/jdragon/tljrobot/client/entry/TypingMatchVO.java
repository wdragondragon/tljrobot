package com.jdragon.tljrobot.client.entry;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author JDragon
 * @Date 2021.08.29 下午 10:41
 * @Email 1061917196@qq.com
 * @Des:
 */

@Data
public class TypingMatchVO {

    private Integer id;

    private Date holdDate;

    private Integer articleId;

    private String author;

    private Integer matchType;

    private ArticleDto article;
}
