package netty.a1_bioDemo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用一个Buffer完成文件的读写
 */
public class NIOFileChannel_3 {
public static void main(String[] args) throws IOException {
    FileInputStream fileInputStream = new FileInputStream("E:\\test.txt");
    FileChannel fileChannel_1 = fileInputStream.getChannel();

    FileOutputStream fileOutputStream = new FileOutputStream("E:\\test2.txt");
    FileChannel fileChannel_2 = fileOutputStream.getChannel();

    ByteBuffer byteBuffer = ByteBuffer.allocate(512);

    while (true) {
        // 读数据   一定要清空Buffer
        byteBuffer.clear();
        int read = fileChannel_1.read(byteBuffer);
        System.out.println("read: " + read);
        if (read == -1) {  // 未读到数据，表示已读完
            break;
        }

        // 写数据   一定要flip() 操作
        byteBuffer.flip();
        fileChannel_2.write(byteBuffer);
    }

    // 关闭流
    fileChannel_1.close();
    fileChannel_2.close();
}

}
