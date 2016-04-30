package com.example.group87.androidchess87;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    }



    public ArrayList<String> populateList(){
        File f = getFilesDir();
        System.out.println("this is dir: " + f);

        File file[] = f.listFiles();

        ArrayList<String> files = new ArrayList<String>();
        for(int i = 0; i < file.length; i++){
            System.out.println("this is file: " + file[i]);
            files.add(file[i].getName());
        }
        Collections.sort(files);
        for(String i: files){
            System.out.println(i);
        }
        return files;
    }



}
