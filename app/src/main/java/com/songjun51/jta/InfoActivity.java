package com.songjun51.jta;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity {

    Toolbar toolbar;
    ArrayList<StudentData> arrayList;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView = (ListView)  findViewById(R.id.info_listview);
        setSupportActionBar(toolbar);
        toolbar.setTitle("정보");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        arrayList = new ArrayList<>();
        arrayList.add(new StudentData("정보"));
        arrayList.add(new StudentData("건의사항"));
        arrayList.add(new StudentData("버전 1.2"));
        arrayList.add(new StudentData("개발자 정보"));
        DataAdapter adapters = new DataAdapter(getApplication(), arrayList);
        listView.setAdapter(adapters);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(InfoActivity.this);
                if(position==0){
                    dialog.setMessage("Japanese Teacher's Association \n 한국일본어교육연구회 어플리케이션");
                    dialog.create();
                    dialog.show();
                } else if(position==1){

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://blog.songjun51.com/220785730543")));
                } else if (position==2) {
                    dialog.setMessage("");
                    dialog.create();
                    dialog.show();
                }else{
                    dialog.setMessage("blog : blog.songjun51.com\n" +
                            "mr ail : song@wonjun.kr\n" +
                            "website : wongjun.kr");
                    dialog.create();
                    dialog.show();
                }
            }
        });

    }



}
