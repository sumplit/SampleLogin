package id.test.tada.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.net.URLEncoder

/**
 * Using it for replacement fragment
 *
 * @param fragment => your fragment class
 * @param frameId => your container layout id
 */
fun AppCompatActivity.replaceFragmentInActivity(
    fragment: Fragment,
    frameId: Int
) {
    supportFragmentManager.transact {
        replace(frameId, fragment, fragment::class.java.simpleName)
    }

    //=========== How to using it ===========
    // override fun onYourMethod() {
    //      replaceFragmentInActivity(Fragment.newInstance(), R.id.frame_container)
    // }
    //=======================================
}

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

/**
 * Using it for replacement fragment and add it into backstack
 *
 * @param fragment => your fragment class
 * @param frameId => your container layout id
 * @param TAG => your TAG usually the name of activity class
 */
fun AppCompatActivity.replaceFragmentInActivityWithBackStack(
    fragment: Fragment,
    frameId: Int,
    TAG: String? = "",
    isAllowCommitLost: Boolean = false
) {
    supportFragmentManager.transact(isAllowCommitLost)
    {
        replace(frameId, fragment, fragment::class.java.simpleName)
        addToBackStack(fragment::class.java.simpleName)
    }
    //=========== How to using it ===========
    // override fun onYourMethod() {
    //      replaceFragmentInActivityWithBackStack(Fragment.newInstance(), R.id.frame_container, TAG)
    // }
    //=======================================
}


// Get length of file in bytes
val File.fileSizeInBytes: Long
    get() = length()

// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
val File.fileSizeInKB: Long
    get() = fileSizeInBytes / 1024

// Convert the KB to MegaBytes (1 MB = 1024 KBytes)
val File.fileSizeInMB: Long
    get() = fileSizeInKB / 1024

// Get length of file in bytes
val ByteArray.fileSizeInBytes: Long
    get() = size.toLong()

// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
val ByteArray.fileSizeInKB: Long
    get() = fileSizeInBytes / 1024

// Convert the KB to MegaBytes (1 MB = 1024 KBytes)
val ByteArray.fileSizeInMB: Long
    get() = fileSizeInKB / 1024

fun getTimeStamp(): String {
    return dateFormatFromTimeLong(
        System.currentTimeMillis(),
        "dd-MM-yyyy",
        false
    ) + "_" + dateFormatFromTimeLong(System.currentTimeMillis(), "HH:mm:ss", false)
}

fun Activity.getScreenSize(width: Boolean = false, height: Boolean = false): Int {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    if (width) {
        return displayMetrics.widthPixels
    } else {
        return displayMetrics.heightPixels
    }
}

fun String.dateFormatFromTimeLong(
    timeLong: Long,
    newFormat: String,
    amount: Int,
    isLocale: Boolean
): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timeLong
    calendar.add(Calendar.HOUR_OF_DAY, amount)

    return if (timeLong != 0.toLong() && timeLong != null) SimpleDateFormat(
        newFormat,
        isLocale.isLocaleDate(isLocale)
    )
        .format(calendar.time) else SimpleDateFormat(newFormat, isLocale.isLocaleDate(isLocale))
        .format(System.currentTimeMillis())
}

fun rotateImage(source: Bitmap, angle: Float): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(angle)
    return Bitmap.createBitmap(
        source, 0, 0, source.width, source.height,
        matrix, true
    )
}

fun dateFromTimeLong(
    timeLong: Long
): Date {
    return Date(timeLong)
}

fun dateFromTimeString(
    date: String,
    format: String,
    isLocale: Boolean
): Date {
    return SimpleDateFormat(format, isLocale.isLocaleDate(isLocale)).parse(date)
}


private inline fun FragmentManager.transact(
    isAllowCommitLost: Boolean = false,
    action: FragmentTransaction.() -> Unit
) {
    if (!isAllowCommitLost)
        beginTransaction().apply {
            action()
        }.commit()
    else
        beginTransaction().apply {
            action()
        }.commitAllowingStateLoss()
}


fun Boolean.isLocaleDate(
    isLocale: Boolean
): Locale {
    return if (isLocale) Locale("id", "ID")
    else Locale("en", "EN")
}

fun dateFormatFromTimeString(
    date: String,
    oldFormat: String,
    newFormat: String,
    isLocale: Boolean
): String {
    val dateTimeMillis = if (!TextUtils.isEmpty(date)) {
        SimpleDateFormat(oldFormat, isLocale.isLocaleDate(isLocale)).parse(date).time
    } else {
        0.toLong()
    }

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = dateTimeMillis

    return if (dateTimeMillis != 0.toLong() && dateTimeMillis != null) {
        SimpleDateFormat(newFormat, isLocale.isLocaleDate(isLocale))
            .format(calendar.time)
    } else {
        SimpleDateFormat(newFormat, isLocale.isLocaleDate(isLocale))
            .format(System.currentTimeMillis())
    }
}

fun dateFormatFromTimeLong(
    timeLong: Long,
    newFormat: String,
    isLocale: Boolean
): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timeLong

    return if (timeLong != 0.toLong() && timeLong != null) SimpleDateFormat(
        newFormat,
        isLocale.isLocaleDate(isLocale)
    )
        .format(calendar.time) else SimpleDateFormat(newFormat, isLocale.isLocaleDate(isLocale))
        .format(System.currentTimeMillis())
}

fun String?.convertToRequestBody(mediaType: String = "text/plain"): RequestBody? {
    if (this != null) {
        return RequestBody.create(mediaType.toMediaTypeOrNull(), this)
    }
    return null
}

fun File.reduceImageSize(): File {

    val outputStream = ByteArrayOutputStream()

    val imgBitmap = BitmapFactory.decodeFile(this.absolutePath)

    val bitmap2 = Bitmap.createScaledBitmap(
        imgBitmap,
        imgBitmap.width.times(8).div(10),
        imgBitmap.height.times(8).div(10),
        false
    )

    val fileOutputStream = FileOutputStream(this)

    bitmap2.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)

    fileOutputStream.write(outputStream.toByteArray())

    fileOutputStream.close()

    return this
}

fun File.filePathToMultipart(name: String): MultipartBody.Part {
    val reqFile = RequestBody.create(
        when (this.extension) {
            "jpg" -> "image/jpg"
            "png" -> "image/png"
            else -> "image/jpeg"
        }
            .toMediaTypeOrNull(), this
    )
    val image = MultipartBody.Part.createFormData(name, this.name, reqFile)
    return image
}


fun <T : Fragment> T.withArgs(
    argsBuilder: Bundle.() -> Unit
): T =
    this.apply {
        arguments = Bundle().apply(argsBuilder)
    }

fun View.showSnackbar(snackbarText: String, timeLength: Int) {
    Snackbar.make(this, snackbarText, timeLength).show()
}

/**
 * hide soft keyboard
 */
fun Activity.hideKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun String.toDate(): String {
    try {
        val pattern = SimpleDateFormat("yyyy-MM-dd")
        val date = pattern.parse(this)
        return android.text.format.DateFormat.format("EEEE", date).toString()
    } catch (e: Exception) {
        return "-"
    }
}

fun String.mToMm(): String {
    return if (this.length == 1) {
        val oldVal = this
        "0$oldVal"
    } else
        this

}

fun ImageView.loadImage(obj: Any?, progressBar: ProgressBar? = null, cache: Boolean = true) {
    Glide.with(context)
        .load(obj)
        .listener(object : RequestListener<Drawable> {
            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar?.visibility = View.GONE
                return false
            }

            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar?.visibility = View.GONE
                return false
            }
        })
        .transition(
            DrawableTransitionOptions.withCrossFade(
                300
            )
        )
        .into(this)
        .apply {
            if (cache)
                RequestOptions()
                    .diskCacheStrategy
        }
}

fun View.hideShowWithAnimation(show: Boolean) {
    val ANIMATION_DURATION = 300L
    if (show) {
        visibility = View.VISIBLE
        animate()
            .alpha(1f)
            .setDuration(ANIMATION_DURATION)
            .setListener(null)
    } else {
        animate()
            .alpha(0f)
            .setDuration(ANIMATION_DURATION)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    visibility = View.GONE
                }
            });
    }
}

fun Activity.openWhatsApp(number: String) {

    try {
        val packageManager = packageManager
        val i = Intent(Intent.ACTION_VIEW);
        val url = "https://api.whatsapp.com/send?phone=$number&text=${URLEncoder.encode(
            "",
            "UTF-8"
        )}"
        i.setPackage("com.whatsapp");
        i.setData(Uri.parse(url));
        if (i.resolveActivity(packageManager) != null) {
            startActivity(i);
        } else {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp")
                )
            )
        }
    } catch (e: Exception) {

    }

}

interface DateListener {
    fun selectedDate(date: String)
}


interface DialogViewListener {
    fun onCustomViewInflated(dialogView: View)
}


fun isValidPassword(password: String): Boolean {

    val pattern: Pattern
    val matcher: Matcher
    val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{4,}$"
    pattern = Pattern.compile(PASSWORD_PATTERN)
    matcher = pattern.matcher(password)

    return matcher.matches()
}

fun String.isEmailValid(): Boolean {
    return if (this.isNotEmpty()) {
        Patterns.EMAIL_ADDRESS.matcher(this).matches()
    } else {
        false
    }
}

fun TextInputLayout.enableError(error: String) {
    this.isErrorEnabled = true
    this.error = error
}

fun TextInputLayout.disableError() {
    this.isErrorEnabled = false
    this.error = ""
}

fun <T : ViewModel> AppCompatActivity.obtainViewModel(viewModelClass: Class<T>) =
    ViewModelProvider(this, ViewModelFactory.getInstance(application)).get(viewModelClass)


fun <T : ViewModel> Fragment.obtainViewModel(viewModelClass: Class<T>) =
    ViewModelProvider(this, ViewModelFactory.getInstance(requireActivity().application)).get(
        viewModelClass
    )

fun <T : ViewModel> FragmentActivity.obtainViewModel(viewModelClass: Class<T>) =
    ViewModelProvider(this, ViewModelFactory.getInstance(application)).get(viewModelClass)
