package com.example.sk2014.liekkasnote;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import liekkas.moudle.Diary;
import liekkas.moudle.User;
import liekkasnote.utility.Dao.DiaryDao;
import liekkasnote.utility.Dao.DiaryDaoImpl;
import liekkasnote.utility.Dao.UserDao;
import liekkasnote.utility.Dao.UserDaoImpl;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.sk2014.liekkasnote", appContext.getPackageName());
    }
}
