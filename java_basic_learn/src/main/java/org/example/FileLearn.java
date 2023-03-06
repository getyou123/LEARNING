package org.example;

import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLService;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class FileLearn {

    @Test
    public void testAbsoluteFile() {
        String dir = "/Users/XXX/Downloads/testDir";
        File file = new File(dir);

        // 获取绝对路径
        File absoluteFile = file.getAbsoluteFile();
        System.out.println("获取绝对路径：" + absoluteFile);

        // 注意在不同的os中使用不同的 File.separator
        String dir1 = "/Users/XXX/Downloads/testDir" + File.separator + "text.txt";
        System.out.println("获取新的绝对路径：" + new File(dir1).getAbsoluteFile());
    }

    @Test
    public void testGetFileBaseInfo() throws IOException {
        File f1 = new File("/Users/XXX/Downloads/testDir"); //绝对路径
        System.out.println("文件/目录的名称：" + f1.getName());
        System.out.println("文件/目录的构造路径名：" + f1.getPath());
        System.out.println("文件/目录的绝对路径名：" + f1.getAbsolutePath());
        System.out.println("文件/目录的父目录名：" + f1.getParent());


        File f2 = new File("pom.xml");//绝对路径，从根路径开始
        System.out.println("文件/目录的名称：" + f2.getName());
        System.out.println("文件/目录的构造路径名：" + f2.getPath());
        System.out.println("文件/目录的绝对路径名：" + f2.getAbsolutePath());
        System.out.println("文件/目录的父目录名：" + f2.getParent());
        System.out.println("文件的长度：" + f2.length());
        System.out.println("文件的最近修改时间：" + f2.lastModified());

    }

    @Test
    public void testFileList() {
        File f1 = new File("/Users");
        String[] list = f1.list();
        System.out.println("路径下的文件名：" + Arrays.toString(list));

        File[] files = f1.listFiles();
        System.out.println("路径下的文件：" + Arrays.toString(files));
    }


    // 实现重命名
    @Test
    public void testRename() {
        File f1 = new File("/xxx");
        f1.renameTo(new File("/zzzz"));
    }


    @Test
    public void testFileInfo() {
        File f1 = new File("/xxx");
        System.out.println("路径文件是否存在：" + f1.exists());
        System.out.println("是否是文件目录：" + f1.isDirectory());
        System.out.println("是否是文件：" + f1.isFile());
        System.out.println("是否可读：" + f1.canRead());
        System.out.println("是否可写：" + f1.canWrite());
        System.out.println("是否隐藏：" + f1.isHidden());
    }


    // 文件的创建、删除等
    @Test
    public void testCreateDirFile() throws IOException {
        // delete方法，如果此File表示目录，则目录必须为空才能删除。
        File f1 = new File("/xxx");

        System.out.print("文件的删除--不进入回收站的：");
        boolean delete = f1.delete();
        System.out.println(delete);

        System.out.print("文件目录的创建：父目录不存在也会建立");
        boolean mkdirs = f1.mkdirs();
        System.out.println(mkdirs);


        System.out.print("文件的创建：");
        boolean newFile = f1.createNewFile();
        System.out.println(newFile);

    }

}
