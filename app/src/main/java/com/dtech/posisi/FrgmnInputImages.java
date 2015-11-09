package com.dtech.posisi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.dtech.orm.DefaultOps;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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
     ImageView imgv, imgv1, imgv2;
    ImageButton imgbtn;
    private GridView gvImage;
    private int width;
    private int height;
    private int count = 0;
    Uri outputFileUri;

    private ImageAdapter imageAdapter;

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
        //imgv = new ImageView(getContext());

        View rootView = inflater.inflate(R.layout.fragment_input_images, container, false);
        setTextView();
        gvImage = (GridView) rootView.findViewById(R.id.gridViewImage);

        imgv = (ImageView) rootView.findViewById(R.id.imgvi);
        imgv1 = (ImageView) rootView.findViewById(R.id.imgvi1);
        imgv2 = (ImageView) rootView.findViewById(R.id.imgvi2);
        imageAdapter = new ImageAdapter(getContext());

        gvImage.setAdapter(imageAdapter);
        gvImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /*switch ((int)parent.getItemAtPosition(position)) {
                    case 0:
                        Toast.makeText(getContext(),"boo", Toast.LENGTH_SHORT).show();
                        imgv.setImageResource(R.drawable.ic_add);
                        imageAdapter.setImageView(imgv);
                        break;
                    case 1:
                        Toast.makeText(getContext(),"boo1", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getContext(),"boo2", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getContext(),"boo3", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(getContext(),"boo", Toast.LENGTH_SHORT).show();
                        break;
                }

                int item = (int) parent.getItemAtPosition(position);

                String showItem = Integer.toString(item);
                Toast.makeText(getContext(), "Item = " + showItem, Toast.LENGTH_SHORT).show();*/
            }
        });
        //imgv=imageAdapter.getImageView();
//        setImageButton(rootView, gvImage, 0);
        imgbtn = (ImageButton) rootView.findViewById(R.id.imgbtn);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("")
                        .setMessage("Ambil gambar dari ... ")
                        .setNegativeButton("CAMERA", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                setButtonTakeImage();
                            }
                        })
                        .setPositiveButton("GALERY", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                setButtonUploadImg();
                            }
                        }).create().show();
            }
        });

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
        private Bitmap[] mis_fotos;
        private List<Bitmap> bitmapsData;
        ImageView imageView;

        public ImageAdapter(Context c) {
            mContext = c;
            bitmapsData = new ArrayList<Bitmap>();
            bitmapsData.add(BitmapFactory.decodeResource(getResources(), R.drawable.no_image_large));
            bitmapsData.add(BitmapFactory.decodeResource(getResources(), R.drawable.no_image_large));
            bitmapsData.add(BitmapFactory.decodeResource(getResources(), R.drawable.no_image_large));
            bitmapsData.add(BitmapFactory.decodeResource(getResources(), R.drawable.no_image_large));
            bitmapsData.add(BitmapFactory.decodeResource(getResources(), R.drawable.no_image_large));
            bitmapsData.add(BitmapFactory.decodeResource(getResources(), R.drawable.no_image_large));
        }



        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        public int getCount() {
            return (bitmapsData == null ? 0 : bitmapsData.size());
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            /*if (position >= (getCount() - 1)) {
                ImageButton imgButton = new ImageButton(mContext);
                imgButton.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.add_image));
                imgButton.setLayoutParams(new GridView.LayoutParams(width / 3, height / 4));
                imgButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(mContext)
                                .setTitle("")
                                .setMessage("Ambil gambar dari ... ")
                                .setNegativeButton("CAMERA", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        setButtonTakeImage();
                                    }
                                })
                                .setPositiveButton("GALERY", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        setButtonUploadImg();
                                    }
                                }).create().show();
//                        Toast.makeText(mContext, "Avaliable Soon!", Toast.LENGTH_SHORT).show();
                    }
                });
                return imgButton;
           } else { */

                if (convertView == null) {
                    //imgv.setImageResource(R.drawable.no_image_large);
                    imageView = new ImageView(mContext);
                    imageView.setLayoutParams(new ViewGroup.LayoutParams(width/3, height/4));


                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setPadding(0, 0, 0, 0);
                } else {
                    imageView = (ImageView) convertView;
                }
                imageView.setImageBitmap(bitmapsData.get(position));
                return imageView;
            //}

        }

   }


public void setImageVi(Uri selectedImageUri, Bitmap bm) {
        /*if (imgv == null) {

        }*/

    if (imgv.getDrawable() == null && imgv1.getDrawable() == null && imgv2.getDrawable() == null) {
        if (selectedImageUri != null) {
            imgv.setImageURI(selectedImageUri);
            //imageAdapter.setImageView(imgv);
        }

        if (bm != null) {
            imgv.setImageBitmap(bm);
            //imageAdapter.setImageView(imgv);
        }
    } else if (imgv.getDrawable() != null && imgv1.getDrawable() == null && imgv2.getDrawable() == null) {
        if (selectedImageUri != null) {
            imgv1.setImageURI(selectedImageUri);
            //imageAdapter.setImageView(imgv);
        }

        if (bm != null) {
            imgv1.setImageBitmap(bm);
            //imageAdapter.setImageView(imgv);
        }
    } else if (imgv.getDrawable() != null && imgv1.getDrawable() != null && imgv2.getDrawable() == null){
        if (selectedImageUri != null) {
            imgv2.setImageURI(selectedImageUri);
            //imageAdapter.setImageView(imgv);
        }

        if (bm != null) {
            imgv2.setImageBitmap(bm);
            //imageAdapter.setImageView(imgv);
        }
    }
    /*if (selectedImageUri != null) {

        imgv.setImageURI(selectedImageUri);
        //imageAdapter.setImageView(imgv);
    }

    if (bm != null) {
        imgv.setImageBitmap(bm);
        //imageAdapter.setImageView(imgv);
    }*/
}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case SELECT_PICTURE:
                setImageUpload(data);
                break;
            case TAKE_PHOTO_CODE:
                previewCaptured();
                break;
        }
    }

    public void setImageUpload(Intent data) {

        Uri selectedImageUri = data.getData();
        setImageVi(selectedImageUri, null);

    }

    public void previewCaptured() {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            final Bitmap bitmap = BitmapFactory.decodeFile(outputFileUri.getPath(), options);
            setImageVi(null, bitmap);
        } catch (NullPointerException e) {

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
       // Uri selectedImageUri = intentUpload.getData();
//        imageAdapter.bitmapsData.add() // TODO ngelu
    }



}
