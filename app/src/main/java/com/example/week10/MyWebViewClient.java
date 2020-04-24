package com.example.week10;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URL;
import java.util.ArrayList;
import java.util.ListIterator;

public class MyWebViewClient extends WebViewClient {

    private ArrayList<String> previousUrls = new ArrayList<>();
    private ArrayList<String> nextUrls = new ArrayList<>();
    private Boolean flag = true; //true when a new page is loaded
    private String previousPage = "";

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon){
        if(flag && previousUrls.size() < 10 && !previousPage.equals("")) { //doesn't add the first url when app is opened
            nextUrls.clear();
            previousUrls.add(previousPage);
        } else if (flag && previousUrls.size() == 10 && !previousPage.equals("")) {
            nextUrls.clear();
            previousUrls.remove(0);
            previousUrls.add(previousPage);
        } else {
            flag = true;
        }
        previousPage = url;
    }

    public String nextUrl(String currentUrl){
        String url = "";
        if(nextUrls.size() != 0) {
            url = nextUrls.get(0);
            nextUrls.remove(0);
            if(previousUrls.size() < 10){
                previousUrls.add(currentUrl);
                System.out.println(previousUrls);
            }else{
                previousUrls.remove(0);
                previousUrls.add(currentUrl);
            }
        }
        return url;
    }

    String previousUrl(String currentUrl){
        String url = "";
        if(nextUrls.size() < 10 && previousUrls.size() > 0) {
            url = previousUrls.get(previousUrls.size() -1); //get the most recent page
            previousUrls.remove(previousUrls.size() -1); //delete the page from previous pages
            nextUrls.add(0, currentUrl); //add the newest page to next pages
        }else if (nextUrls.size() == 10 && previousUrls.size() > 0){
            url = previousUrls.get(previousUrls.size() -1);
            previousUrls.remove(previousUrls.size() -1);
            nextUrls.remove(0);
            nextUrls.add(0, currentUrl);
        }else { //if the first page has been reached
        }
        return url;
    }

    void setFlag(){
        flag = false;
    }

}
