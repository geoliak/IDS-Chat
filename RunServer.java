import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

public class RunServer{
    public static void main (String[] argv) {

        try {

            Registry registry = LocateRegistry.getRegistry();

            registry.bind("ChatServer", new Server());

            System.out.println("Server is ready:");

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