package overflow;

/**
 * Created by xinszhou on 7/11/16.
 * <p>
 * VM args
 * -Xss=128k
 */
public class StackOverflowByRecursion {

    public double calculate() {
        double num = 23.43;
        return num + 20;
    }

    public int recursive(int workload) {
        if (workload >= 19) return recursive(workload / 2);
        else return 0;
    }

    public static void main(String[] args) {
        new StackOverflowByRecursion().recursive(2);
    }

}
