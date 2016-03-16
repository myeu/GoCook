package com.example.marisayeung.gocook;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.evernote.client.android.EvernoteSession;
import com.evernote.client.android.type.NoteRef;
import com.evernote.edam.type.Notebook;
import com.evernote.edam.type.User;
import com.example.marisayeung.gocook.task.FindNotesTask;
import com.example.marisayeung.gocook.task.GetUserTask;

import net.vrallev.android.task.TaskResult;

import java.util.ArrayList;
import java.util.List;

public class EvernoteList extends AppCompatActivity {

    private static final String KEY_SELECTED_NAV_ITEM = "KEY_SELECTED_NAV_ITEM";
    private static final String KEY_USER = "KEY_USER";

    private DrawerLayout mDrawerLayout;
    private TextView mTextViewUserName;
    private int mSelectedNavItem;
    private User mUser;

    private static final int MAX_NOTES = 20;
    private Notebook mNotebook;
    private String mQuery;

    private List<NoteRef> mNoteRefList;
    private List<String> mStringRefList;
    private ListView mListView;
    private TextView emptyList;
    NoteAdapter noteAdapter;

//    protected SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evernote_list);

        if (!EvernoteSession.getInstance().isLoggedIn()) {
            // LoginChecker will call finish
            return;
        }
        Log.d("HELLO", "1");

        Toolbar toolbar = (Toolbar) findViewById(R.id.evernote_list_toolbar);
        setSupportActionBar(toolbar);

        mNoteRefList = new ArrayList<>();

//        mStringRefList = new ArrayList<>();

        mListView = (ListView) findViewById(R.id.notes_list_view);
        emptyList = (TextView) findViewById(R.id.empty_notes);

        /*
         *  If user returning, get previous nav item selection
         */
        mSelectedNavItem = R.id.nav_item_notes;
        if (savedInstanceState != null) {
            mSelectedNavItem = savedInstanceState.getInt(KEY_SELECTED_NAV_ITEM, mSelectedNavItem);
            mUser = (User) savedInstanceState.getSerializable(KEY_USER);
        }
        Log.d("HELLO", "2");

        // Set listener for nav menu items
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                onNavDrawerItemClick(menuItem.getItemId());
                return true;
            }
        });
        navigationView.getMenu().findItem(mSelectedNavItem).setChecked(true);
        Log.d("HELLO", "3");

        // get nav drawer header for later
        View headerView = navigationView.inflateHeaderView(R.layout.nav_drawer_header);
        mTextViewUserName = (TextView) headerView.findViewById(R.id.textView_user_name);
        Log.d("HELLO", "4");

        // set up tool bar toggle button for nav drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        mDrawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
        Log.d("HELLO", "5");

        /*
         *  User not returning (but logged in) show default selection and get user
         */
        if (savedInstanceState == null) {
            showItem(mSelectedNavItem);
            new GetUserTask().start(this);
        } else if (mUser != null) {
            Log.d("TRIGGER", "manual onGetUser call");
            onGetUser(mUser);
        }
        Log.d("HELLO", "6");

        // Prep listview adapter
        noteAdapter = new NoteAdapter(this, R.layout.recipe_list_row, mNoteRefList);
//        noteAdapter = new NoteAdapter(this, R.layout.recipe_list_row, mStringRefList);

        mListView.setAdapter(noteAdapter);
        Log.d("ONCREATE", "created note adapter");
    }

    @TaskResult
    public void onGetUser(User user) {
        mUser = user;
        if (user != null) {
            mTextViewUserName.setText(mUser.getUsername());
        }
    }

    /*
     *  Navigation bar item selection made, save selection and load the data
     */
    private void onNavDrawerItemClick(int navItemId) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        mSelectedNavItem = navItemId;
        // load data
        showItem(navItemId);
    }

    private void showItem(int navItemId) {
        switch (navItemId) {
            case R.id.nav_item_notes:
                loadData();
                break;

            case R.id.nav_item_favorites:
                loadData();

            default:
                throw new IllegalStateException("not implemented");
        }
    }

    protected void loadData() {
        Log.d("LOADING", "" + mQuery);
        new FindNotesTask(0, MAX_NOTES, mNotebook, mQuery).start(this);
        mQuery = null;
    }

    @TaskResult
    public void onFindNotes(List<NoteRef> noteRefList) {
        Log.d("ONFIND", "" + noteRefList.size());

        if (emptyList != null) {
            if (noteRefList.size() > 0) {
                emptyList.setVisibility(View.GONE);
                mListView.setVisibility(View.VISIBLE);
            } else {
                emptyList.setVisibility(View.VISIBLE);
                mListView.setVisibility(View.GONE);
            }
        }

        mNoteRefList.clear();
        for (NoteRef noteRef: noteRefList) {
            mNoteRefList.add(noteRef);
//            mStringRefList.add(noteRef.getTitle());
            noteAdapter.notifyDataSetChanged();
        }
    }

    /*
     *  Save the currently viewed nav bar item
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SELECTED_NAV_ITEM, mSelectedNavItem);
        outState.putSerializable(KEY_USER, mUser);
    }

    public void search(String query) {
        mQuery = query;
        refresh();
    }

    public void refresh() {
//        mSwipeRefreshLayout.setRefreshing(true);
        loadData();
    }
}
