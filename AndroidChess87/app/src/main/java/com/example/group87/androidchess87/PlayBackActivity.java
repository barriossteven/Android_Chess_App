package com.example.group87.androidchess87;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayBackActivity extends AppCompatActivity {
    /*
        Activity to show the list of games
     */

    private ArrayList<String> filesList;
    private ListView listView;
    public static final String game_name = "game_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_back);
        filesList = populateList();
        listView = (ListView)findViewById(R.id.gameList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                        R.layout.name_layout,
                        filesList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView,
                                            View view,
                                            int i,
                                            long l) {
                        playGame(i);
                    }
                });


    }



    public ArrayList<String> populateList(){
        File f = getFilesDir();
        System.out.println("this is dir: " + f);

        File file[] = f.listFiles();

        ArrayList<String> files = new ArrayList<String>();
        for(int i = 0; i < file.length; i++){
            System.out.println("this is file: " + file[i]);
            if(file[i].getName().equals("tempplacement.txt") || file[i].getName().equals("tempdonotshow.txt") || file[i].getName().equals("instant-run") )
            {

            }
            else {
                files.add(file[i].getName());
            }
        }
        Collections.sort(files);
        for(String i: files){
            System.out.println(i);
        }
        return files;
    }


    private void playGame(int pos) {
        Bundle bundle = new Bundle();
        bundle.putString(game_name, filesList.get(pos));
        Intent intent = new Intent(this, playBackChessGameActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }



}
