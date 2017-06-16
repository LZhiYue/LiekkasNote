package liekkasnote.utility.Dao;

import liekkas.moudle.User;

/**
 * Created by Shower on 2017/6/16 0016.
 */

public interface UserDao {
    User createUser(String username, String password, String avatar);
    User updateUser(long id, String avatar);
    User findById(long id);
}
