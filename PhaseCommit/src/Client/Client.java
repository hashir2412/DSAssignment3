package PhaseCommit.src.Client;

import PhaseCommit.src.Transaction_Coordinator.ICommunicate;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {

  public static void main(String[] args) {
    try {
      Registry registry = LocateRegistry.getRegistry();
      ICommunicate stub = (ICommunicate) registry.lookup("batman");
      Scanner sc = new Scanner(System.in);
      boolean validate = true;
      while (validate) {
        String message = sc.nextLine();
        if (message.equals("close")) {
          validate = false;
          break;
        }
        stub.commit(message);
      }
      sc.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
