package netty.a4_nettySimpleDemo;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Date;

/**
 * 自定义一个 Handler 需要继承 netty 规定好的某个 HandlerAdapter
 */
public class NettySimpleServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取数据 (亦可发送消息)
     * @param ctx  上下文对象，含有 管道pipeline  通道channel  地址
     * @param msg  客户端发送的数据
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        System.out.println("服务器读取的线程：" + Thread.currentThread().getName());
//        System.out.println("server ctx: " + ctx);
//        Channel channel = ctx.channel();
//        ChannelPipeline pipeline = ctx.pipeline();
//
        // 将 msg 转成一个 ByteBuf
        // ByteBuf 是 Netty 提供的，不是 NIO 的 ByteBuffer
        System.out.println(new Date());
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送消息是：" + buf.toString(CharsetUtil.UTF_8));
//        System.out.println("客户端地址是：" + channel.remoteAddress());


        // 读取处理操作假设非常耗时，会被阻塞
//        Thread.sleep(10 * 1000);
//        System.out.println(new Date());
//        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端~~~1", CharsetUtil.UTF_8));
//        System.out.println("go on ...");

        // 解决方案1：用户自定义普通任务
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10 * 1000);
                } catch (Exception e) {
                    System.out.println("发生异常：" + e.getMessage());
                }
                System.out.println(new Date());
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端~~~1", CharsetUtil.UTF_8));
            }
        });
        System.out.println("go on ...");




    }

    /**
     * 数据读取完毕
     * @param ctx  上下文
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 将数据写入到缓存，并刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端~~~2", CharsetUtil.UTF_8));
    }

    /**
     * 异常处理
     * @param ctx 上下文
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
