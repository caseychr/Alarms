package com.practice.mcasey.myapplication.CountdownTimer;

public interface CountdownView {

    void onClickCancel();

    void onClickStart();

    void onClickPause();

    void onClickResume();

    void updateCountdown();

    void updateEditTextTimer();
}
