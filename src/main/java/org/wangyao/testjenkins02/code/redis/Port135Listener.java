//package org.wangyao.testjenkins02.code.redis;
//
///**
// *
// *
// * @author wangyao
// * @Date 2025/12/18 15:59
// *
// */
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//public class Port135Listener {
//
//    private static final int LISTEN_PORT = 225;
//
//    public static void main(String[] args) {
//        try (ServerSocket serverSocket = new ServerSocket(LISTEN_PORT)) {
//            System.out.println("🚀 服务启动成功，监听端口: " + LISTEN_PORT);
//            System.out.println("📡 等待客户端连接...");
//
//            while (true) {
//                Socket clientSocket = serverSocket.accept();
//                System.out.println("\n🔗 收到来自 "
//                        + clientSocket.getInetAddress() + " 的连接");
//
//                handleClient(clientSocket);
//            }
//
//        } catch (IOException e) {
//            handlePortError(e);
//        }
//    }
//
//    private static void handleClient(Socket clientSocket) {
//        try (BufferedReader reader = new BufferedReader(
//                new InputStreamReader(clientSocket.getInputStream()))) {
//
//            StringBuilder data = new StringBuilder();
//            String line;
//
//            // 读取客户端发送的所有数据
//            while ((line = reader.readLine()) != null) {
//                data.append(line).append("\n");
//                // 避免长时间阻塞的简单保护机制
//                if (data.length() > 10000) break;
//            }
//
//            System.out.println("📥 接收到的数据: (" + data.length() + " bytes)");
//            System.out.println("----------------------------------------");
//            System.out.println(data);
//            System.out.println("----------------------------------------");
//
//        } catch (IOException e) {
//            System.err.println("⚠️ 读取数据错误: " + e.getMessage());
//        } finally {
//            try {
//                clientSocket.close();
//                System.out.println("🔒 连接已关闭");
//            } catch (IOException e) {
//                System.err.println("❌ 关闭连接时出错: " + e.getMessage());
//            }
//        }
//    }
//
//    private static void handlePortError(IOException e) {
//        // 检查最常见的错误原因
//        if (e.getMessage().contains("Permission denied")) {
//            System.err.println("❌ 权限不足！请使用管理员权限运行此程序");
//        } else if (e.getMessage().contains("Address already in use")) {
//            System.err.println("❌ 端口已被占用！可能已有服务使用135端口");
//        }
//
//
//        System.err.println("⚠️ 启动服务失败: " + e.getMessage());
//        System.err.println("\n💡 可能原因: ");
//        System.err.println("1. 请确保以管理员权限运行 (Windows: 右键->以管理员身份运行)");
//        System.err.println("2. 检查是否有其他程序占用135端口");
//        System.err.println("3. 防火墙可能阻止了此端口访问");
//        System.err.println("4. 在Windows上135端口常被RPC服务使用，可能需要停止该服务");
//    }
//}
