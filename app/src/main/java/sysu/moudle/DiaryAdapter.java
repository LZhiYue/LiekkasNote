package sysu.moudle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sysu.liekkasnote.R;

/**
 * Created by sk2014 on 2016/12/20.
 */

public class DiaryAdapter extends BaseAdapter {
    public ArrayList<Diary> diaryArrayList;
    public Context context;


    public DiaryAdapter(Context context) {
        this.context = context;
        diaryArrayList = new ArrayList<Diary>();
    }

    @Override
    public int getCount() {
        return diaryArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return diaryArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // to be continue
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.diary_item_layout, null);
        }
        TextView title = (TextView)view.findViewById(R.id.title);
        TextView date = (TextView)view.findViewById(R.id.date);
        title.setText(diaryArrayList.get(i).title);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((diaryArrayList.get(i).date.getYear() + 1900) + "-" + (diaryArrayList.get(i).date.getMonth() + 1) + "-" + diaryArrayList.get(i).date.getDate());
        date.setText(stringBuilder.toString());
        return view;
    }
}
