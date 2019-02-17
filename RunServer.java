import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

public class RunServer{
    public static void main (String[] argv) {

        try {

            Registry registry = LocateRegistry.getRegistry();

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
            System.out.println("Server failed: " + e.getStackTrace()[0].getLineNumber());
        }
    }
}
