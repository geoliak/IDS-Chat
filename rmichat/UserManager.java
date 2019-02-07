/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmichat;

import java.util.HashMap;

/**
 *
 * @author liakopog
 */
public class UserManager implements UserAuth_itf {

    HashMap<String, User_itf> userlist = new HashMap<>();

    @Override
    public String register(String name, User_itf client) {
        if (userlist.containsKey(name)) {
            return "This username is already in use, please choose another";
        } else {
            userlist.put(name, client);
            return "login succesful, welcome to our chat!";
        }
    }

    @Override
    public void leave(User_itf client) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
