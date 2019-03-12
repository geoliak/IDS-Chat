package chatInterfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client_itf extends Remote {
    void sendMsg(String sender, String msg) throws RemoteException;
    String getName() throws RemoteException;
    ChatRoom_itf getChatRoom() throws RemoteException;
    void setChatRoom(ChatRoom_itf chatRoom) throws RemoteException;
}
