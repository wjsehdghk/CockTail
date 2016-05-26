package com.mingle.myapplication.severcall;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.mingle.myapplication.Result;
import cz.msebera.android.httpclient.Header;
/**
 * Created by multimedia on 2016-05-23.
 */
public class Servercall extends AppCompatActivity {
    Gson gson;
    Result result;
    AsyncHttpClient client = new AsyncHttpClient();
    private static final String server_url = "http://113.198.84.87:8080/cocktail";

    public void postcinemainfo(Context context, String id){
        RequestParams params=new RequestParams();
        String cinema=id;
        params.put("sectorId",cinema);
        client.post(context, server_url + "/app/incUseCount", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("cinema","fail");
                Log.d("cinema","fail "+statusCode);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("cinema","success1");
                Log.d("cinema","success2 "+responseString );
            }
        });
    }
    public void confirm(Context context, String nickname) {
        RequestParams params=new RequestParams();
        params.put("nickname",nickname);
        client.post(context, server_url + "/test", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("test","fail");
                Log.d("fail"+statusCode,"fail");
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("test","success");
                Log.d("test","success "+statusCode);
            }
        });
    }
    public void customizeset(Context context){
        RequestParams params=new RequestParams();
        client.post(context, server_url + "/test", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("fail","custom "+statusCode);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("success","custom "+statusCode);
                // gson 을 사용하겠음.
                gson = new Gson();
                Log.d("asd","asdaasd "+responseString);
                // responseString 에 JSON 이 한줄로 옴.
                // Parsing (가져온 것을 바꾸는 일) 을 GSON library로!
                result = new Result();
                String ss = responseString.replaceAll("[\n \r]","");
                result = gson.fromJson(ss, Result.class);
                Log.d("Reslut ",ss);
                // gson.fromJson(받은 JSON(responseString) , 변환할객체 클래스.class)
                // >> "객체"가 리턴
                // 객체를 전역으로 설정해두고 사용
                Log.d("custom1","1 " +result.parameter.brightness);
                Log.d("custom2","2 " +result.parameter.sectorId);
                Log.d("custom3","3 " +result.parameter.callId);
                Log.d("custom4","4 " +result.parameter.modeId);
            }
        });
    }
}
