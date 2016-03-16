package com.example.marisayeung.gocook.example;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marisayeung.gocook.R;

/**
 *
 * From Evernote SDK with modifications for GoCook functionality
 * @author rwondratschek
 * https://github.com/evernote/evernote-sdk-android/
 *
 * edited by Marisa Yeung
 */
public abstract class AbstractContainerFragment extends Fragment {

    protected SwipeRefreshLayout mSwipeRefreshLayout;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_abstract_container, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        view.findViewById(R.id.fragment_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        if (savedInstanceState == null) {
            mSwipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    refresh();
                }
            }, 200L);
        }

        return view;
    }

    public void refresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        loadData();
    }

    protected abstract void loadData();

    private final SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            refresh();
        }
    };
}

