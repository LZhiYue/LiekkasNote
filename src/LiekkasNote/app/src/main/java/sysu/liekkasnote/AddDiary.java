package sysu.liekkasnote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;

import sysu.moudle.Diary;

public class AddDiary extends AppCompatActivity {
    public EditText title_edit;
    public EditText diary_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_diary);

        title_edit = (EditText)findViewById(R.id.title_edit);
        diary_edit = (EditText)findViewById(R.id.diary_edit);

        Button submit = (Button)findViewById(R.id.submit);
        if (submit != null) {
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    submitDairy();
                }
            });
        }
    }

    public void submitDairy() {
        String title = title_edit.getText().toString();
        String content = diary_edit.getText().toString();
        if (!title.equals("") && !content.equals("")) {
            final AVObject object = new AVObject("theDiary");
            String author = AVUser.getCurrentUser().getUsername();
            object.put("title", title);
            object.put("content", content);
            object.put("author", author);
            object.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        //Toast.makeText(AddDiary.this, "update ok", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddDiary.this, DiaryDetail.class);
                        Bundle bundle = new Bundle();
                        Diary diary = new Diary(object.getString("author"), object.getString("title"), object.getString("content"), object.getObjectId(), object.getCreatedAt());
                        bundle.putSerializable("object", diary);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        AddDiary.this.finish();
                    } else {
                        Toast.makeText(AddDiary.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(AddDiary.this, "标题和内容不能为空", Toast.LENGTH_SHORT).show();
        }
    }
}

