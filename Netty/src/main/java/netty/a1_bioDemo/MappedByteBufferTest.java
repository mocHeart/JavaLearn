package netty.a1_bioDemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * MappedByteBuffer 可以让文件直接在内存中（堆外的内存）修改，由NIO同步到文件
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("E://test.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();

        /**
         * 参数1： FileChannel.MapMode.READ_WRITE  使用的读写模式
         * 参数2: 0 可以直接修改的起始位置
         * 参数3: 5 映射到内存的大小
         */
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0, (byte) 'A');
        mappedByteBuffer.put(3, (byte) '9');

        randomAccessFile.close();
    }
}
