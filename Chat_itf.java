import java.rmi.*;

public interface Chat_itf extends Remote{
    public void register(Client_itf client) throws RemoteException;
    public void unRegister(Client_itf client) throws RemoteException;
    public void sendMsg(Client_itf target, String sender, String msg) throws RemoteException;
    public void broadcastMsg(String clientname , String message) throws RemoteException;
    public Client_itf getClient(String name) throws RemoteException;
}