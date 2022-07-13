package com.example.telegram.ui.stories.widget

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import com.example.telegram.R
//Кастомный прогресс бар, отвечает за показ верхнего ползунка в откртых историях
class StoriesProgressBar(context: Context, attributeSet: AttributeSet?, defStyle: Int) :
    ProgressBar(context, attributeSet, defStyle) {

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context) : this(context, null)

    private var objectAnimator = ObjectAnimator.ofInt(this, "progress", 0, 10000)
    private var listener: Listener? = null
    private var hasCanceled = false

    init {
        initView()
    }

    private fun initView() {
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1f
        )
        params.marginEnd = context.resources.getDimensionPixelSize(R.dimen.stories_progress_margin)
        max = 10000
        progress = 0
        layoutParams = params
        progressDrawable = ContextCompat.getDrawable(context, R.drawable.stories_progress_bar)
        objectAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}

            override fun onAnimationEnd(animation: Animator?) {
                if (hasCanceled.not()) {
                    listener?.onProgressEnd()
                }
            }

            override fun onAnimationCancel(animation: Animator?) {
                hasCanceled = true
            }

            override fun onAnimationRepeat(animation: Animator?) {}
        })
    }

    fun addListener(listener: Listener) {
        this.listener = listener
    }

    fun startProgress(durationInSeconds: Float) {
        val safetyDuration = if (durationInSeconds < 0) 0f else durationInSeconds

        hasCanceled = false
        cancelProgress()

        objectAnimator.apply {
            duration = (safetyDuration * 1000).toLong()
            start()
        }
    }

    fun cancelProgress() {
        objectAnimator.cancel()
    }

    fun pauseProgress() {
        objectAnimator.pause()
    }

    fun resumeProgress() {
        objectAnimator.resume()
    }

    interface Listener {
        fun onProgressEnd()
    }
}