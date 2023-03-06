package org.example;

import org.junit.Test;

import java.io.*;

public class FileInputStreamOutStreamLearn {

    @Test
    public void test02() throws IOException {
        // 使用文件名称创建流对象
        FileInputStream fis = new FileInputStream("read.txt");
        // 定义变量，保存数据
        int b;
        // 循环读取
        while ((b = fis.read()) != -1) {
            System.out.println((char) b);
        }
        // 关闭资源
        fis.close();
    }

    public void test1() {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            File file1 = new File("pony.jpg");
            File file2 = new File("pony_secret.jpg");
            fis = new FileInputStream(file1);
            fos = new FileOutputStream(file2);

            //每次读入一个字节数组，效率高
            int len;
            byte[] buffer = new byte[1024];
            while ((len = fis.read(buffer)) != -1) {

                //  for(int i = 0;i < len;i++){ // 操作每个字节
                //      buffer[i] = (byte) (buffer[i] );
                //  }

                fos.write(buffer, 0, len);

            }
            System.out.println("数据写出成功");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
