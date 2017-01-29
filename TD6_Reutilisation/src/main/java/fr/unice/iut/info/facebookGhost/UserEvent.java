package main.java.fr.unice.iut.info.facebookGhost;

public class UserEvent implements Event
{
    
    private User user;
    
    public UserEvent (User user)
    {
        this.user = user;
    }
    
    public User getAddedUser ()
    {
        return user;
    }
    
}
