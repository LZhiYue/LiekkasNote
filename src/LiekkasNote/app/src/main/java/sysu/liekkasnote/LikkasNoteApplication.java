package sysu.liekkasnote;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

public class LikkasNoteApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"cbTGGh3CXISdG0ulijUXi3Fd-gzGzoHsz","TROXTejyLqy0JokhMWJ7IqYq");
    }
}
