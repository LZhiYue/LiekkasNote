package liekkasnote.utility.Dao;

import java.util.Date;
import java.util.List;

import liekkas.moudle.Memo;
import liekkas.moudle.User;

/**
 * Created by Shower on 2017/6/16 0016.
 */

public class MemoDaoImpl implements MemoDao {
    @Override
    public Memo createMemo(String content, boolean is_done, Date modify_at, long user_id) {
        Memo memo = new Memo(content, is_done, modify_at, User.findById(User.class, user_id));
        memo.save();
        return memo;
    }

    @Override
    public Memo updateMemo(String content, boolean is_done, Date modify_at, long memo_id) {
        Memo memo = Memo.findById(Memo.class, memo_id);
        memo.setContent(content);
        memo.setIs_done(is_done);
        memo.setModify_at(modify_at);
        memo.save();
        return memo;
    }

    @Override
    public Memo findById(long id) {
        return Memo.findById(Memo.class, id);
    }

    @Override
    public List<Memo> findAll() {
        return Memo.listAll(Memo.class);
    }

    @Override
    public void deleteMemo(long id) {
        Memo.findById(Memo.class, id).delete();
    }
}
