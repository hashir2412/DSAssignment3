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
      TC.out.println(message);
      TC.out2.println(message);
      switch (message) {
        case "1":
          TC.out.println("get()");
          TC.out2.println("get()");
          Thread.sleep(4000);
          TC.out.println("prepare");
          TC.out2.println("prepare");
          String res1 = TC.in.readLine();
          if (res1.equals("yes")) {
            TC.out.println("commit");
          } else {
            System.out.println(res1 + " from " + TC.n1.getPort());
          }
          String resCase1 = TC.in2.readLine();
          if (resCase1.equals("yes")) {
            TC.out2.println("commit");
          } else {
            System.out.println(resCase1 + " from " + TC.n2.getPort());
          }
          break;
        case "2":
          TC.out.println("get()");
          TC.out2.println("get()");
          Instant prepareSent = Instant.now();
          TC.out.println("prepare");
          TC.out2.println("prepare");
          String res2 = TC.in.readLine();
          String resCase2 = TC.in2.readLine();
          long elap = Duration.between(prepareSent, Instant.now()).toMillis();
          if (elap >= 3000) {
            System.out.println(
              "Transaction aborted by TC not receiving yes on time"
            );
            TC.out.println("Abort");
            TC.out2.println("Abort");
            // return;
          } else if (res2.equals("yes")) {
            TC.out.println("commit");
            TC.out2.println("commit");
          } else {
            System.out.println("Node " + TC.n1.getPort() + ": " + res2);
            System.out.println("Node " + TC.n2.getPort() + ": " + resCase2);
          }
          break;
        case "3":
          File myObj3 = new File("TC.txt");
          myObj3.createNewFile();
          FileWriter myWriter3 = new FileWriter("TC.txt", true);
          TC.out.println("get()");
          TC.out.println("prepare");
          TC.out2.println("get()");
          TC.out2.println("prepare");
          myWriter3.write("Prepare sent to " + TC.n1.getPort());
          myWriter3.write("Prepare sent to " + TC.n2.getPort());
          String resNode1 = TC.in.readLine();
          String resNode2 = TC.in2.readLine();
          if (resNode1.equals("yes")) {
            // Add commit for all the nodes connected
            myWriter3.write("\nCommit added for " + TC.n1.getPort());

            TC.out.println("commit");
            resNode1 = TC.in.readLine();
            if (resNode1.contains("Acknowledged")) {
              myWriter3.write("\nAcknowledged " + TC.n1.getPort());
              System.out.println(resNode1);
            } else {
              System.out.println("No acknowledgement from 1000");
            }

            // Timeout after sending one commit
            Thread.sleep(4000);
            if (resNode2.equals("yes")) {
              TC.out2.println("commit");
              resNode2 = TC.in2.readLine();
              if (resNode2.contains("Acknowledged")) {
                myWriter3.write("\nAcknowledged " + TC.n2.getPort());
                System.out.println(resNode2);
              } else {
                System.out.println("No acknowledgement from 1001");
              }
              myWriter3.write("Commit added for " + TC.n2.getPort());
              myWriter3.close();
            }
            // Send the commits again which werent sent earlier
          } else {
            System.out.println(resNode1);
          }
          break;
        case "4":
          TC.out.println("get()");
          TC.out.println("prepare");
          TC.out2.println("get()");
          TC.out2.println("prepare");
          String mes = TC.in.readLine();
          String mes2 = TC.in2.readLine();
          File myObj = new File("TC.txt");
          myObj.createNewFile();
          FileWriter myWriter = new FileWriter("TC.txt",true);
          if (mes.equals("yes")) {
        
            // Add commit for all the nodes connected
            myWriter.write("\nCommit added for " + TC.n1.getPort());
            myWriter.write("\nCommit added for " + TC.n2.getPort());

            TC.out.println("commit");
            TC.out2.println("commit");
        
            mes = TC.in.readLine();
            mes2 = TC.in2.readLine();
            myWriter.append("\n " + mes + " for " + TC.n1.getPort());
            myWriter.append("\n " + mes2 + " for " + TC.n2.getPort());
            // Send the commits again which werent sent earlier

          } else {
            System.out.println(mes);
          }
          // NodeManager node1 = new NodeManager(TC.out, TC.in, TC.n1);
          // NodeManager node2 = new NodeManager(TC.out2, TC.in2, TC.n2);
          // Thread t1 = new Thread(node1);
          // Thread t2 = new Thread(node2);
          // t1.start();
          // t2.start();
          break;
      }
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
  }
}
