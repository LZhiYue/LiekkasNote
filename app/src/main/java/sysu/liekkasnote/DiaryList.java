package sysu.liekkasnote;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.LogUtil;

import java.util.List;

import sysu.moudle.Diary;
import sysu.moudle.DiaryAdapter;


public class DiaryList extends AppCompatActivity {
    public ListView listView;
    public DiaryAdapter diaryAdapter;
    public View progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_diary_history);

        listView = (ListView)findViewById(R.id.diary_list);
        diaryAdapter = new DiaryAdapter(this);
        progressView = (View)findViewById(R.id.register_progress);


        getData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(DiaryList.this, DiaryDetail.class);
                Diary diary = (Diary) adapterView.getAdapter().getItem(i);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object", diary);
                bundle.putInt("tag", 1);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }

    public void getData() {
        showProgress(true);
        AVQuery<AVObject> query = new AVQuery<>("theDiary");
        query.whereEqualTo("author", AVUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                Log.d("test", Integer.toString(list.size()));
                for (AVObject obj : list) {
                    diaryAdapter.diaryArrayList.add(new Diary(obj.getString("author"), obj.getString("title"), obj.getString("content"), obj.getObjectId(), obj.getCreatedAt()));
                }
                showProgress(false);
                listView.setAdapter(diaryAdapter);
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

