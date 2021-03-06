package com.bigpicture.team.ev;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bigpicture.team.ev.item.MemberInfoItem;
import com.bigpicture.team.ev.lib.GoLib;
import com.bigpicture.team.ev.lib.StringLib;
import com.bigpicture.team.ev.remote.RemoteService;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String TAG = getClass().getSimpleName();
    MemberInfoItem memberInfoItem;
    DrawerLayout drawer;
    View headerLayout;
    CircleImageView profileIconImage;

    //액티비티와 네이게이션 뷰를 설정하고 Fragment를 보여준다.
    //savedInstanceState: 액티비티가 새로 생성되었을 경우에 이전 상태 값을 가지는 객체
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memberInfoItem =((MyApp)getApplication()).getMemberInfoItem();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        headerLayout = navigationView.getHeaderView(0);

        //GoLib.getInstance().goFragment(getSupportFragmentManager(), R.id.content_main,ESCListFragment.newInstance());
    }

    //프로필 정보는 별도 액티비티에서 변경될 수 있으므로 변경을 바로 감지하기 위해서
    //화면이 새로 보여질 때마다 setProfileView()를 호출


    @Override
    protected void onResume() {
        super.onResume();
        setProfileView();
    }

    //프로필 이미지와 프로필 이름 설정

    private void setProfileView(){
        profileIconImage = (CircleImageView)headerLayout.findViewById(R.id.profile_icon);
        profileIconImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                GoLib.getInstance().goProfileActivity(MainActivity.this);
            }
        });
        if(StringLib.getInstance().isBlank(memberInfoItem.memberIconFilename)) {
            Picasso.with(this).load(R.drawable.leaf).into(profileIconImage);
        } else {
            Picasso.with(this).load(RemoteService.MEMBER_ICON_URL + memberInfoItem.memberIconFilename).into(profileIconImage);
        }

        TextView nameText = (TextView) headerLayout.findViewById(R.id.name);

        if(memberInfoItem.name == null || memberInfoItem.name.equals("")){
            nameText.setText(R.string.name_need);
        }else {
            nameText.setText(memberInfoItem.name);
        }

    }

    //뒤로가기 키를 눌렀을 때 호출.
    //네비게이션이 보이는 상태라면 네비게이션을 닫는다

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_list) {

        } else if (id == R.id.nav_map) {

        } else if (id == R.id.nav_keep) {

        } else if (id == R.id.nav_register) {

        } else if (id == R.id.nav_profile) {
            GoLib.getInstance().goProfileActivity(this);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
