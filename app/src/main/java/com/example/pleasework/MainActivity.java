package com.example.pleasework;

import android.Manifest;
import android.content.ContentResolver;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView TextViewSongName;
    songAdapter adapter;
    ImageButton PlayPauseSong;
    RelativeLayout rel;
    private List<Song> songList = new ArrayList<>();
    public int pos;
    View BottomView;
    MediaPlayer player = new MediaPlayer();
    ImageButton PrevButton;
    ImageButton NextButton;
    int index;

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


        TextViewSongName = findViewById(R.id.TextViewSongName);
        PrevButton = findViewById(R.id.prevSong);
        PrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
                if (--index == -1)
                    index = songList.size()-1;
                Song song = songList.get(index);
                TextViewSongName.setText(song.getName() + "\n" + song.getArtist());

                player = new MediaPlayer();
                try {
                    player.setDataSource(song.getLoc());
                    player.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                player.start();
            }
        });
        NextButton = findViewById(R.id.nextSong);
        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
                if (++index == songList.size())
                    index = 0;
                Song song = songList.get(index);
                TextViewSongName.setText(song.getName() + "\n" + song.getArtist());

                player = new MediaPlayer();
                try {
                    player.setDataSource(song.getLoc());
                    player.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                player.start();
            }
        });

        //Play Pause Button
        PlayPauseSong = findViewById(R.id.playPauseSong);
        PlayPauseSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player.isPlaying())
                    player.pause();
                else
                    player.start();
            }
        });


        //if i understand correctly this need to be a new service so that
        // it plays when app is minimized or in the notification bar
        doStuff();
    }

    public void doStuff() {
        recyclerView = findViewById(R.id.myView);
        adapter = new songAdapter(songList);
        getMusic();

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new songRecyclerClickListener(getApplicationContext(), recyclerView, new songRecyclerClickListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                index = position;
                Song song = songList.get(position);
                TextViewSongName.setText(song.getName() + "\n" + song.getArtist());

                player.stop();
                player = new MediaPlayer();
                try {
                    player.setDataSource(song.getLoc());
                    player.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                player.start();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    public void getMusic() {
        ContentResolver contentResolver = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, null, null, null, null);

        if (songCursor != null && songCursor.moveToFirst()) {
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songLocation = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int songIcon = songCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
            do {
                Song temp = new Song();
                temp.setName(songCursor.getString(songTitle));
                temp.setArtist(songCursor.getString(songArtist));
                temp.setLoc(songCursor.getString(songLocation));
                //temp.setAlbumArt(songCursor.getString(songIcon));
                songList.add(temp);
            } while (songCursor.moveToNext());
        }
        adapter.notifyDataSetChanged();
    }
}