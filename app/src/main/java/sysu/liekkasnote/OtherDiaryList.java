package sysu.liekkasnote;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;

import java.util.List;

import sysu.moudle.Diary;
import sysu.moudle.DiaryAdapter;

public class OtherDiaryList extends AppCompatActivity {
    // layout variables
    private ListView listView;
    public ProgressBar progressView;
    //other variables
    public DiaryAdapter diaryAdapter;
    public TextView totalNumber;
    public TextView yearNumber;
    public TextView monthNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_diary_list);

        initVariables();
        initDiaries();
        initEventListener();
    }

    private void initVariables() {
        listView = (ListView) findViewById(R.id.myListView);
        diaryAdapter = new DiaryAdapter(this);
        progressView = (ProgressBar)findViewById(R.id.register_progress);
        totalNumber = (TextView)findViewById(R.id.diaryTotalNumber);
        yearNumber = (TextView)findViewById(R.id.yearlyTotalNumber);
        monthNumber = (TextView)findViewById(R.id.monthlyTotalNumber);
    }

    private void initEventListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(OtherDiaryList.this, OtherDiaryDetail.class);
                Diary diary = (Diary) adapterView.getAdapter().getItem(i);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object", diary);
                bundle.putInt("tag", 1);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    private void initDiaries() {
        showProgress(true);
        AVQuery<AVObject> query = new AVQuery<>("otherDiaryHistory");
        query.whereEqualTo("owner", AVUser.getCurrentUser().getUsername());
        query.include("diary");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for (AVObject obj : list) {
                    AVObject diary = obj.getAVObject("diary");
                    diaryAdapter.diaryArrayList.add(
                            new Diary(diary.getString("author"),
                                    diary.getString("title"),
                                    diary.getString("content"),
                                    diary.getObjectId(),
                                    diary.getCreatedAt()));}
                listView.setAdapter(diaryAdapter);
                showProgress(false);
                monthNumber.setText(Integer.toString(list.size()));
                yearNumber.setText(Integer.toString(list.size()));
                totalNumber.setText(Integer.toString(list.size()));
            }
        });
    }

    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {

            listView.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
            listView.animate().setDuration(2000).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    listView.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
            progressView.animate().setDuration(2200).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
                }
            });
        }
    }

}
