package org.example.flink_training.pojo;

import java.io.Serializable;

public class WordCount implements Serializable {
    public String word;
    public int count;

    public WordCount() {
    }

    public WordCount(String word, int count) {
        this.word = word;
        this.count = count;
    }

    @Override
    public String toString() {
        return "(" + word + ", " + count + ")";
    }
}
