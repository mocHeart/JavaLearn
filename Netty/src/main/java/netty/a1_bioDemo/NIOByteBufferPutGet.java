package netty.a1_bioDemo;

import java.nio.ByteBuffer;

public class NIOByteBufferPutGet {
    public static void main(String[] args) {
        // 创建一个 Buffer
        ByteBuffer buffer = ByteBuffer.allocate(64);

        // 类型化方式放入数据
        buffer.putInt(100);
        buffer.putLong(9);
        buffer.putLong('尚');
        buffer.putShort((short) 4);

        // 取出
        buffer.flip();

        // 输出
        System.out.println(buffer.getInt());   // 100
        System.out.println(buffer.getLong());  // 9
        System.out.println(buffer.getChar());  //
        System.out.println(buffer.getChar());  //
    }
}
