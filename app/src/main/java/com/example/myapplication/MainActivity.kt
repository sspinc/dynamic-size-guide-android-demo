package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.webviewexample.DynamicSizeGuideObject

class MainActivity : AppCompatActivity() {

    private var webView: WebView? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialize WebView
        val myWebView: WebView = findViewById(R.id.webview)

        // Enable JavaScript
        val webSettings: WebSettings = myWebView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true

        // Set a WebViewClient to handle loading URLs within the WebView
        myWebView.webViewClient = WebViewClient()

        // Add the JavaScript interface
        myWebView.addJavascriptInterface(DynamicSizeGuideObject(context = this),"SSP_MESSAGE_HANDLER")

        // Load a URL
        myWebView.loadUrl("https://api.sspinc.io/fitpredictor/profile/webview.html?partner_key=acme&gender=FEMALE&preview=true&user_id=user1&mobile_platform=ios&auth_token=acme:TZZ26YS1bW1DIVXzXp4yp-Yvu3x4WoqKXZfW2_7OHxE")

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        webView = myWebView
    }

    // Method to update the WebView size
    fun updateWebViewSize(height: Int) {
        if (webView != null) {
            val density = this.resources.displayMetrics.density
            val px = height * density
            val params = webView!!.layoutParams
            params.height = px.toInt()
            webView!!.layoutParams = params
        }
    }

    fun handleProfileOpen() {
        // Handle profile opening here
        Log.v("SSP", "Opening fit profile");
    }

    fun Float.pxToDp(context: Context): Float =
        (this * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT))
}