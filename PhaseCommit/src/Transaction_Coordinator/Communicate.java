package PhaseCommit.src.Transaction_Coordinator;

import java.io.File;
import java.io.FileWriter;
import java.rmi.RemoteException;
import java.time.Duration;
import java.time.Instant;

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
          Instant prepareSent = Instant.now();
          TC.out.println("prepare");

          var res2 = TC.in.readLine();
          long elap = Duration.between(prepareSent, Instant.now()).toMillis();
          if (elap >= 3000) {
            System.out.println("Transaction aborted by TC");
            // return;
          } else if (res2.equals("yes")) {
            TC.out.println("commit");
          } else {
            System.out.println(res2);
          }
          break;
        case "3":
          break;
        case "4":
          TC.out.println("get()");
          TC.out.println("prepare");
          var mes = TC.in.readLine();
          if (mes.equals("yes")) {
            File myObj = new File("filename.txt");
            myObj.createNewFile();
            FileWriter myWriter = new FileWriter("filename.txt");
            // Add commit for all the nodes connected
            myWriter.write("Commit added for " + "1000");
            myWriter.close();

            TC.out.println("commit");
            // Timeout after sending one commit
            Thread.sleep(4000);

            // Send the commits again which werent sent earlier
          } else {
            System.out.println(mes);
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
