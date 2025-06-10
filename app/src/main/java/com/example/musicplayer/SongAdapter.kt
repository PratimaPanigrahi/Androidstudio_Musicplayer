package com.example.musicplayer

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SongAdapter (private val context: Context, private val songList: List<song>) : BaseAdapter() {

    override fun getCount(): Int = songList.size

    override fun getItem(position: Int): Any = songList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.song_item, parent, false)

        val songTitle: TextView = view.findViewById(R.id.songTitle)
        val artistName: TextView = view.findViewById(R.id.artistName)

        val song = songList[position]
        songTitle.text = song.title
        artistName.text = song.artist

        return view
    }
}