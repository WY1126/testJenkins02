package org.wangyao.testjenkins02.code.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyLocalTestClient {
    // 本地服务地址端口，改成你本地服务
    private static final String HOST = "172.25.230.230";
    private static final int PORT = 10011;

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(1);
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 这里可以添加你的 长度解码器、自定义编解码器
                            // pipeline.addLast(new LengthFieldBasedFrameDecoder(xxx,xxx,xxx,xxx,xxx));
                            pipeline.addLast(new TestClientHandler());
                        }
                    });

            // 发起连接
            ChannelFuture future = bootstrap.connect(HOST, PORT).sync();
            log.info("连接本地服务 {}:{} 成功", HOST, PORT);

            // 测试发送自定义报文，替换成你组装好的sendCode字节数组
            String testMsg = "test connect";
            byte[] sendBytes = testMsg.getBytes();
            byte[] data = testMsg.getBytes();

            ByteBuf buf = Unpooled.wrappedBuffer(data);
//            future.channel().writeAndFlush(sendBytes).sync();
            future.channel()
                    .writeAndFlush(Unpooled.copiedBuffer(testMsg, CharsetUtil.UTF_8))
                    .sync();
            log.info("已发送测试消息:{}", testMsg);

            // 等待通道关闭
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("连接本地Netty服务失败", e);
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    /** 客户端读写处理器 */
    static class TestClientHandler extends SimpleChannelInboundHandler<byte[]> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, byte[] msg) {
            log.info("收到服务端返回字节:{}", new String(msg));
        }
        // test

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            log.info("通道激活");
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) {
            log.warn("通道断开");
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            log.error("通道异常", cause);
            ctx.close();
        }
    }
}