package com.dtech.posisi;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.dtech.adapters.AdapterNavigationList;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavDrawerFragment extends Fragment {

    public static final String PREF_FILE_NAME="test";
    public static final String KEY_USER_LEARNED="learned";


    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout mDrawerLayout;
    private View containerView;
    //RecyclerView Variable==========
    private RecyclerView recyclerView;
    private AdapterNavigationList adapter;
    //===============================
    MainActivity mainActivity;

    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;

    public NavDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer=Boolean.valueOf(readFromPreferences(getActivity(),KEY_USER_LEARNED, "false" ));
        if(savedInstanceState!=null){
            mFromSavedInstanceState=true;
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout=inflater.inflate(R.layout.fragment_nav_drawer, container, false);
        mainActivity=(MainActivity)getActivity();
        // inserting RecyclerView to this NavigationDrawer
        recyclerView=(RecyclerView)layout.findViewById(R.id.drawerList);
        adapter=new AdapterNavigationList(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                switch (position) {
                    case 0:
                        startActivity(new Intent(mainActivity, ActvtMainInput.class));
                        break;
                    case 1:
                        startActivity(new Intent(mainActivity, ActvtGalleries.class));
                        break;
                    case 2:
                        startActivity(new Intent(mainActivity, ActvtMapsCheck.class));
                        break;
                    case 3:
                        startActivity(new Intent(mainActivity, ActvtLogs.class));
                        break;
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return layout;

    }



    public static List<AdapterNavigationList.MenuHolder> getData(){
        List<AdapterNavigationList.MenuHolder> data=new ArrayList<>();

        int[] icons = {
            R.drawable.ic_polymer_black_24dp, R.drawable.ic_camera_enhance_black_24dp,
            R.drawable.ic_room_black_24dp, R.drawable.ic_assignment_ind_black_24dp
        };
        String[] title = {
            "Input Data", "Gallery",
            "Check Map", "Show Log"
        };
        for (int i=0;i<title.length && i<icons.length; i++){
            AdapterNavigationList.MenuHolder menuHolder =
                    new AdapterNavigationList.MenuHolder(title[i], icons[i]);
            data.add(menuHolder);
        }
        return data;
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, android.support.v7.widget.Toolbar toolbar) {
        containerView=getActivity().findViewById(fragmentId);
        mDrawerLayout=drawerLayout;

        drawerToggle=new ActionBarDrawerToggle(getActivity(),drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!mUserLearnedDrawer){
                    mUserLearnedDrawer=true;
                    saveToPreference(getActivity(), KEY_USER_LEARNED, mUserLearnedDrawer+"");
                }
                getActivity().invalidateOptionsMenu();
                //
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };
        if(!mUserLearnedDrawer && !mFromSavedInstanceState){

            mDrawerLayout.openDrawer(containerView);
        }

        mDrawerLayout.setDrawerListener(drawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                drawerToggle.syncState();
            }
        });

    }

    public static void saveToPreference(Context context, String preferenceName, String preferenceValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
    }

    public static interface ClickListener{
        public void onClick(View view, int position);
        public void onLongClick(View view, int position);

    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{
        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener){
            this.clickListener=clickListener;
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e){
                return true;
            }
                @Override
            public void onLongPress(MotionEvent e){
                    View child=recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if(child!=null && clickListener!=null){
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            View child=recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
            if(child!=null && clickListener!=null && gestureDetector.onTouchEvent(motionEvent)){
                clickListener.onClick(child, recyclerView.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean b) {

        }
    }
}
