package info.esoft.ko.presentation.common.moxymvp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import androidx.core.content.FileProvider
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.petproject.BuildConfig
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.concurrent.atomic.AtomicBoolean


/**
 * Created by f0x on 26.10.17.
 */
abstract class ABasePresenter<View : MvpView> : MvpPresenter<View>() {

    companion object {
        private val handler = Handler()
    }

    var context: Context? = null
    private val isAttached = AtomicBoolean()

    override fun attachView(view: View?) {
        super.attachView(view)
        isAttached.set(true)
    }

    override fun detachView(view: View?) {
        isAttached.set(false)
        super.detachView(view)
    }

    override fun onDestroy() {
        context = null
        super.onDestroy()
    }

//    fun errorProcessing(error: Throwable): String {
//        return ErrorProcessor.errorProcessing(error)
//    }

    fun prepareImage(imageLink: String?, title: String, typeShare: String, id: Int, onLoadFailed :() -> Unit, onResourceReady: (uri: Uri) -> Unit) {
        val context = context ?: return

        Glide.with(context)
                .asBitmap()
                .load(imageLink)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                .into(object : CustomTarget<Bitmap?>() {
                    override fun onLoadCleared(placeholder: Drawable?) {}
                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        super.onLoadFailed(errorDrawable)
                    }

                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
//                        viewState.showLoadingProgress(false)
                        getLocalBitmapUri(context, resource, typeShare, id)?.let { onResourceReady(it) }
                    }
                })
    }

    fun getLocalBitmapUri(context: Context, bmp: Bitmap, typeShare: String, id: Int): Uri? {

        try {

            val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_$typeShare-$id.jpg")
            if (file.exists()) file.delete()
            val out = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.close()

            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file)
            else
                Uri.fromFile(file)

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    fun isActive() = isAttached.get()

    fun post(block: () -> Unit, delay: Long = 0L) {
        if (delay == 0L) handler.post(block)
        else handler.postDelayed(block, delay)
    }
}