package com.example.marisayeung.gocook;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.evernote.client.android.EvernoteSession;
import com.evernote.client.android.type.NoteRef;
import com.evernote.edam.type.Notebook;
import com.evernote.edam.type.User;
import com.example.marisayeung.gocook.task.FindNotesTask;
import com.example.marisayeung.gocook.task.GetNoteHtmlTask;
import com.example.marisayeung.gocook.task.GetUserTask;

import net.vrallev.android.task.TaskResult;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evernote_list);

        if (!EvernoteSession.getInstance().isLoggedIn()) {
            // LoginChecker will call finish
            return;
        }

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

        // get nav drawer header for later
        View headerView = navigationView.inflateHeaderView(R.layout.nav_drawer_header);
        mTextViewUserName = (TextView) headerView.findViewById(R.id.textView_user_name);

        // set up tool bar toggle button for nav drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        mDrawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        /*
         *  User not returning (but logged in) show default selection and get user
         */
        if (savedInstanceState == null) {
            showItem(mSelectedNavItem);
            new GetUserTask().start(this);
        } else if (mUser != null) {
            onGetUser(mUser);
        }
        Log.d("HELLO", "6");

        // Prep listview adapter
        noteAdapter = new NoteAdapter(this, R.layout.recipe_list_row, mNoteRefList);

        // List click listener
        mListView.setAdapter(noteAdapter);
        mListView.setOnItemClickListener((new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                // call start with ref to this class and the TaskResult tag
                new GetNoteHtmlTask(mNoteRefList.get(position)).start(EvernoteList.this, "html");
            }
        }));
        refresh();
    }

    /*
        Update username in nav drawer
     */
    @TaskResult
    public void onGetUser(User user) {
        mUser = user;
        if (user != null) {
            mTextViewUserName.setText(mUser.getUsername());
        }
    }

    /*public static void longInfo(String str) {
        if(str.length() > 2000) {
            Log.i("HTML", str.substring(0, 2000));
            longInfo(str.substring(2000));
        } else
            Log.i("HTML", str);
    }*/

    /*
        Parse html into a recipe object, load recipe viewer
     */
    @TaskResult(id = "html")
    public void onGetNoteContentHtml(String html, GetNoteHtmlTask task) {
        RecipeParser r = new RecipeParser();

        if (r.parseNYTimesRecipe(html, false)) {
            startViewer(r.getRecipe());
        }
    }

    /*
        Load recipe viewer with the recipe
     */
    private void startViewer(Recipe recipe) {
        Intent intent = new Intent(this, RecipeViewer.class);
        intent.putExtra("chosenRecipe", recipe);
        startActivity(intent);
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

    /*
        Load list items based on nav menu item id
     */
    private void showItem(int navItemId) {
        switch (navItemId) {
            case R.id.nav_item_notes:
                loadData();
                break;

            default:
                throw new IllegalStateException("not implemented");
        }
    }

    /*
        Get notes
     */
    protected void loadData() {
        new FindNotesTask(0, MAX_NOTES, mNotebook, mQuery).start(this);
        mQuery = null;
    }

    /*
        Find notes task
     */
    @TaskResult
    public void onFindNotes(List<NoteRef> noteRefList) {

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
        loadData();
    }

    /*public String readHtmlFromFile(String in) {
        BufferedReader bufferedReader = null;
        String html = "";
        try {
            StringBuilder stringBuilder = new StringBuilder();
            InputStream htmlStream = getAssets().open(in);
            bufferedReader = new BufferedReader(new InputStreamReader(htmlStream, "UTF-8"));

            int ch;
            while ((ch = bufferedReader.read()) != -1) {
                stringBuilder.append((char) ch);
            }
            html = stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return html;
    } */
}
