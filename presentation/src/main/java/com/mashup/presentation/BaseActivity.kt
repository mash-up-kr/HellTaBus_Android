/*
 * Created by Leo on 2021. 09. 26 ..
 */
package com.mashup.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<B : ViewDataBinding>(private val layoutId: Int) : AppCompatActivity() {
    
    protected lateinit var binding: B
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
        initViews()
        initObserves()
    }
    
    open fun initViews() {
        /* explicitly empty */
    }
    
    open fun initObserves() {
        /* explicitly empty */
    }
}
