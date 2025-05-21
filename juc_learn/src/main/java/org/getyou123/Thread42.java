package org.getyou123;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

class MyRes42 {
    int num;
    boolean aBoolean;
}

public class Thread42 {
    public static void main(String[] args) {
        // 打印虚拟机基本信息
        System.out.println(VM.current().details());
        /*
         * # VM mode: 64 bits
         * # Compressed references (oops): 3-bit shift
         * # Compressed class pointers: 3-bit shift
         * # Object alignment: 8 bytes
         * #                       ref, bool, byte, char, shrt,  int,  flt,  lng,  dbl
         * # Field sizes:            4,    1,    1,    2,    2,    4,    4,    8,    8
         * # Array element sizes:    4,    1,    1,    2,    2,    4,    4,    8,    8
         * # Array base offsets:    16,   16,   16,   16,   16,   16,   16,   16,   16
         */

        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        /*
         * java.lang.Object object internals:
         * OFF  SZ   TYPE DESCRIPTION               VALUE
         *   0   8        (object header: mark)     0x0000000000000001 (non-biasable; age: 0)
         *   8   4        (object header: class)    0x00000f68
         *  12   4        (object alignment gap)
         * Instance size: 16 bytes
         * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
         */

        MyRes42 myRes42 = new MyRes42();
        System.out.println(ClassLayout.parseInstance(myRes42).toPrintable());
        /*
         * org.getyou123.MyRes42 object internals:
         * OFF  SZ      TYPE DESCRIPTION               VALUE
         *   0   8           (object header: mark)     0x0000000000000001 (non-biasable; age: 0)
         *   8   4           (object header: class)    0x000abad0
         *  12   4       int MyRes42.num               0
         *  16   1   boolean MyRes42.aBoolean          false
         *  17   7           (object alignment gap)
         * Instance size: 24 bytes
         * Space losses: 0 bytes internal + 7 bytes external = 7 bytes total
         */

    }
}
