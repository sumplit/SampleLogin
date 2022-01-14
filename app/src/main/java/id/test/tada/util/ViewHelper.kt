package id.test.tada.util

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.snackbar.Snackbar
import id.test.tada.R

fun AppCompatActivity.makeStatusBarTransparent() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
            statusBarColor = Color.TRANSPARENT
        }
    }
}

fun ImageView.processImage(progressBar: ProgressBar, uri: String, action: (s: Bitmap) -> Unit) {

    val imageView = this

    val requestOptions = RequestOptions()
        .error(R.drawable.iv_image_default)
        .diskCacheStrategy(DiskCacheStrategy.ALL).timeout(6000)

    Glide.with(imageView.context)
        .asBitmap()
        .load(uri)
        .apply(requestOptions)
        .into(object : SimpleTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                progressBar.gone()
                resource.height
                resource.width
                imageView.setImageBitmap(resource)
                imageView.setOnClickListener {
                    action(resource)
                }
            }

            override fun onLoadStarted(placeholder: Drawable?) {
                progressBar.visible()
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                progressBar.gone()
                imageView.setImageDrawable(errorDrawable)
            }

        })
}

fun View.visible() {
    if (this.visibility == View.GONE || this.visibility == View.INVISIBLE) this.visibility =
        View.VISIBLE
}

fun View.gone() {
    if (this.visibility == View.VISIBLE) this.visibility = View.GONE
}

fun Button.active() {
    this.isEnabled = true
    var bd = this.background
    bd = DrawableCompat.wrap(bd)
    DrawableCompat.setTint(bd, ContextCompat.getColor(this.context, R.color.colorPrimary))
    this.background = bd
}

fun Button.inActive() {
    this.isEnabled = false
    var bd = this.background
    bd = DrawableCompat.wrap(bd)
    DrawableCompat.setTint(bd, ContextCompat.getColor(this.context, R.color.colorC0C0C0))
    this.background = bd
}

fun View.showSnackbarError(snackbarText: String, timeLength: Int) {
    val snackbar = Snackbar.make(this, snackbarText, timeLength)
    val snackBarView = snackbar.view
    val snackbarTextId = com.google.android.material.R.id.snackbar_text

    val textView = snackBarView.findViewById(snackbarTextId) as TextView
    textView.setTextColor(ContextCompat.getColor(context, android.R.color.white))
    textView.text = snackbarText
    snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.colord84372))
    snackbar.show()
}