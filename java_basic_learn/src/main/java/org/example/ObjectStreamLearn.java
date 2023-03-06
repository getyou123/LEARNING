package org.example;

import lombok.Data;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;


@Data
class StructForSer implements Serializable {
    static final long serialVersionUID = 23234234234L;
    int i;
    int j;
    String name;

    public static int staticInt; // 这个不会序列化因为是类共有的
    public transient int age; // transient瞬态修饰成员,不会被序列化
}

public class ObjectStreamLearn {


    // 实现各种基础数据类型的写出到文件中
    @Test
    public void save() throws IOException {
        String name = "巫师";
        int age = 300;
        char gender = '男';
        int energy = 5000;
        double price = 75.5;
        boolean relive = true;

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("game"));
        oos.writeUTF(name);
        oos.writeInt(age);
        oos.writeChar(gender);
        oos.writeInt(energy);
        oos.writeDouble(price);
        oos.writeBoolean(relive);
        oos.close();
    }

    // 实现读取上述数据的写出
    @Test
    public void reload() throws IOException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("game"));
        String name = ois.readUTF();
        int age = ois.readInt();
        char gender = ois.readChar();
        int energy = ois.readInt();
        double price = ois.readDouble();
        boolean relive = ois.readBoolean();
        System.out.println(name + "," + age + "," + gender + "," + energy + "," + price + "," + relive);
        ois.close();
    }


    // 前提是这个类实现了Serializable接口
    // 且最好指定 serialVersionUID
    // 下面的实现对象的写出和重新读取
    @Test
    public void testObjectStream() throws IOException, ClassNotFoundException {
        StructForSer structForSer = new StructForSer();
        structForSer.setAge(123);
        structForSer.setName("Tom");

        // 写入
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("game"));
        oos.writeObject(structForSer);

        // 读取
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("game"));
        StructForSer o = (StructForSer) ois.readObject();
        System.out.println(o);

        // 多个写入&读取

        ArrayList<StructForSer> structForSers = new ArrayList<>();
        StructForSer structForSer1 = new StructForSer();
        structForSer1.setName("Jerry");
        structForSer1.setAge(23);
        structForSers.add(structForSer1);
        structForSers.add(structForSer);

        oos.writeObject(structForSers);

        ArrayList<StructForSer> readed = (ArrayList<StructForSer>) ois.readObject();
        System.out.println(readed);

        ois.close();
        oos.close();
    }


}
