package com.bignerdranch.android.lighttoolbarpalette;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer);

        //在设置给activity的bar前写，之后没效果
        mToolbar.setTitle("Tool Bar");
        setSupportActionBar(mToolbar);
        //放在设置了之后，放前面，设置了后没效果，关联不起来
        setLeftCornerClick();


        getSupportActionBar().setLogo(R.drawable.ic_action_name);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.share_bar:
                        Toast.makeText(MainActivity.this, "share bar", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.configuration:
                        Toast.makeText(MainActivity.this, "configuration", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }

                return true;
            }
        });

        //palette
        palette();
    }

    private void palette() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.star);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch swatch = palette.getVibrantSwatch();
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(swatch.getRgb()));
            }
        });
    }

    private void setLeftCornerClick() {
        //是否给左上角图标的左边加上一个的图标
        getSupportActionBar().setHomeButtonEnabled(true);
        //左上角展示图标正确
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //放后面，放在放图标的后面，放前面没效果
        mDrawerTool = new ActionBarDrawerToggle(this, mDrawer, R.string.open, R.string.close);
        mDrawerTool.syncState();
        mDrawer.addDrawerListener(mDrawerTool);
    }


    //action bar的项目的选择
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            //抽屉打开的？（抽屉的部位）
            if (mDrawer.isDrawerOpen(GravityCompat.START)) {
                mDrawer.closeDrawers();
            } else {
                //抽屉打开（抽屉的部位）
                mDrawer.openDrawer(GravityCompat.START);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /*@Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                if(mDrawer.isDrawerOpen(GravityCompat.START)){
                    mDrawer.closeDrawers();
                }else{
                    mDrawer.openDrawer(GravityCompat.START);
                }
                return true;
            }

            return super.onOptionsItemSelected(item);
        }
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
