package com.kostas.clunter;

/**
 * Created by Kostas on 2015-12-05.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class AdventureActivity extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;
    Fragment fragment ;
    String passedAdventureId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adventure);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertThing();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        passedAdventureId = getIntent().getExtras().getString("adventureInfoId");
        int sId = getResources().getIdentifier("Name" + passedAdventureId, "string", getPackageName());
        setTitle(getResources().getString(sId));
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                goBack();
                return true;
            case R.id.action_settings:
                AdventureActivityFragment.countDownTimer.onFinish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void goBack(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setMessage("Išėjus nuotykis baigsis!");

        alert.setPositiveButton("Išeiti", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                AdventureActivityFragment.countDownTimer.cancel();
                finish();
            }
        });

        alert.setNegativeButton("Likti", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
        alert.show();
    }

    public void insertThing(){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Įveskite atsakymą:");
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Įvesti", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                String str = input.getText().toString();
                String ans = AdventureActivityFragment.getAnswer();

                if (str.equals(ans)) {
                    AdventureActivityFragment.correctAnswer();
                }
            }
        });

        alert.setNegativeButton("Atgal", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
        alert.show();
    }

    public void setDone(){
        SharedPreferences prefs = getSharedPreferences("AdventureListData", 0);
        SharedPreferences.Editor editor = prefs.edit();
        String old = prefs.getString("toughness_" + passedAdventureId.charAt(3), null);

        if (old.length() == 1){
            editor.putString("toughness_" + passedAdventureId.charAt(3), old + 'd');
            editor.commit();
        }
    }
}

