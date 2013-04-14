/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.model;

/**
 *
 * @author Baro
 */
public class Invitation {
     private long Id_Invitation;
     private String CreatedAt;
     private Event Id_Event;
     private User Id_User;
     
     
    public Invitation() {
    }

    public Invitation(long Id_Invitation, String CreatedAt, Event Id_Event, User Id_User) {
        this.Id_Invitation = Id_Invitation;
        this.CreatedAt = CreatedAt;
        this.Id_Event = Id_Event;
        this.Id_User = Id_User;
    }
    public Invitation(String CreatedAt, Event Id_Event, User Id_User) {
        this.CreatedAt = CreatedAt;
        this.Id_Event = Id_Event;
        this.Id_User = Id_User;
    }

    public Event getId_Event() {
        return Id_Event;
    }

    public void setId_Event(Event Id_Event) {
        this.Id_Event = Id_Event;
    }

    public User getId_User() {
        return Id_User;
    }

    public void setId_User(User Id_User) {
        this.Id_User = Id_User;
    }

   

    
    public long getId_Invitation() {
        return Id_Invitation;
    }

    public void setId_Invitation(long Id_Invitation) {
        this.Id_Invitation = Id_Invitation;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String CreatedAt) {
        this.CreatedAt = CreatedAt;
    }
     
}
