package org.example;

import java.util.Collections;
import java.util.PriorityQueue;

public class PriorityQeueLearn {
    public static void main(String[] args) {

        // 小顶堆
        PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();

        // 大顶堆
        PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(Collections.reverseOrder());


        // Add Element
        minHeap.add(10);
        minHeap.add(8);
        minHeap.add(9);
        minHeap.add(11);
        minHeap.add(2);

        System.out.println(minHeap.toString());
        // [2, 8, 9, 11, 10]

        System.out.println("minHeap.peek()" + "=>" + minHeap.peek());

        System.out.println("minHeap.poll()" + "=>" + minHeap.poll());

        System.out.println("minHeap.peek()" + "=>" + minHeap.peek());

        System.out.println("minHeap.size()" + "=>" + minHeap.size());


        while(!minHeap.isEmpty()){
            Integer poll = minHeap.poll();
            System.out.println(poll);
        }





        maxHeap.add(10);
        maxHeap.add(8);
        maxHeap.add(9);
        maxHeap.add(11);
        maxHeap.add(2);

        System.out.println(maxHeap.toString());
        // [11, 10, 9, 8, 2]

    }
}
