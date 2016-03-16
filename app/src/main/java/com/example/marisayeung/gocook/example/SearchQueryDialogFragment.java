package com.example.marisayeung.gocook.example;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.example.marisayeung.gocook.R;

/**
 *
 * From Evernote SDK with modifications for GoCook functionality
 * @author rwondratschek
 * https://github.com/evernote/evernote-sdk-android/
 *
 * edited by Marisa Yeung
 */
public class SearchQueryDialogFragment extends DialogFragment {

    public static final String TAG = "SearchQueryDialogFragment";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_query, null);
        final TextInputLayout textIn = (TextInputLayout) view.findViewById(R.id.text_input);

        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        if (getParentFragment() instanceof RecipeContainerFragment) {
                            ((RecipeContainerFragment) getParentFragment()).search(textIn.getEditText().getText().toString());
                        } else {
                            throw new IllegalStateException();
                        }
                        break;
                }
            }
        };

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.search)
                .setView(view)
                .setPositiveButton(android.R.string.ok, onClickListener)
                .setNegativeButton(android.R.string.cancel, onClickListener)
                .create();
    }
}
