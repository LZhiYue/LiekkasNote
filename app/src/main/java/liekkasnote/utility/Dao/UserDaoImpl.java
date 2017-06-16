package liekkasnote.utility.Dao;

import liekkas.moudle.User;

/**
 * Created by Shower on 2017/6/16 0016.
 */

public class UserDaoImpl implements UserDao {
    @Override
    public User createUser(String username, String password, String avatar) {
        User user = new User(username, password, avatar);
        user.save();
        return user;
    }

    @Override
    public User updateUser(long id, String avatar) {
        User user = User.findById(User.class, id);
        user.setAvatar(avatar);
        user.save();
        return user;
    }

    @Override
    public User findById(long id) {
        return User.findById(User.class, id);
    }
}
