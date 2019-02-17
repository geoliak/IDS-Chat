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
                System.out.println("Type 0 and press Enter to exit");
                System.out.println("Type 1 followed by your message to send to all clients and press Enter");
                System.out.println("Type 2 to get a history of all public messages sent by server");
                System.out.println("Type 3 followed by a client's name and your message to send to that client and press Enter");
                System.out.println("Type 4 to view available chat rooms and press Enter");
                System.out.println("Type 5 followed by the chat room name to join the chat room and press Enter");
                System.out.println("Type 6 to consult the chat room's history and press Enter");
                System.out.println("Type 7 to send a message to the chat room and press Enter");
                System.out.println("Type 8 to leave the chat room and press Enter");
                System.out.println("Type 9 to view the menu again");

                while (notExit) {

                    msg = scanner.nextLine().trim();
                    //System.out.println("startswith 0: " + msg.startsWith("0"));
                    if (msg.startsWith("0")) {
                        notExit = false;
                    } else if (msg.startsWith("1")) {
                        server.broadcastMsg(clientName, msg.substring(1));
                    } else if (msg.startsWith("2")) {
                        server.getHistory(c);
                    } else if (msg.startsWith("3")) {
                        String username = msg.split(" ")[1];
                        Client_itf target = server.getClient(username);
                        if(target != null) {
                            server.sendMsg(target, clientName, msg.substring(username.length()+2));
                        }else{
                            System.out.println("The client "+ username +" doesn't exist");
                        }
                    }else if(msg.startsWith("4")){
                        server.getAllChatRooms(c);
                    }else if(msg.startsWith("5")){//join chatroom
                        String chatRoomTitle = msg.substring(2);
                        ChatRoom_itf chatRoom = server.getChatRoom(chatRoomTitle);
                        if(chatRoom != null) {
                            chatRoom.addUser(c);
                            server.setChatRoom(chatRoom,c);
                        }else{
                            System.out.println("The chat room "+ chatRoomTitle +" doesn't exist");
                        }
                    }else if(msg.startsWith("6")) {
                        String chatRoomTitle = c.getChatRoom().getTitle();
                        ChatRoom_itf chatRoom = server.getChatRoom(chatRoomTitle);
                        if(chatRoom != null && chatRoom.getUser(c) != null) {
                            chatRoom.getHistory(c,chatRoom);
                        }else{
                            System.out.println("You are not a member of "+ chatRoomTitle +" or it doesn't exist");
                        }
                    }else if(msg.startsWith("7")) {
                        String chatRoomTitle = c.getChatRoom().getTitle();
                        ChatRoom_itf chatRoom = server.getChatRoom(chatRoomTitle);
                        if(chatRoom != null && chatRoom.getUser(c) != null) {
                            System.out.println("length: "+chatRoomTitle.length());
                            chatRoom.addMessage(clientName,msg.substring(1));
                        }else{
                            System.out.println("You are not a member of "+ chatRoomTitle +" or it doesn't exist");
                        }
                    }else if(msg.startsWith("8")) {
                        String chatRoomTitle = c.getChatRoom().getTitle();
                        ChatRoom_itf chatRoom = server.getChatRoom(chatRoomTitle);
                        if(chatRoom != null && chatRoom.getUser(c) != null) {
                            chatRoom.removeUser(c);
                        }else{
                            System.out.println("You are not a member of "+ chatRoomTitle);
                        }
                    }else if(msg.startsWith("9")) {
                        System.out.println("Type 0 and press Enter to exit");
                        System.out.println("Type 1 followed by your message to send to all clients and press Enter");
                        System.out.println("Type 2 to get a history of all public messages sent by server");
                        System.out.println("Type 3 followed by a client's name and your message to send to that client and press Enter");
                        System.out.println("Type 4 to view available chat rooms and press Enter");
                        System.out.println("Type 5 followed by the chat room name to join the chat room and press Enter");
                        System.out.println("Type 6 to consult the chat room's history and press Enter");
                        System.out.println("Type 7 to send a message to the chat room and press Enter");
                        System.out.println("Type 8 to leave the chat room and press Enter");
                        System.out.println("Type 9 to view the menu again");
                    }
                }
                server.unRegister(c);
                System.out.println("Successfully disconnected client: " + c.getName());

            }else{
                System.out.println("Error in registration process");
            }


        } catch (Exception e) {
            System.out.println("Server failed: " + e);
        }
    }

}
