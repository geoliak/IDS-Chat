package serverChat;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import chatInterfaces.Client_itf;
import chatInterfaces.ChatRoom_itf;

public class ChatRoom extends UnicastRemoteObject implements ChatRoom_itf {
    private String title;
    private ArrayList<Client_itf> clientList = null;
    private ArrayList<String> messageList = null;


    public ChatRoom(String name) throws RemoteException {
        this.title = name;
        this.clientList = new ArrayList<>();
        this.messageList = new ArrayList<>();
    }

    public Client_itf getUser(Client_itf client){
        if(this.clientList.contains(client))
            return client;
        else
            return null;
    }

    public String getTitle(){
        return this.title;
    }

    public void addUser(Client_itf client) throws RemoteException{
        if(!this.clientList.contains(client)){
            this.clientList.add(client);
        }
        broadcastMsg(null,"User [" + client.getName() + "] joined chat room " + this.getTitle());
    }

    public void addMessage(String sender,String msg) throws  RemoteException{
        //this.messageList.add("[" + sender + "] wrote: " + msg);
        broadcastMsg(sender,msg);
    }

    public void broadcastMsg(String sender, String message) throws RemoteException {
        if (clientList.size() > 0) {
            for (Client_itf c : clientList) {
                c.sendMsg(sender, message);
            }

            try {//write to file
                File chatFile = new File(this.title);

                if (!chatFile.exists()) {
                    chatFile.createNewFile();
                }

                OutputStream outputStream = new FileOutputStream(chatFile, true);
                String msg;

                if (sender != null) {
                    msg = "[" + sender + "] wrote to all: " + message + "\n";
                    outputStream.write(msg.getBytes(), 0, msg.length());
                    //this.messageList.add("[" + clientName + "] wrote: " + message);

                } else {
                    //this.messageList.add(message);
                    msg = message + "\n";
                    outputStream.write(msg.getBytes(), 0, msg.length());
                }
            } catch (java.io.IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void removeUser(Client_itf client) throws  RemoteException{
        if(this.clientList.contains(client)){
            this.clientList.remove(client);
        }
        client.sendMsg(null,"You are no longer a member of "+ this.getTitle());
    }

    public void getHistory(Client_itf client,ChatRoom_itf chatRoom){
        String msg;
        try{
            File history = new File(chatRoom.getTitle());
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
        catch(java.io.IOException ex){
            ex.printStackTrace();
        }
    }
}
