package sysu.liekkasnote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.List;

import sysu.moudle.Diary;
import sysu.moudle.DiaryMessage;
import sysu.moudle.DiaryMessageAdapter;

public class OtherDiaryDetail extends AppCompatActivity {

    // layout variables
    private ListView listView;
    private TextView title;
    private TextView content;
    private TextView date;
    private EditText submit_area;
    private Button submit;

    //other variables
    public DiaryMessageAdapter diaryMessageAdapter;
    private Bundle bundle;
    private Diary diary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_diary_detail);

        initVariables();
        if (bundle.getInt("tag") == 2)
            saveDiaryHistory();
        initUI();
        initDiscussions();
        initEventListener();
    }
    private void saveDiaryHistory() {
        AVObject historyToSave = new AVObject("otherDiaryHistory");
        historyToSave.put("diary", AVObject.createWithoutData("theDiary", diary.objectId));
        historyToSave.put("diaryId", diary.objectId);
        historyToSave.put("owner", AVUser.getCurrentUser().getUsername());
        historyToSave.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {

                } else {
                    Toast.makeText(OtherDiaryDetail.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void initVariables() {
        bundle = this.getIntent().getExtras();
        diary = (Diary) bundle.getSerializable("object");

        title = (TextView)findViewById(R.id.title);
        content = (TextView)findViewById(R.id.content);
        date = (TextView)findViewById(R.id.date);
        submit_area = (EditText)findViewById(R.id.submit_area);
        submit = (Button)findViewById(R.id.submit);

        listView = (ListView) findViewById(R.id.myListView);
        diaryMessageAdapter = new DiaryMessageAdapter(this);

    }
    private void initUI() {
        title.setText(diary.title);
        content.setText(diary.content);
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
        date.setText(sf.format(diary.date));
    }

    private void initEventListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageContent = submit_area.getText().toString();
                if (!messageContent.isEmpty()) {
                    final AVObject obj = new AVObject("theDiaryMessage");
                    obj.put("messageOwner", AVUser.getCurrentUser().getUsername());
                    obj.put("messageContent", messageContent);
                    obj.put("diaryId", diary.objectId);
                    obj.put("author", diary.author);
                    obj.put("diary", AVObject.createWithoutData("theDiary", diary.objectId));
                    obj.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                DiaryMessage diaryMessage = new DiaryMessage(
                                        diary.author,
                                        diary.objectId,
                                        obj.getString("messageOwner"),
                                        obj.getString("messageContent"),
                                        obj.getObjectId(),
                                        obj.getCreatedAt());
                                diaryMessageAdapter.diaryMessageArrayList.add(diaryMessage);
                                diaryMessageAdapter.notifyDataSetChanged();
                                submit_area.setText("");
                            } else {
                                Toast.makeText(OtherDiaryDetail.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(OtherDiaryDetail.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void initDiscussions() {
        AVQuery<AVObject> query = new AVQuery<>("theDiaryMessage");
        query.whereEqualTo("diaryId", diary.objectId);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    for (AVObject obj : list) {
                        //  Log.d("test", AVUser.getCurrentUser().getUsername() + " " + obj.getString("messageOwner"));
                        DiaryMessage diaryMessage = new DiaryMessage(
                                diary.author,
                                diary.objectId,
                                obj.getString("messageOwner"),
                                obj.getString("messageContent"),
                                obj.getObjectId(),
                                obj.getCreatedAt());
                        diaryMessageAdapter. diaryMessageArrayList.add(diaryMessage);
                    }
                    listView.setAdapter(diaryMessageAdapter);
                } else {
                    Toast.makeText(OtherDiaryDetail.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

