package chatInterfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatRoom_itf extends Remote {
    String getTitle() throws RemoteException;
    Client_itf getUser(Client_itf client) throws RemoteException;
    void addUser(Client_itf client) throws RemoteException;
    void removeUser(Client_itf client) throws  RemoteException;
    void getHistory(Client_itf client, ChatRoom_itf chatRoom) throws RemoteException;
    void addMessage(String sender, String msg) throws RemoteException;
}
