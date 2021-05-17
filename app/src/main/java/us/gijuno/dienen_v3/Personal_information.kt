package us.gijuno.dienen_v3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_personal_information.*
import android.view.View
import android.view.ViewGroup

class Personal_information : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_information)
        // 웹뷰안에 새 창이 뜨지 않도록 방지
        personal_information_webview.webViewClient = WebViewClient()
        personal_information_webview.webChromeClient = WebChromeClient()

        personal_information_webview.apply {
            settings.useWideViewPort = true
            settings.setSupportZoom(true)
            settings.builtInZoomControls = true
        }

        personal_information_webview.loadUrl("https://dienen.org/dienen_privacy_information.html")

    }

    override fun onDestroy() {
        super.onDestroy()
        personal_information_webview.stopLoading()
        val webParent = personal_information_webview.getParent() as ViewGroup
        webParent.removeView(personal_information_webview)
        personal_information_webview.destroy()
    }

}