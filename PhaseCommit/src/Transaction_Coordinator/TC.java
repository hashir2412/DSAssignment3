package PhaseCommit.src.Transaction_Coordinator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class TC {
  static Socket n1;
  static PrintWriter out;
  static BufferedReader in;
  public static void main(String[] args) {
    try {
      Communicate obj = new Communicate();
      Registry reg = LocateRegistry.createRegistry(1099);
      ICommunicate stub = (ICommunicate) UnicastRemoteObject.exportObject(
        obj,
        1099
      );
      reg.bind("batman", stub);
      System.out.println("Server ready");
      n1 = new Socket("localhost", 1000);
      out = new PrintWriter(TC.n1.getOutputStream(), true);
      in = new BufferedReader(
        new InputStreamReader(n1.getInputStream())
      );
    //   Socket n2 = new Socket("localhost",1001);
    } catch (Exception e) {
      System.err.println("Server exception: " + e.toString());
      e.printStackTrace();
    }
  }
}
