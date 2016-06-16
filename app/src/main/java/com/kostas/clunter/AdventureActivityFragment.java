package com.kostas.clunter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class AdventureActivityFragment extends Fragment {

    static ArrayList<String> rowItems;
    static String[] clues;
    static String[] answers;
    static int clue_num = 0;
    static View rootView;
    static CluesAdapter adapter;
    static MyCountDownTimer countDownTimer;
    static int startTime = 300000;
    static TextView part;
    static TextView time;
    static int part_num = 0;
    static int part_num_all = 0;
    static ListView myListView;
    static int  time_all = 0;
    static long t;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rowItems = new ArrayList<>();
        rootView = inflater.inflate(R.layout.fragment_adventure, container, false);
        part = (TextView) rootView.findViewById(R.id.part_num);
        time = (TextView) rootView.findViewById(R.id.clue_time);

        clue_num = 0;
        time_all = 0;

        String passedAdventureId = getActivity().getIntent().getExtras().getString("adventureInfoId");

        int arrayId = getActivity().getResources().getIdentifier("Clues" + passedAdventureId, "array", getActivity().getPackageName());
        clues = getResources().getStringArray(arrayId);

        arrayId = getActivity().getResources().getIdentifier("Answers" + passedAdventureId, "array", getActivity().getPackageName());
        answers = getResources().getStringArray(arrayId);

        rowItems.add(clues[0]);

        countDownTimer = new MyCountDownTimer(startTime, 1000);
        countDownTimer.start();


        // gauti part_num_all
        part.setText(Integer.toString(part_num+1) + " / " + Integer.toString(clues.length/3));

        myListView = (ListView) rootView.findViewById(R.id.clue_listView);
        adapter = new CluesAdapter(getContext(), rowItems);
        myListView.setAdapter(adapter);

        return rootView;
    }

    public static String getAnswer(){
        //part.setText(str + ' ' + answers[part_num-1]);
        return answers[clue_num/3];
    }

    public static void correctAnswer(){
        clue_num = clue_num + ( 2 - clue_num % 3);
        countDownTimer.onFinish();
    }

    /*public void skipTime(){

        countDownTimer = new MyCountDownTimer(startTime, 1000);
        countDownTimer.start();

    }*/


    public class MyCountDownTimer extends CountDownTimer
    {

        public MyCountDownTimer(long startTime, long interval)
        {
            super(startTime, interval);
        }


        @Override
        public void onTick(long millisUntilFinished) {

            time_all ++;
            t = millisUntilFinished / 1000;
            time.setText("Kita užuomina po " + String.valueOf(t/60) + " min " + String.valueOf(t % 60) + " s");
        }

        @Override
        public void onFinish() {

            clue_num += 1;

            //time_all = time_all + countDownTimer.millisUntilFinished;

            if (clue_num % 3 == 0){
                if(clue_num == clues.length){
                    winWindow();
                    clue_num -= 1;
                }
                rowItems.clear();
            }
            if (clue_num < clues.length) { // Išimt vėliau
                rowItems.add(clues[clue_num]);
            }

            part.setText(Integer.toString(clue_num/3+1) + " / " + Integer.toString(clues.length/3));

            adapter = new CluesAdapter(getContext(), rowItems);
            myListView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            countDownTimer.start();
        }
    }

    public void winWindow(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setTitle("Žaidimas baigtas");
        alert.setMessage("Įveikta per " + String.valueOf(time_all/(60*4)) + " min " + String.valueOf((int)time_all % 60) + " s");



        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                ((AdventureActivity)getActivity()).setDone();
                countDownTimer.cancel();
                getActivity().finish();

            }
        });



        alert.show();
    }
}
