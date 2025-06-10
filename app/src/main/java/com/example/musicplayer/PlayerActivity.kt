package com.example.musicplayer

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

class PlayerActivity:AppCompatActivity() {
    private lateinit var playPauseBtn: ImageView
    private lateinit var nextBtn: ImageView
    private lateinit var prevBtn: ImageView
    private lateinit var seekBar: SeekBar
    private lateinit var songTitle: TextView
    private lateinit var currentTimeTextView: TextView
    private lateinit var totalTimeTextView: TextView
    private lateinit var albumArt: ImageView
    private lateinit var lyricsTextView: TextView
    private lateinit var lyricsScrollView: ScrollView
    private lateinit var rootLayout: LinearLayout
// Change from ScrollView

    private var mediaPlayer: MediaPlayer? = null
    private var timer: Timer? = null
    private val handler = Handler(Looper.getMainLooper())
    private var scrollPosition = 0
    private var isScrolling = false



    private val songs = listOf(R.raw.ishqmein, R.raw.khariyat, R.raw.labonko,R.raw.baarish,R.raw.chaahat,R.raw.dopal,R.raw.humnava,R.raw.kabhi,R.raw.lo,R.raw.kahani,R.raw.dd,R.raw.orep,R.raw.bol,R.raw.tuhainto,R.raw.sawarlusong,R.raw.ishbulwasong)
    private val songNames = listOf("ishqmein", "khariyat", "laboko","Baarish","chaahat","Do pal","Humnava","kabhi na kabhi","Lo maan Liya","Kahani suno","Dagabaaz re","O Re piya","Bol na halke halke","Tu hain to main hu","sawaar loon","Ishq Bulawa")
    private val gradientBackgrounds = listOf(
        R.drawable.gradient2_bg,
        R.drawable.gradient3,
        R.drawable.gradient4,
        R.drawable.gradient5,
        R.drawable.gradient6,
        R.drawable.gradient7
        ,R.drawable.gradient_bg,
        R.drawable.gradient8,
        R.drawable.gradient9,
        R.drawable.gradient10,
        R.drawable.gradient11,
        R.drawable.ore_bg,
        R.drawable.bol_bg,
        R.drawable.tu_bg,
        R.drawable.sawar_bg,
        R.drawable.ish_bul_bg


    )
    private val albumArts = listOf(
       R.drawable.ishqmein,
         R.drawable.khariyat,
        R.drawable.laboko,
         R.drawable.baarish,
          R.drawable.chahat,
         R.drawable.dopal,
         R.drawable.humnava,
         R.drawable.kabhina,
          R.drawable.loman,
         R.drawable.kahani,

         R.drawable.dagabaazre,
        R.drawable.ore,
        R.drawable.bol,
        R.drawable.tuhain,
        R.drawable.sawaarlooimg,
        R.drawable.ishqbulawaimg
    )
    private val lyricsFiles = listOf(
        R.raw.ishqmein_txt, R.raw.khariyat_txt, R.raw.labonko_txt,
        R.raw.baarish_txt, R.raw.chaahat_txt, R.raw.dopal_txt,
        R.raw.humnava_txt, R.raw.kabhi_txt, R.raw.lo_txt,
        R.raw.kahani_txt, R.raw.dd_txt,R.raw.ore,R.raw.bolna,R.raw.tuhain_txt,
        R.raw.sawartxt,R.raw.ishbulawatxt
    )
    private var currentSongIndex = 0
    private val scrollHandler = Handler(Looper.getMainLooper())
    private val scrollRunnable = object : Runnable {
        override fun run() {
            if (isScrolling && mediaPlayer?.isPlaying == true) {
                scrollPosition += 2 // Adjust speed of scrolling
                lyricsScrollView.smoothScrollTo(0, scrollPosition)
                scrollHandler.postDelayed(this, 100)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        currentSongIndex = intent.getIntExtra("songIndex", 0)
        rootLayout = findViewById(R.id.rootLayout)
        playPauseBtn = findViewById(R.id.playPauseBtn)
        nextBtn = findViewById(R.id.nextBtn)
        prevBtn = findViewById(R.id.prevBtn)
        seekBar = findViewById(R.id.seekBar)
        songTitle = findViewById(R.id.songTitle)
        currentTimeTextView = findViewById(R.id.currentTimeTextView)
        totalTimeTextView = findViewById(R.id.totalTimeTextView)
        albumArt = findViewById(R.id.albumArt)
        lyricsTextView = findViewById(R.id.lyricsTextView)
        lyricsScrollView = findViewById(R.id.lyricsScrollView)

        loadSong()
        playPauseBtn.setOnClickListener {
            if (mediaPlayer?.isPlaying == true) pauseSong() else playSong()
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) mediaPlayer?.seekTo(progress)
                currentTimeTextView.text = formatTime(progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        nextBtn.setOnClickListener { changeSong(1) }
        prevBtn.setOnClickListener { changeSong(-1) }
    }

    private fun loadSong() {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, songs[currentSongIndex])

        songTitle.text = songNames[currentSongIndex]
        albumArt.setImageResource(albumArts[currentSongIndex])
        rootLayout.background = ContextCompat.getDrawable(this, gradientBackgrounds[currentSongIndex])

        seekBar.max = mediaPlayer?.duration ?: 0
        totalTimeTextView.text = formatTime(mediaPlayer?.duration ?: 0)
        currentTimeTextView.text = formatTime(0)

        loadLyrics(currentSongIndex)
        mediaPlayer?.setOnCompletionListener { changeSong(1) }
    }

    private fun loadLyrics(index: Int) {
        try {
            val inputStream = resources.openRawResource(lyricsFiles[index])
            val reader = BufferedReader(InputStreamReader(inputStream))
            val lyrics = reader.readText()
            reader.close()
            lyricsTextView.text = lyrics
            lyricsScrollView.post { lyricsScrollView.scrollTo(0, 0) }
            startLyricsScrolling()
        } catch (e: Exception) {
            lyricsTextView.text = "Lyrics not available"
        }
    }

    private fun playSong() {
        mediaPlayer?.start()
        playPauseBtn.setImageResource(R.drawable.ic_pause)
        startSeekBarUpdate()
        startLyricsScrolling()
    }

    private fun pauseSong() {
        mediaPlayer?.pause()
        playPauseBtn.setImageResource(R.drawable.ic_play)
        stopLyricsScrolling()
    }

    private fun changeSong(direction: Int) {
        stopLyricsScrolling()
        currentSongIndex = (currentSongIndex + direction + songs.size) % songs.size
        loadSong()
        playSong()
    }
    private fun startSeekBarUpdate() {
        timer?.cancel()
        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    val currentPos = mediaPlayer?.currentPosition ?: 0
                    seekBar.progress = currentPos
                    currentTimeTextView.text = formatTime(currentPos)
                }
            }
        }, 0, 1000)
    }

    private fun startLyricsScrolling() {
        val mediaPlayer = mediaPlayer ?: return
        val songDuration = mediaPlayer.duration // Total song duration in milliseconds
        val totalScrollHeight = lyricsTextView.height // Total height of lyrics text

        if (totalScrollHeight > 0) {
            isScrolling = true
            scrollHandler.post(object : Runnable {
                override fun run() {
                    // Check if mediaPlayer is still valid before accessing it
                    if (!isScrolling || mediaPlayer == null || !mediaPlayer!!.isPlaying) {
                        return  // Stop scrolling if song changed or stopped
                    }

                    val currentTime = mediaPlayer!!.currentPosition
                    val progressFraction = currentTime.toFloat() / songDuration
                    val scrollY = (totalScrollHeight * progressFraction).toInt()

                    lyricsScrollView.scrollTo(0, scrollY)
                    scrollHandler.postDelayed(this, 500) // Update every 500ms
                }
            })
        }
    }
    private fun stopLyricsScrolling() {
        isScrolling = false
        scrollHandler.removeCallbacksAndMessages(null)
    }

    private fun formatTime(milliseconds: Int) =
        String.format("%02d:%02d", (milliseconds / 1000) / 60, (milliseconds / 1000) % 60)

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        timer?.cancel()
        stopLyricsScrolling()
    }
}

