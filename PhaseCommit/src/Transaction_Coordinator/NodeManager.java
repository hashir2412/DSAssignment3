package PhaseCommit.src.Transaction_Coordinator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.Buffer;

public class NodeManager implements Runnable {

  PrintWriter out;
  BufferedReader in;
  Socket socket;

  NodeManager(PrintWriter out, BufferedReader in, Socket socket) {
    this.out = out;
    this.in = in;
    this.socket = socket;
  }

  @Override
  public void run() {
    try {
      //   while (!socket.isClosed()) {
      out.println("get()");
      out.println("prepare");
      File myObj = new File("TC.txt");
      myObj.createNewFile();
      FileWriter myWriter = new FileWriter("TC.txt", true);
      String mes = in.readLine();
      if (mes.equals("yes")) {
        // Add commit for all the nodes connected
        myWriter.append("\nCommit added for " + socket.getPort());
        out.println("commit");
        mes = in.readLine();
        myWriter.append("\n " + mes + " for " + socket.getPort());
      } else {
        System.out.println(mes);
      }
      myWriter.close();
      //   }
    } catch (Exception e) {
      // TODO: handle exception
    }
  }
}
