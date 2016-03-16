package com.example.marisayeung.gocook.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.evernote.client.android.type.NoteRef;
import com.evernote.edam.type.Notebook;
import com.example.marisayeung.gocook.EvernoteList;
import com.example.marisayeung.gocook.R;
import com.example.marisayeung.gocook.RecipeAdapter;
import com.example.marisayeung.gocook.task.FindNotesTask;

import net.vrallev.android.task.TaskResult;

import java.util.List;

/**
 *
 * From Evernote SDK with modifications for GoCook functionality
 * @author rwondratschek
 * https://github.com/evernote/evernote-sdk-android/
 *
 * edited by Marisa Yeung
 */
public class RecipeContainerFragment extends AbstractContainerFragment {
    private static final int MAX_NOTES = 20;
    private static final String KEY_NOTEBOOK = "KEY_NOTEBOOK";

    private List<NoteRef> mNoteRefList;

    private ListView listview;
    private RecipeAdapter adapter;

    public static RecipeContainerFragment create() {
        return create(null);
    }

    public static RecipeContainerFragment create(@Nullable Notebook notebook) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_NOTEBOOK, notebook);

        RecipeContainerFragment fragment = new RecipeContainerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private Notebook mNotebook;
    private String mQuery;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

//        setContentView(R.layout.activity_recipe_list);

        mNotebook = (Notebook) getArguments().getSerializable(KEY_NOTEBOOK);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentActivity activity = getActivity();
        if (activity instanceof EvernoteList) {
            ((EvernoteList) activity).getSupportActionBar().setTitle(R.string.notes);
        }
    }

    protected void loadData() {
        new FindNotesTask(0, MAX_NOTES, mNotebook, mQuery).start(this);
        mQuery = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_note_container, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                new SearchQueryDialogFragment().show(getChildFragmentManager(), SearchQueryDialogFragment.TAG);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @TaskResult
    public void onFindNotes(List<NoteRef> noteRefList) {
        mNoteRefList = noteRefList;
        mSwipeRefreshLayout.setRefreshing(false);

        if (noteRefList == null || noteRefList.isEmpty()) {
            Log.d("NOTEREFLIST", "empty notes");

        } else {
            Log.d("NOTEREFLIST", "notes here");

        }
    }

    public void search(String query) {
        mQuery = query;
        refresh();
    }
}
