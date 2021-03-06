package netty.a4_nettySimpleDemo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Date;

public class NettySimpleClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当通道就绪时触发该方法
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("客户端 ctx: " + ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello, 服务端！！！", CharsetUtil.UTF_8));
    }


    /**
     * 当通道有事件读取时触发该方法
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println(new Date());
        System.out.println("服务器回复的消息是：" + buf.toString(CharsetUtil.UTF_8));
//        System.out.println("服务器的地址：" + ctx.channel().remoteAddress());
    }


    /**
     * 异常处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


}
