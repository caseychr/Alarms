package com.practice.mcasey.myapplication.CreateAlarm;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.practice.mcasey.myapplication.R;

import java.util.List;

public class RingtoneRecyclerAdapter extends RecyclerView.Adapter<RingtoneRecyclerAdapter.ViewHolder> {

    public interface OnItemClickListener{
        void onClick();
    }

    List<String> mRingtones;
    String mRingtoneString;
    private RingtoneRecyclerAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(RingtoneRecyclerAdapter.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public RingtoneRecyclerAdapter(List<String> ringtones){mRingtones = ringtones;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_ringtone, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        String ringtone = mRingtones.get(i);
        viewHolder.mRingtone.setText(ringtone);
        viewHolder.mRingtone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRingtoneString = viewHolder.mRingtone.getText().toString();
                mOnItemClickListener.onClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRingtones.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mRingtone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mRingtone = itemView.findViewById(R.id.ringtone_tv);
        }
    }
}
