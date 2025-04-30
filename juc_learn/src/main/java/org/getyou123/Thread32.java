package org.getyou123;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.omg.CORBA.TIMEOUT;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

@AllArgsConstructor
@ToString
@NoArgsConstructor
@Data
class Book {
    int id;
    String name;
}

public class Thread32 {

    /**
     * 基础的操作方式
     */
    public static void atomicStampedReferenceBasic() {
        Book book = new Book(1, "唐诗");
        // 加上版本号
        AtomicStampedReference<Book> bookAtomicStampedReference = new AtomicStampedReference<Book>(book, 1);
        System.out.println(bookAtomicStampedReference.getReference() + "\t" + bookAtomicStampedReference.getStamp());
    }

    /**
     * 多线程中操作，防止ABA问题
     */
    private static void atomicStampedReferenceInMultiTread() {
        AtomicStampedReference<Integer> integerAtomicStampedReference =
                new AtomicStampedReference<Integer>(10, 1);
        new Thread(() -> {

            try {
                TimeUnit.MICROSECONDS.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // 读取并改变
            System.out.println(
                    Thread.currentThread().getName() +
                            "\t" +
                            integerAtomicStampedReference.compareAndSet(10, 11, 1, 2)
                            + "\t"
                            + integerAtomicStampedReference.getStamp()
                            + "\t"
                            + integerAtomicStampedReference.getReference()
            );

            // 重新改回去
            System.out.println(
                    Thread.currentThread().getName() +
                            "\t" +
                            integerAtomicStampedReference.compareAndSet(11, 10, 2, 3)
                            + "\t"
                            + integerAtomicStampedReference.getStamp()
                            + "\t"
                            + integerAtomicStampedReference.getReference()

            );

        }, "AA").start();

        new Thread(() -> {

            Integer reference = integerAtomicStampedReference.getReference();
            int stamp = integerAtomicStampedReference.getStamp();

            System.out.println(Thread.currentThread().getName()
                    + "\t 首次获取到的情况是 "
                    + reference
                    + "\t"
                    + stamp);

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


            // 1s读取并改变 虽然值还是10但是版本号不正确了
            System.out.println(
                    Thread.currentThread().getName() +
                            "\t" +
                            integerAtomicStampedReference.compareAndSet(reference, 11, stamp, 2)
                            + "\t"
                            + integerAtomicStampedReference.getStamp()
                            + "\t"
                            + integerAtomicStampedReference.getReference()
            );

        }, "BB").start();


    }

    public static void main(String[] args) {

//        atomicStampedReferenceBasic();

        atomicStampedReferenceInMultiTread();

    }


}
