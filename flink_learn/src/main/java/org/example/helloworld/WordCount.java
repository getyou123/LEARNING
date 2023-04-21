package org.example.helloworld;

public class WordCount {
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
