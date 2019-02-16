import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class ChatRoom extends UnicastRemoteObject implements ChatRoom_itf{

    private String title;
    private ArrayList<Client_itf> clientList = null;
    private ArrayList<String> messageList = null;


    public ChatRoom(String name) throws RemoteException{
        this.title = name;
        this.clientList = new ArrayList<>();
        this.messageList = new ArrayList<>();
    }

    public Client_itf getUser(Client_itf client) throws RemoteException{
        if(this.clientList.contains(client))
            return client;
        else
            return null;
    }

    public String getTitle() throws RemoteException{
        return this.title;
    }

    public void addUser(Client_itf client) throws RemoteException{
        if(!this.clientList.contains(client)){
            this.clientList.add(client);
        }
        broadcastMsg(null,"User [" + client.getName() + "] joined chat room " + this.getTitle());
    }

    public void addMessage(String sender,String msg) throws  RemoteException{
        this.messageList.add("[" + sender + "] wrote: " + msg);
        broadcastMsg(sender,msg);
    }

    public void broadcastMsg(String sender, String msg) throws RemoteException{
        if(clientList.size() > 0) {
            for (Client_itf c: clientList) {
                c.sendMsg(sender, msg);
            }
            if (sender != null) {
                this.messageList.add("[" + sender + "] wrote: " + msg);
            } else {
                this.messageList.add(msg);
            }
        }
    }

    public void removeUser(Client_itf client) throws  RemoteException{
        if(this.clientList.contains(client)){
            this.clientList.remove(client);
        }
        client.sendMsg(null,"You are no longer a member of "+ this.getTitle());
    }

    public ArrayList<String> getAllMessages(){
        return this.messageList;
    }

    public void printAllMessages(Client_itf client) throws  RemoteException{
        for(String msg: getAllMessages()){
            client.sendMsg(null,msg);
        }
    }
}
