package easy;

/**
 * Created by xinszhou on 16/7/5.
 */
public class AddTwoNumbers {
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode resultNode = new ListNode(0);
        ListNode currentNode = resultNode;
        int carryover = 0;
        while (l1 != null) {

            if (l2 != null) {
                ListNode newNode = new ListNode((l1.val + l2.val + carryover) % 10);
                carryover = (l1.val + l2.val + carryover) / 10;
                currentNode.next = newNode;
                currentNode = newNode;
                l2 = l2.next;
            } else {
                currentNode.next = new ListNode((l1.val + carryover) % 10);
                carryover = (l1.val + carryover) / 10;
                currentNode = currentNode.next;
            }
            l1 = l1.next;
        }

        while (l2 != null) {
            currentNode.next = new ListNode((carryover + l2.val) % 10);
            carryover = (carryover + l2.val) / 10;
            currentNode = currentNode.next;
            l2 = l2.next;
        }

        if (carryover > 0) {
            currentNode.next = new ListNode(1);
        }

        return resultNode.next;
    }

    public static void main(String[] args) {
        ListNode ln1 = new ListNode(2);
        ln1.next = new ListNode(4);
        ln1.next.next = new ListNode(3);

        ListNode ln2 = new ListNode(5);
        ln2.next = new ListNode(6);
        ln2.next.next = new ListNode(4);

        ListNode mergedNode = new AddTwoNumbers().addTwoNumbers(ln1, ln2);

        while (mergedNode != null) {
            System.out.println(mergedNode.val);
            mergedNode = mergedNode.next;
        }
    }
}
