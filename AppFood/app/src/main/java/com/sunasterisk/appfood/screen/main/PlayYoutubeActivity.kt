package com.sunasterisk.appfood.screen.main
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.sunasterisk.appfood.R

class PlayYoutubeActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    private var youTubePlayerView: YouTubePlayerView? = null
    private var mKey: String = "abc"
    private var apiKey = "AIzaSyDQeq6jVACdQWDja7vesIr7Lk0yg8p-xHs"
    private var requestVideo = 123
    private var key : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_youtube)
        val intent = intent
        key = intent.getStringExtra("you")

        youTubePlayerView = findViewById(R.id.myYouTube)
        youTubePlayerView!!.initialize(apiKey, this)
    }

    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?,
        p2: Boolean
    ) {
        this.cutKey(key!!)
        p1!!.cueVideo(mKey)
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        youTubeInitializationResult: YouTubeInitializationResult?
    ) {
        if(youTubeInitializationResult!!.isUserRecoverableError){
            youTubeInitializationResult.getErrorDialog(this, requestVideo)
        } else {
            Toast.makeText(this, TAG, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode== requestVideo) {
            youTubePlayerView!!.initialize(apiKey, this)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun cutKey(x : String) {
        val y = x.indexOf("=")
        mKey = x.substring(y + 1)
    }

    companion object {
        const val TAG = "AAA"
    }
}
