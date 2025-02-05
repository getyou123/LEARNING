package org.example;

import org.apache.commons.io.FilenameUtils;
import org.junit.Test;

import java.io.File;

public class FileNameUtilsTest {
    @Test
    public void test001(){
        String path = "/user/hao/1.txt";
        // 获取文件的name
        System.out.println(FilenameUtils.getName(path));
        // -- 1.txt
        // 文件的名字 不含扩展名
        System.out.println(FilenameUtils.getBaseName(path));
        // -- 1
        // 文件的扩展名
        System.out.println(FilenameUtils.getExtension(path));
        // --txt
    }
}
