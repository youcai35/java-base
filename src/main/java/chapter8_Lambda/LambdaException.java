package chapter8_Lambda;

import org.junit.Test;

/**
 * 简单对比lambda写法和普通写法异常对比
 */
public class LambdaException {

    @Test
    public void simple() {
        Runnable runnable = () ->{
            System.out.println("lambda is run");
            throw new RuntimeException("lambda exception");
        };
        runnable.run();
    }

    // 匿名内部类
    @Test
    public void normal(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("normal is run");
                throw new RuntimeException("normal exception");
            }
        };
        runnable.run();
    }
}
