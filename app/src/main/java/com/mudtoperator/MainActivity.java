package com.mudtoperator;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mudtoperator.fragments.AboutFragment;
import com.mudtoperator.fragments.HistorialFragment;
import com.mudtoperator.fragments.LoginFragment;
import com.mudtoperator.fragments.MainFragment;
import com.mudtoperator.fragments.ProcessFragment;
import com.mudtoperator.fragments.SolicitudFragment;
import com.mudtoperator.fragments.ViajeDetailFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private DrawerLayout drawerLayout;
    private FrameLayout contenLay;
    private LoginFragment loginFragment;
    private MainFragment mainFragment;
    private HistorialFragment historialFragment;
    private SolicitudFragment solicitudFragment;
    private AboutFragment aboutFragment;
    private ImageView userPic;
    private TextView userName;

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Singleton.setMainActivity(this);

        setToolbar();
        initView();
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View tool = inflater.inflate(R.layout.actionbar, null);
        toolbar.addView(tool);

        ImageView expanded_menu = (ImageView)tool.findViewById(R.id.expanded_menu);
        expanded_menu.setOnClickListener(this);
        Singleton.setMenuBtn(expanded_menu);

        TextView appText = (TextView)tool.findViewById(R.id.appText);
        Singleton.setActionText(appText);

        ImageView actionBtn = (ImageView)tool.findViewById(R.id.actionBtn);
        Singleton.setActionButon(actionBtn);
        actionBtn.setOnClickListener(this);

        ProgressBar actionProgress = (ProgressBar)tool.findViewById(R.id.actionProgress);
        Singleton.setActionProgress(actionProgress);

        TextView calif = (TextView)tool.findViewById(R.id.calif);
        calif.setOnClickListener(this);
        Singleton.setCalifText(calif);
    }

    private void initView(){
        initFragments();

        contenLay = (FrameLayout)findViewById(R.id.mainContent);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Singleton.setDrawerLayout(drawerLayout);

        if(Singleton.getSettings().getBoolean("login_flag", false))
            initMainFragment();
        else
            initLoginFragment();
    }

    private void initFragments() {
        loginFragment = new LoginFragment();
        mainFragment = new MainFragment();
        historialFragment = new HistorialFragment();
        solicitudFragment = new SolicitudFragment();
        aboutFragment = new AboutFragment();
    }

    private void removeFragments(){
        if(Singleton.getCurrentFragment() != null){
            Log.d("fragment remove", Singleton.getCurrentFragment().getClass().toString());

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.remove(Singleton.getCurrentFragment()).commit();
        }
    }

    private void initLoginFragment(){
        if(Singleton.getCurrentFragment() != loginFragment){
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, findViewById(R.id.left_drawer));
            removeFragments();
            Bundle bundle = new Bundle();
            bundle.putInt("lay", contenLay.getId());
            if(loginFragment.getArguments() == null)
                loginFragment.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(contenLay.getId(), loginFragment).commit();
        }
    }

    public void initMainFragment(){
        if(Singleton.getCurrentFragment() != mainFragment){
            initUser();
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, findViewById(R.id.left_drawer));
            removeFragments();
            Bundle bundle = new Bundle();
            bundle.putInt("lay", contenLay.getId());
            bundle.putString("title", getResources().getString(R.string.next_mudt));
            if(mainFragment.getArguments() == null)
                mainFragment.setArguments(bundle);
            else
                mainFragment.getArguments().putString("title", getResources().getString(R.string.next_mudt));
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(contenLay.getId(), mainFragment).commit();
        }
    }

    public void initHistoryFragment(){
        if(Singleton.getCurrentFragment() != historialFragment){
            initUser();
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, findViewById(R.id.left_drawer));
            removeFragments();
            Bundle bundle = new Bundle();
            bundle.putInt("lay", contenLay.getId());
            bundle.putString("title", getResources().getString(R.string.hist_muds));
            if(historialFragment.getArguments() == null)
                historialFragment.setArguments(bundle);
            else
                historialFragment.getArguments().putString("title", getResources().getString(R.string.hist_muds));
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(contenLay.getId(), historialFragment).commit();
        }
    }

    public void initAboutFragment(){
        if(Singleton.getCurrentFragment() != aboutFragment){
            initUser();
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, findViewById(R.id.left_drawer));
            removeFragments();
            Bundle bundle = new Bundle();
            bundle.putInt("lay", contenLay.getId());
            if(aboutFragment.getArguments() == null)
                aboutFragment.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(contenLay.getId(), aboutFragment).commit();
        }
    }

    public void initSolicitudFragment(){
        if(Singleton.getCurrentFragment() != solicitudFragment){
            initUser();
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, findViewById(R.id.left_drawer));
            removeFragments();
            Bundle bundle = new Bundle();
            bundle.putInt("lay", contenLay.getId());
            bundle.putString("title", getResources().getString(R.string.sols_muds));
            if(solicitudFragment.getArguments() == null)
                solicitudFragment.setArguments(bundle);
            else
                solicitudFragment.getArguments().putString("title", getResources().getString(R.string.sols_muds));
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(contenLay.getId(), solicitudFragment).commit();
        }
    }

    private void openCloseDrawer(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.expanded_menu:
                if(Singleton.getCurrentFragment().getClass() == MainFragment.class)
                    openCloseDrawer();
                if(Singleton.getCurrentFragment().getClass() == ViajeDetailFragment.class)
                    onBackPressed();
                if(Singleton.getCurrentFragment().getClass() == ProcessFragment.class)
                    onBackPressed();
                if(Singleton.getCurrentFragment().getClass() == HistorialFragment.class)
                    initMainFragment();
                if(Singleton.getCurrentFragment().getClass() == SolicitudFragment.class)
                    initMainFragment();
                if(Singleton.getCurrentFragment().getClass() == AboutFragment.class)
                    onBackPressed();
                break;
            case R.id.menu_prox:
                openCloseDrawer();
                initMainFragment();
            break;
            case R.id.menu_his:
                openCloseDrawer();
                initHistoryFragment();
            break;
            case R.id.menu_sol:
                openCloseDrawer();
                initSolicitudFragment();
            break;
            case R.id.menu_guia:
                openCloseDrawer();
            break;
            case R.id.menu_about:
                openCloseDrawer();
                initAboutFragment();
            break;
            case R.id.menu_close:
                openCloseDrawer();
                //finish();
                mainIntent();
            break;
        }
    }

    private void initUser(){
        userName = (TextView)findViewById(R.id.name);
        userName.setText(Singleton.getUSerObj().NombreCompleto);

        ProgressBar progressBar = new ProgressBar(this);
        userPic = (ImageView)findViewById(R.id.user_pic);
        Singleton.loadImage(Singleton.getUSerObj().Foto, userPic, progressBar);

        LinearLayout menu_prox = (LinearLayout)findViewById(R.id.menu_prox);
        menu_prox.setOnClickListener(this);
        LinearLayout menu_his = (LinearLayout)findViewById(R.id.menu_his);
        menu_his.setOnClickListener(this);
        LinearLayout menu_sol = (LinearLayout)findViewById(R.id.menu_sol);
        menu_sol.setOnClickListener(this);
        LinearLayout menu_guia = (LinearLayout)findViewById(R.id.menu_guia);
        menu_guia.setOnClickListener(this);
        LinearLayout menu_about = (LinearLayout)findViewById(R.id.menu_about);
        menu_about.setOnClickListener(this);
        LinearLayout menu_close = (LinearLayout)findViewById(R.id.menu_close);
        menu_close.setOnClickListener(this);
    }

    @Override
    public void onBackPressed(){
        if(Singleton.getCurrentFragment().getClass() == HistorialFragment.class)
            initMainFragment();
        else if(Singleton.getCurrentFragment().getClass() == SolicitudFragment.class)
            initMainFragment();
        else if(Singleton.getCurrentFragment().getClass() == AboutFragment.class)
            initMainFragment();
        else if(Singleton.getCurrentFragment().getClass() == ViajeDetailFragment.class)
            super.onBackPressed();
        else
            super.onBackPressed();
    }

    private void mainIntent(){
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}
