package liekkasnote.applicationpage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.sk2014.liekkasnote.R;

public class DiaryList extends AppCompatActivity {
    public ImageView addDiary;
    public ImageView deleteDiary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_list);
        findViews();
        setClickListener();
    }

    public void findViews() {
        addDiary = (ImageView)findViewById(R.id.add_diary);
        deleteDiary = (ImageView)findViewById(R.id.delete_diary);
    }

    public void setClickListener() {
        addDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
