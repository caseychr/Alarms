package com.practice.mcasey.myapplication.CreateAlarm;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.RingtonePreference;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import com.practice.mcasey.myapplication.Model.Alarm;
import com.practice.mcasey.myapplication.Model.AlarmSingleton;
import com.practice.mcasey.myapplication.R;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateAlarmPresenter {

    Context mContext;
    public static List<String> sDays = new ArrayList<>();
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

    private static final int ZERO = 0;

    long timeLong;

    CreateAlarmView mView;

    public CreateAlarmPresenter(CreateAlarmView view, Context context) {
        mView = view;
        mContext = context;
    }

    public void currentTime(){
        String currentTime = timeFormat.format(Calendar.getInstance().getTime());
        mView.updateTime(currentTime);
    }

    public void updateTime(Alarm alarm, int hour, int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND ,ZERO);
        alarm.setTime(calendar.getTime().toString());
        timeLong = calendar.getTimeInMillis();
        //alarm.setTimeLong(calendar.getTimeInMillis());

        mView.updateTime(timeFormat.format(calendar.getTime()));
    }

    public void updateDays(){
        mView.updateDays();
    }

    public void updateAlarm(String description, String time, String ringtone, String days, Alarm alarm){
        alarm.setAlarmDescription(description);
        alarm.setTime(time);
        alarm.setRingtone(ringtone);
        alarm.setDays(days);
        if(!days.equals("Today"))
            alarm.setRecurring(true);
        alarm.setEnabled(true);
        AlarmSingleton.getInstance(mContext).updateAlarm(alarm);
        Log.i("ALARMU", alarm.toString());
        mView.onClickCreateAlarm();
    }

    public void createAlarm(String description, String time, String ringtone, String days, Alarm alarm){
        alarm = new Alarm(description, time, ringtone, days, true ,false);
        alarm.setTimeLong(timeLong);
        if(!days.equals("Today"))
            alarm.setRecurring(true);
        AlarmSingleton.getInstance(mContext).addAlarm(alarm);
        mView.onClickCreateAlarm();
    }

    private String setDaysOfWeek(String days){
        return days;
    }

    public void addDays(){
        sDays.add("Sunday");sDays.add("Monday");
        sDays.add("Tuesday");sDays.add("Wednesday");
        sDays.add("Thursday");sDays.add("Friday");
        sDays.add("Saturday");
    }

    public String updateDaysText(List<String> daysList){
        String daysText = "";
        if(daysList.size()==7)
            daysText = "Every Day";
        else if(daysList.size()==2 && daysList.contains("Saturday") && daysList.contains("Sunday"))
            daysText = "Weekend Days";
        else if(daysList.size()==5 && daysList.contains("Monday") && daysList.contains("Tuesday")
                && daysList.contains("Wednesday") && daysList.contains("Thursday") && daysList.contains("Friday"))
            daysText = "Week Days";
        else if(daysList.size()==0)
            daysText = "Today";
        else {
            for(String d:daysList)
                daysText += d+", ";
        }
        return daysText;
    }

    /**
     * getRingtones - We need to grab the URI of the ringtones and then we can set the default URI for each alarm
     * upon Pending Intent perhaps? But first we need to get permissions to alter settings from our app.
     * https://stackoverflow.com/questions/32083410/cant-get-write-settings-permission/42327628
     * @param context
     * @return
     */
    public List<String> getRingtones(Context context){
        if(!Settings.System.canWrite(context))
            openAndroidPermissionsMenu(context);
        //openAndroidPermissionsMenu(context);
        Uri ringtoneUri = MediaStore.Audio.Media.getContentUri("Carbon");
        //Ringtone  r = RingtoneManager.getRingtone(context, ringtoneUri);
        List<String> ringtoneList = new ArrayList<>();
        RingtoneManager ringtoneManager = new RingtoneManager(context);
        ringtoneManager.setType(RingtoneManager.TYPE_ALARM);
        Cursor cursor = ringtoneManager.getCursor();
        while(cursor.moveToNext()){
            String ringtoneTitle = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
                    //cursor.getNotificationUri();
            ringtoneList.add(ringtoneTitle);
        }
        //RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_ALARM, ringtoneUri);
        Log.i("CANWRITE", String.valueOf(Settings.System.canWrite(context)));
        Log.i("RINGTONES", ringtoneUri.toString());
        Log.i("RINGTONES_DEFAULT", RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_ALARM).toString());
        return ringtoneList;
    }

    private void openAndroidPermissionsMenu(Context context) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }
}
