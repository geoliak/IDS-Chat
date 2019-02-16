import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

public class RunServer{
    public static void main (String[] argv) {

        try {

            Registry registry = LocateRegistry.getRegistry();

            Server s = new Server();

            registry.bind("ChatServer", s);

            System.out.println("Server is ready:");

            ChatRoom distributed_systems = new ChatRoom("Distributed Systems");
            ChatRoom java = new ChatRoom("Java");

            s.addChatRoom(distributed_systems);
            s.addChatRoom(java);


           /*while(true){
                String msg=s.nextLine().trim();
                if (server.getClient()!=null){
                    ChatInterface client=server.getClient();
                    msg=server.getName() +msg;
                    client.send(msg);
                }
            }*/

        } catch (Exception e) {
            System.out.println("Server failed: " + e.getStackTrace()[0].getLineNumber());
        }
    }
}
