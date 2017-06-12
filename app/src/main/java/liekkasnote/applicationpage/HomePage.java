package liekkasnote.applicationpage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.view.View;
import android.widget.ImageView;

import com.example.sk2014.liekkasnote.R;

public class HomePage extends AppCompatActivity {
    public ImageView diaryArrow;
    public ImageView todoArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        findViews();
        setClickEvent();
    }

    public void setClickEvent() {
        diaryArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, DiaryList.class));
            }
        });
    }

    public void findViews() {
        diaryArrow = (ImageView)findViewById(R.id.diary_arrow);
        todoArrow = (ImageView)findViewById(R.id.todo_arrow);
    }
}
