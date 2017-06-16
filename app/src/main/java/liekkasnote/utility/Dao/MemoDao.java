package liekkasnote.utility.Dao;

import java.util.Date;
import java.util.List;

import liekkas.moudle.Memo;

/**
 * Created by Shower on 2017/6/16 0016.
 */

public interface MemoDao {
    Memo createMemo(String content, boolean is_done, Date modify_at, long user_id);
    Memo updateMemo(String content, boolean is_done, Date modify_at, long memo_id);
    Memo findById(long id);
    List<Memo> findAll();
    void deleteMemo(long id);
}
