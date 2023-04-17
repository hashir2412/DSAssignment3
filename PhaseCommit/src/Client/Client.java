package PhaseCommit.src.Client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import PhaseCommit.src.Transaction_Coordinator.ICommunicate;

public class Client {

  public static void main(String[] args) {
    try {

      Registry registry = LocateRegistry.getRegistry();
      ICommunicate stub = (ICommunicate) registry.lookup("batman");
      Scanner sc = new Scanner(System.in);
      while(true){
        String message = sc.nextLine();
        stub.commit(message);
      }     
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
  }
}
