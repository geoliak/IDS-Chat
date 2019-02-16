import java.rmi.*;
import java.util.*;

public interface ChatRoom_itf extends Remote {
    String getTitle() throws RemoteException;
    Client_itf getUser(Client_itf client) throws RemoteException;
    void addUser(Client_itf client) throws RemoteException;
    void removeUser(Client_itf client) throws  RemoteException;
    void printAllMessages(Client_itf client) throws RemoteException;
    void addMessage(String sender,String msg) throws RemoteException;
}