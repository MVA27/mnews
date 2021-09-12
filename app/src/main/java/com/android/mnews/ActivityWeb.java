package com.android.mnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ActivityWeb extends AppCompatActivity {

    WebView webView;
    ProgressBar progressBar;
    ImageButton shareImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Intent intent = getIntent();
        String url = intent.getStringExtra("POST_LINK");

        progressBar = findViewById(R.id.activity_web_progressbar);
        progressBar.setIndeterminate(true);

        shareImageButton = findViewById(R.id.activity_web_share_button);
        shareImageButton.setOnClickListener(v -> {
            Toast.makeText(this, "Share..!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent();
            i.setAction(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_TEXT,url);
            startActivity(Intent.createChooser(i,"Share via"));
        });

        if(getSupportActionBar() != null)
            getSupportActionBar().hide();

        webView = findViewById(R.id.activity_web_webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");

        //To open the link in this activity itself
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                shareImageButton.setVisibility(View.VISIBLE);
            }
        });

        if(url == null) {
            Toast.makeText(this, "Access Denied", Toast.LENGTH_SHORT).show();
        }
        else {
            webView.loadUrl(url);
        }

    }
}