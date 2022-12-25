package com.vsegura15dev.webviewsample

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebViewClient
import android.widget.Toast
import com.vsegura15dev.webviewsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val jsInterfaceKey = "JSInterface"
    private val sampleHtmlPath = "file:///android_asset/index.html"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebView() {
        binding.customWebView.run {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            loadUrl(sampleHtmlPath)
            addJavascriptInterface(
                getJSInterface(), jsInterfaceKey
            )
        }
    }

    private fun getJSInterface() = JSInterface(
        this, object : CustomWebViewCallback {
            override fun onClose() {
                finish()
            }
        }
    )

    private interface CustomWebViewCallback {
        fun onClose()
    }

    private class JSInterface constructor(
        private val context: Context,
        private val callback: CustomWebViewCallback,
    ) {

        @JavascriptInterface
        fun toastMe(text: String) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }

        @JavascriptInterface
        fun closeMe() {
            callback.onClose()
        }
    }
}