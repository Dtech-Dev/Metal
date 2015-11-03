package com.dtech.posisi;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FrgmInputPelanggaran.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FrgmInputPelanggaran#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FrgmInputPelanggaran extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Spinner spinnerTarif;
    private Spinner spinnerFoulType;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FrgmInputPelanggaran.
     */
    // TODO: Rename and change types and number of parameters
    public static FrgmInputPelanggaran newInstance(String param1, String param2) {
        FrgmInputPelanggaran fragment = new FrgmInputPelanggaran();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FrgmInputPelanggaran() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_input_pelanggaran, container, false);
        setSpinnerTarif(rootView, "");
        setSpinnerFoulType(rootView, "");
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        public void onFragmentInteraction(Uri uri);
    }

    private void setSpinnerTarif(View rootView, String value) {
        // SPINNER
        spinnerTarif = (Spinner) rootView.findViewById(R.id.spinnerTarif);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.array_tarif, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerTarif.setAdapter(adapter);
        int spinnerPosition = 0;
        if (!value.equals(null) || value.length() > 1)
            spinnerPosition = adapter.getPosition(value);
        spinnerTarif.setSelection(spinnerPosition);
    }

    private void setSpinnerFoulType(View rootView, String value) {
        // SPINNER
        spinnerFoulType = (Spinner) rootView.findViewById(R.id.spinnerFoulType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.array_foul_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerFoulType.setAdapter(adapter);
        int spinnerPosition = 0;
        if (!value.equals(null) || value.length() > 1)
            spinnerPosition = adapter.getPosition(value);
        spinnerFoulType.setSelection(spinnerPosition);
    }

}
