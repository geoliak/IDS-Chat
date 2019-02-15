import java.rmi.*;

public interface Client_itf extends Remote{
    public void sendMsg(String sender, String msg) throws RemoteException;
    public String getName() throws RemoteException;
}