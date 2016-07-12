package annotation.mkyong;

/**
 * Created by xinszhou on 16/7/12.
 */
@TestInfo(priority = TestInfo.Priority.HIGH,
        createdBy = "xinszhou",
        tags = {"sales", "test"})
public class TestExample {

    @Test
    void testA() {
        if (true) {
            throw new RuntimeException("This test will fail");
        }
    }

    @Test(enabled = false)
    void testB() {
        if (false) {
            throw new RuntimeException("This test always pass");
        }
    }

    @Test
    void testC() {
        if(10 > 1) {}
    }
}