package com.wilkinszhang;

// 某区间内两个一组反转链表
public class ReverseInPairsInRange {
    public static ListNode reverse(ListNode head, int left, int right) {
        if(head==null || left>=right)return head;
        ListNode dummy =new ListNode(0);
        dummy.next=head;
        ListNode prev=dummy;
        for(int i=1;i<left;i++){
            prev=prev.next;
        }
        ListNode curr=prev.next;
        int numPair=(right-left+1)/2;
        for(int i=0;i<numPair;i++){
            ListNode next=curr.next;
            prev.next=next;
            curr.next=next.next;
            next.next=curr;
            prev=curr;
            curr=curr.next;
        }
        return dummy.next;
    }

    public static void main(String []args){
        ListNode head=new ListNode(1);
        ListNode p=head;
        for(int i=2;i<=6;i++){
            p.next=new ListNode(i);
            p=p.next;
        }
        ListNode newHead=reverse(head,2,5);
        p=newHead;
        while(p!=null){
            System.out.println(p.val+" ");
            p=p.next;
        }
    }
}
