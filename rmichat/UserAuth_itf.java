package rmichat;

import java.rmi.Remote;

/**
 * @author liakopog
 */
public interface UserAuth_itf extends Remote {

    public String register(String name, User_itf client);

    public void leave(User_itf client);

}
