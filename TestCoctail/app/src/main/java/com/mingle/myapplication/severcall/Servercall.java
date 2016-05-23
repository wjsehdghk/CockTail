package com.mingle.myapplication.severcall;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by multimedia on 2016-05-23.
 */
public class Servercall extends AppCompatActivity {



    AsyncHttpClient client = new AsyncHttpClient();
    private static final String server_url = "http://113.198.84.107:8080/cocktail";


    public void confirm(Context context, String nickname) {
        RequestParams params=new RequestParams();
        params.put("nickname",nickname);
        client.post(context, server_url + "/test", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("test","fail");
                Toast.makeText(getApplicationContext(), "fail"+statusCode, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("test","success");
            }
        });
    }
}
