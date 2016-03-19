package com.example.marisayeung.gocook;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.marisayeung.gocook.fragment.DescriptionFragment;
import com.example.marisayeung.gocook.fragment.IngredientFragment;
import com.example.marisayeung.gocook.fragment.PreparationFragment;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;

public class RecipeViewer extends AppCompatActivity
        implements DescriptionFragment.OnFragmentInteractionListener,
                    IngredientFragment.OnFragmentInteractionListener,
                    PreparationFragment.OnFragmentInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private Recipe recipe;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_viewer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        recipe = intent.getParcelableExtra("chosenRecipe");

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        /*
         *  Launch async task to load top image over the network
         */
//        String name = recipe.getImages().get(0);
//        String url = recipe.getImages().get(1);
//        ImageRequestRunnable irr = new ImageRequestRunnable(name, url);
//        irr.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_viewer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDescriptionFragmentInteraction(Uri uri) {
    }

    @Override
    public void onIngredientFragmentInteraction(Uri uri) {
    }

    @Override
    public void onPreparationFragmentInteraction(Uri uri) {
    }

    public static class TabFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public TabFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static TabFragment newInstance(int sectionNumber) {
            TabFragment fragment = new TabFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_recipe_viewer, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a TabFragment (defined as a static inner class below).
//            TODO: instead of passing recipe object, just pass the required peices
            if (getPageTitle(position).equals("OVERVIEW")) {
                return DescriptionFragment.newInstance(recipe);
            } else if (getPageTitle(position).equals("INGREDIENTS")) {
                return IngredientFragment.newInstance(recipe);
            } else if (getPageTitle(position).equals("PREPARATION")) {
                return PreparationFragment.newInstance(recipe);
            }
            return TabFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "OVERVIEW";
                case 1:
                    return "INGREDIENTS";
                case 2:
                    return "PREPARATION";
            }
            return null;
        }
    }

    /*
     * Get recipe image over the network and update ui
     * TODO: Fix FileNotFound exception and then re-enable code in OnCreate to run it
     */
    class ImageRequestRunnable extends AsyncTask<Void, Void, Void> {
        String filename;
        URL url;
        FileOutputStream outputStream;
        BufferedInputStream in;

        public ImageRequestRunnable(String name, String url) {
            filename = name;
            in = null;
            try {
                this.url = new URL(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URLConnection connection = url.openConnection();
                connection.connect();

                in = new BufferedInputStream(connection.getInputStream());
                outputStream = openFileOutput(filename, Context.MODE_PRIVATE);

                final byte data[] = new byte[1024];
                int count;
                while ((count = in.read(data, 0, 1024)) != -1) {
                    outputStream.write(data, 0, count);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mSectionsPagerAdapter.notifyDataSetChanged();
            Log.d("POSTEX", "post execute");
        }
    }
}
