package Server.Entity;

import java.io.Serializable;
import lombok.Getter;

@Getter
public class Users implements Serializable {
    private Integer user_id;
    private String login, password;

    @Getter
    private boolean active;

    public void setUser_id(Integer user_id){
        this.user_id = user_id;
    }

    public void setLogin(String login){
        this.login = login;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setActive(boolean active){this.active = active;}

    @Override
    public String toString(){
        return "Users{" +
                "user_id=" + user_id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", active='" + active + '}';
    }

    public Users() {}

    public Users(String login, String password){
        this.login = login;
        this.password = password;
        this.active = false;
    }
}
