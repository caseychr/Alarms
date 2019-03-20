package com.practice.mcasey.myapplication.AlarmList;

import com.practice.mcasey.myapplication.Model.Alarm;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AlarmListPresenter {

    private AlarmListView mView;

    public AlarmListPresenter(AlarmListView view) {
        mView = view;
    }

    public void sortAlarms(List<Alarm> alarms){
        if(alarms != null)
            Collections.sort(alarms, mAlarmComparator);
    }

    public void deleteAlarm(List<Alarm> alarms, int position){
        if(alarms.size()==1){
            alarms.clear();
        }
        else{
            alarms.remove(position);
        }
    }

    Comparator<Alarm> mAlarmComparator = new Comparator<Alarm>() {
        @Override
        public int compare(Alarm alarm, Alarm t1) {
            return alarm.getTime().compareTo(t1.getTime());
        }
    };
}
