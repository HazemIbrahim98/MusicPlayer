package com.example.pleasework;

import android.Manifest;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public Song song;
    int Duration;
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


        //if i understand correctly this need to be a new service so that
        // it plays when app is minimized or in the notification bar
        ImageButton playPause = findViewById(R.id.playPauseButton);
        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Song song = new Song();
                song.setLoc(Environment.getExternalStorageDirectory() + "/test.mp3");

                if (!player.isPlaying()) {
                    player = new MediaPlayer();
                    try {
                        player.setDataSource(song.getLoc());
                        player.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    player.seekTo(Duration);
                    player.start();
                } else {
                    player.stop();
                    Duration = player.getCurrentPosition();
                }
            }
        });
    }
}