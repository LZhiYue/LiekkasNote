package sysu.moudle;

import java.io.Serializable;
import java.util.Date;

/**
 * 用于表示评论日记的消息的数据结构
 * 其变量成员与数据库的表中一致
 */

public class DiaryMessage implements Serializable {
    public String author;  // 日记作者
    public String diaryId;  // 该日记在数据库中的ID
    public String messageOwner; // 评论的用户的ID
    public String messageContent; // 评论的内容
    public String objectId;   // 该评论在数据库中的ID
    public Date date;  // 日期

    public DiaryMessage(String author, String diaryId, String messageOwner, String messageContent, String objectId, Date date) {
        this.author = author;
        this.diaryId = diaryId;
        this.messageOwner = messageOwner;
        this.messageContent = messageContent;
        this.objectId = objectId;
        this.date = date;
    }
}