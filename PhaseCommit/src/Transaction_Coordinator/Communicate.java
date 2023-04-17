package PhaseCommit.src.Transaction_Coordinator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;

public class Communicate implements ICommunicate{
    @Override
    public void commit(String message) throws RemoteException{
        System.out.println(message);
        try {
            Socket n1 = new Socket("localhost",1000);
            PrintWriter out = new PrintWriter(n1.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(n1.getInputStream()));
            out.println("prepare");
            out.flush();
            String msg = in.readLine();
            System.out.println(msg);
            if(msg.equals("yes")){
                out.println(message + " commit");
                out.flush();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
