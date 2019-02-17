package com.practice.mcasey.myapplication.Model;

import org.parceler.Parcel;

import java.util.UUID;

@Parcel
public class Alarm {

    private String mAlarmDescription;
    private String mTime;
    private long mTimeLong;
    private String mDays;
    private String mRingtone;
    private boolean mEnabled;
    private boolean mRecurring;

    public Alarm() {
    }

    public Alarm(String alarmDescription, String time, String days, String ringtone, boolean enabled, boolean recurring) {
        mAlarmDescription = alarmDescription;
        mTime = time;
        mDays = days;
        mRingtone = ringtone;
        mEnabled = enabled;
        mRecurring = recurring;
    }

    public String getAlarmDescription() {
        return mAlarmDescription;
    }

    public void setAlarmDescription(String alarmDescription) {
        mAlarmDescription = alarmDescription;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public long getTimeLong() {
        return mTimeLong;
    }

    public void setTimeLong(long timeLong) {
        mTimeLong = timeLong;
    }

    public String getDays() {
        return mDays;
    }

    public void setDays(String days) {
        mDays = days;
    }

    public String getRingtone() {
        return mRingtone;
    }

    public void setRingtone(String ringtone) {
        mRingtone = ringtone;
    }

    public boolean isEnabled() {
        return mEnabled;
    }

    public void setEnabled(boolean enabled) {
        mEnabled = enabled;
    }

    public boolean isRecurring() {
        return mRecurring;
    }

    public void setRecurring(boolean recurring) {
        mRecurring = recurring;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "mAlarmDescription='" + mAlarmDescription + '\'' +
                ", mTime='" + mTime + '\'' +
                ", mTimeLong=" + mTimeLong +
                ", mDays='" + mDays + '\'' +
                ", mRingtone='" + mRingtone + '\'' +
                ", mEnabled=" + mEnabled +
                ", mRecurring=" + mRecurring +
                '}';
    }
}
