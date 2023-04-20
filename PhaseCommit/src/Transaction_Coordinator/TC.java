package PhaseCommit.src.Transaction_Coordinator;

import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class TC {
  static Socket n1;
  public static void main(String[] args) {
    try {
      Communicate obj = new Communicate();
      Registry reg = LocateRegistry.createRegistry(1099);
      ICommunicate stub = (ICommunicate) UnicastRemoteObject.exportObject(
        obj,
        1099
      );
      reg.bind("batman", stub);
      System.err.println("Server ready");
      n1 = new Socket("localhost", 1000);
    //   Socket n2 = new Socket("localhost",1001);
    } catch (Exception e) {
      System.err.println("Server exception: " + e.toString());
      e.printStackTrace();
    }
  }
}
