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
import java.util.concurrent.Semaphore;

public class Node {

  static Semaphore sem = new Semaphore(1);
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
      String failureCase = in.readLine();

      while (!tc.isClosed()) {
        String message = in.readLine();
        switch (message) {
          case "get()":
            if (failureCase.equals("1")) {
              getReceived = Instant.now();
            }
            t.start();
            System.out.println("in get()");
            break;
          case "prepare":
            if (failureCase.equals("1")) {
              long elap = Duration
                .between(getReceived, Instant.now())
                .toMillis();
              if (elap >= 3000) {
                System.out.println("Sending no because of prepare not coming in time.");
                writer.write("Failure 1. Too much time taken for prepare message.");
                out.println("no");
                sem.release();
              } else {
                out.println("yes");
                state = "prepared";
              }
            } else if (failureCase.equals("2")) {
              Thread.sleep(4000);
              out.println("yes");
            }else if(failureCase.equals("4")){
              writer.write("Sending yes to TC "+port_number);
              out.println("yes");
              Thread.sleep(4000);
            }else{
              out.println("yes"); // Case 4
            }
            writer.append("\n Prepare for "+port_number);
            break;
          case "commit":
            writer.write("Acknowledgement sent to TC");
            out.println("Acknowledged " + port_number);
            break;
          case "Abort":
            sem.release();
            writer.write("\n Transaction aborted");
            System.out.println("Transaction aborted.");
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
    try {
      sem.acquire();
      System.out.println("Lock acquired");
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
