package com.example.webviewexample

import android.content.Context
import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.Toast
import com.example.myapplication.MainActivity
import org.json.JSONObject


class Response(json: String) : JSONObject(json) {
    val event: String? = this.optString("event")
    val gender: String? = this.optString("gender")
    val target: String? = this.optString("target")
    val service: String? = this.optString("service")
    val height: Int? = this.optInt("clientHeight")
}

class DynamicSizeGuideObject(private val context: Context) {
    @JavascriptInterface
    fun postMessage(event: String) {
        val response = Response(json = event)
        val height = response.height?.toInt()
        Log.v("SSP", event);
        if (context is MainActivity && height != null && response.event == "resize") {
            context.runOnUiThread {
                context.updateWebViewSize(height = height)
            }
        }
        if (context is MainActivity && response.event == "profile:open") {
            context.runOnUiThread {
                context.handleProfileOpen()
            }
        }
    }
}