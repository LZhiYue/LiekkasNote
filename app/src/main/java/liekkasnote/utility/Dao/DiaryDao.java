package liekkasnote.utility.Dao;

import java.util.Date;
import java.util.List;

import liekkas.moudle.Diary;

/**
 * Created by Shower on 2017/6/16 0016.
 */

public interface DiaryDao {
    Diary createDiary(String title, String content, Date modify_at, long user_id);
    Diary updateDiary(String title, String content, Date modify_at, long diary_id);
    Diary findById(long id);
    List<Diary> findAll();
    void deleteDiary(long id);
}
