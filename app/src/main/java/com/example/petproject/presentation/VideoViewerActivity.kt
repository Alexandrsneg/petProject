package com.example.petproject.presentation

import android.app.Activity
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.MediaController
import android.widget.Toast
import com.example.petproject.AActivityForResult
import com.example.petproject.IOnActivityResultListener
import com.example.petproject.R
import com.example.petproject.common.Layout
import com.video.trimmer.interfaces.OnTrimVideoListener
import com.video.trimmer.interfaces.OnVideoListener
import com.video.trimmer.utils.FileUtils
import kotlinx.android.synthetic.main.video_viewer_activity.*
import java.io.File


@Layout(value = R.layout.video_viewer_activity)
class VideoViewerActivity : AActivityForResult()
     {

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

//        findViewById<PlayerView>(R.id.vvScreen).apply {
//
//            player = SimpleExoPlayer.Builder(this@VideoViewerActivity).build().apply {
//                setMediaItem(MediaItem.fromUri(bigVideo))
//                this.addListener(object: Player.Listener {
//                    override fun onRenderedFirstFrame() {
//                        super.onRenderedFirstFrame()
//                        pbProcessing.visibility = View.GONE
//                    }
//                })
//                prepare()
//                play()
//            }
//        }

             btnVideo.setOnClickListener {
                 getVideo()
             }
         }

         private fun startTrimActivity(uri: Uri) {
             val intent = Intent(this, VideoTrimerActivity::class.java)
             intent.putExtra("EXTRA_VIDEO_PATH", FileUtils.getPath(this, uri))
             startActivity(intent)
         }

         private fun getVideo() {

             val intent: Intent
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                 intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                 intent.addCategory(Intent.CATEGORY_OPENABLE)
             } else {
                 intent = Intent(Intent.ACTION_PICK)
             }
             intent.type = "video/*"; // Только видео

             runActivityForResult(intent, 111)

         }

         override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
             super.onActivityResult(requestCode, resultCode, data)

             if (requestCode == 111) {
                 showToast(requestCode.toString())
                 val selectedVideo = data?.data
//                 val path = getRealPathFromURI(selectedVideo!!, this)
                 selectedVideo?.let {
                     startTrimActivity(it)
                     showToast(requestCode.toString())
                 }
             }
         }



//         private val projection = arrayOf(MediaStore.Video.Media.DATA)
//
//         private fun getRealPathFromURI(contentURI: Uri, activity: Activity): String? {
//             val cursor = activity.contentResolver
//                 .query(contentURI, projection, null, null, null)
//                 ?: // Source is Dropbox or other similar local file path
//                 return contentURI.path
//             cursor.moveToFirst()
//             val idx = cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA)
//             return if (idx == -1) null else cursor.getString(idx)
//         }
}