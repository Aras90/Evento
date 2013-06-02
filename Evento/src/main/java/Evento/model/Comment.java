/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.model;

import java.sql.Timestamp;

/**
 *
 * @author Baro
 */
public class Comment {
     private long Id_Comment;
     private String Description;
     private Timestamp CreatedAt;
     private Picture Id_Picture;
     private User Id_User;

    

     
     
    public Comment() {
    }

    public Comment(long Id_Comment, String Description, Timestamp CreatedAt, Picture Id_Picture, User Id_User) {
        this.Id_Comment = Id_Comment;
        this.Description = Description;
        this.CreatedAt = CreatedAt;
        this.Id_Picture = Id_Picture;
        this.Id_User = Id_User;
    }
    public Comment(String Description, Timestamp CreatedAt, Picture Id_Picture, User Id_User) {
        this.Description = Description;
        this.CreatedAt = CreatedAt;
        this.Id_Picture = Id_Picture;
        this.Id_User = Id_User;
    }

    public Picture getId_Picture() {
        return Id_Picture;
    }

    public void setId_Picture(Picture Id_Picture) {
        this.Id_Picture = Id_Picture;
    }

    public User getId_User() {
        return Id_User;
    }

    public void setId_User(User Id_User) {
        this.Id_User = Id_User;
    }

   

    public long getId_Comment() {
        return Id_Comment;
    }

    public void setId_Comment(long Id_Comment) {
        this.Id_Comment = Id_Comment;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public Timestamp getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Timestamp CreatedAt) {
        this.CreatedAt = CreatedAt;
    }
      
      
}
