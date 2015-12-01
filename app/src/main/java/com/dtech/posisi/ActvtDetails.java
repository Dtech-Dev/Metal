package com.dtech.posisi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import com.dtech.orm.MtlImagePelanggaran;
import com.dtech.orm.MtlPelanggan;
import com.dtech.orm.MtlPelanggaran;

public class ActvtDetails extends AppCompatActivity  {

    SectionsVwDtPagerAdapter mSectionsPagerAdapter;
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

    private FrgmInputPelanggan frgmViewPelanggan;
//    private FrgmInputPelanggaran frgmInputPelanggaran;
//    private FrgmnInputImages frgmnInputImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.barViewCust);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View Data");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsVwDtPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.containerview);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabsview);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabview);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_actvt_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
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

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsVwDtPagerAdapter extends FragmentPagerAdapter {
        private String tabName[] = {"PELANGGAN"}; //, "PELANGGARAN", "GALERI"};

        public SectionsVwDtPagerAdapter(FragmentManager fm) {
            super(fm);
            frgmViewPelanggan = new FrgmInputPelanggan();
//            frgmInputPelanggaran = new FrgmInputPelanggaran();
//            frgmnInputImages = new FrgmnInputImages();
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return frgmViewPelanggan;
//                case 1:
//                    return frgmInputPelanggaran;
//                case 2:
//                    return frgmnInputImages;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return tabName.length;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
