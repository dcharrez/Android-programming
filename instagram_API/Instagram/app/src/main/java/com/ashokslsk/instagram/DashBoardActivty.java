package com.ashokslsk.instagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class DashBoardActivty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_activty);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new GraphFragment()).commit();
        }
    }

}
