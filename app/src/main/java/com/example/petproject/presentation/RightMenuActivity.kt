package com.example.petproject.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.petproject.R
import com.example.petproject.common.Layout
import com.example.petproject.moxymvp.activities.ABaseActivity
import kotlinx.android.synthetic.main.activity_right_menu.*

@SuppressLint("NonConstantResourceId")
@Layout(
        value = R.layout.activity_right_menu,
        fragmentContainerId = R.id.container
)
class RightMenuActivity : ABaseActivity() {

    companion object {
        fun runActivity(context: Context?) {
            val intent = Intent(context, RightMenuActivity::class.java)
            context?.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ivCloseScreen.setOnClickListener { finish() }
        llProfile.setOnClickListener {
            VideoViewerActivity.runActivity(this)
        }
    }
}