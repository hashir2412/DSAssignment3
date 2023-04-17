package PhaseCommit.src.Transaction_Coordinator;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICommunicate extends Remote{
    public void commit(String message) throws RemoteException;
}
