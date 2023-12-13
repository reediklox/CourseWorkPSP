package Server.Entity;

import java.io.Serializable;

import Server.Entity.Interface.IntegerValidate;
import lombok.Getter;


public class Users implements Serializable {
    @Getter
    private Integer user_id;
    @Getter
    private String login, password;

    private boolean active;

    public void setUser_id(Integer user_id){
        this.user_id = IntegerValidate.SetInteger(user_id);
    }

    public void setLogin(String login){
        this.login = login;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setActive(boolean active){this.active = active;}
    public Boolean getActive() {return this.active;}

    @Override
    public String toString(){
        return "Users{" +
                "user_id=" + user_id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", active='" + active + '}';
    }

    public Users() {}

    public Users(Integer user_id, String login, String password){
        this.user_id = IntegerValidate.SetInteger(user_id);
        this.login = login;
        this.password = password;
        this.active = false;
    }

    public Users(String login, String password){
        this.login = login;
        this.password = password;
        this.active = false;
    }
}
