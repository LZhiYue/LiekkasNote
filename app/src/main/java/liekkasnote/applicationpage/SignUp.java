package liekkasnote.applicationpage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.example.sk2014.liekkasnote.R;

import liekkasnote.utility.LeanCloudUtility;

public class SignUp extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private EditText confirm_password;
    private Button signup_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        findViews();
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupUser();
            }
        });
    }

    private void signupUser() {
        String name = username.getText().toString();
        String pwd = password.getText().toString();
        String confirm_pwd = confirm_password.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(SignUp.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(SignUp.this, "密码不能为空", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(confirm_pwd)) {
            Toast.makeText(SignUp.this, "确认密码不能为空", Toast.LENGTH_SHORT).show();
        } else if (!pwd.equals(confirm_pwd)) {
            Toast.makeText(SignUp.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
        } else {
            LeanCloudUtility.signUp(name, pwd, SignUp.this);
        }
    }

    private void findViews() {
        username = (EditText) findViewById(R.id.input_username);
        password = (EditText) findViewById(R.id.input_password);
        confirm_password = (EditText) findViewById(R.id.confirm_input_password);

        signup_btn = (Button) findViewById(R.id.signUp_button);
    }
}
