package com.dtech.posisi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dtech.MetalAppsGlobalClass;
import com.dtech.orm.DefaultOps;
import com.dtech.orm.DuplicateException;
import com.dtech.orm.MtlImagePelanggaran;
import com.dtech.orm.MtlPelanggan;
import com.dtech.orm.MtlPelanggaran;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActvtMainInput extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    MtlPelanggan customer;
    MtlPelanggaran fouls;
    MtlImagePelanggaran foulImages;

    private String custCode;
    private String custName;
    private String custAddress;
    private String foulDate;
    private String foulDaya;
    private String foulType;
    private String foulTariff;

    private FrgmInputPelanggan frgmInputPelanggan;
    private FrgmInputPelanggaran frgmInputPelanggaran;
    private FrgmnInputImages frgmnInputImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_input);

        Toolbar toolbar = (Toolbar) findViewById(R.id.barInputCust);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Input Data");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_actvt_main_input, menu);
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

    public void onTextChanged(String field, CharSequence s) {
        switch(field){
            case "code":
                setCustCode(s.toString());
                break;
            case "name":
                setCustName(s.toString());
                break;
            case "address":
                setCustAddress(s.toString());
                break;
            case "foulDate":
                setFoulDate(s.toString());
                break;
            case "foulDaya":
                setFoulDaya(s.toString());
                break;
            case "foulType":
                setFoulType(s.toString());
                break;
            case "foulTariff":
                setFoulTariff(s.toString());
                break;
        }
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getFoulDate() {
        return foulDate;
    }

    public void setFoulDate(String foulDate) {
        this.foulDate = foulDate;
    }

    public String getFoulType() {
        return foulType;
    }

    public void setFoulType(String foulType) {
        this.foulType = foulType;
    }

    public String getFoulTariff() {
        return foulTariff;
    }

    public void setFoulTariff(String foulTariff) {
        this.foulTariff = foulTariff;
    }

    public String getFoulDaya() {
        return foulDaya;
    }

    public void setFoulDaya(String foulDaya) {
        this.foulDaya = foulDaya;
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private String tabName[] = {"PELANGGAN", "PELANGGARAN", "GALERI"};

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            MetalAppsGlobalClass.setActiveCustId(Long.parseLong("0"));
            frgmInputPelanggan = new FrgmInputPelanggan();
            frgmInputPelanggaran = new FrgmInputPelanggaran();
            frgmnInputImages = new FrgmnInputImages();
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return frgmInputPelanggan;
                case 1:
                    return frgmInputPelanggaran;
                case 2:
                    return frgmnInputImages;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
//            https://guides.codepath.com/android/google-play-style-tabs-using-tablayout
//            // Generate title based on item position
//            // return tabTitles[position];
//
//            // getDrawable(int i) is deprecated, use getDrawable(int i, Theme theme) for min SDK >=21
//            // or ContextCompat.getDrawable(Context context, int id) if you want support for older versions.
//            // Drawable image = context.getResources().getDrawable(iconIds[position], context.getTheme());
//            // Drawable image = context.getResources().getDrawable(imageResId[position]);
//
//            Drawable image = ContextCompat.getDrawable(context, imageResId[position]);
//            image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
//            SpannableString sb = new SpannableString(" ");
//            ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
//            sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            return sb;
            return tabName[position];
        }
    }

    private void saveData() throws IOException {
        validateData();
        boolean custExist = MtlPelanggan.custExist("code = ?", getCustCode());
        try {
            if (custExist) {
                List<MtlPelanggan> customers = MtlPelanggan.find(MtlPelanggan.class, "code = ? ", getCustCode());
                customer = customers.get(0);
                customer.setName(getCustName());
                customer.setAddress(getCustAddress());
            } else {
                customer = new MtlPelanggan(getCustCode(), getCustName()
                        , getCustAddress());
            }
        } catch (DuplicateException e) {
            new AlertDialog.Builder(this)
                    .setTitle("!")
                    .setMessage("Gagal menyimpan data. Mungkin karena duplikasi.")
                    .setPositiveButton(android.R.string.ok, null).create().show();
        }

        fouls = new MtlPelanggaran(customer, getFoulDate(), getFoulType(), getFoulTariff()
                , new Integer(getFoulDaya()));

        Map<String, Bitmap> imagesData = frgmnInputImages.getBitmapMap();
        final List<MtlImagePelanggaran> tempImage = new ArrayList<>();
        for (String imagePath : imagesData.keySet()){
            String image = DefaultOps.encodeImage(imagesData.get(imagePath)
                    , DefaultOps.DEFAULT_COMPRESS_FORMAT
                    , DefaultOps.DEFAULT_COMPRESSION);
            float[] coordinat = DefaultOps.getLocationRef(imagePath);
            if (coordinat.length <= 0) {
                Toast.makeText(getBaseContext(), "Gambar '" + imagePath + "' tidak memiliki koordinat!"
                        , Toast.LENGTH_LONG).show();
                continue;
            }
            foulImages  = new MtlImagePelanggaran(fouls, image, imagePath
                    , Float.toString(coordinat[0]), Float.toString(coordinat[1]));
            tempImage.add(foulImages);
        }
        new AlertDialog.Builder(this)
            .setTitle("?")
            .setMessage("Pastikan data yang diinputkan sudah benar!")
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    customer.save();
                    fouls.save();
                    for (MtlImagePelanggaran data : tempImage)
                        data.save();
                    Toast.makeText(getBaseContext(), "Data Sudah Disimpan!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }).create().show();
    }

    private boolean validateData() {
        // TODO we need to validate inputed data
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
