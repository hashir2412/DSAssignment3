package PhaseCommit.src.Nodes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Node {

  static Lock lock = new ReentrantLock();
  static Instant getReceived;
  static String state = "idle";

  public static void main(String[] args) {
    
    System.out.println("Enter port number: ");
    Scanner sc = new Scanner(System.in);
    int port_number = sc.nextInt();

    try {
      File myObj = new File(port_number + ".txt");
      myObj.createNewFile();
      FileWriter writer = new FileWriter(myObj, true);
      ServerSocket serverSocket = new ServerSocket(port_number);
      Socket tc = serverSocket.accept();
      BufferedReader in = new BufferedReader(
        new InputStreamReader(tc.getInputStream())
      );
      PrintWriter out = new PrintWriter(tc.getOutputStream(), true);
      Thread t = new Thread(
        new Runnable() {
          @Override
          public void run() {
            // TODO Auto-generated method stub
            initiateTrnx();
          }
        }
      );
      while (!tc.isClosed()) {
        String message = in.readLine();
        switch (message) {
          case "get()":
            getReceived = Instant.now();
            t.start();
            System.out.println("in get()");
            break;
          case "prepare":
            long elap = Duration.between(getReceived, Instant.now()).toMillis();
            if(elap >= 3000){
              out.println("no");
            }
             else {
              out.println("yes");
              state = "prepared";
            }
            System.out.println(
              "Node " + port_number + " in " + state + " state."
            );
            break;
          case "commit":
            writer.write("blablas");
            out.println("Acknowledged " + port_number);
          default:
            break;
        }
      }
      serverSocket.close();
      sc.close();
      writer.close();
    } catch (Exception e) {
      // TODO: handle exception
      System.out.println(e);
    }
  }

  public static void initiateTrnx() {
    lock.lock();
    try {
      System.out.println("Lock acquired");
      Thread.sleep(3000);
    } catch (Exception e) {
      // TODO: handle exception
    }
  }
}
