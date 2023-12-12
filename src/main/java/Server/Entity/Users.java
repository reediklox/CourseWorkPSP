package Server.Entity;

import java.io.Serializable;
import lombok.Getter;

@Getter
public class Users implements Serializable {
    private Integer user_id;
    private String login, password;

    public void setUser_id(Integer user_id){
        this.user_id = user_id;
    }

    public void setLogin(String login){
        this.login = login;
    }

    public void setPassword(String password){
        this.password = password;
    }

}
