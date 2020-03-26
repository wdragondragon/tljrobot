package com.jdragon.tljrobot.client.test.jdk8Test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.function.*;



/**
 * Function<T,R> T 作为输入，返回的 R 作为输出
 * Supplier<R> 没有输入 , R 作为输出
 * Consumer<T> T 作为输入 ，没有输出
 * Predicate<T> T 作为输入 ，返回 boolean 值的输出
 * BinaryOperator<T> 两个 T 作为输入 ，T 同样是输出
 * UnaryOperator<T> 是 Function 的变种 ，输入输出者是 T
 */
public class FunctionTest {
    public static void main(String[] args){
        Function<Integer, UserJdk8Test> functionTest = Test::getUserById;
        Supplier<Test> supplierTest = Test::new;
        Consumer<Object> consumerTest = System.out::println;
        Predicate<UserJdk8Test> predicateTest = supplierTest.get()::equals;
        BinaryOperator<Integer> binaryOperatorTest = supplierTest.get()::addUserId;
        UnaryOperator<UserJdk8Test> unaryOperatorTest = supplierTest.get()::addUser;


        UserJdk8Test userJdk8Test = functionTest.apply(1);
        consumerTest.accept(supplierTest.get());
        consumerTest.accept(predicateTest.test(userJdk8Test));
        consumerTest.accept(binaryOperatorTest.apply(1,2));
        consumerTest.accept(unaryOperatorTest.apply(userJdk8Test));

        Test.userSupplier(consumerTest,supplierTest);
    }
    static class Test{
        static UserJdk8Test getUserById(Integer id){
            return new UserJdk8Test(id,id.toString());
        }
        boolean equals(UserJdk8Test userJdk8Test){
            return userJdk8Test.equals(userJdk8Test);
        }
        UserJdk8Test addUser(UserJdk8Test userJdk8Test){
            userJdk8Test.setId(userJdk8Test.getId()+1);
            userJdk8Test.setUsername(userJdk8Test.getId().toString());
            return userJdk8Test;
        }
        Integer addUserId(Integer a,Integer b){
            return a+b;
        }
        static void userSupplier(Consumer consumer,Supplier supplier){
            consumer.accept("userSupplier:"+supplier.get());
        }
        @Override
        public String toString(){
            return "Test";
        }
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class UserJdk8Test{
        private Integer id;
        private String username;
        @Override
        public String toString(){
            return "[id="+id+",username="+username+"]";
        }
    }
}
