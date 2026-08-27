package org.wangyao.testjenkins02.code.io;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

/**
 *
 *
 * @author wangyao
 * @Date 2026/1/29 15:05
 *
 */
public class SimpleFileTransferTest implements Serializable {

//    private static final String aa = "C:\\Users\\17706\\Desktop\\aa.txt";
//    private static final String bb = "C:\\Users\\17706\\Desktop\\bb.txt";

    // 使用传统的 I/O 方法传输文件
    private long transferFile(File source, File des) throws IOException {
        long startTime = System.currentTimeMillis();

        if (!des.exists())
            des.createNewFile();

        // 创建输入输出流
        BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(source.toPath()));
        BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(des.toPath()));

        // 使用数组传输数据
        byte[] bytes = new byte[1024 * 1024];
        int len;
        while ((len = bis.read(bytes)) != -1) {
            bos.write(bytes, 0, len);
        }

        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    // 使用 NIO 方法传输文件
    private long transferFileWithNIO(File source, File des) throws IOException {
        long startTime = System.currentTimeMillis();

        if (!des.exists())
            des.createNewFile();

        // 创建随机存取文件对象
        RandomAccessFile read = new RandomAccessFile(source, "rw");
        RandomAccessFile write = new RandomAccessFile(des, "rw");

        // 获取文件通道
        FileChannel readChannel = read.getChannel();
        FileChannel writeChannel = write.getChannel();

        // 创建并使用 ByteBuffer 传输数据
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024);
        while (readChannel.read(byteBuffer) > 0) {
            byteBuffer.flip();
            writeChannel.write(byteBuffer);
            byteBuffer.clear();
        }

        // 关闭文件通道
        writeChannel.close();
        readChannel.close();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public static void main(String[] args) throws IOException {

        List<String> aa = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h");

        aa.forEach(
                e -> {
                    if (e.equals("a")) {
                        return;
                    }
                    System.out.printf("%s\n", e);
                }
        );

//        SimpleFileTransferTest simpleFileTransferTest = new SimpleFileTransferTest();
//        File sourse = new File("D:\\迅雷下载\\阳光电影dygod.org.南京照相馆.2025.HD.1080P.国语中英双字.mkv");
//        File des = new File("D:\\迅雷下载\\io.mkv");
//        File nio = new File("D:\\迅雷下载\\nio.mkv");
//
//        // 比较传统的 I/O 和 NIO 传输文件的时间
//        long time = simpleFileTransferTest.transferFile(sourse, des);
//        System.out.println(time + "：普通字节流时间");
//
//        long timeNio = simpleFileTransferTest.transferFileWithNIO(sourse, nio);
//        System.out.println(timeNio + "：NIO时间");
    }

    private void test(){
        System.out.printf("sssas");
    }
}