package com.example.petproject

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
class AppChooserReceiver : BroadcastReceiver() {

    companion object {
        private var shareObject: Any? = null
        private var shareTypeEntity: String? = null
        private var shareFrom: String? = null

        fun prepareShareContent(shareTypeEntity: String, shareFrom: String, shareObject: Any) {
            this.shareTypeEntity = shareTypeEntity
            this.shareObject = shareObject
            this.shareFrom = shareFrom
        }

        const val SHARE_TYPE_WORK = "SHARE_TYPE_WORK"
        const val SHARE_TYPE_WORK_ONBOARDING = "SHARE_TYPE_WORK_ONBOARDING"
        const val SHARE_TYPE_URBAN_OBJECT = "SHARE_TYPE_URBAN_OBJECT"
        const val SHARE_TYPE_EVENT = "SHARE_TYPE_EVENT"
        const val SHARE_TYPE_NEWS = "SHARE_TYPE_NEWS"
        const val SHARE_TYPE_PROPOSAL = "SHARE_TYPE_PROPOSAL"
        const val SHARE_TYPE_POLL = "SHARE_TYPE_POLL"
        const val SHARE_TYPE_PETITION = "SHARE_TYPE_PETITION"
        const val SHARE_TYPE_QUESTION = "SHARE_TYPE_QUESTION"
        const val SHARE_TYPE_DIRECT_LINE = "SHARE_TYPE_DIRECT_LINE"

        const val SHARE_FROM_MAIN_FEED = "SHARE_FROM_MAIN_FEED"

        const val SHARE_FROM_WORK_LIST = "SHARE_FROM_WORK_LIST"
        const val SHARE_FROM_WORK_DETAIL = "SHARE_FROM_WORK_DETAIL"

        const val SHARE_FROM_PROPOSAL_LIST = "SHARE_FROM_PROPOSAL_LIST"
        const val SHARE_FROM_PROPOSAL_DETAIL = "SHARE_FROM_PROPOSAL_DETAIL"

        const val SHARE_FROM_POLL_LIST = "SHARE_FROM_POLL_LIST"
        const val SHARE_FROM_POLL_DETAIL = "SHARE_FROM_POLL_DETAIL"

        const val SHARE_FROM_NEWS_LIST = "SHARE_FROM_NEWS_LIST"
        const val SHARE_FROM_NEWS_DETAIL = "SHARE_FROM_NEWS_DETAIL"

        const val SHARE_FROM_URBAN_OBJECT_LIST = "SHARE_FROM_URBAN_OBJECT_LIST"
        const val SHARE_FROM_URBAN_OBJECT_DETAIL = "SHARE_FROM_URBAN_OBJECT_DETAIL"

        const val SHARE_FROM_EVENT_LIST = "SHARE_FROM_EVENT_LIST"
        const val SHARE_FROM_EVENT_DETAIL = "SHARE_FROM_EVENT_DETAIL"

        const val SHARE_FROM_QUESTION_DETAIL = "SHARE_FROM_QUESTION_DETAIL"

        const val SHARE_FROM_PETITION_DETAIL = "SHARE_FROM_PETITION_DETAIL"

        const val SHARE_FROM_DIRECT_LINE = "SHARE_FROM_DIRECT_LINE"
        const val SHARE_FROM_DIRECT_LINE_DETAIL = "SHARE_FROM_DIRECT_LINE_DETAIL"
    }

    override fun onReceive(context: Context, intent: Intent?) {
        Log.d("receiver", "receiver")

        val extra = intent?.extras ?: return
        for (key in extra.keySet()) {
            try {
                val componentInfo = extra[key] as ComponentName
                val packageManager = context.packageManager
                val applicationInfo = packageManager.getApplicationInfo(componentInfo.packageName, PackageManager.GET_META_DATA)
                val appName = packageManager.getApplicationLabel(applicationInfo) as String
//                val chosenApp = Utils.getAppName(appName)
//
//                val attr = StatAttr().put("Куда", chosenApp)

                var eventName = ""

                when (shareTypeEntity) {

                    SHARE_TYPE_WORK -> {
                        eventName = "Поделился рем. работой (М)"
                    }

                    SHARE_TYPE_WORK_ONBOARDING -> {
                        eventName = "Поделился рем. работой с онбординга (М)"
                    }

                    SHARE_TYPE_URBAN_OBJECT -> {
                        eventName = "Градостроительные планы. Поделиться объектом"
                    }

                    SHARE_TYPE_PETITION -> {
                        eventName = "Поделился петицией"
                    }

                    SHARE_TYPE_QUESTION -> {
                        eventName = "Поделился вопросом"
                    }
                }

//                StatHelper.logStat(eventName, attr)

            } catch (e: Exception) {

            }
        }
    }


}