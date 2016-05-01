package com.example.group87.androidchess87;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class playBackChessGameActivity extends AppCompatActivity {
    private String gameName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_back_chess_game);
        Bundle bundle = getIntent().getExtras();
        gameName = bundle.getString(PlayBackActivity.game_name);
        System.out.println("THIS IS SELECTED TEXT");
        TextView gameNameView = (TextView)findViewById(R.id.textView2);
        gameNameView.setText(gameName);
    }

    





}
