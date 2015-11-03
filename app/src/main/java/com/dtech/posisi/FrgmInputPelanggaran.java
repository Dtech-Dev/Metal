package com.dtech.posisi;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.dtech.orm.DefaultOps;

import java.util.Calendar;


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
    private EditText etFoulDate;
    private EditText etFoulDaya;
    private View rootView;

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
        if (rootView != null)
            return rootView;
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_input_pelanggaran, container, false);
        setSpinnerTarif(rootView, "");
        setSpinnerFoulType(rootView, "");
        setFoulDate(rootView, "");
        setFoulDaya(rootView, "");
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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

    public Spinner getSpinnerTarif() {
        return spinnerTarif;
    }

    public void setSpinnerTarif(Spinner spinnerTarif) {
        this.spinnerTarif = spinnerTarif;
    }

    public Spinner getSpinnerFoulType() {
        return spinnerFoulType;
    }

    public void setSpinnerFoulType(Spinner spinnerFoulType) {
        this.spinnerFoulType = spinnerFoulType;
    }

    public EditText getEtFoulDate() {
        return etFoulDate;
    }

    public void setEtFoulDate(EditText etFoulDate) {
        this.etFoulDate = etFoulDate;
    }

    public EditText getEtFoulDaya() {
        return etFoulDaya;
    }

    public void setEtFoulDaya(EditText etFoulDaya) {
        this.etFoulDaya = etFoulDaya;
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
        setSpinnerTarif((Spinner) rootView.findViewById(R.id.spinnerTarif));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.array_tarif, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        getSpinnerTarif().setAdapter(adapter);
        int spinnerPosition = 0;
        if (!value.equals(null) || value.length() > 1)
            spinnerPosition = adapter.getPosition(value);
        getSpinnerTarif().setSelection(spinnerPosition);
        getSpinnerTarif().setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((ActvtMainInput) getActivity())
                                .onTextChanged("foulTariff", parent.getItemAtPosition(position)
                                        .toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );
    }

    private void setSpinnerFoulType(View rootView, String value) {
        // SPINNER
        setSpinnerFoulType((Spinner) rootView.findViewById(R.id.spinnerFoulType));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.array_foul_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        getSpinnerFoulType().setAdapter(adapter);
        int spinnerPosition = 0;
        if (!value.equals(null) || value.length() > 1)
            spinnerPosition = adapter.getPosition(value);
        getSpinnerFoulType().setSelection(spinnerPosition);
        getSpinnerFoulType().setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((ActvtMainInput) getActivity())
                                .onTextChanged("foulType", parent.getItemAtPosition(position)
                                        .toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );
    }

    private void setFoulDate(View rootView, String value) {
        setEtFoulDate((EditText) rootView.findViewById(R.id.etTanggal));
        String xValue = (value == null || value.length() <= 0) ?
                DefaultOps.dateToString(Calendar.getInstance().getTime()
                , DefaultOps.DEFAULT_DATE_FORMAT) :
                value;
        getEtFoulDate().setText(xValue);
        ((ActvtMainInput) getActivity()).onTextChanged("foulDate", xValue);
        getEtFoulDate().addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        ((ActvtMainInput) getActivity()).onTextChanged("foulDate", s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                }
        );
    }

    private void setFoulDaya(View rootView, String value) {
        setEtFoulDaya((EditText) rootView.findViewById(R.id.etDaya));
        getEtFoulDaya().setText(value);
        getEtFoulDaya().addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        ((ActvtMainInput) getActivity()).onTextChanged("foulDaya", s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                }
        );
    }

}
