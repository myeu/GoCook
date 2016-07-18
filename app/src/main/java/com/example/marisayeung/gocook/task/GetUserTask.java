package com.example.marisayeung.gocook.task;

import com.evernote.edam.type.User;
import com.evernote.client.android.EvernoteSession;

/**
 *
 * @author rwondratschek
 * https://github.com/evernote/evernote-sdk-android/
 *
 */

public class GetUserTask extends BaseTask<User> {
    public GetUserTask() {
        super(User.class);
    }

    @Override
    protected User checkedExecute() throws Exception {
        return EvernoteSession.getInstance().getEvernoteClientFactory().getUserStoreClient().getUser();
    }
}
