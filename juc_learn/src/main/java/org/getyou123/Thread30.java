package org.getyou123;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
class Student {
    int age;
    String name;
}

public class Thread30 {
    public static void main(String[] args) {
        AtomicReference<Student> studentAtomicReference = new AtomicReference<>();

        Student zhangsan = new Student(12, "zhangsan");
        Student lisi = new Student(13,"lisi");

        studentAtomicReference.set(zhangsan);

        System.out.println(studentAtomicReference.compareAndSet(zhangsan, lisi) + "\t" + studentAtomicReference.get().toString());
        System.out.println(studentAtomicReference.compareAndSet(zhangsan, lisi) + "\t" + studentAtomicReference.get().toString());

    }
}
