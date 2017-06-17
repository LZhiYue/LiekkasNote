package sysu.liekkasnote;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.MainThread;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import sysu.moudle.Diary;
import sysu.moudle.DiaryMessage;
import sysu.moudle.DiaryMessageAdapter;

import sysu.service.MessageService;

import android.widget.LinearLayout;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.okhttp.internal.framed.FrameReader;

public class HomePageActivity extends AppCompatActivity {
    private boolean flag = true;
    private MessageService ms;
    private Context context;
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ms = ((MessageService.MyBinder) iBinder).getService();
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        context = HomePageActivity.this;
        ms = new MessageService(context);
        Intent intent = new Intent(this, MessageService.class);
        bindService(intent, sc, BIND_AUTO_CREATE);
        ms.request();
        LinearLayout edit_button = (LinearLayout) findViewById(R.id.edit_button);
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePageActivity.this, AddDiary.class));
            }
        });

        LinearLayout my_diary_button = (LinearLayout) findViewById(R.id.my_diary_button);
        my_diary_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePageActivity.this, DiaryList.class));
            }
        });

        LinearLayout other_diary_button = (LinearLayout) findViewById(R.id.other_diary_button);
        other_diary_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePageActivity.this, OtherDiaryList.class));
            }
        });

        LinearLayout request_diary_button = (LinearLayout) findViewById(R.id.request_diary_button);
        request_diary_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOtherDiary();
            }
        });
        AVInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                String installationId = AVInstallation.getCurrentInstallation().getInstallationId();
            }
        });
        PushService.setDefaultPushCallback(this, HomePageActivity.class);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        PackageManager pm = getPackageManager();
        ResolveInfo homeInfo = pm.resolveActivity(
                new Intent(Intent.ACTION_MAIN)
                        .addCategory(Intent.CATEGORY_HOME), 0);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            ActivityInfo ai = homeInfo.activityInfo;
//            Intent startIntent = new Intent(Intent.ACTION_MAIN);
//            startIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//            startIntent
//                    .setComponent(new ComponentName(ai.packageName, ai.name));
//            startActivity(startIntent);
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    HomePageActivity.this);
            builder.setTitle("提示");
            builder.setMessage("确定注销账号吗");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    unbindService(sc);
                    ms.setflag(false);
                    AVUser.getCurrentUser().logOut();
                    Intent intent = new Intent(HomePageActivity.this, LoginActivity.class);
                    HomePageActivity.this.finish();
                    startActivity(intent);
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
            return true;
        } else
            return super.onKeyDown(keyCode, event);
    }
    private void getOtherDiary() {

        AVQuery<AVObject> historyDiary = new AVQuery<AVObject>("otherDiaryHistory");
        historyDiary.whereEqualTo("owner", AVUser.getCurrentUser().getUsername());
        AVQuery<AVObject> allDiary = new AVQuery<>("theDiary");
        allDiary.whereNotEqualTo("author", AVUser.getCurrentUser().getUsername());
        allDiary.whereDoesNotMatchKeyInQuery("objectId", "diaryId", historyDiary);
        allDiary.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null ) {
                    if (list.size() != 0) {
                        int random = (int)(Math.random()*list.size());
                        AVObject obj = list.get(random);
                        Intent intent = new Intent(HomePageActivity.this, OtherDiaryDetail.class);
                        Diary diary = new Diary(
                                obj.getString("author"),
                                obj.getString("title"),
                                obj.getString("content"),
                                obj.getObjectId(),
                                obj.getCreatedAt());
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("object", diary);
                        bundle.putInt("tag", 2);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        Toast.makeText(HomePageActivity.this, "无更多日记", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomePageActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        float[] accValues = new float[3];
        int times = 0;

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            switch (sensorEvent.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    accValues = sensorEvent.values;
                    float x = accValues[0]; // x轴方向的重力加速度，向右为正
                    float y = accValues[1]; // y轴方向的重力加速度，向前为正
                    float z = accValues[2]; // z轴方向的重力加速度，向上为正
                    if (x > 18 || y > 18 || z > 18) {
                        times++;
                        if (times % 18 == 0) {
//                            Toast.makeText(NavigationPage.this, "Shake the phone " + times + " times", Toast.LENGTH_SHORT).show();
                            getOtherDiary();
                            times = 0;
                        }
                    }
                    break;

                default:
                    break;
            }

        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {}
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
    }
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
    }
}

