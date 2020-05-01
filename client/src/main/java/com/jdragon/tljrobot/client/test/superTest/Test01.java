package com.jdragon.tljrobot.client.test.superTest;

import java.lang.reflect.Constructor;

class Book{
    String name;//图书名称
    int id,price;//图书编号和价格
    //空的构造方法
    private Book() {

    }
    //带两个参数的构造方法
    protected Book(String _name,int _id) {
        this.name=_name;
        this.id=_id;
    }
    //带可变参数的构造方法
    public Book(String...strings) {
        if(0<strings.length) {
            id=Integer.valueOf(strings[0]);
        }
        if(1<strings.length) {
            price=Integer.valueOf(strings[1]);
        }

    }
    public void print() {
        System.out.println("name="+name);
        System.out.println("id="+id);
        System.out.println("price="+price);
    }
}




public class Test01 {

    public static void main(String[] args) {
        // 动态获取Book类的类类型
        Class book=Book.class;
        Constructor declaredConstructors[]=book.getDeclaredConstructors();
        for(int i=0;i<declaredConstructors.length;i++) {
            Constructor con=declaredConstructors[i];
            //判断构造方法的参数是否可变
            System.out.println("查看是否允许带可变数量的参数："+con.isVarArgs());
            System.out.println("该构造方法的入口参数类型依次为：");
            Class parameterTypes[]=con.getParameterTypes();
            for(int j=0;j<parameterTypes.length;j++) {
                System.out.println(""+parameterTypes[j]);
            }
            System.out.println("该构造方法可能抛出的异常类型为：");
            Class exceptionTypes[]=con.getExceptionTypes();
            for(int j=0;j<exceptionTypes.length;j++) {
                System.out.println(""+exceptionTypes[j]);
            }
            Book book1=null;
            while(book1==null) {
                try {
                    //如果该构造方法的访问权限为private,即抛出异常
                    if(i==0) {
                        Object parameters[]=new Object[] {new String[] {"100","200"}};
                        book1=(Book)con.newInstance(parameters);
                        //通过执行两个参数的构造方法实例化book1

                    }
                    else if(i==1) {
                        //通过执行默认构造方法实例化book1
                        book1=(Book)con.newInstance("Java教程",10);

                    }
                    else {
                        book1=(Book)con.newInstance();
                    }
                }catch(Exception e) {
                    System.out.println("在创建对象时抛出异常，下面执行setAccessible 方法");
                    con.setAccessible(true);
                }
            }
            book1.print();
            System.out.println("==================");
        }

    }

}
