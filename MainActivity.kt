package com.example.marketplaceonly

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebResourceRequest
import android.webkit.WebChromeClient
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import android.net.Uri

class MainActivity : Activity() {
    private lateinit var web: WebView
    private lateinit var notice: TextView
    private val marketplace = "https://www.facebook.com/marketplace/"
    private val messenger = "https://www.messenger.com/"

    // Facebook login and security paths must remain available for authentication.
    private fun allowed(url: String): Boolean {
        val uri = try { Uri.parse(url) } catch (_: Exception) { return false }
        if (uri.scheme != "https") return false
        val host = uri.host?.lowercase() ?: return false
        val path = uri.path.orEmpty().lowercase()
        if (host == "www.messenger.com" || host == "messenger.com") return true
        if (host != "www.facebook.com" && host != "m.facebook.com" && host != "facebook.com") return false
        val permitted = listOf("/marketplace", "/messages", "/login", "/checkpoint", "/recover", "/two_step_verification", "/device-based", "/privacy/consent", "/cookie", "/help/contact")
        return permitted.any { path == it || path.startsWith("$it/") } || path == "/"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setBackgroundColor(Color.WHITE) }
        val bar = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        fun addButton(label: String, url: String) {
            val b = Button(this).apply { text = label; setOnClickListener { web.loadUrl(url) } }
            bar.addView(b, LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f))
        }
        addButton("Marketplace", marketplace)
        addButton("Messenger", messenger)
        notice = TextView(this).apply { text = "Bara Marketplace och Messenger"; setPadding(18, 8, 18, 8) }
        web = WebView(this).apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.setSupportMultipleWindows(false)
            settings.javaScriptCanOpenWindowsAutomatically = false
            settings.allowFileAccess = false
            settings.allowContentAccess = false
            webChromeClient = WebChromeClient()
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                    if (!request.isForMainFrame) return false
                    val url = request.url.toString()
                    if (allowed(url)) return false
                    notice.text = "Blockerad sida – endast Marketplace och Messenger är tillåtna"
                    Toast.makeText(this@MainActivity, "Sidan är blockerad", Toast.LENGTH_SHORT).show()
                    return true
                }
                override fun onPageFinished(view: WebView, url: String) {
                    if (url.contains("facebook.com/") && Uri.parse(url).path == "/") {
                        view.loadUrl(marketplace)
                    }
                }
            }
        }
        root.addView(bar)
        root.addView(notice)
        root.addView(web, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f))
        setContentView(root)
        web.loadUrl(marketplace)
    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (web.canGoBack()) web.goBack() else super.onBackPressed()
    }
    override fun onDestroy() { web.destroy(); super.onDestroy() }
}
