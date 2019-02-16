import java.rmi.*;

public interface Chat_itf extends Remote{
    boolean register(Client_itf client) throws RemoteException;
    void unRegister(Client_itf client) throws RemoteException;
    void sendMsg(Client_itf target, String sender, String msg) throws RemoteException;
    void broadcastMsg(String clientName , String message) throws RemoteException;
    Client_itf getClient(String name) throws RemoteException;
    void addChatRoom(ChatRoom_itf chatRoom) throws RemoteException;
    void getAllChatRooms(Client_itf client) throws RemoteException;
    ChatRoom_itf getChatRoom(String chatRoomTitle) throws RemoteException;
    void getHistory(Client_itf client) throws RemoteException;
}
