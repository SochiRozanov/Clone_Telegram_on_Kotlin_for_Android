package com.example.telegram.ui.screens.stories

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.telegram.databinding.FragmentStoriesItemBinding
import com.example.telegram.extension.setImage
import com.example.telegram.ui.stories.gesture.SwipeDetector
import com.example.telegram.ui.screens.stories.model.MediaType
import com.example.telegram.ui.screens.stories.model.StoriesModel
import com.example.telegram.ui.stories.widget.StoriesProgressBar

const val EXTRA_STORY_MODEL = "EXTRA_STORY_MODEL"
//фрагмент наполнения сториз
class StoriesItemFragment : Fragment() {

    private var model: StoriesModel? = null
    private lateinit var binding: FragmentStoriesItemBinding

    companion object {
        fun create(model: StoriesModel) =
            StoriesItemFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_STORY_MODEL, model)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            model = it.getParcelable(EXTRA_STORY_MODEL) as? StoriesModel
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoriesItemBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
        setupClickListeners()
        setupGestures()
    }

    override fun onResume() {
        super.onResume()
        startProgress()
    }

    override fun onPause() {
        super.onPause()
        stopProgress()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupGestures() {
        val swipeDetector = GestureDetector(requireContext(), object : SwipeDetector() {
            override fun onSwipe(direction: SwipeDirection): Boolean {
                if (direction == SwipeDirection.DOWN) {
                    val parent = parentFragment as StoriesFragment?
                    parent?.dismiss()
                    return true
                }
                return false
            }

            override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
                val width = binding.clLayout.width
                val pointX = e?.x ?: 0f
                val parent = parentFragment as StoriesFragment?
                if (pointX > width / 2f)
                    parent?.showNextStory()
                else
                    parent?.showPreviousStory()
                return super.onSingleTapConfirmed(e)
            }
        })
        val touchListener = View.OnTouchListener { _, event ->
            swipeDetector.onTouchEvent(event)
        }
        binding.clLayout.setOnTouchListener(touchListener)
    }

    private fun startProgress() {
        val model = this.model
        model?.let {
            when (it.mediaType) {
                MediaType.IMAGE -> binding.progress.startProgress(15f)
                else -> {
                    binding.videoView.start()
                    binding.videoView.seekTo(0)
                    if (binding.videoView.duration != -1) {
                        binding.progress.startProgress(binding.videoView.duration / 1000f)
                    }
                }
            }
        }
    }

    private fun stopProgress() {
        binding.progress.cancelProgress()
        binding.videoView.pause()
    }

    private fun setupData() {
        model?.let {
            initializeView(it)
        }
    }

    private fun initializeView(model: StoriesModel) {
        binding.tvTitle.text = model.title
        binding.tvDescription.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(model.description, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(model.description)
        }

        when (model.mediaType) {
            MediaType.IMAGE -> setupViewForImage(model)
            MediaType.VIDEO -> setupViewForVideo(model)
        }
        if (isResumed) startProgress()
    }

    private fun setupViewForVideo(model: StoriesModel) {
        binding.apply {
            videoView.visibility = View.VISIBLE
            videoViewContainer.visibility = View.VISIBLE
            imageView.visibility = View.GONE
            tvTitle.visibility = View.GONE
            tvDescription.visibility = View.GONE
            videoView.setVideoURI(Uri.parse(model.video))
            pbLoading.visibility = View.VISIBLE
        }
        setupVideoInfoListener()
    }

    private fun setupVideoInfoListener() {
        with(binding) {
            videoView.setOnInfoListener { _, what, _ ->
                when (what) {
                    MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START -> {
                        pbLoading.visibility = View.GONE
                        tvTitle.visibility = View.VISIBLE
                        tvDescription.visibility = View.VISIBLE
                        if (isResumed) progress.startProgress(videoView.duration / 1000f)
                        true
                    }
                    MediaPlayer.MEDIA_INFO_BUFFERING_START -> {
                        progress.pauseProgress()
                        pbLoading.visibility = View.VISIBLE
                        tvTitle.visibility = View.GONE
                        tvDescription.visibility = View.GONE
                        true
                    }
                    MediaPlayer.MEDIA_INFO_BUFFERING_END -> {
                        progress.resumeProgress()
                        pbLoading.visibility = View.GONE
                        tvTitle.visibility = View.VISIBLE
                        tvDescription.visibility = View.VISIBLE
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }
    }

    private fun setupViewForImage(model: StoriesModel) {
        binding.apply {
            tvTitle.visibility = View.VISIBLE
            tvDescription.visibility = View.VISIBLE
            imageView.visibility = View.VISIBLE
            videoView.visibility = View.GONE
            videoViewContainer.visibility = View.GONE
            imageView.setImage(model.imagePath)
            pbLoading.visibility = View.INVISIBLE
        }
    }

    private fun setupClickListeners() {
        binding.leftLayout.setOnClickListener {
            val parent = parentFragment as StoriesFragment?
            parent?.showPreviousStory()
        }
        binding.rightLayout.setOnClickListener {
            val parent = parentFragment as StoriesFragment?
            parent?.showNextStory()
        }
        binding.ivClose.setOnClickListener {
            val parent = parentFragment as StoriesFragment?
            parent?.dismiss()
        }
        binding.progress.addListener(object : StoriesProgressBar.Listener {
            override fun onProgressEnd() {
                val parent = parentFragment as StoriesFragment?
                parent?.showNextStory()
            }
        })
    }

    
}