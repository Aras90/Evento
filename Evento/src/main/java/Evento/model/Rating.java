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
public class Rating {
     private long Id_Rating;
     private Timestamp CreatedAt;
     private int Value;
     private Picture Id_Picture;
     private User Id_User;
     
     
    public Rating() {
    }

    public Rating(long Id_Rating, Timestamp CreatedAt, int Value, Picture Id_Picture, User Id_User) {
        this.Id_Rating = Id_Rating;
        this.CreatedAt = CreatedAt;
        this.Value = Value;
        this.Id_Picture = Id_Picture;
        this.Id_User = Id_User;
    }
    public Rating(Timestamp CreatedAt, int Value, Picture Id_Picture, User Id_User) {
        this.CreatedAt = CreatedAt;
        this.Value = Value;
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


   

    public long getId_Rating() {
        return Id_Rating;
    }

    public void setId_Rating(long Id_Rating) {
        this.Id_Rating = Id_Rating;
    }

    public Timestamp getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Timestamp CreatedAt) {
        this.CreatedAt = CreatedAt;
    }

    public int getValue() {
        return Value;
    }

    public void setValue(int Value) {
        this.Value = Value;
    }
     
}
