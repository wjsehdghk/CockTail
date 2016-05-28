package com.mingle.myapplication;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.mingle.myapplication.model.SharedPreferenceUtil;
import com.mingle.myapplication.severcall.Servercall;

/**
 * Created by multimedia on 2016-05-23.
 */
public class DialogCall extends android.app.DialogFragment {
    public EditText editNick;
    Servercall servercall;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogtest, null);
        editNick=(EditText)view.findViewById(R.id.NickName);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("확인",
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int id)
                            {
                                servercall=new Servercall();
                                SharedPreferenceUtil.putSharedPreference(getActivity(),"UserNickname",editNick.getText().toString()); //닉네임값을 저장.
                                Log.d("jeon","Nicknametest"+SharedPreferenceUtil.getSharedPreference2(getActivity(),"UserNickname"));
                                servercall.confirm(getActivity(),SharedPreferenceUtil.getSharedPreference2(getActivity(),"UserNickname")); //닉네임값 서버에 보내기.
                            }
                        }).setNegativeButton("취소", null);
        return builder.create();
    }
}


