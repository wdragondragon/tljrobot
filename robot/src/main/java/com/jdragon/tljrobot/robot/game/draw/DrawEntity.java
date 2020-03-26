package com.jdragon.tljrobot.robot.game.draw;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.26 09:12
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DrawEntity {

    List<String> recordList;

    boolean delete;
}
