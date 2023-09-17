package org.example.org.getyou123;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class ZeroCopy {
    public static void main(String[] args) throws Exception {
        File file = new File("xxxxxx. Log");
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        FileChannel channel = raf.getChannel();
//Opens a socket channel and connects it to a remote address.
        SocketChannel socketChannel = SocketChannel.open(
                new InetSocketAddress("192.168.2.222", 9091)
        );
//Transfers bytes from this channel's file to the given writable byte chan
        channel.transferTo(0, channel.size(), socketChannel);
    }
}