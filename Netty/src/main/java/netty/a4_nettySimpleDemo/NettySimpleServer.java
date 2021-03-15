package netty.a4_nettySimpleDemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettySimpleServer {

    public static void main(String[] args) throws InterruptedException {


        // 创建 BoosGroup 和 WorkerGroup
        // 1. 创建两个线程组  bossGroup 和 workerGroup
        // 2. bossGroup 只是处理连接请求，真正和客户端的业务处理，会交给 workerGroup 完成
        // 3. 两个线程都是无限循环
        // 4. bossGroup 和 workerGroup 含有子线程（NioEventLoop）的个数 （电脑核数*2）
        EventLoopGroup bossGroup = new NioEventLoopGroup(2);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 创建服务器端的启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();

            // 使用链式编程来设置参数
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)   // 设置两个线程组
                    .option(ChannelOption.SO_BACKLOG, 128)   // 设置线程队列的连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)   // 设置保存活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettySimpleServerHandler());
                        }
                    });

            System.out.println("...服务器 is ready ...");

            // 绑定一个端口并且同步，生成一个 ChannelFuture 对象
            // 启动服务器（并绑定端口）
            ChannelFuture cf = bootstrap.bind(6668).sync();

            // 对关闭通道进行监听
            cf.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }




    }






















}
