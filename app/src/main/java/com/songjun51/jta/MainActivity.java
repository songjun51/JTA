package com.songjun51.jta;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private BackPressCloseHandler backPressCloseHandler;

    WebView webView;
    SearchView searchView;
    private Toolbar toolbar;
    ProgressDialog dialog;
    FloatingActionMenu floatingActionMenu;
    private FloatingActionButton fab1;
    private FloatingActionButton fab2;
    private FloatingActionButton fab3, fab4, fab5, fab6, fab7, fab8;
    int status = 1;
    DrawerLayout drawer;

    //    public HttpClient httpclient =  new DefaultHttpClient();  //멤버변수로 선언
    public CookieManager cookieManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        handleIntent(getIntent());
        webView = (WebView) findViewById(R.id.webview1);
        CookieSyncManager.createInstance(this);
        dialog = new ProgressDialog(this);
        backPressCloseHandler = new BackPressCloseHandler(this);
        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.menu_green);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab4 = (FloatingActionButton) findViewById(R.id.fab4);
        fab5 = (FloatingActionButton) findViewById(R.id.fab5);
        fab6 = (FloatingActionButton) findViewById(R.id.fab6);
        fab7 = (FloatingActionButton) findViewById(R.id.fab7);
        fab8 = (FloatingActionButton) findViewById(R.id.fab8);
        fab1.setLabelText("로그인");
        fab2.setLabelText("게시판");
        fab3.setVisibility(View.GONE);
        fab4.setVisibility(View.GONE);
        fab5.setVisibility(View.GONE);
        fab6.setVisibility(View.GONE);
        fab7.setVisibility(View.GONE);
        fab8.setVisibility(View.GONE);
        fab1.setVisibility(View.VISIBLE);
        fab2.setVisibility(View.VISIBLE);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        final Context myApp = this;
//
        webView.setWebChromeClient(new WebChromeClient() {

            @Override

            public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result)

            {
                new AlertDialog.Builder(myApp)
//                        .setTitle("  ")
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok,
                                new AlertDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        result.confirm();
                                    }
                                })
                        .setCancelable(false)
                        .create()
                        .show();
                return true;

            } ;
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                dialog.setMessage("loading...");
                isNetworkStat(getApplicationContext());
                dialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                dialog.cancel();
            }


            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Toast.makeText(MainActivity.this, "오류코드 : " + errorCode, Toast.LENGTH_SHORT).show();


            }

        }); // 이걸 안해주면 새창이 뜸
        webView.loadUrl("http://jteacher.net/m/");

        WebSettings settings = webView.getSettings();
        settings.setAllowFileAccess(true);
        settings.setSupportZoom(true);
        settings.setJavaScriptEnabled(true);
        settings.setPluginState(WebSettings.PluginState.ON_DEMAND);
        settings.setSupportZoom(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true); // javascript가 window.open()을 사용할 수 있도록 설정
        settings.setPluginState(WebSettings.PluginState.ON_DEMAND); // 플러그인을 사용할 수 있도록 설정
        settings.setSupportMultipleWindows(true);

        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        fab1.setOnClickListener(clickListener);
        fab2.setOnClickListener(clickListener);
        fab3.setOnClickListener(clickListener);
        fab4.setOnClickListener(clickListener);
        fab5.setOnClickListener(clickListener);
        fab6.setOnClickListener(clickListener);
        fab7.setOnClickListener(clickListener);
        fab8.setOnClickListener(clickListener);
        floatingActionMenu.setOnMenuButtonLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                webView.loadUrl("http://jteacher.net/m/");
                fab1.setLabelText("로그인");
                fab2.setLabelText("게시판");
                status = 1;
                fab3.setVisibility(View.GONE);
                fab4.setVisibility(View.GONE);
                fab5.setVisibility(View.GONE);
                fab6.setVisibility(View.GONE);
                fab7.setVisibility(View.GONE);
                fab8.setVisibility(View.GONE);
                fab1.setVisibility(View.VISIBLE);
                fab2.setVisibility(View.VISIBLE);

                return false;
            }
        });

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String ty, long contentLength) {
                try {
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                    request.setMimeType(ty);
                    request.addRequestHeader("User-Agent", userAgent);
                    request.setDescription("Downloading file");
                    String fileName = contentDisposition.replace("inline; filename=", "");
                    fileName = fileName.replaceAll("\"", "");
                    request.setTitle(fileName);
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
                    DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    dm.enqueue(request);
                    Toast.makeText(getApplicationContext(), "Downloading File", Toast.LENGTH_LONG).show();
                } catch (Exception e) {

                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            Toast.makeText(getBaseContext(), "첨부파일 다운로드를 위해\n동의가 필요합니다.", Toast.LENGTH_LONG).show();
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    110);
                        } else {
                            Toast.makeText(getBaseContext(), "첨부파일 다운로드를 위해\n동의가 필요합니다.", Toast.LENGTH_LONG).show();
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    110);
                        }
                    }
                }
            }
        });

    }// 끝

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);
        }
        if (floatingActionMenu.isOpened()) {
            floatingActionMenu.close(true);
        } else {
            backPressCloseHandler.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_1) {
            webView.loadUrl("http://jteacher.net/m/");

            fab1.setLabelText("로그인");
            fab2.setLabelText("게시판");
            fab3.setVisibility(View.GONE);
            fab4.setVisibility(View.GONE);
            fab5.setVisibility(View.GONE);
            fab6.setVisibility(View.GONE);
            fab7.setVisibility(View.GONE);
            fab8.setVisibility(View.GONE);
            fab1.setVisibility(View.VISIBLE);
            fab2.setVisibility(View.VISIBLE);
            floatingActionMenu.close(true);
            status = 1;
            //메인화면
        } else if (id == R.id.nav_2) {
            webView.loadUrl("http://jteacher.net/m/bbs/new.php");
            fab1.setVisibility(View.GONE);
            fab2.setVisibility(View.GONE);
            fab3.setVisibility(View.GONE);
            fab4.setVisibility(View.GONE);
            fab5.setVisibility(View.GONE);
            fab6.setVisibility(View.GONE);
            fab7.setVisibility(View.GONE);
            fab8.setVisibility(View.GONE);
            floatingActionMenu.close(true);

            status = 2;

            //최신
        } else if (id == R.id.nav_3) {
            webView.loadUrl("http://jteacher.net/m/group.php?gr_id=notice");
            fab1.setLabelText("JTA소개");
            fab2.setLabelText("연혁");
            fab3.setLabelText("가입인사 & point충전");
            fab4.setLabelText("홈피 운영자에게");
            fab5.setVisibility(View.GONE);
            fab5.setVisibility(View.GONE);
            fab6.setVisibility(View.GONE);
            fab7.setVisibility(View.GONE);
            fab8.setVisibility(View.GONE);
            fab1.setVisibility(View.VISIBLE);
            fab2.setVisibility(View.VISIBLE);
            fab3.setVisibility(View.VISIBLE);
            fab4.setVisibility(View.VISIBLE);
            floatingActionMenu.close(true);

            status = 3;

//JTA소개
        } else if (id == R.id.nav_4) {
            webView.loadUrl("http://jteacher.net/m/group.php?gr_id=board");
            fab1.setLabelText("공지사항");
            fab2.setLabelText("자유게시판");
            fab3.setLabelText("토론게시판");
            fab4.setLabelText("수업연구실");
            fab5.setLabelText("지식IN");
            fab6.setLabelText("선생님일기장");
            fab7.setLabelText("포토갤러리");
            fab8.setLabelText("동영상갤러리");
            fab1.setVisibility(View.VISIBLE);
            fab2.setVisibility(View.VISIBLE);
            fab3.setVisibility(View.VISIBLE);
            fab4.setVisibility(View.VISIBLE);
            fab5.setVisibility(View.VISIBLE);
            fab6.setVisibility(View.VISIBLE);
            fab7.setVisibility(View.VISIBLE);
            fab8.setVisibility(View.VISIBLE);
            floatingActionMenu.close(true);

            status = 4;

            //사랑방
        } else if (id == R.id.nav_5) {
            webView.loadUrl("http://jteacher.net/m/group.php?gr_id=StudyMater");
            fab1.setLabelText("중학교공통자료");
            fab2.setLabelText("다락원");
            fab3.setLabelText("미래앤");
            fab4.setLabelText("시사");
            fab5.setLabelText("천재[박민영]");
            fab6.setLabelText("천재[최충희]");
            fab7.setLabelText("중학교문제은행");
            fab1.setVisibility(View.VISIBLE);
            fab2.setVisibility(View.VISIBLE);
            fab3.setVisibility(View.VISIBLE);
            fab4.setVisibility(View.VISIBLE);
            fab5.setVisibility(View.VISIBLE);
            fab6.setVisibility(View.VISIBLE);
            fab7.setVisibility(View.VISIBLE);
            floatingActionMenu.close(true);

            status = 5;
            //중학교자료실
        } else if (id == R.id.nav_6) {
            webView.loadUrl("http://jteacher.net/m/group.php?gr_id=StudyMate2");
            fab1.setLabelText("교학");
            fab2.setLabelText("다락");
            fab3.setLabelText("미래엔");
            fab4.setLabelText("지학사");
            fab5.setLabelText("천재(임영철)");
            fab6.setLabelText("천재(최충희");
            fab7.setLabelText("문제은행");
            fab8.setLabelText("(구)교과서자료실");
            fab1.setVisibility(View.VISIBLE);
            fab2.setVisibility(View.VISIBLE);
            fab3.setVisibility(View.VISIBLE);
            fab4.setVisibility(View.VISIBLE);
            fab5.setVisibility(View.VISIBLE);
            fab6.setVisibility(View.VISIBLE);
            fab7.setVisibility(View.VISIBLE);
            fab8.setVisibility(View.VISIBLE);
            floatingActionMenu.close(true);
            status = 6;
            //고등학교자료실
        } else if (id == R.id.nav_7) {
            webView.loadUrl("http://jteacher.net/m/group.php?gr_id=multimedia");
            fab1.setLabelText("J-pop");
            fab2.setLabelText("학습용J-pop");
            fab3.setLabelText("동영상자료");
            fab4.setLabelText("학습용동영상자료");
            fab5.setLabelText("실시간동영상");
            fab6.setLabelText("학습용사진자료");
            fab7.setVisibility(View.GONE);
            fab8.setVisibility(View.GONE);
            fab1.setVisibility(View.VISIBLE);
            fab2.setVisibility(View.VISIBLE);
            fab3.setVisibility(View.VISIBLE);
            fab4.setVisibility(View.VISIBLE);
            fab5.setVisibility(View.VISIBLE);
            fab6.setVisibility(View.VISIBLE);
            floatingActionMenu.close(true);
            status = 7;
            //멀티자료실

        } else if (id == R.id.nav_8) {
            webView.loadUrl("http://jteacher.net/m/group.php?gr_id=public");
            fab1.setLabelText("문화수업자료");
            fab2.setLabelText("CA,동아리자료실");
            fab3.setLabelText("담임 및 업무관련자료");
            fab4.setLabelText("추천사이트");
            fab5.setLabelText("기출문제");
            fab6.setLabelText("추천도서");
            fab7.setLabelText("유학&여행&한일인류");
            fab8.setLabelText("연수자료");

            fab1.setVisibility(View.VISIBLE);
            fab2.setVisibility(View.VISIBLE);
            fab3.setVisibility(View.VISIBLE);
            fab4.setVisibility(View.VISIBLE);
            fab5.setVisibility(View.VISIBLE);
            fab6.setVisibility(View.VISIBLE);
            fab7.setVisibility(View.VISIBLE);
            fab8.setVisibility(View.VISIBLE);
            floatingActionMenu.close(true);


            status = 8;
            //커뮤니
        } else if (id == R.id.nav_9) {
            webView.loadUrl("http://jteacher.net/m/group.php?gr_id=localboard");
            fab1.setLabelText("경기");
            fab2.setLabelText("강원");
            fab3.setLabelText("충청");
            fab4.setLabelText("전라");
            fab5.setLabelText("경상");
            fab6.setLabelText("제주");
            fab7.setLabelText("원어민");
            fab8.setLabelText("국제문화포럼");
            fab1.setVisibility(View.VISIBLE);
            fab2.setVisibility(View.VISIBLE);
            fab3.setVisibility(View.VISIBLE);
            fab4.setVisibility(View.VISIBLE);
            fab5.setVisibility(View.VISIBLE);
            fab6.setVisibility(View.VISIBLE);
            fab7.setVisibility(View.VISIBLE);
            fab8.setVisibility(View.VISIBLE);
            floatingActionMenu.close(true);

            status = 9;

        } else {
            Intent act = new Intent(this, InfoActivity.class);
            startActivity(act);
        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                webView.loadUrl("http://jteacher.net/m/bbs/search.php?stx=" + s + "&sfl=wr_subject%7C%7Cwr_content&sop=and&x=0&y=0&where=m&sm=mtp_hty");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


        return true;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    public boolean isNetworkStat(Context context) {
        ConnectivityManager manager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo lte_4g = manager.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);
        boolean blte_4g = false;
        if (lte_4g != null)
            blte_4g = lte_4g.isConnected();
        if (mobile != null) {
            if (mobile.isConnected() || wifi.isConnected() || blte_4g)
                return true;
        } else {
            if (wifi.isConnected() || blte_4g)
                return true;
        }


        Intent intent = new Intent(getApplicationContext(), ErrorActivity.class);
        startActivity(intent);


        return false;
    }

    public View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            floatingActionMenu.close(true);
            if (status == 1) {
                switch (v.getId()) {
                    case R.id.fab1:
                        webView.loadUrl("http://jteacher.net/m/login.php?url=%2Fm%2Fgroup.php%3Fgr_id%3Dnotice");
                        break;
                    case R.id.fab2:
                        drawer.openDrawer(GravityCompat.START);
                        floatingActionMenu.close(true);
                        break;
                    case R.id.fab3:
                        break;
                    case R.id.fab4:
                        break;
                    case R.id.fab5:
                        break;
                    case R.id.fab6:
                        break;
                    case R.id.fab7:
                        break;
                    case R.id.fab8:
                        webView.loadUrl("http://jteacher.net/m/");
                        break;
                }
            }
            if (status == 2) {
                switch (v.getId()) {
                    case R.id.fab1:
                        break;
                    case R.id.fab2:
                        break;
                    case R.id.fab3:
                        break;
                    case R.id.fab4:
                        break;
                    case R.id.fab5:
                        break;
                    case R.id.fab6:
                        break;
                    case R.id.fab7:
                        break;
                    case R.id.fab8:
                        webView.loadUrl("http://jteacher.net/m/");
                        break;
                    //end

                }
            }
            if (status == 3) {
                switch (v.getId()) {
                    case R.id.fab1:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=jta");
                        break;
                    case R.id.fab2:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=jta&sca=intro");
                        break;
                    case R.id.fab3:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=guest");
                        break;
                    case R.id.fab4:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=jta01");
                        break;
                    case R.id.fab8:
                        webView.loadUrl("http://jteacher.net/m/");
                        break;
                }
            }
            if (status == 4) {
                switch (v.getId()) {
                    case R.id.fab1:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=notice");
                        break;
                    case R.id.fab2:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=free");
                        break;
                    case R.id.fab3:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=board1");
                        break;
                    case R.id.fab4:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=qna");
                        break;
                    case R.id.fab5:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=board3");
                        break;
                    case R.id.fab6:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=board4");
                        break;
                    case R.id.fab7:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=images1");
                        break;
                    case R.id.fab8:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=movie_test");
                        break;
                }
            }
            if (status == 5) {
                switch (v.getId()) {
                    case R.id.fab1:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=md_common");
                        break;
                    case R.id.fab2:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=md_darak");
                        break;
                    case R.id.fab3:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=md_mirae");
                        break;
                    case R.id.fab4:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=md_sisa");
                        break;
                    case R.id.fab5:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=md_chunjae");
                        break;
                    case R.id.fab6:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=md_chunjae2");
                        break;
                    case R.id.fab7:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=md_bank");
                        break;
                }
            }
            if (status == 6) {
                switch (v.getId()) {
                    case R.id.fab1:
                        //다락원
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=jta13");
                        break;
                    case R.id.fab2:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=jta15");
                        break;
                    case R.id.fab3:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=jta16");
                        break;
                    case R.id.fab4:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=jta17");
                        break;
                    case R.id.fab5:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=jta18");
                        break;
                    case R.id.fab6:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=jta19");
                        break;
                    case R.id.fab7:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=jta20");
                        break;
                    case R.id.fab8:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=jta14");
                        break;
                }
            }
            if (status == 7) {
                switch (v.getId()) {
                    case R.id.fab1:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=musicplay");
                        break;
                    case R.id.fab2:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=musicstudy");
                        break;
                    case R.id.fab3:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=movie");
                        break;
                    case R.id.fab4:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=moviestudy");
                        break;
                    case R.id.fab5:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=video");
                        break;
                    case R.id.fab6:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=gallery");
                        break;
                }
            }
            if (status == 8) {
                switch (v.getId()) {
                    case R.id.fab1:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=public4");
                        break;
                    case R.id.fab2:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=dongari");
                        break;
                    case R.id.fab3:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=cate");
                        break;
                    case R.id.fab4:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=public5");
                        break;
                    case R.id.fab5:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=public1");
                        break;
                    case R.id.fab6:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=honor");
                        break;
                    case R.id.fab7:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=public3");
                        break;
                    case R.id.fab8:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=public2");
                        break;
                }
            }
            if (status == 9) {
                switch (v.getId()) {
                    case R.id.fab1:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=localboard2");
                        break;
                    case R.id.fab2:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=localboard3");
                        break;
                    case R.id.fab3:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=localboard4");
                        break;
                    case R.id.fab4:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=localboard5");
                        break;
                    case R.id.fab5:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=localboard6");
                        break;
                    case R.id.fab6:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=localboard7");

                        break;
                    case R.id.fab7:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=Native");

                        break;
                    case R.id.fab8:
                        webView.loadUrl("http://jteacher.net/m/bbs/board.php?bo_table=tjf");

                        break;
                }
            }

        }

    };

    @Override
    protected void onResume() {
        super.onResume();
        CookieSyncManager.getInstance().startSync();
    }

    @Override
    protected void onPause() {
        super.onPause();
        CookieSyncManager.getInstance().stopSync();
    }

}
