package fr.sboivin.springdemo.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class User {
    private int id;
    private String email;
    private String roles;
    private String password;
    private String name;
    private String photouser;
    private String userResetpasstoken;
    private Timestamp userResetpassdate;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "roles")
    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "photouser")
    public String getPhotouser() {
        return photouser;
    }

    public void setPhotouser(String photouser) {
        this.photouser = photouser;
    }

    @Basic
    @Column(name = "user_resetpasstoken")
    public String getUserResetpasstoken() {
        return userResetpasstoken;
    }

    public void setUserResetpasstoken(String userResetpasstoken) {
        this.userResetpasstoken = userResetpasstoken;
    }

    @Basic
    @Column(name = "user_resetpassdate")
    public Timestamp getUserResetpassdate() {
        return userResetpassdate;
    }

    public void setUserResetpassdate(Timestamp userResetpassdate) {
        this.userResetpassdate = userResetpassdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (roles != null ? !roles.equals(user.roles) : user.roles != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (photouser != null ? !photouser.equals(user.photouser) : user.photouser != null) return false;
        if (userResetpasstoken != null ? !userResetpasstoken.equals(user.userResetpasstoken) : user.userResetpasstoken != null)
            return false;
        if (userResetpassdate != null ? !userResetpassdate.equals(user.userResetpassdate) : user.userResetpassdate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (photouser != null ? photouser.hashCode() : 0);
        result = 31 * result + (userResetpasstoken != null ? userResetpasstoken.hashCode() : 0);
        result = 31 * result + (userResetpassdate != null ? userResetpassdate.hashCode() : 0);
        return result;
    }
}
