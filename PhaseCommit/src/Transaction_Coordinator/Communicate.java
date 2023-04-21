package PhaseCommit.src.Transaction_Coordinator;

import java.rmi.RemoteException;

public class Communicate implements ICommunicate {

  @Override
  public void commit(String message) throws RemoteException {
    try {
        TC.out.println(message + " commit");
        System.out.println(TC.in.readLine());
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
  }
}
