package com.example.petproject.presentation

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.example.petproject.AActivityForResult
import com.example.petproject.R
import com.example.petproject.common.Layout
import com.video.trimmer.interfaces.OnTrimVideoListener
import com.video.trimmer.interfaces.OnVideoListener
import kotlinx.android.synthetic.main.video_trimer_activity.*
import java.io.File


@Layout(value = R.layout.video_trimer_activity)
class VideoTrimerActivity : AActivityForResult(), OnTrimVideoListener, OnVideoListener
     {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extraIntent = intent
        var path = ""
        if (extraIntent != null) path = extraIntent.getStringExtra("EXTRA_VIDEO_PATH").toString()

        videoTrimmer.setTextTimeSelectionTypeface(Typeface.DEFAULT_BOLD)
            .setOnTrimVideoListener(this)
            .setOnVideoListener(this)
            .setVideoURI(Uri.parse(path))
            .setVideoInformationVisibility(true)
            .setMaxDuration(10)
            .setMinDuration(2)
            .setDestinationPath(Environment.getExternalStorageDirectory().toString() + File.separator + "temp" + File.separator + "Videos" + File.separator)


    }


    override fun cancelAction() {
        showToast("onCanceled")
    }

    override fun getResult(uri: Uri) {
        runOnUiThread {
            Toast.makeText(this, "Video saved at ${uri.path}", Toast.LENGTH_SHORT).show()
            val mediaMetadataRetriever = MediaMetadataRetriever()
            mediaMetadataRetriever.setDataSource(this, uri)
            val duration = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.toLong()
            val width = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)?.toLong()
            val height = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)?.toLong()
            val values = ContentValues()
            values.put(MediaStore.Video.Media.DATA, uri.path)
            values.put(MediaStore.Video.VideoColumns.DURATION, duration)
            values.put(MediaStore.Video.VideoColumns.WIDTH, width)
            values.put(MediaStore.Video.VideoColumns.HEIGHT, height)
            val id = contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values)?.let {
                ContentUris.parseId(
                    it
                )
            }
            Log.e("VIDEO ID", id.toString())
        }
    }

    override fun onError(message: String) {
        showToast("onError")
    }

    override fun onTrimStarted() {
        showToast("trimStarted")
    }


    override fun onVideoPrepared() {
        showToast("onVideoPrepared")
    }
}