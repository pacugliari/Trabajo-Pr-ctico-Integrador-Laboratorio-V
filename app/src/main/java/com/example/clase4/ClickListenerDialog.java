package com.example.clase4;

import android.content.DialogInterface;

import androidx.fragment.app.DialogFragment;

public class ClickListenerDialog implements DialogInterface.OnClickListener {

    @Override
    public void onClick(DialogInterface dialog, int which) {
        //which cuando las posicione son > 0 es la posicion del item del dialog message

        if(which== DialogInterface.BUTTON_POSITIVE){

        }else if (which== DialogInterface.BUTTON_NEGATIVE){

        }else if (which== DialogInterface.BUTTON_NEUTRAL) {

        }
    }
}
