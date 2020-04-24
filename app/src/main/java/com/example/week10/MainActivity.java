package com.example.week10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    EditText editText;
    Button refreshButton;
    Button executeButton;
    Button initializeButton;
    MyWebViewClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.web);
        editText = (EditText) findViewById(R.id.editText);
        refreshButton = (Button) findViewById(R.id.refreshButton);
        executeButton = (Button) findViewById(R.id.executeJava);
        initializeButton = (Button) findViewById(R.id.initializeButton);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(client = new MyWebViewClient());
        webView.loadUrl("http://google.fi");
        editText.setText(webView.getUrl());
        editText.setOnEditorActionListener(editorListener);

    }

    private TextView.OnEditorActionListener editorListener = new TextView.OnEditorActionListener(){

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                if(editText.getText().toString().equals("index.html")){
                    webView.loadUrl("file:///android_asset/index.html");
                } else if(editText.getText().toString().contains("http://")) {
                    webView.loadUrl(editText.getText().toString());
                    editText.setText(webView.getUrl());
                } else {
                    webView.loadUrl("http://" + editText.getText().toString());
                    editText.setText(webView.getUrl());
                }
            }
            return false;
        }
    };

    public void refresh(View v){
        client.setFlag(); //set flag to false to keep url from being added to list
        webView.reload();
        editText.setText(webView.getUrl());
    }

    public void executeJava(View v){
        webView.evaluateJavascript("javascript:shoutOut()", null);
    }
    public void initializeJava(View v){
        webView.evaluateJavascript("javascript:initialize()", null);
    }

    public void nextPage(View v){

        client.setFlag(); //set flag to false to keep url from being added to list
        String next = client.nextUrl(webView.getUrl());
        if(next.equals("")){ //newest page available
        }else {
            webView.loadUrl(next);
        }
        editText.setText(webView.getUrl());
    }

    public void previousPage(View v){
        client.setFlag(); //set flag to false to keep url from being added to list
        String previous = client.previousUrl(webView.getUrl());
        if(previous.equals("")){ //oldest page available
        }else {
            webView.loadUrl(previous);
        }
        editText.setText(webView.getUrl());
    }

}
