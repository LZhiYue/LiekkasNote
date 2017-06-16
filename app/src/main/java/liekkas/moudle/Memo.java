package liekkas.moudle;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by sk2014 on 2017/6/10.
 */

public class Memo extends SugarRecord<Memo> {
    private String content;
    private boolean is_done;
    private Date modify_at;

    // relation
    private User user;

    public Memo() {
    }

    public Memo(String content, boolean is_done, Date modify_at, User user) {
        this.content = content;
        this.is_done = is_done;
        this.modify_at = modify_at;
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean is_done() {
        return is_done;
    }

    public void setIs_done(boolean is_done) {
        this.is_done = is_done;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getModify_at() {
        return modify_at;
    }

    public void setModify_at(Date modify_at) {
        this.modify_at = modify_at;
    }

    @Override
    public String toString() {
        return "Memo{" +
                "content='" + content + '\'' +
                ", is_done=" + is_done +
                ", user=" + user +
                ", modify_at=" + modify_at +
                '}';
    }
}
