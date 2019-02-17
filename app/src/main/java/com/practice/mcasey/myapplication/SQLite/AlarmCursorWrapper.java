package com.practice.mcasey.myapplication.SQLite;

import static com.practice.mcasey.myapplication.SQLite.AlarmDBSchema.AlarmTable.*;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.practice.mcasey.myapplication.Model.Alarm;
import com.practice.mcasey.myapplication.SQLite.AlarmDBSchema.AlarmTable;

import java.util.UUID;

public class AlarmCursorWrapper extends CursorWrapper {

    public AlarmCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Alarm getAlarm(){
        String description = getString(getColumnIndex(Cols.DESCRIPTION));
        String time = getString(getColumnIndex(Cols.TIME));
        long timeLong = getLong(getColumnIndex(Cols.TIME_LONG));
        String days = getString(getColumnIndex(Cols.DAYS));
        String ringtone = getString(getColumnIndex(Cols.RINGTONE));
        int isEnabled = getInt(getColumnIndex(Cols.ENABLED));
        int isRecurring = getInt(getColumnIndex(Cols.RECURRING));

        Alarm alarm = new Alarm();
        alarm.setAlarmDescription(description);
        alarm.setTime(time);
        alarm.setTimeLong(timeLong);
        alarm.setDays(days);
        alarm.setRingtone(ringtone);
        alarm.setEnabled(isEnabled != 0);
        alarm.setRecurring(isRecurring != 0);

        return alarm;
    }
}
