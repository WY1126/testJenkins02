//package org.wangyao.testjenkins02.code.leecode;
//
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.PriorityQueue;
//
///**
// * Definition for singly-linked list.
// * public class ListNode {
// *     int val;
// *     ListNode next;
// *     ListNode(int x) { val = x; }
// * }
// */
///**
// * Definition for singly-linked list.
// * public class ListNode {
// *     int val;
// *     ListNode next;
// *     ListNode() {}
// *     ListNode(int val) { this.val = val; }
// *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
// * }
// */
//class Solution {
//    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
//        // 为空补0
//        ListNode tempA = l1;
//        ListNode tempB = l2;
//        ListNode resultNode = new ListNode(-1);
//        ListNode outNode = resultNode;
//        int carry = 0;
//
//        while (tempA != null || tempB != null) {
//            // 定义相加的参数
//            int numA = tempA != null ? tempA.val : 0;
//            int numB = tempB != null ? tempB.val : 0;
//
//            int result = (numA + numB + carry) % 10;
//            carry = (numA + numB + carry) / 10;
//            outNode.next = new ListNode(result);
//            outNode = outNode.next;
//            if (tempA != null) {
//                tempA = tempA.next;
//            }
//            if (tempB != null) {
//                tempB = tempB.next;
//            }
//        }
//        if (carry > 0) {
//            outNode.next = new ListNode(carry);
//        }
//        return resultNode.next;
//    }
//}
