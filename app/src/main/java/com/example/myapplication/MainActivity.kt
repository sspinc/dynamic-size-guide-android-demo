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
        myWebView.loadUrl("https://api.sspinc.io/sizeguide/mobile/webview.html?partner_key=walmart&domain_userid=5654345&webview.id=&product.id=4234324&product.brand=adidas&product.department=apparel&product.gender=male&product.sizes=&site.currency=&site.environment=dev&site.language=en&site.layout=&site.market=US&user.id=&user.email_hash=&page.type=pdp&mobile.platform=android&mobile.app_version=")

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

    fun Float.pxToDp(context: Context): Float =
        (this * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT))
}