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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FrgmnInputImages.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class FrgmnInputImages extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int TAKE_PHOTO_CODE = 1;
    private static final int SELECT_PICTURE = 2;

    private List<Bitmap> bitmapsData;

    private OnFragmentInteractionListener mListener;

    private GridView gvImage;
    private int width;
    private int height;
    private int count = 0;
    Uri outputFileUri;

    private ImageAdapter imageAdapter;

    public FrgmnInputImages() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_input_images, container, false);
        setWidthHeight();
        gvImage = (GridView) rootView.findViewById(R.id.gridViewImage);

        bitmapsData = new ArrayList<>();
        imageAdapter = new ImageAdapter(getContext(), bitmapsData);
        gvImage.setAdapter(imageAdapter);
        gvImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "Available Soon", Toast.LENGTH_SHORT).show();
            }
        });
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

    private void setWidthHeight() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        width = displaymetrics.widthPixels;
        height = displaymetrics.heightPixels;
    }

    public class ImageAdapter extends BaseAdapter {

        private Context mContext;
        private List<Bitmap> bitmaps;

        public ImageAdapter(Context c, List<Bitmap> bitmaps){
            mContext = c;
            this.bitmaps = bitmaps;
        }

        public int getCount() {
            return (this.bitmaps == null ? 0 : this.bitmaps.size()) + 1;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (position >= (getCount() - 1)) {
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
                                        setBtnTakeImage();
                                    }
                                })
                                .setPositiveButton("GALERY", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        setBtnUploadImage();
                                    }
                                }).create().show();
                    }
                });
                return imgButton;
            } else {
                ImageView imageView = convertView == null ? new ImageView(mContext) : (ImageView) convertView;
                imageView.setLayoutParams(new ViewGroup.LayoutParams(width/3, height/4));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setPadding(8, 8, 8, 8);
                imageView.setImageBitmap(bitmapsData.get(position));
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Available Soon!", Toast.LENGTH_SHORT).show();
                    }
                });
                return imageView;
            }
        }
    }

    public void setImageView(Bitmap bm) {
        if (bm == null)
            return;
        bitmapsData.add(bm);
        imageAdapter = new ImageAdapter(getContext(), bitmapsData);
        gvImage.setAdapter(imageAdapter);
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

    private static int getPowerOfTwoForSampleRatio(double ratio){
        int k = Integer.highestOneBit((int)Math.floor(ratio));
        if(k==0) return 1;
        else return k;
    }

    public void setImageUpload(Intent data) {
        try {
            // source http://stackoverflow.com/questions/3879992/get-bitmap-from-an-uri-android
            Uri imageUri = data.getData();
            InputStream input = getContext().getContentResolver().openInputStream(imageUri);
            BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
            onlyBoundsOptions.inJustDecodeBounds = true;
            onlyBoundsOptions.inDither=true;//optional
            onlyBoundsOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
            BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
            input.close();
            if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
                return;

            int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;
            int thumbnailSize = height > width ? height : width;
            double ratio = (originalSize > thumbnailSize) ? (originalSize / thumbnailSize) : 1.0;

            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
            bitmapOptions.inDither=true;//optional
            bitmapOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
            input = getContext().getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
            input.close();
            setImageView(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void previewCaptured() {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            final Bitmap bitmap = BitmapFactory.decodeFile(outputFileUri.getPath(), options);
            setImageView(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void setBtnTakeImage() {
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

    private void setBtnUploadImage() {
        // MENU UPLOAD IMAGE
        Intent intentUpload = new Intent();
        intentUpload.setType("image/*");
        intentUpload.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intentUpload, "Select Picture"), SELECT_PICTURE);
    }
}
