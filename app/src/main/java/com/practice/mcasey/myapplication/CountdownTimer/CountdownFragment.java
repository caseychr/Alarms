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
    int mMillisInFuture = 0;
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
        mTimer = new CountDownTimer(mMillisInFuture, INTERVAL){

            @Override
            public void onTick(long l) {
                String l_seconds = String.valueOf(l%60000);
                String l_minutes = String.valueOf(l/60000);
                String l_hours = String.valueOf(l/3600000);
                Log.i("MINUTES", l_minutes);
                if(l_seconds.length()==5)
                    mTimerET.setText(l_hours+" : "+l_minutes+" : "+l_seconds.substring(0,2));
                else if(l_seconds.length()==3)
                    mTimerET.setText(l_hours+" : "+l_minutes+" : 00");
                else
                    mTimerET.setText(l_hours+" : "+l_minutes+" : 0"+l_seconds.substring(0,1));
            }

            @Override
            public void onFinish() {
                mTimerET.setText("DONE!!!");
            }
        };
        if(mStart.getText().equals("Start") || mStart.getText().equals("Resume")){
            mStart.setText("Pause");
            mTimer.start();
        }
        else if(mStart.getText().equals("Pause")){
            String pausedTimer = mTimerET.getText().toString();
            mTimerET.setText(pausedTimer);
            mStart.setText("Resume");
            mTimer.cancel();
            //TODO set timer to paused time
        }
    }

    @OnClick(R.id.cancel_timer)
    public void onCancelTimer(){
        mTimer.cancel();
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
                StringBuilder sb = new StringBuilder(s.toString());
                boolean updateView = false;
                Log.i("SB", String.valueOf(sb.length()));
                if(sb.length() == 3 && !backspace){
                    if(sb.charAt(1) != ':'){
                        sb = sb.insert(1, ':');
                        Log.i("SB1", String.valueOf(sb.length()));
                        updateView = true;
                    }
                } else if(sb.length() == 5 && !backspace){
                    if(sb.charAt(1) == ':'){
                        sb = sb.deleteCharAt(1);
                        sb = sb.insert(2, ':');
                        Log.i("SB2", String.valueOf(sb.length()));
                        updateView = true;
                    }
                } else if(sb.length() == 6 && !backspace){
                    if(sb.charAt(2) == ':'){
                        sb = sb.deleteCharAt(2);
                        sb = sb.insert(1, ':');
                        sb = sb.insert(4, ':');
                        Log.i("SB3", String.valueOf(sb.length()));
                        updateView = true;
                    }
                } else if(sb.length() == 7 && !backspace){
                    if(sb.charAt(1) != ':' && sb.charAt(0) != 'D'){
                        sb = sb.deleteCharAt(2).insert(2, ':');
                        sb = sb.deleteCharAt(5).insert(4, ':');
                        Log.i("SB4", String.valueOf(sb.length()));
                        updateView = true;
                    }
                } else if(sb.length() == 8 && !backspace){
                    if(sb.charAt(1) == ':'){
                        sb = sb.deleteCharAt(1).insert(2, ':');
                        sb = sb.deleteCharAt(4).insert(5, ':');
                        Log.i("SB4", String.valueOf(sb.length()));
                        updateView = true;
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
                    mMillisInFuture = Integer.parseInt(sbString.toString()) * INTERVAL;
                }
            }
        });
    }
}
