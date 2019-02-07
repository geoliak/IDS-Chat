/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmichat;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.rmi.Remote;

/**
 *
 * @author liakopog
 */
public class ChatClient implements User_itf, Remote {

    public static void main(String[] args) {
        try {
            if (args.length < 1) {
                System.out.println("Usage: java ChatClient <rmiregistry host>");
                return;
            }

            String host = args[0];
            Registry registry = LocateRegistry.getRegistry(host);

            UserAuth_itf uauth = (UserAuth_itf) registry.lookup("RegistrationService");
            ChatSystem_itf cs = (ChatSystem_itf) registry.lookup("ChatService");

            User_itf cc_stub = (User_itf) UnicastRemoteObject.exportObject(ChatClient, 0);

//            uauth.register("name" + (new Random().doubles()),);
//            cs.send_message("hello chat!");
        } catch (RemoteException | NotBoundException e) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, e);

        }

    }
}
