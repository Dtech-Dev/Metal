package com.dtech.posisi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.dtech.orm.DefaultOps;

import java.io.File;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FrgmnInputImages.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FrgmnInputImages#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FrgmnInputImages extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int TAKE_PHOTO_CODE = 1;
    private static final int SELECT_PICTURE = 2;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ImageButton imgButton;
    private GridView gvImage;
    private int width;
    private int height;
    private int count = 0;
    Uri outputFileUri;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FrgmnInputImages.
     */
    // TODO: Rename and change types and number of parameters
    public static FrgmnInputImages newInstance(String param1, String param2) {
        FrgmnInputImages fragment = new FrgmnInputImages();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FrgmnInputImages() {
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
        View rootView = inflater.inflate(R.layout.fragment_input_images, container, false);
        setTextView();
        gvImage = (GridView) rootView.findViewById(R.id.gridViewImage);
        gvImage.setAdapter(new ImageAdapter(getContext()));
//        setImageButton(rootView, gvImage, 0);
        return rootView;
    }

    private void setImageButton(View rootView, GridView gvImage, int i) {
        imgButton = new ImageButton(getContext());
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

    private  void setTextView() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        width = displaymetrics.widthPixels;
        height = displaymetrics.heightPixels;
    }
    public class ImageAdapter extends BaseAdapter {

        private Context mContext;
        private Bitmap[]mis_fotos;

        public ImageAdapter(Context c) {
            mContext = c;
            mis_fotos = new Bitmap[]{
                    BitmapFactory.decodeResource(getResources(), R.drawable.no_image_large),
                    BitmapFactory.decodeResource(getResources(), R.drawable.no_image_large),
                    BitmapFactory.decodeResource(getResources(), R.drawable.no_image_large),
                    BitmapFactory.decodeResource(getResources(), R.drawable.no_image_large),
                    BitmapFactory.decodeResource(getResources(), R.drawable.no_image_large),
                    BitmapFactory.decodeResource(getResources(), R.drawable.no_image_large),
                    BitmapFactory.decodeResource(getResources(), R.drawable.no_image_large),
                    BitmapFactory.decodeResource(getResources(), R.drawable.no_image_large),
                    BitmapFactory.decodeResource(getResources(), R.drawable.no_image_large)
            };
        }

        public int getCount() {
            return (mis_fotos == null ? 0 : mis_fotos.length);
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (position >= (getCount()-1)) {
                ImageButton imgButton = new ImageButton(mContext);
                imgButton.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.add_image));
                imgButton.setLayoutParams(new GridView.LayoutParams(width / 3, height / 4));
                imgButton.setBackgroundColor(Color.BLUE);
                imgButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "Avaliable Soon!", Toast.LENGTH_SHORT).show();
                    }
                });
                return imgButton;
            } else {
                ImageView imageView;
                if (convertView == null) {
                    imageView = new ImageView(mContext);
                    imageView.setLayoutParams(new GridView.LayoutParams(width/3, height/4));
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setPadding(0, 0, 0, 0);
                } else {
                    imageView = (ImageView) convertView;
                }
                imageView.setImageBitmap(mis_fotos[position]);
                return imageView;
            }

        }
    }

    private void setButtonTakeImage() {
        // MENU TAKE IMAGE
        File newdir = new File(DefaultOps.IMAGE_DIRECTORY_NAME);
        if (!newdir.exists())
            if (!newdir.mkdir())
                Log.d(DefaultOps.IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + DefaultOps.IMAGE_DIRECTORY_NAME + " directory");
        setImageNameFile(newdir.getAbsolutePath());

        // start camera
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
    }

    private Uri setImageNameFile(String dir) {
        count++;
        String file = dir+count+".jpg";
        File newfile = new File(file);
        try {
            newfile.createNewFile();
        } catch (IOException e) {
            Log.d(newfile.getAbsolutePath(), "Oops! Failed create "
                    + newfile.getAbsolutePath() + " directory");
        }

        outputFileUri = Uri.fromFile(newfile);
        return outputFileUri;
    }

    private void setButtonUploadImg() {
        // MENU UPLOAD IMAGE
        Intent intentUpload = new Intent();
        intentUpload.setType("image/*");
        intentUpload.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intentUpload, "Select Picture"), SELECT_PICTURE);
    }

}
