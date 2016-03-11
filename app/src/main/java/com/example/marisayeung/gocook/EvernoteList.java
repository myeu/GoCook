package com.example.marisayeung.gocook;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.evernote.client.android.EvernoteSession;
import com.evernote.edam.type.User;
import com.example.marisayeung.gocook.task.GetUserTask;

import net.vrallev.android.task.TaskResult;

public class EvernoteList extends AppCompatActivity {

    private static final String KEY_SELECTED_NAV_ITEM = "KEY_SELECTED_NAV_ITEM";
    private static final String KEY_USER = "KEY_USER";

    private DrawerLayout mDrawerLayout;
    private TextView mTextViewUserName;

    private int mSelectedNavItem;

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EvernoteSession.getInstance().isLoggedIn()) {
            // LoginChecker will call finish
            return;
        }

        setContentView(R.layout.activity_evernote_list);
        Resources resources = getResources();

        mSelectedNavItem = R.id.nav_item_notes;
        if (savedInstanceState != null) {
            mSelectedNavItem = savedInstanceState.getInt(KEY_SELECTED_NAV_ITEM, mSelectedNavItem);
            mUser = (User) savedInstanceState.getSerializable(KEY_USER);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);

        mDrawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

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

        View headerView = navigationView.inflateHeaderView(R.layout.nav_drawer_header);
        mTextViewUserName = (TextView) headerView.findViewById(R.id.textView_user_name);

        if (savedInstanceState == null) {
            showItem(mSelectedNavItem);
            new GetUserTask().start(this);
        } else if (mUser != null) {
            onGetUser(mUser);
        }
    }

    @TaskResult
    public void onGetUser(User user) {
        mUser = user;
        if (user != null) {
            mTextViewUserName.setText(user.getUsername());
        }
    }

    private void onNavDrawerItemClick(int navItemId) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        mSelectedNavItem = navItemId;

        showItem(navItemId);
    }

    private void showItem(int navItemId) {
        switch (navItemId) {
            case R.id.nav_item_notes:
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_container, NoteContainerFragment.create())
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                        .commit();

                break;

            case R.id.nav_item_notebooks:
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_container, new NotebookTabsFragment())
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                        .commit();

            default:
                throw new IllegalStateException("not implemented");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SELECTED_NAV_ITEM, mSelectedNavItem);
        outState.putSerializable(KEY_USER, mUser);
    }
}
