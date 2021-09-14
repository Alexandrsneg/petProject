package com.example.petproject.presentation

import android.content.ContentUris
import android.content.ContentValues
import android.graphics.Typeface
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.petproject.AActivityForResult
import com.example.petproject.R
import com.example.petproject.common.Layout
import com.example.petproject.utils.Utils
import com.example.petproject.utils.video.VideoCompressor
import com.video.trimmer.interfaces.OnTrimVideoListener
import com.video.trimmer.interfaces.OnVideoListener
import kotlinx.android.synthetic.main.video_trimer_activity.*
import java.io.File


@Layout(value = R.layout.video_trimer_activity)
class VideoTrimerActivity : AActivityForResult(), OnTrimVideoListener, OnVideoListener {

    private val videoCompressor = VideoCompressor(this)
    private var videoPath = ""
    private var destPath = File(Utils.getExternalStorageDirectorySD(), "ko_videos")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extraIntent = intent
        var path = ""
        if (extraIntent != null) path = extraIntent.getStringExtra("EXTRA_VIDEO_PATH").toString()
        destPath.mkdirs()

        videoTrimmer.setTextTimeSelectionTypeface(Typeface.DEFAULT_BOLD)
            .setOnTrimVideoListener(this)
            .setOnVideoListener(this)
            .setVideoURI(Uri.parse(path))
            .setVideoInformationVisibility(true)
            .setMaxDuration(10)
            .setMinDuration(2)
            .setDestinationPath(destPath.toString())
//            .setDestinationPath(
//                Environment.getExternalStorageDirectory()
//                    .toString() + File.separator + "temp" + File.separator + "Videos" + File.separator
//            )


        back.setOnClickListener {
            videoTrimmer.onCancelClicked()
        }

        save.setOnClickListener {
            videoTrimmer.onSaveClicked()
        }

    }


    override fun cancelAction() {
        videoTrimmer.destroy()
        finish()
    }

    override fun getResult(uri: Uri) {
        runOnUiThread {
            Toast.makeText(this, "Video saved at ${uri.path}", Toast.LENGTH_SHORT).show()
            videoPath = uri.path.toString()
            val mediaMetadataRetriever = MediaMetadataRetriever()
            mediaMetadataRetriever.setDataSource(this, uri)
            val width =
                mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)
                    ?.toLong()
            val height =
                mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)
                    ?.toLong()
            val values = ContentValues()
            values.put(MediaStore.Video.Media.DATA, uri.path)
            values.put(MediaStore.Video.VideoColumns.WIDTH, width)
            values.put(MediaStore.Video.VideoColumns.HEIGHT, height)
            val id =
                contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values)?.let {
                    ContentUris.parseId(it)
                }
            Log.e("VIDEO ID", id.toString())
        }

        compressVideo()
    }

    override fun onError(message: String) {
        toast("onError")
    }

    override fun onTrimStarted() {
        pbLoader.visibility = View.VISIBLE
    }


    override fun onVideoPrepared() {
        toast("onVideoPrepared")
    }

    private fun compressVideo() {
        videoCompressor.startCompressing(videoPath, destPath.absolutePath, object : VideoCompressor.CompressionListener {
            override fun compressionFinished(
                status: Int,
                isVideo: Boolean,
                fileOutputPath: String?
            ) {
                if (videoCompressor.isDone()) {
                    val outputFile = File(fileOutputPath)
                    val outputCompressVideosize = outputFile.length()
                    val fileSizeInKB = outputCompressVideosize / 1024
                    val fileSizeInMB = fileSizeInKB / 1024
                    val s = """
                        Output video path : $fileOutputPath
                        Output video size : ${fileSizeInMB}mb
                        """.trimIndent()
                    pbLoader.visibility = View.GONE
                    toast(s)
                    finish()
                }
            }

            override fun onFailure(message: String?) {
                toast(message!!)
                Log.d("FFMPEG", message!!)
            }

            override fun onProgress(progress: Int) {
            }

        })
    }
}