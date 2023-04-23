package PhaseCommit.src.Transaction_Coordinator;

import java.rmi.RemoteException;

public class Communicate implements ICommunicate {

  @Override
  public void commit(String message) throws RemoteException {
    try {
      TC.out.println("get()");
      Thread.sleep(4000);
      TC.out.println("prepare");
      var res = TC.in.readLine();
      if (res.equals("yes")) {
        TC.out.println("commit");
      } else {
        System.out.println(res);
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
