package PhaseCommit.src.Transaction_Coordinator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.rmi.RemoteException;

public class Communicate implements ICommunicate {

  @Override
  public void commit(String message) throws RemoteException {
    System.out.println(message);
    try {
      PrintWriter out = new PrintWriter(TC.n1.getOutputStream());
      BufferedReader in = new BufferedReader(
        new InputStreamReader(TC.n1.getInputStream())
      );
      out.println("prepare");
      out.flush();
      Thread t = new Thread(
        new Runnable() {
          @Override
          public void run() {
            // TODO Auto-generated method stub
            try {
              while (!TC.n1.isClosed()) {
                String msg = in.readLine();
                System.out.println(msg);
                if (msg.equals("yes")) {
                  out.println(message + " commit");
                  out.flush();
                }
              }
            } catch (Exception e) {
              // TODO: handle exception
            }
          }
        }
      );
      t.start();
    } catch (Exception e) {
      // TODO: handle exception
    }
  }
}
