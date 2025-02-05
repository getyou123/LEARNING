package org.example;


import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

public class FileUtilsTest {
    @Test
    public void test001() throws IOException {
        // 把整个文本内容读取为一个字符串
        String path  = Objects.requireNonNull(this.getClass().getResource("/1.txt")).getPath();
        String readFileToString = FileUtils.readFileToString(new File(path), StandardCharsets.UTF_8);
        System.out.println(readFileToString);

        // 把整个文本读为List<String>
        List<String> strings = FileUtils.readLines(new File(path), StandardCharsets.UTF_8);

        System.out.println("文件行数" + strings.size() + ":" + strings);

    }
}
