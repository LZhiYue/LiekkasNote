package sysu.liekkasnote;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SaveCallback;

public class LoginActivity extends AppCompatActivity {
    public EditText usernameEdit;
    public EditText passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEdit = (EditText)findViewById(R.id.usernameEdit);
        passwordEdit = (EditText)findViewById(R.id.passwordEdit);

        Button submit = (Button)findViewById(R.id.submit);
        if (submit != null) {
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    attemptLogin();
                }
            });
        }
        TextView zhuce = (TextView)findViewById(R.id.zhuce);
        if (zhuce != null) {
            zhuce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                    LoginActivity.this.finish();
                }
            });
        }
        ImageView register = (ImageView) findViewById(R.id.register);
        if (register != null) {
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                    LoginActivity.this.finish();
                }
            });
        }

    }

    public void attemptLogin() {
        String username = usernameEdit.getText().toString();
        String password = passwordEdit.getText().toString();

        if (username.equals("")) Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
        else if (password.equals("")) Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
        else {
            AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
                @Override
                public void done(AVUser avUser, AVException e) {
                    if (e == null) {
                        // 登录成功 页面挑战
                        // startActivity();
                        //Toast.makeText(MainActivity.this, "OKK", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, HomePageActivity.class));
                        finish();
                    } else {
                        if (e.getCode() == AVException.USERNAME_PASSWORD_MISMATCH) Toast.makeText(LoginActivity.this, "密码不正确", Toast.LENGTH_SHORT).show();
                        else if (e.getCode() == AVException.USER_DOESNOT_EXIST) Toast.makeText(LoginActivity.this, "用户名不存在", Toast.LENGTH_SHORT).show();
                        else if (e.getCode() == 219) Toast.makeText(LoginActivity.this, "密码失败次数过多，请稍后尝试", Toast.LENGTH_SHORT).show();
                        else Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
