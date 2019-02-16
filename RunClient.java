import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
import java.rmi.registry.*;

public class RunClient {

    public static void main(String[] args) {
        try {
            if (args.length < 1) {
                System.out.println("Usage: java Client <rmiregistry host>");
                return;
            }

            String host = args[0];

            // Get remote object reference
            Registry registry = LocateRegistry.getRegistry(host);
            //System.setSecurityManager(new RMISecurityManager());

            Chat_itf server = (Chat_itf) registry.lookup("ChatServer");

            System.out.println("Client is ready:");

            System.out.println("Enter your name and press enter");

            Scanner scanner = new Scanner(System.in);

            String clientName = scanner.nextLine();

            boolean notExit = true;


            //System.out.println("notExit" + notExit);

            Client c = null;

            if(clientName != null) {
                c = new Client(clientName, server);

            }else{
                System.out.println("You must enter a name");
            }

            //System.out.println("notExit2" + notExit);

            String msg = null;

            if(c.isRegistered()) {
                System.out.println("Type 0 to exit");
                System.out.println("Type 1 followed by your message to send to all clients");
                System.out.println("Type 2 followed by a client's name and your message to send to that client");

                while (notExit) {

                    msg = scanner.nextLine().trim();
                    //System.out.println("startswith 0: " + msg.startsWith("0"));
                    if (msg.startsWith("0")) {
                        //server.unRegister(c);
                        notExit = false;
                    } else if (msg.startsWith("1")) {
                        server.broadcastMsg(clientName, msg);
                    } else if (msg.startsWith("2")) {
                        String username = msg.split(" ")[1];
                        Client_itf target = server.getClient(username);
                        if(target != null) {
                            server.sendMsg(target, clientName, msg);
                        }else{
                            System.out.println("The client "+ username +"doesn't exist");
                        }
                    }
                }
                System.out.println("Successfully disconnected client: " + c.getName());

            }else{
                System.out.println("Error in registration process");
            }


        } catch (Exception e) {
            System.out.println("Server failed: " + e);
        }
    }

}
