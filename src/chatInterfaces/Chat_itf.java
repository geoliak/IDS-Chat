package chatInterfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Chat_itf extends Remote {
    boolean register(Client_itf client) throws RemoteException;
    void unRegister(Client_itf client) throws RemoteException;
    void sendMsg(Client_itf target, String sender, String msg) throws RemoteException;
    void broadcastMsg(String clientName, String message) throws RemoteException;
    Client_itf getClient(String name) throws RemoteException;
    void getAllChatRooms(Client_itf client) throws RemoteException;
    ChatRoom_itf getChatRoom(String chatRoomTitle) throws RemoteException;
    void getHistory(Client_itf client) throws RemoteException;
    void setChatRoom(ChatRoom_itf chatRoom, Client_itf client) throws RemoteException;
}
