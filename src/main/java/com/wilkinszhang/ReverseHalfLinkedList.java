package com.wilkinszhang;

class ListNode {
    int val;
    ListNode next;

    ListNode(int val) {
        this.val = val;
        this.next = null;
    }
}

public class ReverseHalfLinkedList {

    public static ListNode reverseFirstHalf(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        // 快慢指针找到链表的中点
        ListNode slow = head;
        ListNode fast = head;
        ListNode prev = null;

        // 快指针每次移动两步，慢指针每次移动一步，最后慢指针指向中点
        while (fast != null && fast.next != null) {
            fast = fast.next.next;

            // 反转前半部分
            ListNode nextNode = slow.next;
            slow.next = prev;
            prev = slow;
            slow = nextNode;
        }

        // 将反转后的前半部分与后半部分连接
        head.next = slow;

        return prev;  // 返回新的头节点
    }

    // 打印链表
    public static void printList(ListNode head) {
        ListNode current = head;
        while (current != null) {
            System.out.print(current.val + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }

    public static void main(String[] args) {
        // 创建一个链表 1 -> 2 -> 3 -> 4 -> 5 -> null
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        System.out.println("原链表：");
        printList(head);

        ListNode newHead = reverseFirstHalf(head);
        System.out.println("反转前半部分后的链表：");
        printList(newHead);
    }
}
