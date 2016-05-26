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

import com.mingle.myapplication.severcall.Servercall;

/**
 * Created by multimedia on 2016-05-23.
 */
public class DialogCall extends android.app.DialogFragment {
    public EditText editNick;
    public String NickNick;
    Servercall servercall;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogtest, null);

        editNick=(EditText)view.findViewById(R.id.NickName);

        NickNick=editNick.getText().toString();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Sign in",
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int id)
                            {
                                servercall=new Servercall();
                                servercall.confirm(getActivity(),NickNick);
                            }
                        }).setNegativeButton("Cancel", null);
        return builder.create();
    }
}


