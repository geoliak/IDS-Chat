import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.util.*;

public class Client extends UnicastRemoteObject implements Client_itf{

    private String name = null;
    private Chat_itf chatInterface;
    private boolean registered = false;

    public Client(String name, Chat_itf cItf) throws RemoteException{
        //System.out.println("in constructor ");
        this.name = name;
        this.chatInterface = cItf;
        registered = cItf.register(this);
    }

    public boolean isRegistered(){
        return registered;
    }

    public void sendMsg(String sender, String msg) throws RemoteException{
        if(sender != null){
            System.out.println("["+sender+"]"+ msg);//other client's msg
        }else{
            System.out.println(msg);//server msg
        }
    }

    public String getName() throws RemoteException{
        //System.out.println("in getName ");
        return this.name;
    }

}
