
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import javarmi.Hello;

public class RunServer {

    public static void main(String[] argv) {

        try {
            Registry registry = LocateRegistry.getRegistry("localhost");
            Server serv = new Server();
            Chat_itf Server_stub = (Chat_itf) UnicastRemoteObject.exportObject(serv, 0);
            registry.bind("ChatServer", Server_stub);

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

        }
    }
}
