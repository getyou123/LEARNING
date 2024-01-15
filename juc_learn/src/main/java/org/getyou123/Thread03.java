package org.getyou123;

public class Thread03 {

    public static void main(String[] args) {

    }
}

class sharedNum {
    int num = 0;

    public synchronized void incr() {
        if (num == 0) {
            num++;
        }
    }
}
