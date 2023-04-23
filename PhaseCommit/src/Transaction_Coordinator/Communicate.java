package PhaseCommit.src.Transaction_Coordinator;

import java.rmi.RemoteException;

public class Communicate implements ICommunicate {

  @Override
  public void commit(String message) throws RemoteException {
    try {
      switch (message) {
        case "1":
          TC.out.println("get()");
          Thread.sleep(4000);
          TC.out.println("prepare");
          var res = TC.in.readLine();
          if (res.equals("yes")) {
            TC.out.println("commit");
          } else {
            System.out.println(res);
          }
          break;
        case "2":
          TC.out.println("get()");
          TC.out.println("prepare");
          var res2 = TC.in.readLine();
          if (res2.equals("yes")) {
            TC.out.println("commit");
          } else {
            System.out.println(res2);
          }
          break;
        default:
          break;
      }

      if (TC.in.readLine().equals("Acknowledged")) {
        System.out.println("Transaction successfully committed");
      }
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
  }
}
