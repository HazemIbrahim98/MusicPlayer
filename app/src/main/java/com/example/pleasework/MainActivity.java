package com.example.pleasework;

import android.Manifest;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.TextView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView; //Recycler View Stuff
    songAdapter adapter;

    TextView TextViewSongName; //Text at the bottom

    private List<Song> songList = new ArrayList<>(); //Library

    ImageButton PlayPauseSong; //Next - Prev - Play buttons
    ImageButton PrevButton;
    ImageButton NextButton;
    int index;

    //if i understand correctly this need to be a new service so that
    // it plays when app is minimized or in the notification bar
    MediaPlayer player = new MediaPlayer(); //Main Music Player

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


        Initialize();

        //Play Next Song Listener
        NextButton = findViewById(R.id.nextSong);
        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNextSong();
            }
        });

        //Play Prev Song Listener
        PrevButton = findViewById(R.id.prevSong);
        PrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPrevSong();
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
    }

    public void Initialize() {

        TextViewSongName = findViewById(R.id.TextViewSongName); //For the bottom song title

        recyclerView = findViewById(R.id.myView);
        adapter = new songAdapter(songList);

        getMusic();//Build Song Library

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext())); //Stuff for the recycler view adapter
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new songRecyclerClickListener(getApplicationContext(), recyclerView, new songRecyclerClickListener.ClickListener() {//Play Clicked On Song
            @Override
            public void onClick(View view, int position) {
                playSong(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    public void playSong(int position) {
        index = position;
        Song song = songList.get(position);
        TextViewSongName.setText(song.getName() + "\n" + song.getArtist());

        player.stop();
        player = new MediaPlayer();
        try {
            player.setDataSource(song.getLocation());
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.start();
    }

    public void playNextSong() {

        player.stop();
        if (++index == songList.size())
            index = 0;
        Song song = songList.get(index);
        TextViewSongName.setText(song.getName() + "\n" + song.getArtist());

        player = new MediaPlayer();
        try {
            player.setDataSource(song.getLocation());
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.start();
    }

    public void playPrevSong() {
        player.stop();
        if (--index == -1)
            index = songList.size() - 1;
        Song song = songList.get(index);
        TextViewSongName.setText(song.getName() + "\n" + song.getArtist());

        player = new MediaPlayer();
        try {
            player.setDataSource(song.getLocation());
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.start();

    }

    public void getMusic() {
        ContentResolver contentResolver = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, null, null, null, null);

        if (songCursor != null && songCursor.moveToFirst()) {
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songLocation = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            //int x = songCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
            //String thisArt = songCursor.getString(x);

            do {
                Song temp = new Song();
                temp.setName(songCursor.getString(songTitle));
                temp.setArtist(songCursor.getString(songArtist));
                temp.setLocation(songCursor.getString(songLocation));

                //Trials to get the image to work....
                //temp.setAlbumArtLocation(songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)));
                //temp.setBitmap(BitmapFactory.decodeFile(thisArt));
                //temp.setAlbumArtLocation(songCursor.getString(songIcon));

                songList.add(temp);
            } while (songCursor.moveToNext());
        }
        adapter.notifyDataSetChanged();
    }
}