package com.practice.mcasey.myapplication.CountdownTimer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.practice.mcasey.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CountdownFragment extends Fragment {

    private static final int INTERVAL = 1000;

    @BindView(R.id.countdown_tv) TextView mTimerTV;
    @BindView(R.id.time_enter) EditText mTimerET;
    @BindView(R.id.cancel_timer) Button mCancel;
    @BindView(R.id.start_timer) Button mStart;
    CountDownTimer mTimer;
    int mTimeInt;
    int mMillisInFuture;
    View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_countdown_timer, container, false);
        ButterKnife.bind(this, mView);
        getInput();
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @OnClick(R.id.start_timer)
    public void onStartTimer(){
        setMillisInFuture(mTimerET.getText().toString());
        Log.i("FUTURE", String.valueOf(mMillisInFuture));
        mTimer = new CountDownTimer(mMillisInFuture, INTERVAL){

            @Override
            public void onTick(long l) {
                String l_seconds = String.valueOf(l%60000);
                String l_minutes = String.valueOf(l/60000);
                String l_hours = String.valueOf(l/3600000);
                Log.i("MINUTES", l_minutes);
                if(l_seconds.length()==5)
                    mTimerTV.setText(l_hours+" : "+l_minutes+" : "+l_seconds.substring(0,2));
                else if(l_seconds.length()==3)
                    mTimerTV.setText(l_hours+" : "+l_minutes+" : 00");
                else
                    mTimerTV.setText(l_hours+" : "+l_minutes+" : 0"+l_seconds.substring(0,1));
            }

            @Override
            public void onFinish() {
                mTimerTV.setText("DONE!!!");
                mStart.setText("Start");
            }
        };
        if(mStart.getText().equals("Start") || mStart.getText().equals("Resume")){
            mStart.setText("Pause");
            mTimer.start();
        }
        else if(mStart.getText().equals("Pause")){
            String pausedTimer = mTimerTV.getText().toString();
            mTimerTV.setText(pausedTimer);
            mStart.setText("Resume");
            mTimer.cancel();
            //TODO set timer to paused time
        }
    }

    @OnClick(R.id.cancel_timer)
    public void onCancelTimer(){
        mTimer.cancel();
        mStart.setText("Start");
    }

    private void getInput(){
        mTimerET.addTextChangedListener(new TextWatcher() {

            boolean backspace = false;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                backspace = i2 == 0;
            }

            @Override
            public void afterTextChanged(Editable s) {

                //TODO set protocols to not allow out of bounds things (i.e. 65 seconds etc)
                StringBuilder sb = new StringBuilder(s.toString());
                boolean updateView = false;
                if(sb.length() == 3 && !backspace){
                    if(sb.charAt(1) != ':'){
                        sb = sb.insert(1, ':');
                        updateView = true;
                    }
                } else if(sb.length() == 5 && !backspace){
                    if(sb.charAt(1) == ':'){
                        sb = sb.deleteCharAt(1);
                        sb = sb.insert(2, ':');
                        updateView = true;
                    }
                } else if(sb.length() == 6 && !backspace){
                    if(sb.charAt(2) == ':'){
                        sb = sb.deleteCharAt(2);
                        sb = sb.insert(1, ':');
                        sb = sb.insert(4, ':');
                        updateView = true;
                    }
                } else if(sb.length() == 7 && !backspace){
                    if(sb.charAt(1) != ':' && sb.charAt(0) != 'D'){
                        sb = sb.deleteCharAt(2).insert(2, ':');
                        sb = sb.deleteCharAt(5).insert(4, ':');
                        updateView = true;
                    }
                } else if(sb.length() == 8 && !backspace){
                    if(sb.charAt(1) == ':'){
                        sb = sb.deleteCharAt(1).insert(2, ':');
                        sb = sb.deleteCharAt(4).insert(5, ':');
                        updateView = true;
                        Log.i("STRING_SEC", sb.toString().substring(sb.toString().length()-2, sb.toString().length()));
                        Log.i("STRING_MIN", sb.toString().substring(sb.toString().length()-5, sb.toString().length()-3));
                        Log.i("STRING_HRS", sb.toString().substring(sb.toString().length()-8, sb.toString().length()-6));
                    }
                }
                if (updateView) {
                    mTimerET.setText(sb);
                    mTimerET.setSelection(mTimerET.getText().length());
                    StringBuilder sbString = sb;
                    for(int i=0;i<sbString.length();i++){
                        if(sbString.charAt(i) == ':')
                            sbString.deleteCharAt(i);
                    }
                    Log.i("STRING", sbString.toString());
                    mMillisInFuture = Integer.parseInt(sbString.toString());
                    Log.i("INTEGER", String.valueOf(mMillisInFuture));

                }
            }
        });
    }

    private void setMillisInFuture(String millisInFutureString){
        mMillisInFuture = 0;
        int seconds = 0;
        int minutes = 0;
        int hours = 0;
        if(millisInFutureString.length()==1)
            seconds = Integer.parseInt(millisInFutureString
                    .substring(millisInFutureString.length()-1, millisInFutureString.length()));
        else if(millisInFutureString.length()>=2)
            seconds = Integer.parseInt(millisInFutureString
                    .substring(millisInFutureString.length()-2, millisInFutureString.length()));
        if(millisInFutureString.length()==4)
            minutes = Integer.parseInt(millisInFutureString
                    .substring(millisInFutureString.length()-4, millisInFutureString.length()-3));
        else if(millisInFutureString.length()>=5)
            minutes = Integer.parseInt(millisInFutureString
                    .substring(millisInFutureString.length()-5, millisInFutureString.length()-3));
        if(millisInFutureString.length()==7)
            hours = Integer.parseInt(millisInFutureString
                    .substring(millisInFutureString.length()-7, millisInFutureString.length()-6));
        else if(millisInFutureString.length()==8)
            hours = Integer.parseInt(millisInFutureString
                    .substring(millisInFutureString.length()-8, millisInFutureString.length()-6));
        Log.i("INTS", "h "+hours+" m "+minutes+" s "+seconds);
        mMillisInFuture += hours * 60;
        mMillisInFuture += minutes * 60;
        mMillisInFuture += seconds;
        mMillisInFuture = mMillisInFuture * INTERVAL;
    }
}
