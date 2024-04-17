package org.example;

import org.junit.Test;

import java.io.*;

public class FileReaderWriterLearn {


    // 测试按照字符进行数据的读取
    @Test
    public void testFileReader() throws IOException {
        // 构造FileReader
        File file = new File("/Users/xxx/IdeaProjects/LEARNING/java_basic_learn/src/main/resources/jdbc.properties");
        FileReader fileReader = new FileReader(file);
        // 注意是按照字符进行读取的 读取到-1则表示结尾
        System.out.println("单个字符读取");
        while (true) {
            int read = fileReader.read();
            if (read == -1) {
                break;
            }
            System.out.println((char) (read));
        }

        fileReader.close();


        fileReader = new FileReader(file);
        System.out.println("char数组读取");
        char[] chars = new char[10];
        int read = fileReader.read(chars);
        while (read != -1) {
            for (int i = 0; i < read; i++) { // 注意这里的最后读取是不完全的
                System.out.print(chars[i]);
            }
            System.out.println();
            read = fileReader.read(chars);

        }
        fileReader.close();
    }


    // 实现数据按照字符写出
    // 覆盖写 new FileWriter()
    //`public void write(int c)` ：写出单个字符。
    //`public void write(char[] cbuf) `：写出字符数组。
    //`public void write(char[] cbuf, int off, int len) `：写出字符数组的某一部分。off：数组的开始索引；len：写出的字符个数。
    //`public void write(String str) `：写出字符串。
    //`public void write(String str, int off, int len)` ：写出字符串的某一部分。off：字符串的开始索引；len：写出的字符个数。
    //`public void flush() `：刷新该流的缓冲。
    //`public void close()` ：关闭此流。
    @Test
    public void testFileWriter() throws IOException {
        File file = new File("/Users/xxx/IdeaProjects/LEARNING/java_basic_learn/src/main/resources/jdbc.properties1");
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write("test for love 中国");
        fileWriter.close();
    }

    // 实现追加写
    @Test
    public void testFileWriterAppend() throws IOException {
        File file = new File("/Users/xx/IdeaProjects/LEARNING/java_basic_learn/src/main/resources/jdbc.properties1");
        FileWriter fileWriter = new FileWriter(file, true);
        fileWriter.write("test for love 中国");
        fileWriter.close();
    }
    @Test
    public void test001() throws IOException {
        String csvFile = "src/main/resources/1.csv"; // 替换为你的 CSV 文件路径

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            StringBuilder csvData = new StringBuilder();

            while ((line = br.readLine()) != null) {
                // 将每一行追加到 StringBuilder 中
                String[] split = line.split(",", -1);
                csvData.append("\"");
                csvData.append(split.length>0?split[0]:"");
                csvData.append("\"");
                csvData.append("->");
                csvData.append("\"");
                csvData.append(split.length>1?split[1]:"");
                csvData.append("\"");
                csvData.append(" [label = ");
                csvData.append("\"");
                csvData.append(split.length>2?split[2]:"");
                csvData.append("\"");
                csvData.append("];");
                csvData.append("\n"); // 添加换行符（可选）
            }

            // 将整个 CSV 数据存储到一个字符串中
            String csvContent = csvData.toString();

            // 输出整个 CSV 数据字符串
            System.out.println(csvContent);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
