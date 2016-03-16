package com.example.marisayeung.gocook.task;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;

import com.evernote.client.android.EvernoteSession;
import com.evernote.client.android.helper.Cat;
import com.evernote.edam.error.EDAMUserException;

import com.example.marisayeung.gocook.EvernoteLogin;
import com.example.marisayeung.gocook.R;

import net.vrallev.android.task.Task;
import net.vrallev.android.task.TaskExecutor;

import java.util.concurrent.Executors;

/**
 *
 * From Evernote SDK with modifications for GoCook functionality
 * @author rwondratschek
 * https://github.com/evernote/evernote-sdk-android/
 *
 * edited by Marisa Yeung
 */

public abstract class BaseTask<RESULT> extends Task<RESULT> {

    private static final Cat CAT = new Cat("BaseTask");

    private static final TaskExecutor TASK_EXECUTOR = new TaskExecutor.Builder()
            .setExecutorService(Executors.newFixedThreadPool(12))
            .build();

    private final Class<RESULT> mResultClass;

    public BaseTask(Class<RESULT> resultClass) {
        mResultClass = resultClass;
    }

    public void start(Activity activity) {
        TASK_EXECUTOR.execute(this, activity);
    }

    public void start(Activity activity, String annotationId) {
        TASK_EXECUTOR.execute(this, activity, annotationId);
    }

    public void start(Fragment fragment) {
        TASK_EXECUTOR.execute(this, fragment);
    }

    public void start(Fragment fragment, String annotationId) {
        TASK_EXECUTOR.execute(this, fragment, annotationId);
    }

    @Override
    protected final RESULT execute() {
        try {
            return checkedExecute();
        } catch (Exception e) {
            CAT.e(e);
            checkException(e, getActivity());
            return null;
        }
    }

    protected abstract RESULT checkedExecute() throws Exception;

    @Override
    protected final Class<RESULT> getResultClass() {
        return mResultClass;
    }

    protected static void checkException(@NonNull final Exception e, @Nullable final Activity activity) {
        if (e instanceof EDAMUserException) {
            switch (((EDAMUserException) e).getErrorCode()) {
                case AUTH_EXPIRED:
                    if (activity != null) {
                        EvernoteSession.getInstance().logOut();
                        EvernoteLogin.launch(activity);
                        activity.finish();
                    }
                    break;

                case PERMISSION_DENIED:
                    if (activity != null) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                View view = activity.findViewById(android.R.id.content);

                                final Snackbar snackbar = Snackbar.make(view, ((EDAMUserException) e).getErrorCode().toString(), Snackbar.LENGTH_INDEFINITE);
                                snackbar.setActionTextColor(activity.getResources().getColor(R.color.colorSecondaryText))
                                        .setAction(android.R.string.ok, new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                snackbar.dismiss();
                                            }
                                        }).show();
                            }
                        });
                    }
                    break;
            }
        }
    }
}

