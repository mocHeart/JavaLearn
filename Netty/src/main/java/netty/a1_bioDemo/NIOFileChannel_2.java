package netty.a1_bioDemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 通过 FileChannel 读取文件到程序
 */
public class NIOFileChannel_2 {

    public static void main(String[] args) throws IOException {
        // 创建文件的输入流
        File file = new File("E:\\test.txt");
        FileInputStream fileInputStream = new FileInputStream(file);

        // 通过 fileInputStream 获取对应的 FileChannel (FileChannelImpl)
        FileChannel fileChannel = fileInputStream.getChannel();

        // 创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

        // 将数据读到Buffer
        fileChannel.read(byteBuffer);

        // 字节数据转为String输出
        System.out.println(new String(byteBuffer.array()));
        fileInputStream.close();
    }

}
