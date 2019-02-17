import java.rmi.*;

public interface Client_itf extends Remote{
    void sendMsg(String sender, String msg) throws RemoteException;
    String getName() throws RemoteException;
    void setChatRoom(ChatRoom_itf chatRoom) throws RemoteException;
    ChatRoom_itf getChatRoom() throws RemoteException;
}
