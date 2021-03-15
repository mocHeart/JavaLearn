package netty.a1_bioDemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * 通过多个 Buffer （即Buffer 数组）来完成读写操作，即 Scattering 和 Gathering
 */
public class ScatteringAndGatheringTest {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        // 绑定端口到 socket，并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        // 创建 buffer 数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        // 等待客户端连接(telnet)
        SocketChannel socketChannel = serverSocketChannel.accept();
        int messageLength = 8;  // 假设从客户端接收到8个字节
        while (true) {
            int byteRead = 0;
            while (byteRead < messageLength) {
                long l = socketChannel.read(byteBuffers);
                byteRead += l;   // 累计读取到的字节数
                System.out.println("byteRead=" + byteRead);
                // 使用流打印，观察buffer的position和limit
                Arrays.stream(byteBuffers).map(buffer -> "position=" + buffer.position() +
                        ", limit=" + buffer.limit()).forEach(System.out::println);
            }
            // 将所有的buffer进行flip
            Arrays.asList(byteBuffers).forEach(Buffer::flip);

            // 将数据读取显示到客户端
            long byteWrite = 0;
            while (byteWrite < messageLength) {
                long l = socketChannel.write(byteBuffers);
                byteWrite += l;
            }

            // 将所有的 buffer 进行clear
            Arrays.asList(byteBuffers).forEach(Buffer::clear);
            System.out.println("byteRead=" + byteRead + ", byteWrite=" + byteWrite +
                    ", messageLength=" + messageLength);
        }
    }
}
