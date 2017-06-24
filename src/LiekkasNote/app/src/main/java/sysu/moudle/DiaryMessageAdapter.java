package sysu.moudle;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import sysu.liekkasnote.R;

import java.util.ArrayList;

/**
 * Created by sk2014 on 2016/12/20.
 */

public class DiaryMessageAdapter extends BaseAdapter {
    public Context context;
    public ArrayList<DiaryMessage> diaryMessageArrayList;

    public DiaryMessageAdapter(Context context) {
        this.context = context;
        diaryMessageArrayList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return diaryMessageArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return diaryMessageArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // to be continue
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.message_item_layout, null);
        }
        TextView messageOwner = (TextView)view.findViewById(R.id.messageOwner);
        TextView messageContent = (TextView)view.findViewById(R.id.messageContent);
        TextView date = (TextView)view.findViewById(R.id.date);
        ImageView imageView = (ImageView)view.findViewById(R.id.sex);
        if (diaryMessageArrayList.get(i).messageOwner.equals(AVUser.getCurrentUser().getUsername())) {
            messageOwner.setText(AVUser.getCurrentUser().getUsername());
            imageView.setImageResource(R.mipmap.app_logo);
        }
        else {
            messageOwner.setText("匿名用户");
            imageView.setImageResource(R.mipmap.ic_launcher);
        }
        messageContent.setText(diaryMessageArrayList.get(i).messageContent);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((diaryMessageArrayList.get(i).date.getYear() + 1900) + "-" + (diaryMessageArrayList.get(i).date.getMonth() + 1) + "-" + diaryMessageArrayList.get(i).date.getDate());
        date.setText(stringBuilder.toString());
        return view;
    }
}

