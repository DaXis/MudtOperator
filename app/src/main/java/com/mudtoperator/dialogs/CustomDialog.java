package com.mudtoperator.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mudtoperator.R;

public class CustomDialog extends DialogFragment implements View.OnClickListener {

    private String title, body, action;

    public static CustomDialog newInstance(String title, String body, String action){
        CustomDialog custoDialog = new CustomDialog();

        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("body", body);
        bundle.putString("action", action);
        custoDialog.setArguments(bundle);

        return custoDialog;
    }

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        int style = DialogFragment.STYLE_NORMAL;
        int theme = android.R.style.Theme_Holo;

        title = getArguments().getString("title");
        body = getArguments().getString("body");
        action = getArguments().getString("action");

        setStyle(style, theme);
    }

    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.custom_dialog, null);
        builder.setView(v);

        TextView titleTv = (TextView)v.findViewById(R.id.title);
        titleTv.setText(title);

        TextView bodyTv = (TextView)v.findViewById(R.id.body);
        bodyTv.setText(body);

        Button actionBtn = (Button)v.findViewById(R.id.action);
        actionBtn.setText(action);
        actionBtn.setOnClickListener(this);

        return builder.create();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.action:
                dismiss();
                break;
        }
    }

}
