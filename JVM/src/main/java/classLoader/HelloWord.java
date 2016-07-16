package classLoader;

/**
 * Created by xinszhou on 16/7/6.
 *
 * 修改了 HelloWorld 的 class 文件, 但是没有修改 Hello world 文件,
 * 当执行 main 方法时, intellij 找到的还是老的 class 文件,
 * 显示的内容就是更新完以后的 .class 文件的输出
 */
public class HelloWord {
    public static void main(String[] args) {
        String regards = "The Lannister send their regards";
        System.out.println(regards);
    }
}
