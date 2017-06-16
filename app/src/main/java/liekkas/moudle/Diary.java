package liekkas.moudle;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by sk2014 on 2017/6/10.
 */

public class Diary extends SugarRecord<Diary> {
    private String title;
    private String content;
    private Date modify_at;

    // relation
    private User user;

    public Diary() {
    }

    public Diary(String title, String content, Date modify_at, User user) {
        this.title = title;
        this.content = content;
        this.modify_at = modify_at;
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getModify_at() {
        return modify_at;
    }

    public void setModify_at(Date modify_at) {
        this.modify_at = modify_at;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Diary{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", modify_at=" + modify_at +
                ", user=" + user +
                '}';
    }
}
