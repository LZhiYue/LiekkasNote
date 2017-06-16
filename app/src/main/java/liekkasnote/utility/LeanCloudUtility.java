package liekkasnote.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;

import liekkasnote.applicationpage.HomePage;
import liekkasnote.applicationpage.SignIn;

/**
 * Created by sk2014 on 2017/6/16.
 */

public class LeanCloudUtility {

    /*
        @return the string "success" means signIn successfully or the error message
     */
    public static void signUp(String username, String password, final Context context) {
        AVUser user = new AVUser();
        user.setUsername(username);
        user.setPassword(password);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Toast.makeText(context, "注册成功", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, SignIn.class));
                    ((Activity)context).finish();
                } else {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*
        @return the string "success" means signIn successfully or the error message
     */
    public static void SignIn(String username, String password, final Context context) {
        AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null) {
                    Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, HomePage.class));
                    ((Activity)context).finish();
                } else {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}