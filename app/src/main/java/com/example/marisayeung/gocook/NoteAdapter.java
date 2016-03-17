package com.example.marisayeung.gocook;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.evernote.client.android.type.NoteRef;
import com.evernote.edam.type.Note;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marisayeung
 */
public class NoteAdapter extends ArrayAdapter<NoteRef> {
    private List<NoteRef> notes;
    Note note;

    public NoteAdapter(Context context, int resource, List<NoteRef> notes) {
        super(context, resource, notes);
        this.notes = notes;
        Log.d("NOTEADAPTER", "constructed: " + notes.size());
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        NoteRef noteRef = notes.get(position);

        Log.d("NOTEADAPTER", position + ": " + noteRef.getTitle());

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.recipe_list_row, null);

        //        show recipe title
        TextView titleView = (TextView) row.findViewById(R.id.rowTitle);
        String title = noteRef.getTitle();
        titleView.setText(title);
        return row;
    }
}
