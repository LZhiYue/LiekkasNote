package sysu.moudle;

import java.io.Serializable;
import java.util.Date;

/**
 *  这个是代表日记的数据结构
 *  其变量成员与数据库一致
 */

public class Diary implements Serializable {
    public String author;   // 用户
    public String title;    // 日记标题
    public String content;  // 日记内容
    public String objectId; // 唯一标识符 由数据库自动生成
    public Date date; // 日期

    public Diary(String author, String title, String content, String objectId, Date date) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.objectId = objectId;
        this.date = date;
    }

}
