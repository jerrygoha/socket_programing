package com.company;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class TCPClient_1to1 {
    public static void main(String args[]){

        //클라이언트 소켓 생성

        Socket socket = new Socket();
        Scanner sc = new Scanner(System.in);

        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        OutputStream os = null;
        OutputStreamWriter osw = null;
        PrintWriter pw = null;

        try {
            socket.connect(new InetSocketAddress(InetAddress.getLocalHost(), 7335)); //소켓 연결
            System.out.println("서버와 연결되었습니다.");

            while (true){

                is = socket.getInputStream();
                isr = new InputStreamReader(is, "UTF-8");
                br = new BufferedReader(isr);

                os = socket.getOutputStream();
                osw = new OutputStreamWriter(os, "UTF-8");
                pw = new PrintWriter(osw, true);

                SimpleDateFormat datetime = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");
                String format = datetime.format(System.currentTimeMillis());


                System.out.print(">> ");
                String data = sc.nextLine();    //scanner로 데이터 입력받음


                if(data.equals("exit")){
                    System.out.println("종료합니다.");
                    break;
                }else if(data.contains("시간") || data.contains("날짜")){
                    System.out.println(format);
                }

                pw.println(data);

                data = br.readLine();   //입력받은 데이터 버퍼에 넣음
                System.out.println("<< " + data);
            }



        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try{
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            sc.close();
        }


    }
}
