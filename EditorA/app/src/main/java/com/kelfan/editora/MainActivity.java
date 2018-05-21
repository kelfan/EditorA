package com.kelfan.editora;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kelfan.editfiler.EditFilerFragment;
import com.kelfan.editora.filelist.FilelistAdapter;
import com.kelfan.filepicker.ActivityFilePicker;
import com.kelfan.filepicker.MaterialFilePicker;
import com.kelfan.logfiler.LogFilerFragment;
import com.kelfan.utillibrary.FileConfiger;
import com.kelfan.utillibrary.FileLocal;
import com.kelfan.utillibrary.FileWorker;
import com.kelfan.utillibrary.StringWorker;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, LoopActor {

    public static final int FILE_PICKER_REQUEST_CODE = 1;
    private ArrayList<String> openFilelist;
    private FilelistAdapter filelistAdapter;
    private LogFilerFragment logFilerFragment = null;
    private EditFilerFragment editFilerFragment = null;
    private Fragment mainFragment = null;
    private String currentFilePath = "";
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int actResult = 0;
                if (mainFragment != null) {
                    if (mainFragment instanceof LogFilerFragment) {
                        logFilerFragment = (LogFilerFragment) mainFragment;
                        logFilerFragment.saveNewItem();
                        actResult = 1;
                    }
                    if (mainFragment instanceof EditFilerFragment) {
                        editFilerFragment = (EditFilerFragment) mainFragment;
                        actResult = editFilerFragment.saveNewItem();
                    }
                }
                String out = "Update File Fail.";
                if (actResult == 1) {
                    out = "Update File Success";
                }
                Snackbar.make(view, out, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        String config = FileConfiger.getConfigStr(FileConfiger.OPEN_FILE_LIST);
        if (!config.equals("")) {
            openFilelist = StringWorker.stringToListByLine(config);
        } else {
            openFilelist = new ArrayList<String>();
        }

        // set recent open file recyclerView
        RecyclerView fileRecyclerView = findViewById(R.id.file_list_recycler_view);
        filelistAdapter = new FilelistAdapter(this, openFilelist);
        fileRecyclerView.setAdapter(filelistAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        fileRecyclerView.setLayoutManager(linearLayoutManager);
        filelistAdapter.setOnItemClickListener(new FilelistAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String filename = openFilelist.get(position);
                currentFilePath = filename;
                FileConfiger.writeConfig(FileConfiger.RECENT_OPEN_FILE, currentFilePath);
                processFragment(filename);
                drawer.closeDrawers();
                setTitle(FileLocal.set(filename).fileName);
            }
        });

        TouchHelper.newHelper(fileRecyclerView, openFilelist, this, this);

        // set navigation buttons
        Button internalButton = findViewById(R.id.nav_internal_storage);
        internalButton.setOnClickListener(this);
        Button externalButton = findViewById(R.id.nav_sd_storage);
        externalButton.setOnClickListener(this);

        // set new Fragment
        String recentFile = FileConfiger.getConfigStr(FileConfiger.RECENT_OPEN_FILE);
        processFragment(recentFile);
        currentFilePath = recentFile;
        setTitle(FileLocal.set(recentFile).fileName);
    }

    public void updateConfig(){
        FileConfiger.writeConfig(FileConfiger.RECENT_OPEN_FILE, currentFilePath);
        FileConfiger.writeConfig(FileConfiger.OPEN_FILE_LIST, StringWorker.listToStringByLine(openFilelist));
    }

    public void doNewFile(String filename){
        currentFilePath = filename;
        FileConfiger.writeConfig(FileConfiger.RECENT_OPEN_FILE, currentFilePath);
        processFragment(filename);
        setTitle(FileLocal.set(filename).fileName);
    }

    public void processFragment(String fpath) {
        String extend = StringWorker.getLast2end(fpath, ".");
        if (extend.toLowerCase().equals(FileWorker.FILE_LOG)) {
            mainFragment = new LogFilerFragment().setFilepath(fpath);

        } else if (extend.toLowerCase().equals("txt")) {
            mainFragment = new EditFilerFragment().setFilepath(fpath);
        } else {
            mainFragment = new DefaultFragment();
        }
        setFragment(mainFragment);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_internal_storage) {

        } else if (id == R.id.nav_sd_storage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openFilePicker(String path) {
        new MaterialFilePicker()
                .withRootPath(path)
                .withActivity(this)
                .withRequestCode(FILE_PICKER_REQUEST_CODE)
                .withHiddenFiles(false)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            String path = data.getStringExtra(ActivityFilePicker.RESULT_FILE_PATH);

            if (path != null) {
                Log.d("Path: ", path);
                Toast.makeText(this, "Picked file: " + path, Toast.LENGTH_LONG).show();
                if (!openFilelist.contains(path)) {
                    openFilelist.add(path);
                    FileConfiger.writeConfig(FileConfiger.OPEN_FILE_LIST, StringWorker.listToStringByLine(openFilelist));
                    Log.e("open files: ", openFilelist.toString());
                    filelistAdapter.notifyDataSetChanged();
                    doNewFile(path);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        int cId = view.getId();
        if (cId == R.id.nav_internal_storage) {
            drawer.closeDrawers();
            openFilePicker(FileWorker.getStoragePath(FileWorker.STORAGE_EXTERNAL, "/").toString());
        } else if (cId == R.id.nav_sd_storage) {
            drawer.closeDrawers();
            openFilePicker(FileWorker.getStoragePath(FileWorker.STORAGE_EXTERNAL_SECOND, "/").toString());
        }
    }

    // set new Fragment into content
    private void setFragment(Fragment inFragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, inFragment);
        fragmentTransaction.commit();
    }

    public void getFragments() {
        if (mainFragment != null) {
            if (mainFragment instanceof LogFilerFragment) {
                logFilerFragment = (LogFilerFragment) mainFragment;
            }
            if (mainFragment instanceof EditFilerFragment) {
                editFilerFragment = (EditFilerFragment) mainFragment;
            }
        }
    }

    public void onClick(MenuItem item) {
        getFragments();
        int cId = item.getItemId();
        if (cId == R.id.sort_menu) {
            if (editFilerFragment != null) {
                editFilerFragment.sort();
            }
        } else if (cId == R.id.action_rename) {
            getUserInput(FileLocal.set(currentFilePath).fileName);
        }
    }

    public void showSnackbar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

    public void getUserInput(String editStr) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Rename File");
        alert.setMessage("Please input a new file name");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        input.setText(editStr);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                // Do something with value!
                if (!value.equals("")){
                    if (FileLocal.set(currentFilePath).rename(value)){
                        FileLocal f  = FileLocal.set(currentFilePath).setNewFileName(value);
                        setTitle(f.fileName);
                        filelistAdapter.removeItem(currentFilePath);
                        filelistAdapter.addItem(f.file.getAbsoluteFile().toString());
                        filelistAdapter.notifyDataSetChanged();
                        currentFilePath = f.file.getAbsolutePath();
                        updateConfig();
                        showSnackbar("Rename success");
                    }else{
                        showSnackbar("Rename fail");
                    }
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }

    @Override
    public void run(Object... args) {
        FileConfiger.writeConfig(FileConfiger.OPEN_FILE_LIST, StringWorker.listToStringByLine(openFilelist));
    }
}
