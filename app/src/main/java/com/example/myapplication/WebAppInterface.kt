package com.example.webviewexample

import android.content.Context
import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.Toast
import com.example.myapplication.MainActivity
import org.json.JSONObject


class Response(json: String) : JSONObject(json) {
    val event: String? = this.optString("event")
    val height: String? = this.optString("clientHeight")
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
    }
}