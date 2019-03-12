package com.example.pleasework;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class songAdapter extends RecyclerView.Adapter<songAdapter.MyViewHolder> {

    private List<Song> songlist;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, artist;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            artist = view.findViewById(R.id.artist);
        }
    }

    public songAdapter(List<Song> songlist) {
        this.songlist = songlist;
    }

    @NonNull
    @Override
    public songAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.song_adapter, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull songAdapter.MyViewHolder myViewHolder, int i) {
        Song song = songlist.get(i);
        myViewHolder.title.setText(song.getName());
        myViewHolder.artist.setText(song.getArtist());
    }

    @Override
    public int getItemCount() {
        return songlist.size();
    }
}