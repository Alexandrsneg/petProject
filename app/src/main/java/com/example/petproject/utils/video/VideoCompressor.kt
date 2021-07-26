package com.example.petproject.utils.video

import android.content.Context
import android.os.Environment
import android.util.Log
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.github.hiteshsondhi88.libffmpeg.FFmpegExecuteResponseHandler
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException
import java.io.File

open class VideoCompressor(context: Context?) {

    val SUCCESS = 1
    val FAILED = 2
    val NONE = 3
    val RUNNING = 4

    private var context: Context? = context
    private var isFinished = false
    private var status = NONE
    private var errorMessage = "Compression Failed!"


    fun startCompressing(inputPath: String?, outputPath: String, listener: CompressionListener?) {
        if (inputPath == null || inputPath.isEmpty()) {
            status = NONE
            listener?.compressionFinished(NONE, false, null)
            return
        }

        val commandParams = arrayOfNulls<String>(26)
        commandParams[0] = "-y"
        commandParams[1] = "-i"
        commandParams[2] = inputPath
        commandParams[3] = "-s"
        commandParams[4] = "480x720"
        commandParams[5] = "-r"
        commandParams[6] = "20"
        commandParams[7] = "-c:v"
        commandParams[8] = "libx264"
        commandParams[9] = "-preset"
        commandParams[10] = "ultrafast"
        commandParams[11] = "-c:a"
        commandParams[12] = "copy"
        commandParams[13] = "-me_method"
        commandParams[14] = "zero"
        commandParams[15] = "-tune"
        commandParams[16] = "fastdecode"
        commandParams[17] = "-tune"
        commandParams[18] = "zerolatency"
        commandParams[19] = "-strict"
        commandParams[20] = "-2"
        commandParams[21] = "-b:v"
        commandParams[22] = "1000k"
        commandParams[23] = "-pix_fmt"
        commandParams[24] = "yuv720p"
        commandParams[25] = "$outputPath/video_compress.mp4"
        compressVideo(commandParams, "$outputPath/video_compress.mp4", listener)
    }

//    fun getAppDir(): String {
//        var outputPath = Environment.getExternalStorageDirectory().absolutePath
//        outputPath += "/" + "vvvvv"
//        var file = File(outputPath)
//        if (!file.exists()) {
//            file.mkdir()
//        }
//        outputPath += "/" + "videocompress"
//        file = File(outputPath)
//        if (!file.exists()) {
//            file.mkdir()
//        }
//        return outputPath
//    }

    private fun compressVideo(
        command: Array<String?>,
        outputFilePath: String,
        listener: CompressionListener?
    ) {
        try {
            FFmpeg.getInstance(context).execute(command, object : FFmpegExecuteResponseHandler {
                override fun onSuccess(message: String?) {
                    status = SUCCESS
                }

                override fun onProgress(message: String?) {
                    status = RUNNING
                }

                override fun onFailure(message: String) {
                    status = FAILED
                    Log.e("VideoCompressor", message)
                    listener?.onFailure("Error : $message")
                }

                override fun onStart() {}
                override fun onFinish() {
                    Log.e("VideoCronProgress", "finnished")
                    isFinished = true
                    listener?.compressionFinished(status, true, outputFilePath)
                }
            })
        } catch (e: FFmpegCommandAlreadyRunningException) {
            status = FAILED
            errorMessage = e.localizedMessage
            listener?.onFailure("Error : " + e.localizedMessage)
        }
    }

    interface CompressionListener {
        fun compressionFinished(status: Int, isVideo: Boolean, fileOutputPath: String?)
        fun onFailure(message: String?)
        fun onProgress(progress: Int)
    }

    fun isDone(): Boolean {
        return status == SUCCESS || status == NONE
    }
}