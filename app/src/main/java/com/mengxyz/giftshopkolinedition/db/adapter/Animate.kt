package com.mengxyz.giftshopkolinedition.db.adapter

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.LinearLayout


fun expand(v: View) {
    val matchParentMeasureSpec: Int = View.MeasureSpec.makeMeasureSpec(
        (v.parent as View).width,
        View.MeasureSpec.EXACTLY
    )
    val wrapContentMeasureSpec: Int =
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    v.measure(matchParentMeasureSpec, wrapContentMeasureSpec)
    val targetHeight: Long = v.measuredHeight.toLong()
    // Older versions of android (pre API 21) cancel animations for views with a height of 0.
    v.layoutParams.height = 1
    v.visibility = View.VISIBLE
    val a: Animation = object : Animation() {
        override fun applyTransformation(
            interpolatedTime: Float,
            t: Transformation?
        ) {
            v.layoutParams.height =
                if (interpolatedTime == 1f) LinearLayout.LayoutParams.WRAP_CONTENT else (targetHeight * interpolatedTime).toInt()
            v.requestLayout()
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }
    // Expansion speed of 1dp/ms
    a.duration = (targetHeight / v.context.resources.displayMetrics.density).toLong()
    v.startAnimation(a)
}

fun collapse(v: View) {
    val initialHeight: Long = v.measuredHeight.toLong()
    val a: Animation = object : Animation() {
        override fun applyTransformation(
            interpolatedTime: Float,
            t: Transformation?
        ) {
            if (interpolatedTime == 1f) {
                v.visibility = View.GONE
            } else {
                v.layoutParams.height =
                    (initialHeight - (initialHeight * interpolatedTime)).toInt()
                v.requestLayout()
            }
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }
    // Collapse speed of 1dp/ms
    a.duration = (initialHeight / v.context.resources.displayMetrics.density).toLong()
    v.startAnimation(a)
}