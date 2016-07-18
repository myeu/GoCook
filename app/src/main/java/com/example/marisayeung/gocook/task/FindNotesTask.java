package com.example.marisayeung.gocook.task;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.evernote.client.android.EvernoteSession;
import com.evernote.client.android.asyncclient.EvernoteSearchHelper;
import com.evernote.client.android.type.NoteRef;
import com.evernote.edam.notestore.NoteFilter;
import com.evernote.edam.type.NoteSortOrder;
import com.evernote.edam.type.Notebook;

import java.util.List;

/**
 *
 * @author rwondratschek
 * https://github.com/evernote/evernote-sdk-android/
 *
 */
public class FindNotesTask extends BaseTask<List<NoteRef>>{

    private final EvernoteSearchHelper.Search mSearch;

    public FindNotesTask(int offset, int maxNotes, @Nullable Notebook notebook, @Nullable String query) {
        super((Class) List.class);

        NoteFilter noteFilter = new NoteFilter();
        noteFilter.setOrder(NoteSortOrder.UPDATED.getValue());

        if (!TextUtils.isEmpty(query)) {
            noteFilter.setWords(query);
        }

        if (notebook != null) {
            noteFilter.setNotebookGuid(notebook.getGuid());
        }

        mSearch = new EvernoteSearchHelper.Search()
                .setOffset(offset)
                .setMaxNotes(maxNotes)
                .setNoteFilter(noteFilter)
                .addScope(EvernoteSearchHelper.Scope.PERSONAL_NOTES);
    }

    @Override
    protected List<NoteRef> checkedExecute() throws Exception {
        EvernoteSearchHelper.Result searchResult = EvernoteSession.getInstance()
                .getEvernoteClientFactory()
                .getEvernoteSearchHelper()
                .execute(mSearch);

        return searchResult.getPersonalResultsAsNoteRef();
    }
}
