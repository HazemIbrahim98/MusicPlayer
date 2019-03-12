package com.example.pleasework;

import android.Manifest;
import android.content.ContentResolver;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> arrayList;
    ListView listView;
    ArrayAdapter<String> adapter;

    public Song song;
    public boolean firstTime = true;
    MediaPlayer player = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //a better way to check for permissions
        //you need to check if you have the permission or not first then ask for them.
        //put the string in resources?
        String[] PERMISSIONS = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        };

        requestPermissions(PERMISSIONS, 1);
        doStuff();
/*
        //if i understand correctly this need to be a new service so that
        // it plays when app is minimized or in the notification bar
        ImageButton playPause = findViewById(R.id.playPauseButton);
        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Song song = new Song();
                song.setLoc(Environment.getExternalStorageDirectory() + "/test.mp3");


                if (firstTime) {
                    firstTime = false;
                    player = new MediaPlayer();
                    try {
                        player.setDataSource(song.getLoc());
                        player.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    player.seekTo(Duration);
                    player.start();
                } else if (player.isPlaying())
                    player.pause();
                else player.start();
            }
        });*/
    }

    public  void doStuff() {
        listView = findViewById(R.id.listView);
        arrayList = new ArrayList<>();
        getMusic();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);

        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //here code to play songs.


            }
        });

    }
    public void getMusic() {
        ContentResolver contentResolver = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, null, null, null, null);

        if (songCursor != null && songCursor.moveToFirst()) {
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            do {
                String currentTitle = songCursor.getString(songTitle);
                String currentArtist = songCursor.getString(songArtist);
                arrayList.add(currentTitle + "\n" + currentArtist);
            } while (songCursor.moveToNext());
        }
    }

}