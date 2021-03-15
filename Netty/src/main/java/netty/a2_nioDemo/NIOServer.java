package netty.a2_nioDemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    public static void main(String[] args) throws IOException {
        // 创建 ServerSocketChannel -- ServerSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 得到一个 Selector 对象
        Selector selector = Selector.open();
        // 绑定一个监听端口
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        // 设置非阻塞
        serverSocketChannel.configureBlocking(false);
        // 把 ServerSocketChannel 的 OP_ACCEPT 注册到 selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


        // 循环等待客户端连接
        while (true) {
            // 等待1秒，看是否有时间发送
            if (selector.select(1000) == 0) {
                System.out.println("服务器等待1秒，无连接...");
                continue;
            }

            // 1. 如果返回>0, 表示已经获取到关注事件
            // 2. selectionKeys 返回关注事件的集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            // 使用迭代器遍历事件
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

            while (keyIterator.hasNext()) {
                // 获取到 SelectionKey
                SelectionKey key = keyIterator.next();
                // 根据 key 对应的通道发生的事件做相应的处理
                // 新客户端连接事件
                if (key.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功，生成了一个 socketChannel: " + socketChannel.hashCode());
                    // 将 socketChannel 设置为非阻塞
                    socketChannel.configureBlocking(false);
                    // 将 socketChannel 注册到 selector, 关注 OP_READ事件, 同时绑定一个 Buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                // OP_READ 事件
                if (key.isReadable()) {
                    // 通过 key 反向获取到 channel
                    SocketChannel channel = (SocketChannel) key.channel();
                    // 获取 channel 关联的 buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    channel.read(buffer);
                    System.out.println("From 客户端: " + new String(buffer.array()));
                }
            }
            keyIterator.remove();
        }

    }
}
 