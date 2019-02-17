import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.util.*;

public class Client extends UnicastRemoteObject implements Client_itf{

    private String name;
    private Chat_itf chatInterface;
    private boolean registered;
    private ChatRoom_itf chatRoom;

    public Client(String name, Chat_itf cItf) throws RemoteException{
        //System.out.println("in constructor ");
        this.name = name;
        this.chatInterface = cItf;
        registered = cItf.register(this);
        this.chatRoom = null;
    }

    public boolean isRegistered(){

        return registered;
    }

    public void sendMsg(String sender, String msg){
        if(sender != null){
            System.out.println("["+sender+"]: "+ msg);//other client's msg
        }else{
            System.out.println(msg);//server msg
        }
    }

    public String getName(){
        //System.out.println("in getName ");
        return this.name;
    }

    public void setChatRoom(ChatRoom_itf chatRoom) {
        this.chatRoom = chatRoom;
    }

    public ChatRoom_itf getChatRoom() {
        return this.chatRoom;
    }
}
