package com.jdragon.tljrobot.client.test.bestTyping;

import com.jdragon.tljrobot.tljutils.string.Comparison;

import java.util.ArrayList;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.05.19 11:44
 * @Description:
 */
public class ComparisonTest {
    public static void main(String[] args) {
        Comparison am = new Comparison();

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            stringBuilder.append("一个人聪明一点可以，但不要自以为聪明！否则，要么你是个傻瓜，要么全世界的人都是傻瓜。一个人糊涂一点可以，但不要糊涂一时。最难得的还是“聪明一时，糊涂一世”的人！一个人懒一点可以，但不能一直懒惰下去。该做的还得要做，并且还要做好，多少都要付出一些，否则，就是一条十足的寄生虫。一个人省一点可以，但是检省不是守财，否则，会为了金钱而扭曲一个人的人格，会成为一个守财奴。要知道，往往会花钱的人，更会赚钱！一个人讲究一点可以，但是讲究不是奢侈，讲究要有分寸，不要因为讲究让平凡的生活变了质。一个人宽容一点可以，但宽容不是放纵！再宽容也要有尊严，有辱人格尊严的，决不能宽容！一个人有点脾气可以，但有脾气不等于有实力。要学会沉默，储蓄知识财富，做个真正有个性、有脾气的人。不在沉默中爆发，就在沉默中灭亡！一个人苦一点可以，但吃苦时不能怕苦，苦尽甘来！吃得苦中苦，方为人上人！");
        }
        long start = System.currentTimeMillis();
        ArrayList<int[]> strMatch = am.getMatch(stringBuilder.toString(), stringBuilder.toString());
        System.out.println(stringBuilder.length()+"长度对比花费时间："+(System.currentTimeMillis()-start));
    }
}
