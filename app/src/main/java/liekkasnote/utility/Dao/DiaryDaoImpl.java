package liekkasnote.utility.Dao;

import java.util.Date;
import java.util.List;

import liekkas.moudle.Diary;
import liekkas.moudle.User;

/**
 * Created by Shower on 2017/6/16 0016.
 */

public class DiaryDaoImpl implements DiaryDao {
    @Override
    public Diary createDiary(String title, String content, Date modify_at, long user_id) {
        Diary diary = new Diary(title, content, modify_at, User.findById(User.class, user_id));
        diary.save();
        return diary;
    }

    @Override
    public Diary updateDiary(String title, String content, Date modify_at, long diary_id) {
        Diary diary = Diary.findById(Diary.class, diary_id);
        diary.setTitle(title);
        diary.setContent(content);
        diary.setModify_at(modify_at);
        diary.save();
        return diary;
    }

    @Override
    public Diary findById(long id) {
        return Diary.findById(Diary.class, id);
    }

    @Override
    public List<Diary> findAll() {
        return Diary.listAll(Diary.class);
    }

    @Override
    public void deleteDiary(long id) {
        Diary.findById(Diary.class, id).delete();
    }
}
