package com.example.musicplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var songListView: ListView
    private lateinit var songAdapter: SongAdapter
    private val songList = mutableListOf<song>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        songListView = findViewById(R.id.songListView)

        // Add songs with artist names
        songList.add(song("Ishq Me", "Arijit Singh"))
        songList.add(song("Khairiyat", "Arijit Singh"))
        songList.add(song("Labon Ko", "KK"))
        songList.add(song("Baarish", "Ash King"))
        songList.add(song("Chahat", "Rahat Fateh Ali Khan"))
        songList.add(song("Do Pal", "Lata Mangeshkar"))
        songList.add(song("Humnava", "Papon"))
        songList.add(song("Kabhi Na Kabhi", "Lucky Ali"))
        songList.add(song("Lo Maan Liya", "Arijit Singh"))
        songList.add(song("Kahani Suno", "Kaifi Khalil"))
        songList.add(song("Dagabaaz Re", "Rahat Fateh Ali Khan"))
        songList.add(song("O Re Piya", "Rahat Fateh Ali Khan"))
        songList.add(song("Bol Na Halke Halke", "Rahat Fateh Ali Khan And Mahalakshmi Iyer"))
        songList.add(song("Tu hain to main hu", "Arijit singh, Tanishk Bagchi"))
        songList.add(song("sawaar loon", "Monali Thakur Amit trivedi"))
        songList.add(song("Ishq Bulawaa", "Vishal & shekhar"))
        songAdapter = SongAdapter(this, songList)
        songListView.adapter = songAdapter


        // Handle click events
        songListView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, PlayerActivity::class.java)
            intent.putExtra("songIndex", position)
            startActivity(intent)
        }
    }
}
















