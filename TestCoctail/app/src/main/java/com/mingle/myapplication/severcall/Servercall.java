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

    AsyncHttpClient client = new AsyncHttpClient();
    private static final String server_url = "http://223.194.129.160:8080/cocktail";
    Context context;

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

    public void customizeset(final Context context){
        RequestParams params=new RequestParams();
        client.post(context, server_url + "/sectorTest", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("fail", "custom " + statusCode);

                SharedPreferenceUtil.putSharedPreference(context,"CinemaBrightness", 50); //시네마 밝기 값 일단 디폴트값으로 조정.
                SharedPreferenceUtil.putSharedPreference(context,"CinemaRingerMode", 1); //시네마 모드 값을 디폴트로 조정.
                SharedPreferenceUtil.putSharedPreference(context, "CinemaChecked", 1);

                SharedPreferenceUtil.putSharedPreference(context,"ExhibitionBrightness", 155);
                SharedPreferenceUtil.putSharedPreference(context, "ExhibitionRingerMode", 1);
                SharedPreferenceUtil.putSharedPreference(context, "ExhibitionPopup", 1);


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

                    Log.d("NDG", "" + p.sectorId);
                    Log.d("NDG", "" + p.brightness);
                    Log.d("NDG ", "" + p.callId);
                    Log.d("NDG ", "" + p.modeId);

                    if (p.sectorId.equals("cinema")) {
                        SharedPreferenceUtil.putSharedPreference(context, "CinemaDefaultBrightness",p.brightness); //디폴트값
                        SharedPreferenceUtil.putSharedPreference(context, "CinemaDefaultModeId",p.modeId);
                        SharedPreferenceUtil.putSharedPreference(context, "CinemaDefaultCallId",p.callId);

                        SharedPreferenceUtil.putSharedPreference(context, "CinemaBrightness",p.brightness); //시네마 밝기 값 일단 디폴트값으로 조정.
                        SharedPreferenceUtil.putSharedPreference(context, "CinemaRingerMode",p.modeId); //시네마 모드 값을 디폴트로 조정.
                        SharedPreferenceUtil.putSharedPreference(context, "CinemaChecked", p.callId);
                        //put
                    } else if (p.sectorId.equals("exhibition")) {
                        SharedPreferenceUtil.putSharedPreference(context, "ExhibitionDefaultBrightness",p.brightness);
                        SharedPreferenceUtil.putSharedPreference(context, "ExhibitionDefaultModeId",p.modeId);
                        SharedPreferenceUtil.putSharedPreference(context, "ExhibitionDefaultCallId",p.callId);

                        SharedPreferenceUtil.putSharedPreference(context, "ExhibitionBrightness",p.brightness);
                        SharedPreferenceUtil.putSharedPreference(context, "ExhibitionRingerMode",p.modeId);
                        SharedPreferenceUtil.putSharedPreference(context, "ExhibitionPopup", p.callId);
                    } else if (p.sectorId.equals("library")){
                        SharedPreferenceUtil.putSharedPreference(context, "LibraryDefaultBrightness",p.brightness);
                        SharedPreferenceUtil.putSharedPreference(context, "LibraryDefaultCallId",p.callId);
                        SharedPreferenceUtil.putSharedPreference(context, "LibraryDefaultModeId",p.modeId);
                        // put
                    } else {
                        Log.d("customizeset", "Resion not yet");
                    }
                }
            }
        });
    }

    public void usersettinginfo(Context context, String userId,String sectorId,int brightness,int modeId,int callId) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("sectorId", sectorId);
        params.put("brightness", brightness);
        params.put("modeId", modeId);
        params.put("callId", callId);
        Log.d("setting", "닉네임 " + userId);
        Log.d("setting", "섹터값 " + sectorId);
        Log.d("setting", "밝기 " + brightness);
        Log.d("setting", "모드값 " + modeId);
        Log.d("setting", "전화수신 " + callId);
        client.post(context, server_url + "/app/insertParameter", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("setting", "fail");
                Log.d("setting" + statusCode, "fail");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("setting", "success");
                Log.d("setting", "success " + statusCode);
            }
        });
    }
}
