package sysu.liekkasnote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {
    public EditText usernameEdit;
    public EditText passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameEdit = (EditText)findViewById(R.id.usernameEdit);
        passwordEdit = (EditText)findViewById(R.id.passwordEdit);

        Button submit = (Button)findViewById(R.id.submit);
        TextView denglu = (TextView) findViewById(R.id.denglu);
        if (denglu != null) {
            denglu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    SignUpActivity.this.finish();
                }
            });
        }

        ImageView backToLogin = (ImageView) findViewById(R.id.back);
        if (backToLogin != null) {
            backToLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    SignUpActivity.this.finish();
                }
            });
        }

        if (submit != null) {
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView username = (TextView)findViewById(R.id.usernameEdit);
                    TextView password = (TextView)findViewById(R.id.passwordEdit);
                    TextView passwordConfirm = (TextView)findViewById(R.id.passwordConfirm);
                    if (!username.getText().equals("") && !password.getText().equals("")) {
                        if (!passwordConfirm.getText().equals(password.getText().toString())) {
                            Toast.makeText(SignUpActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                        }else {
                            registerUser();
                        }
                    } else {
                        Toast.makeText(SignUpActivity.this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        TextView clear = (TextView) findViewById(R.id.clear);
        if (clear != null) {
            clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((TextView)findViewById(R.id.usernameEdit)).setText("");
                    ((TextView)findViewById(R.id.passwordEdit)).setText("");
                    ((TextView)findViewById(R.id.passwordConfirm)).setText("");
                }
            });
        }
    }

    public void registerUser() {
        String username = usernameEdit.getText().toString();
        String password = passwordEdit.getText().toString();

        if (username.equals("")) Toast.makeText(SignUpActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
        else if (password.equals("")) Toast.makeText(SignUpActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
        else {
            AVUser user = new AVUser();
            user.setUsername(username);
            user.setPassword(password);
            Log.d("Test", password);
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                        SignUpActivity.this.finish();
                    } else {
                        if (e.getCode() == AVException.USERNAME_TAKEN) Toast.makeText(SignUpActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
