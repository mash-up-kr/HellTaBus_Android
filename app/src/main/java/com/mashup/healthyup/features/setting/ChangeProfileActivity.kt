package com.mashup.healthyup.features.setting

import androidx.databinding.DataBindingUtil
import com.mashup.healthyup.R
import com.mashup.healthyup.base.BaseActivity
import com.mashup.healthyup.databinding.ActivityChangeProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeProfileActivity :
    BaseActivity<ActivityChangeProfileBinding>(R.layout.activity_change_profile) {

    override fun initViews() {
        super.initViews()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_profile)
    }

    override fun initListeners() {
        super.initListeners()
        binding.appbar.btnBack.setOnClickListener {
            finish()
        }
    }
}
