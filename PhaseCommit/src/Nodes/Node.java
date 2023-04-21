package PhaseCommit.src.Nodes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Node {

  public static void main(String[] args) {
    System.out.println("Enter port number: ");
    Scanner sc = new Scanner(System.in);
    int port_number = sc.nextInt();
    try {
      ServerSocket serverSocket = new ServerSocket(port_number);
      Socket tc = serverSocket.accept();
      BufferedReader in = new BufferedReader(new InputStreamReader(tc.getInputStream()));
      PrintWriter out = new PrintWriter(tc.getOutputStream(),true);
      while (!tc.isClosed()) {
        String message = in.readLine();
        System.out.println(message);
         if(message.equals("prepare")){
            out.println("yes");
         }else if(message.contains("commit")){
          out.println(message+"Successfully commited");
         }
         if(in.ready()){
          System.out.println(in.readLine());
         }
      }
      serverSocket.close();
    } catch (Exception e) {
      // TODO: handle exception
    }
  }
}
