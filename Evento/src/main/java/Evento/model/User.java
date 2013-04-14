/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.model;

/**
 *
 * @author Baro
 */
public class User {
    private long Id_User;
    private String Description;
    private String Password;
    private String CreatedAt; //timestamp?
    private String EditetAt;
    private String Email;

    public User() {
    }

    public User(long Id_User, String Description, String Password, String CreatedAt, String EditetAt, String Email) {
        this.Id_User = Id_User;
        this.Description = Description;
        this.Password = Password;
        this.CreatedAt = CreatedAt;
        this.EditetAt = EditetAt;
        this.Email = Email;
    }
    public User(String Description, String Password, String CreatedAt, String EditetAt, String Email) {
        this.Description = Description;
        this.Password = Password;
        this.CreatedAt = CreatedAt;
        this.EditetAt = EditetAt;
        this.Email = Email;
    }

    public User(String password, String email,String desc) {
		super();
		Password = password;
		Description = desc;
		Email = email;
	}

	public long getId_User() {
        return Id_User;
    }

    public void setId_User(long Id_User) {
        this.Id_User = Id_User;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String CreatedAt) {
        this.CreatedAt = CreatedAt;
    }

    public String getEditetAt() {
        return EditetAt;
    }

    public void setEditetAt(String EditetAt) {
        this.EditetAt = EditetAt;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    
}
