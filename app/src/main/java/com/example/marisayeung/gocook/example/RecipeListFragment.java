package com.example.marisayeung.gocook.example;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.evernote.client.android.type.NoteRef;

import java.util.List;

/**
 * Created by marisayeung on 3/11/16.
 */
public class RecipeListFragment extends Fragment {

    private static final String KEY_NOTE_LIST = "KEY_NOTE_LIST";

    public static RecipeListFragment create(List<NoteRef> noteRefList) {
        Bundle args = new Bundle();
        //ParcelableUtil.putParcelableList(args, noteRefList, KEY_NOTE_LIST);

        RecipeListFragment fragment = new RecipeListFragment();
        //fragment.setArguments(args);
        return fragment;
    }
}
