package netty.a1_bioDemo;

import java.nio.IntBuffer;

/**
 * Buffer 简单示例
 */
public class BasicBuffer {
    public static void main(String[] args) {
        // 创建一个Buffer，大小为5，即可存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);

        // 向Buffer存放数据
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * i);
        }

        // 从Buffer中读取数据
        // 读写切换!!!
        intBuffer.flip();
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
