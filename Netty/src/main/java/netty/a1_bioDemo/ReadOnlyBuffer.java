package netty.a1_bioDemo;

import java.nio.ByteBuffer;

/**
 * 只读 Buffer
 */
public class ReadOnlyBuffer {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);
        for (int i = 0; i < 64; i++) {
            buffer.put((byte) i);
        }

        buffer.flip();

        // 得到一个只读的 Buffer
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer);

        // 读取
        while (readOnlyBuffer.hasRemaining()) {
            System.out.print(readOnlyBuffer.get());
        }

        // ReadOnlyBufferException
        readOnlyBuffer.put((byte) 100);

    }
}
