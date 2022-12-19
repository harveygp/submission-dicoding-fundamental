package com.example.submission_1_fundamental.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import com.example.submission_1_fundamental.common.Constant
import com.example.submission_1_fundamental.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private var _binding: ActivitySettingBinding? = null
    private val binding get() = _binding!!
    private val pref by lazy { SettingHelper(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){

        binding.switchTheme.isChecked = pref.getSwitch(Constant.KEY_PREF)

        binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                pref.putSwitch(Constant.KEY_PREF, true)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                pref.putSwitch(Constant.KEY_PREF, false)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}