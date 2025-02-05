package org.example;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

public class IOUtilTest {
    /**
     * 通过io流读取文件
     */
    @Test
    public void test001() throws IOException {
        String path  = Objects.requireNonNull(this.getClass().getResource("/1.txt")).getPath();
        FileInputStream fileInputStream = new FileInputStream(path);
        List<String> strings = IOUtils.readLines(fileInputStream, StandardCharsets.UTF_8);
        System.out.println(strings);
    }
}
