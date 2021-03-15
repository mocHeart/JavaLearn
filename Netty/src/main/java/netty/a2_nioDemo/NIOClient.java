package netty.a2_nioDemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {
    public static void main(String[] args) throws IOException {
        // 获得一个 SocketChannel
        SocketChannel socketChannel = SocketChannel.open();
        // 设置非阻塞
        socketChannel.configureBlocking(false);
        // 提供服务器的ip和端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.2", 6666);

        // 连接服务器
        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("客户端还未连接成功，非阻塞...");
            }
        }

        // 连接成功，就发送数据给服务器
        String info = "Hello, NIO!";
        ByteBuffer buffer = ByteBuffer.wrap(info.getBytes());
        socketChannel.write(buffer);

        System.in.read();
    }
}
