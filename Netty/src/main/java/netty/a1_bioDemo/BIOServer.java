package netty.a1_bioDemo;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 用 BIO 创建一个服务器
 */
public class BIOServer {
    public static void main(String[] args) throws IOException {

        // 1. 创建一个线程池
        // 2. 如果有客户端连接就创建一个线程与之通讯

        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();

        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动，Port: 6666");

        while (true) {
            System.out.println("线程id：" + Thread.currentThread().getId() + "  线程名：" + Thread.currentThread().getName());
            System.out.println("监听，等待客户端连接...");
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端");

            newCachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    handler(socket);
                }
            });
        }
    }

    // 和客户端通讯
    private static void handler(Socket socket) {
        try {
            System.out.println("线程id：" + Thread.currentThread().getId() + "  线程名：" + Thread.currentThread().getName());
            byte[] bytes = new byte[1025];
            InputStream inputStream = socket.getInputStream();

            while (true) {
                System.out.println("线程id：" + Thread.currentThread().getId() + "  线程名：" + Thread.currentThread().getName());
                System.out.println("read...");
                int len = inputStream.read(bytes);
                if (len != -1) {
                    System.out.println(new String(bytes, 0, len));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭和客户端的连接.");
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
