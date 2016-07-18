package com.example.marisayeung.gocook.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.marisayeung.gocook.R;
import com.example.marisayeung.gocook.Recipe;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DescriptionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DescriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DescriptionFragment extends Fragment {
    private static final String RECIPE = "recipe";

    private Recipe recipe;

    private OnFragmentInteractionListener mListener;

    public DescriptionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param recipe Parameter 1.
     * @return A new instance of fragment DescriptionFragment.
     */
    public static DescriptionFragment newInstance(Recipe recipe) {
        DescriptionFragment fragment = new DescriptionFragment();
        Bundle args = new Bundle();
        args.putParcelable(RECIPE, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipe = getArguments().getParcelable(RECIPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_description, container, false);
        LinearLayout overview = (LinearLayout) rootView.findViewById(R.id.overview);

        TextView title, author, yield, time, description;
        Context context = getActivity();
        if (context != null) {
            title = new TextView(context);
            title.setText(recipe.getTitle());
            title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 32);
            overview.addView(title);

            ImageView topPhoto = new ImageView(context);
            List<String> images = recipe.getImages();

/*            try {
                if (images.size() > 1) {
                    String name = recipe.getImages().get(0);
//                    Drawable image = Drawable.createFromStream(context.getAssets().open(images.get(0)), null);
//                    Drawable image = getFileStreamPath(fileName);
                    FileInputStream fis = getActivity().openFileInput(name);
//                    Drawable image = Drawable.createFromStream(fis);
                    if (fis != null) {
                        Bitmap b = BitmapFactory.decodeStream(fis);
                        fis.close();
                        topPhoto.setImageBitmap(b);
                        overview.addView(topPhoto);
                    }
                } else if (images.size() > 1) {
                    // TODO: add image view elements to content xml
                }
            } catch (IOException e) {
                e.printStackTrace();
            }*/

            if (!recipe.getAuthor().equals("")) {
                author = new TextView(context);
                author.setText(recipe.getAuthor());
                author.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
                author.setPadding(0, 0, 0, 10);
                overview.addView(author);
            }

            if (!recipe.getYield().equals("")) {
                yield = new TextView(context);
                yield.setText(recipe.getYield());
                yield.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                yield.setPadding(0, 10, 0, 10);
                overview.addView(yield);
            }

            time = new TextView(context);
            time.setText(recipe.getTime());
            time.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            time.setPadding(0, 10, 0, 10);
            overview.addView(time);

            description = new TextView(context);
            description.setText(recipe.getDescription());
            description.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            description.setLineSpacing(0, 1.5F);
            description.setPadding(0, 10, 0, 20);
            overview.addView(description);
        }
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onDescriptionFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onDescriptionFragmentInteraction(Uri uri);
    }

    private void updateTextView(String newText, View container, int id) {
        if (!newText.equals("")) {
            TextView view = (TextView) container.findViewById(id);
            view.setText(newText);
        }
    }
}
