package sysu.liekkasnote;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;

import java.util.List;

import datastruct.Diary;
import datastruct.DiaryMessage;
import datastruct.DiaryMessageAdapter;

public class DiaryDetail extends AppCompatActivity {
    public Diary diary;
    public DiaryMessageAdapter diaryMessageAdapter;
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_detail);

        listView = (ListView)findViewById(R.id.myListView);
        diaryMessageAdapter = new DiaryMessageAdapter(MyDiaryDetail.this);

        Bundle bundle = getIntent().getExtras();

        diary = (Diary) bundle.getSerializable("object");
        updateUI();

        initDiscussions();

    }

    public void updateUI() {
        TextView title = (TextView)findViewById(R.id.title);
        TextView date = (TextView)findViewById(R.id.date);
        TextView content = (TextView)findViewById(R.id.content);

        if (diary != null) {
            title.setText(diary.title);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((diary.date.getYear() + 1900) + "-" + (diary.date.getMonth() + 1) + "-" + diary.date.getDate());
            date.setText(stringBuilder.toString());
            content.setText(diary.content);
        }
    }

    private void initDiscussions() {
        AVQuery<AVObject> query = new AVQuery<>("theDiaryMessage");
        query.whereEqualTo("diaryId", diary.objectId);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    for (AVObject obj : list) {
                        DiaryMessage diaryMessage = new DiaryMessage(
                                diary.author,
                                diary.objectId,
                                obj.getString("messageOwner"),
                                obj.getString("messageContent"),
                                obj.getObjectId(),
                                obj.getCreatedAt());
                        diaryMessageAdapter.diaryMessageArrayList.add(diaryMessage);
                    }
                    listView.setAdapter(diaryMessageAdapter);
                } else {
                    Toast.makeText(MyDiaryDetail.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
