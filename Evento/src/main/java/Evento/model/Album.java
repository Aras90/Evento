/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.model;

/**
 *
 * @author Baro
 */
public class Album {
     private long Id_Album;
     private String CreatedAt;
     private Event Id_Event;
    
     public Album() {
    }

    public Album(long Id_Album, String CreatedAt, Event Id_Event) {
        this.Id_Album = Id_Album;
        this.CreatedAt = CreatedAt;
        this.Id_Event = Id_Event;
    }
    public Album(String CreatedAt, Event Id_Event) {
        this.CreatedAt = CreatedAt;
        this.Id_Event = Id_Event;
    }

    public Event getId_Event() {
        return Id_Event;
    }

    public void setId_Event(Event Id_Event) {
        this.Id_Event = Id_Event;
    }


    public long getId_Album() {
        return Id_Album;
    }

    public void setId_Album(long Id_Album) {
        this.Id_Album = Id_Album;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String CreatedAt) {
        this.CreatedAt = CreatedAt;
    }
    
    
     
     
     
}
