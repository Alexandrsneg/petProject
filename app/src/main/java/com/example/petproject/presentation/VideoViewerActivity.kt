package com.example.petproject.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import com.example.petproject.R
import com.example.petproject.common.Layout
import com.example.petproject.moxymvp.activities.ABaseActivity
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import kotlinx.android.synthetic.main.video_viewer_activity.*


@Layout(value = R.layout.video_viewer_activity)
class VideoViewerActivity : ABaseActivity() {

    companion object {
        fun runActivity(context: Context?) {
            val intent = Intent(context, VideoViewerActivity::class.java)
            context?.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mediaController = MediaController(this)

        val bigVideo = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"
        val otherBigVideo = "https://ia800201.us.archive.org/22/items/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4"
        val smallVideo = "http://sites.google.com/site/ubiaccessmobile/sample_video.mp4"

//       vvScreen.apply {
//           setVideoURI(Uri.parse(otherBigVideo))
//           mediaController.setAnchorView(this)
//           setMediaController(mediaController)
//           requestFocus()
//           setOnPreparedListener {
//               pbProcessing.visibility = View.GONE
//               start()
//           }
//       }

        findViewById<PlayerView>(R.id.vvScreen).apply {

            player = SimpleExoPlayer.Builder(this@VideoViewerActivity).build().apply {
                setMediaItem(MediaItem.fromUri(bigVideo))
                this.addListener(object: Player.Listener {
                    override fun onRenderedFirstFrame() {
                        super.onRenderedFirstFrame()
                        pbProcessing.visibility = View.GONE
                    }
                })
                prepare()
                play()
            }
        }


    }
}