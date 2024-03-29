package com.example.telegram.ui.stories.gesture

import android.view.GestureDetector
import android.view.MotionEvent
import kotlin.math.atan2
//распознаём характер свайпа и в зависимоти от этого выполняем логику
open class SwipeDetector : GestureDetector.SimpleOnGestureListener() {

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {

        val x1 = e1?.x?:0f
        val y1 = e1?.y?:0f
        val x2 = e2?.x?:0f
        val y2 = e2?.y?:0f
        val direction = getDirection(x1, y1, x2, y2)
        return onSwipe(direction)
    }

    open fun onSwipe(direction: SwipeDirection) : Boolean{
        return false
    }

    private fun getDirection(x1: Float, y1: Float, x2: Float, y2: Float) : SwipeDirection {
        val angle = getAngle(x1, y1, x2, y2)
        return getDirectionFromAngle(angle)
    }

    private fun getAngle(x1: Float, y1: Float, x2: Float, y2: Float): Double {
        val rad = atan2(y1 - y2, x2 - x1) + Math.PI
        return (rad * 180 / Math.PI + 180) % 360
    }

    private fun getDirectionFromAngle(angle: Double) : SwipeDirection {
        return when {
            45 <= angle && angle < 135 -> SwipeDirection.UP
            225 <= angle && angle < 315 -> SwipeDirection.DOWN
            135 <= angle && angle < 225 -> SwipeDirection.LEFT
            else -> SwipeDirection.RIGHT
        }
    }

    enum class SwipeDirection {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
}