package netty.a1_bioDemo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 通过 FileChannel 写入数据到文件
 */
public class NIOFileChannel_1 {

    public static void main(String[] args) throws IOException {
        String str = "Hello, NIO.";
        // 创建一个输出流 -> channel
        FileOutputStream fileOutputStream = new FileOutputStream("E:\\test.txt");

        // 通过 fileOutputStream 获取对应的 FileChannel (FileChannelImpl)
        FileChannel fileChannel = fileOutputStream.getChannel();

        // 创建一个缓冲区 ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // 将 str 放入 byteBuffer
        byteBuffer.put(str.getBytes());

        // 对 byteBuffer 进行 flip() 操作
        byteBuffer.flip();

        // 将 byteBuffer 数据写入到 fileChannel
        fileChannel.write(byteBuffer);
        fileOutputStream.close();
    }

}
