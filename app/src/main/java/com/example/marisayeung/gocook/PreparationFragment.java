package com.example.marisayeung.gocook;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PreparationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PreparationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PreparationFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String RECIPE = "recipe";

    private Recipe recipe;

    private OnFragmentInteractionListener mListener;

    public PreparationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param recipe Parameter 1.
     * @return A new instance of fragment PreparationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PreparationFragment newInstance(Recipe recipe) {
        PreparationFragment fragment = new PreparationFragment();
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_preparation, container, false);
        LinearLayout preparationRoot = (LinearLayout) rootView.findViewById(R.id.preparation);
        List<String> steps = recipe.getSteps();
        LinearLayout stepRow;
        if(steps.size() > 0) {
            for (int i = 0; i < steps.size(); i++) {
                Activity activity = getActivity();
                if (activity != null) {
                    stepRow = RecipeViewHelper.displayStep(steps.get(i), i + 1, activity);
                    preparationRoot.addView(stepRow);
                }
            }
        }
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onPreparationFragmentInteraction(uri);
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onPreparationFragmentInteraction(Uri uri);
    }
}
