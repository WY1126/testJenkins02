package org.wangyao.testjenkins02.code.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class SimpleNettyTest {
    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            // 空处理器，只保证端口监听
                        }
                    });
            // 强制绑定0.0.0.0，监听所有网卡
            ChannelFuture future = bootstrap.bind("127.0.0.1", 10011).sync();
            System.out.println("✅ Netty测试服务成功监听 127.0.0.1:10011");
            // 阻塞等待关闭
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            // 打印完整启动异常
            System.err.println("❌ 服务启动失败：");
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}