import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;

public class Server extends UnicastRemoteObject implements Chat_itf{

    private ArrayList<Client_itf> registeredClients = null;
    private ArrayList<ChatRoom_itf> chatRooms = null;
    private ArrayList<String> messageList = null;

    public Server() throws RemoteException{
        registeredClients = new ArrayList<>();
        chatRooms = new ArrayList<>();
        this.messageList = new ArrayList<>();
    }

    public Client_itf getClient(String name) throws RemoteException {
        for(Client_itf client: registeredClients){
            if(client.getName().toUpperCase().equals(name.toUpperCase())){
                return client;
            }
        }
        return null;
    }

    public void sendMsg(Client_itf targetClient, String sender, String msg) throws RemoteException{
        if(!registeredClients.contains(targetClient)){
            return;
        }
        targetClient.sendMsg(sender, msg);

    }

    public void getHistory(Client_itf client) throws RemoteException{
        for(String msg: this.messageList){
            client.sendMsg(null,msg);
        }
    }

    public void broadcastMsg(String clientName , String message) throws RemoteException {
        for(Client_itf c: registeredClients) {
            c.sendMsg(clientName , message);
        }
        if (clientName != null) {
            this.messageList.add("[" + clientName + "] wrote: " + message);
        } else {
            this.messageList.add(message);
        }
    }

    public boolean register(Client_itf client) throws RemoteException{
        boolean isRegistered = false;
        if(!this.registeredClients.contains(client)) {
            this.registeredClients.add(client);
            System.out.println("Successfully registered client: " + client.getName());

            sendMsg(client,null,"Successfully registered!");
            broadcastMsg(null,client.getName()+" is online");
            isRegistered = true;
        }else{
            System.out.println("Name already taken, please choose another one");
        }
        return isRegistered;
    }

    public void unRegister(Client_itf client) throws RemoteException{
        System.out.println("registeredClients: "+registeredClients);
        if(this.registeredClients.contains(client)) {
            this.registeredClients.remove(client);
        }
    }

    public void addChatRoom(ChatRoom_itf chatRoom) throws RemoteException{
        this.chatRooms.add(chatRoom);
    }

    public void getAllChatRooms(Client_itf client) throws RemoteException{
        for(ChatRoom_itf chatRoom : this.chatRooms) {
            client.sendMsg(null,chatRoom.getTitle());
        }
    }

    public ChatRoom_itf getChatRoom(String chatRoomTitle) throws RemoteException{
        for(ChatRoom_itf room: chatRooms){
            if(room.getTitle().toUpperCase().equals(chatRoomTitle.toUpperCase())){
                return room;
            }
        }
        return null;
    }
}
