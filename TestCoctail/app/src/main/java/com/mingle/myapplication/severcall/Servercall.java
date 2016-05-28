package com.mingle.myapplication.severcall;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.mingle.myapplication.Parameter;
import com.mingle.myapplication.Result;
import com.mingle.myapplication.model.SharedPreferenceUtil;

import cz.msebera.android.httpclient.Header;
/**
 * Created by multimedia on 2016-05-23.
 */
public class Servercall extends AppCompatActivity {
    Gson gson;
    Result result;
    Parameter p;

    AsyncHttpClient client = new AsyncHttpClient();
    private static final String server_url = "http://113.198.84.87:8080/cocktail";

    public void postResioninfo(Context context, String id){
        RequestParams params=new RequestParams();
        params.put("sectorId",id);
        client.post(context, server_url + "/app/incUseCount", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("resion","fail");
                Log.d("resion","fail "+statusCode);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("resion","success1");
                Log.d("resion","success2 "+responseString );
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
    public void customizeset(final Context context){
        RequestParams params=new RequestParams();
        client.post(context, server_url + "/sectorTest", params, new TextHttpResponseHandler() {
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
                for(Parameter p : result.parameter) {
                    Log.d("NDG ", "" + p.sectorId);
                    Log.d("NDG", "" + p.brightness);
                    Log.d("NDG ", "" + p.callId);
                    Log.d("NDG ", "" + p.modeId);
                    if (p.sectorId.equals("cinema")) {
                        SharedPreferenceUtil.putSharedPreference(context,"CinemaDefaultBrightness",p.brightness); //디폴트값
                        SharedPreferenceUtil.putSharedPreference(context,"CinemaBrightness",p.brightness); //시네마 밝기 값 일단 디폴트값으로 조정.
                        SharedPreferenceUtil.putSharedPreference(context,"CinemaDefaultModeId",p.modeId);
                        SharedPreferenceUtil.putSharedPreference(context,"CinemaRingerMode",p.modeId); //시네마 모드 값을 디폴트로 조정.
                        SharedPreferenceUtil.putSharedPreference(context,"CinemaDefaultCallId",p.callId);
                        //put
                    } else if (p.sectorId.equals("exhibition")) {
                        SharedPreferenceUtil.putSharedPreference(context,"ExhibitionDefaultBrightness",p.brightness);
                        SharedPreferenceUtil.putSharedPreference(context,"ExhibitionBrightness",p.brightness);
                        SharedPreferenceUtil.putSharedPreference(context,"ExhibitionDefaultCallId",p.callId);
                        SharedPreferenceUtil.putSharedPreference(context,"ExhibitionDefaultModeId",p.modeId);
                        SharedPreferenceUtil.putSharedPreference(context,"ExhibitionRingerMode",p.modeId);
                    } else {
                         SharedPreferenceUtil.putSharedPreference(context,"LibraryDefaultBrightness",p.brightness);
                        SharedPreferenceUtil.putSharedPreference(context,"LibraryDefaultCallId",p.callId);
                        SharedPreferenceUtil.putSharedPreference(context,"LibraryDefaultModeId",p.modeId);
                        // put
                    }
                }
            }
        });
    }
}
