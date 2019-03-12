package serverChat;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RunServer {
    public static void main (String[] argv) {

        try {

            Registry registry = LocateRegistry.createRegistry(10990);

            Server remoteServer = new Server();

            registry.bind("ChatServer", remoteServer);

            System.out.println("Server is ready:");

            ChatRoom distributed_systems = new ChatRoom("Distributed Systems");
            ChatRoom java = new ChatRoom("Java");
            ChatRoom science = new ChatRoom("Science");
            ChatRoom nerds = new ChatRoom("Nerds");

            remoteServer.addChatRoom(distributed_systems);
            remoteServer.addChatRoom(java);
            remoteServer.addChatRoom(science);
            remoteServer.addChatRoom(nerds);


        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
