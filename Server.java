import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;

public class Server extends UnicastRemoteObject implements Chat_itf{

    private ArrayList<Client_itf> registeredClients = null;

    public Server() throws  RemoteException{
        super();
        registeredClients = new ArrayList<Client_itf>();
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

    public void broadcastMsg(String clientname , String message) throws RemoteException {
        for(int i=0; i<registeredClients.size(); i++) {
            registeredClients.get(i).sendMsg(clientname , message);
        }
    }

    public boolean register(Client_itf client) throws RemoteException{
        boolean isRegistered = false;
        if(!this.registeredClients.contains(client)) {
            this.registeredClients.add(client);
            System.out.println("Successfully registered client: " + client.getName());

            sendMsg(client,null,"Successfully registered!");
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
}
