package netty.a1_bioDemo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 *  使用通道的 transferFrom()方法拷贝文件
 */
public class NIOFileChannel_4 {
    public static void main(String[] args) throws IOException {
        // 创建相关流
        FileInputStream fileInputStream = new FileInputStream("E:\\1.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream("E:\\2.jpg");

        // 获取流对应的 fileChannel
        FileChannel sourceCh = fileInputStream.getChannel();
        FileChannel dstCh = fileOutputStream.getChannel();

        // 使用 transferFrom 完成拷贝
        dstCh.transferFrom(sourceCh, 0, sourceCh.size());

        // 关闭通道和流
        sourceCh.close();
        dstCh.close();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
