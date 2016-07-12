package annotation.mkyong;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by xinszhou on 16/7/12.
 *
 * Test 都是这样做的么, 很奇妙
 */
public class RunTest {

    public static void main(String[] args) {
        System.out.println("Testing...");

        int passed = 0, failed = 0, count = 0, ignore = 0;

        Class<TestExample> obj = TestExample.class;

        if(obj.isAnnotationPresent(TestInfo.class)) {
            Annotation annotation = obj.getAnnotation(TestInfo.class);
            TestInfo info = (TestInfo) annotation;

            System.out.printf("%nPriority :%s", info.priority());
            System.out.printf("%nCreatedBy :%s", info.createdBy());

        }

        for(Method method: obj.getDeclaredMethods()) {
            if(method.isAnnotationPresent(Test.class)) {
                Test test = method.getAnnotation(Test.class);
                count ++;
                if(test.enabled()) {
                    System.out.println("enabled test method name: " + method.getName());

                    try {
                        method.invoke(obj.newInstance());
                        passed ++;
                    } catch (Throwable th) {
                        failed ++;
                    }
                } else {
                    ignore ++;
                }
            }
        }

        System.out.printf("%nResult : Total : %d, Passed: %d, Failed %d, Ignore %d%n", count, passed, failed, ignore);

    }
}
