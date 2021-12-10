/*
 * Created by Leo on 2021. 12. 11 ..
 */
package com.mashup.healthyup.core

import android.animation.Animator

abstract class AbstractAnimatorListener : Animator.AnimatorListener {

    override fun onAnimationStart(animation: Animator?) {
        /* explicitly empty */
    }

    override fun onAnimationCancel(animation: Animator?) {
        /* explicitly empty */
    }

    override fun onAnimationEnd(animation: Animator?) {
        /* explicitly empty */
    }

    override fun onAnimationRepeat(animation: Animator?) {
        /* explicitly empty */
    }
}
