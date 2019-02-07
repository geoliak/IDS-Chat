/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmichat;

import java.rmi.Remote;

/**
 *
 * @author liakopog
 */
public interface ChatSystem_itf extends Remote {

    public void push_message_to_subscribers();

    public void send_message(String message, UserAuth_itf user);

}
