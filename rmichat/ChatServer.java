/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmichat;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author liakopog
 */
public class ChatServer {

    public static Registry registry;

    public static void main(String[] args) {
        try {
            registry = LocateRegistry.createRegistry(1099);

            ChatService cs = new ChatService();
            ChatSystem_itf cs_stub = (ChatSystem_itf) UnicastRemoteObject.exportObject(cs, 0);
            registry.bind("ChatService", cs_stub);

            UserManager um = new UserManager();
            UserAuth_itf um_stub = (UserAuth_itf) UnicastRemoteObject.exportObject(um, 0);
            registry.bind("RegistrationService", um);

        } catch (RemoteException | AlreadyBoundException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
