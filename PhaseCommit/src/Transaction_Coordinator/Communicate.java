package PhaseCommit.src.Transaction_Coordinator;

import java.rmi.RemoteException;

public class Communicate implements ICommunicate {

  @Override
  public void commit(String message) throws RemoteException {
    try {
        TC.out.println("get()");
        Thread.sleep(2000);
        TC.out.println("prepare");
        if(TC.in.readLine().equals("yes")){
          TC.out.println("commit");
        }
        if(TC.in.readLine().equals("Acknowledged")){
          System.out.println("Transaction successfully committed");
        }
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
  }
}
