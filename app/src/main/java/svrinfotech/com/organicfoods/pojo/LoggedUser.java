package svrinfotech.com.organicfoods.pojo;

import com.orm.SugarRecord;

import java.io.Serializable;

public class LoggedUser extends SugarRecord implements Serializable {

    String username,status,mail_id;

    public LoggedUser() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMail_id() {
        return mail_id;
    }

    public void setMail_id(String mail_id) {
        this.mail_id = mail_id;
    }
}
