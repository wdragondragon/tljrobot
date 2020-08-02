package com.jdragon.tljrobot.client.test.jdk8Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.19 09:05
 * @Description:
 */
public class StreamTest {
    public static void main (String[] args){
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        List<Integer> squaresList = numbers.stream().map(i -> i*i).distinct().sorted(Comparator.reverseOrder()).limit(3).collect(Collectors.toList());
        squaresList.forEach(System.out::println);

        List<String> strings = Arrays.asList("123","456","789");
        Stream<Stream<Character>> charStreamStream =strings.stream()
                .map(StreamTest::filterCharacter);
        charStreamStream.forEach((sm)-> sm.forEach(System.out::print));

        Stream<Character> characterStream = strings.stream().flatMap(StreamTest::filterCharacter);
        List<Character> characters = characterStream.collect(Collectors.toList());
        characters.forEach(System.out::println);


        List<HashMap<String,Integer>> numberMapList = numbers.stream()
                .map(i-> new HashMap<String,Integer>(){{put("value",i);}})
                .collect(Collectors.toList());
        System.out.println(numberMapList);
    }
    public static Stream<Character> filterCharacter(String str){
        List<Character> list = new ArrayList<>();
        for (Character character : str.toCharArray()) {
            list.add(character);
        }
        return list.stream();
    }
}
