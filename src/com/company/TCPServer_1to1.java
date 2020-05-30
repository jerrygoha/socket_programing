package com.company;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TCPServer_1to1 {

    public static final int PORT = 7335;

    public static void main(String args[]) {

        ServerSocket serverSocket = null;

        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        OutputStream os = null;
        OutputStreamWriter osw = null;
        PrintWriter pw = null;
        Scanner sc = new Scanner(System.in);

        try {
            // 서버 소켓 생성
            serverSocket = new ServerSocket();

            InetAddress inetAddress = InetAddress.getLocalHost();
            String localhost = inetAddress.getHostAddress();

            serverSocket.bind(new InetSocketAddress(localhost, PORT));

            System.out.println("[서버] 연결" + localhost);

            //(클라이언트로부터 연결요청 대기)

            Socket socket = serverSocket.accept();
            InetSocketAddress socketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();

            System.out.println("[서버] 클라이언트와 연결되었습니다.");
            System.out.println("[서버] " + socketAddress.getHostString() + "와 연결됨. " + socket.getPort() + " 포트번호");

            while (true) {

                is = socket.getInputStream();
                isr = new InputStreamReader(is, "UTF-8");
                br = new BufferedReader(isr);


                os = socket.getOutputStream();
                osw = new OutputStreamWriter(os, "UTF-8");
                pw = new PrintWriter(osw, true);

                String buffer = null;
                buffer = br.readLine();

                if (buffer == null) {
                    System.out.println("[서버] 서버가 닫혔습니다.");
                    break;
                }

                System.out.println("[서버] 수신 : " + buffer);
                pw.println(buffer);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
                sc.close();

        }

    }

}
