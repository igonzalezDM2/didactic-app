package com.example.didactic_app

import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt


class TouchListener : View.OnTouchListener {
    private var xDelta = 0f
    private var yDelta = 0f

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        val x = motionEvent.rawX
        val y = motionEvent.rawY
        val tolerance: Double = sqrt(view.getWidth().toDouble().pow(2) + view.getHeight().toDouble().pow(2)) / 10;

        var piece: PuzzlePiece = view as PuzzlePiece
        if (!piece.canMove) {
            return true
        }

        val lParams = view.layoutParams as RelativeLayout.LayoutParams

        when (motionEvent.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                xDelta = x - lParams.leftMargin
                yDelta = y - lParams.topMargin
                piece.bringToFront()
            }
            MotionEvent.ACTION_MOVE -> {
                lParams.leftMargin = (x - xDelta).toInt()
                lParams.topMargin = (y - yDelta).toInt()
                view.layoutParams = lParams
            }
            MotionEvent.ACTION_UP -> {
                val xDiff: Int = abs(piece.xCoord - lParams.leftMargin)
                val yDiff: Int = abs(piece.yCoord - lParams.topMargin)
                if (xDiff <= tolerance && yDiff <= tolerance) {
                    lParams.leftMargin = piece.xCoord
                    lParams.topMargin = piece.yCoord
                    piece.layoutParams = lParams
                    piece.canMove = false
                    sendViewToBack(piece)
                }
            }
        }

        return true
    }

    fun sendViewToBack(child: View) {
        val parent = child.parent as ViewGroup
        if (null != parent) {
            parent.removeView(child)
            parent.addView(child, 0)
        }
    }
}
