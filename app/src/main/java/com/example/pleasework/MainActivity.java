package com.example.pleasework;

import android.Manifest;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView; //Recycler View Stuff
    songAdapter adapter;
    private List<Song> songList = new ArrayList<>(); //Library
    private List<Song> songListSearch = new ArrayList<>(); //Library


    public ArrayList<Artist> art = new ArrayList<>();
    boolean found;

    MainPlayer Player;  //Media Player Class
    SeekBar seekBar;

    ImageButton play, pause, play_main, pause_main, playNext, playPrev, searchBtn; //UI stuff
    TextView songs_title, songs_artist_name, startTime, endTime;
    EditText searchText;

    SlidingUpPanelLayout mLayout;

    boolean Searching = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] PERMISSIONS = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        };
        requestPermissions(PERMISSIONS, 1);

        Initialize();

        //On Click Listiners For all of the Buttons
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Player.playResumeSong(Player.getCurrentIndex());
                updateUI();
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Player.pauseSong();
                updateUI();
            }
        });
        play_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Player.playResumeSong(Player.getCurrentIndex());
                updateUI();
            }
        });
        pause_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Player.pauseSong();
                updateUI();
            }
        });
        playNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Player.playNextSong();
                updateUI();
            }
        });
        playPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Player.playPrevSong();
                updateUI();
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchText.getVisibility() == View.INVISIBLE) {
                    searchText.setVisibility(View.VISIBLE);
                    Searching = true;
                } else {
                    searchText.setVisibility(View.INVISIBLE);
                    Searching = false;
                    adapter.setSongList(songList);
                    Player.setSongList(songList);
                    adapter.notifyDataSetChanged();
                    searchText.setText("");
                }
            }
        });

        searchText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    songListSearch.clear();
                    for (Song s : songList)
                        if (s.getName().toLowerCase().contains(searchText.getText().toString().toLowerCase()))
                            songListSearch.add(s);

                    adapter.setSongList(songListSearch);
                    Player.setSongList(songListSearch);
                    adapter.notifyDataSetChanged();

                    return true;
                }
                return false;
            }
        });

        //Seekbar in Sliding UI
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Player.seek(seekBar.getProgress());
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mLayout != null &&
                (mLayout.getPanelState() == PanelState.EXPANDED || mLayout.getPanelState() == PanelState.ANCHORED)) {
            mLayout.setPanelState(PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }

    public void Initialize() {
        play = findViewById(R.id.play_button);  //Sliding Panel UI BUTTONS
        pause = findViewById(R.id.pause_button);
        play_main = findViewById(R.id.play_button_main);
        pause_main = findViewById(R.id.pause_button_main);
        playPrev = findViewById(R.id.PlayPrev);
        playNext = findViewById(R.id.PlayNext);

        seekBar = findViewById(R.id.seekBar);

        endTime = findViewById(R.id.endTime);
        startTime = findViewById(R.id.StartTime);

        searchBtn = findViewById(R.id.imageButtonSearch);
        searchText = findViewById(R.id.searchText);

        mLayout = findViewById(R.id.activity_main);

        songs_title = findViewById(R.id.songs_title);   //Sliding Panel UI TEXT
        songs_artist_name = findViewById(R.id.songs_artist_name);
        //the URI maybe put here for icons

        recyclerView = findViewById(R.id.SongRView);    //View for songs
        adapter = new songAdapter(songList);

        getMusic(); //Build Song Library
        Player = new MainPlayer(songList, seekBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext())); //Stuff for the recycler view adapter
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        seekBarThread.start();

        recyclerView.addOnItemTouchListener(new songRecyclerClickListener(getApplicationContext(), recyclerView, new songRecyclerClickListener.ClickListener() {//Play Clicked On Song
            @Override
            public void onClick(View view, int position) {
                Player.playResumeSong(position);
                updateUI();
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
            int songDuration = songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            //int x = songCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
            //String thisArt = songCursor.getString(x);

            do {
                Artist tempartist = new Artist();
                Song temp = new Song();
                temp.setName(songCursor.getString(songTitle));
                temp.setArtist(songCursor.getString(songArtist));//ngyb el art f kol el song

                if (art == null) {
                    tempartist.Name = songCursor.getString(songArtist);
                    art.add(tempartist);
                }
                if (art != null)  //  hazem bos 3ala el 5ara dah w 2oly lw htshta8al :*
                {
                    if (art.contains(songCursor.getString(songArtist))) {
                        found = true;
                    }
                    if (found != true) {
                        tempartist.Name = songCursor.getString(songArtist);
                        art.add(tempartist);
                    }
                }

                temp.setLocation(songCursor.getString(songLocation));
                temp.setDuration(songCursor.getLong(songDuration));
                //Trials to get the image to work....
                //temp.setAlbumArtLocation(songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)));
                //temp.setBitmap(BitmapFactory.decodeFile(thisArt));
                //temp.setAlbumArtLocation(songCursor.getString(songIcon));

                songList.add(temp);
            } while (songCursor.moveToNext());
        }
        adapter.notifyDataSetChanged();
        songCursor.close();
    }

    public void updateUI() {
        updateUIText();
        updateUIButtons();
    }

    public void updateUIText() {
        Song song;
        if (Searching)
            song = songListSearch.get(Player.getCurrentIndex());
        else
            song = songList.get(Player.getCurrentIndex());


        songs_title.setText(song.getName());
        songs_artist_name.setText(song.getArtist());

        long seconds = TimeUnit.MILLISECONDS.toSeconds(song.getDuration());
        long mins = TimeUnit.MILLISECONDS.toMinutes(song.getDuration());
        for (int i = 0; i < mins; i++)
            seconds -= 60;

        if (seconds < 10)
            endTime.setText(mins + ":0" + seconds);
        else
            endTime.setText(mins + ":" + seconds);
    }

    public void updateUIButtons() {

        if (Player.isPlaying()) {
            play.setVisibility(View.GONE);
            pause.setVisibility(View.VISIBLE);
            if (play_main.getVisibility() == View.VISIBLE) {
                play_main.setVisibility(View.GONE);
                pause_main.setVisibility(View.VISIBLE);
            }

        } else {

            pause_main.setVisibility(View.GONE);
            play_main.setVisibility(View.VISIBLE);
            if (pause.getVisibility() == View.VISIBLE) {
                pause.setVisibility(View.GONE);
                play.setVisibility(View.VISIBLE);
            }
        }
    }

    Thread seekBarThread = new Thread() {
        @Override
        public void run() {
            try {
                while (!seekBarThread.isInterrupted()) {
                    Thread.sleep(10);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {//UI of Current Duration
                            long seconds = TimeUnit.MILLISECONDS.toSeconds(Player.getDuration());
                            long mins = TimeUnit.MILLISECONDS.toMinutes(Player.getDuration());
                            for (int i = 0; i < mins; i++)
                                seconds -= 60;

                            if (seconds < 10)
                                startTime.setText(mins + ":0" + seconds);
                            else
                                startTime.setText(mins + ":" + seconds);

                            //Animate SeekBar
                            seekBar.setProgress(Player.getDuration());

                            //Play Next Song When Done
                            if (Player.getDuration() >= Player.getMaxDuration()) {
                                Player.playNextSong();
                                updateUI();
                            }
                        }
                    });
                }
            } catch (InterruptedException e) {
            }
        }
    };
}