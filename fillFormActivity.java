package com.example.ncc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class fillFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_form);

        WebView webView = findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSfZHfGouINZRlbNT_RK-J0ieLCi6RmBb_W3DL22OpVvFaz6Qw/viewform");
    }
}