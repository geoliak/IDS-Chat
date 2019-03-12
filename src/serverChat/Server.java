package serverChat;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import chatInterfaces.ChatRoom_itf;
import chatInterfaces.Chat_itf;
import chatInterfaces.Client_itf;

public class Server extends UnicastRemoteObject implements Chat_itf, Serializable {
    private ArrayList<Client_itf> registeredClients = null;
    private ArrayList<ChatRoom_itf> chatRooms = null;
    //private ArrayList<String> messageList = null;

    public Server() throws RemoteException {
        registeredClients = new ArrayList<>();
        chatRooms = new ArrayList<>();
        //this.messageList = new ArrayList<>();
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

    public void getHistory(Client_itf client){
        /*for(String msg: this.messageList){
            client.sendMsg(null,msg);
        }*/
        String msg;
        try{
            File history = new File("AllHistory");
            BufferedReader buf = new BufferedReader(new FileReader(history));

            while((msg = buf.readLine()) != null){
                client.sendMsg(null,msg);
            }
            if(history.length() == 0)
                client.sendMsg(null,"Sorry there is no history to display");

        }
        catch(FileNotFoundException ex){
            System.out.println(ex.getMessage());
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public void broadcastMsg(String clientName , String message) throws RemoteException {
        for(Client_itf c: registeredClients) {
            c.sendMsg(clientName , message);
        }
        try{//write to file
            File historyFile = new File("AllHistory");

            if(!historyFile.exists()){
                historyFile.createNewFile();
            }

            OutputStream outputStream = new FileOutputStream(historyFile, true);
            String msg;

            if (clientName != null) {
                msg = "[" + clientName + "] wrote to all: "+message + "\n";
                outputStream.write(msg.getBytes(), 0, msg.length());
                //this.messageList.add("[" + clientName + "] wrote: " + message);

            } else {
                //this.messageList.add(message);
                msg = message+ "\n";
                outputStream.write(msg.getBytes(), 0, msg.length());
            }
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }

    }

    public boolean register(Client_itf client) throws RemoteException{
        boolean isRegistered = false;
        broadcastMsg(null,client.getName()+" is online");

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

    public void unRegister(Client_itf client){
        System.out.println("registeredClients: "+registeredClients);
        if(this.registeredClients.contains(client)) {
            this.registeredClients.remove(client);
        }
    }

    public void addChatRoom(ChatRoom_itf chatRoom){
        //not remote
        //not called by client
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

    public void setChatRoom(ChatRoom_itf chatRoom, Client_itf client) throws RemoteException{
        client.setChatRoom(chatRoom);
    }
}
