package com.eit.paulamanz.firstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import java.util.List;

import es.upm.hcid.pui.assignment.Article;
import es.upm.hcid.pui.assignment.exceptions.ServerCommunicationError;

public class NewsListActivity extends AppCompatActivity {

    //public static TextView txt; // temporal, para ver los resultados
    public ListView list;

    String[] itemname ={
            "Safari",
            "Camera",
            "Global",
            "FireFox",
            "UC Browser",
            "Android Folder",
    };

    Integer[] imgid={
            R.drawable.puppy1,
            R.drawable.puppy2,
            R.drawable.puppy3,
            R.drawable.puppy4,
            R.drawable.puppy5,
            R.drawable.puppy6
    };

    CustomListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_list);
        Intent intent = getIntent();

        ModelManagerHandler modelManagerHandler = ModelManagerHandler.getInstance();
        final String value = intent.getStringExtra("key"); //The string we stored

        GetArticlesTask getArticlesTask = new GetArticlesTask(this, this);
        getArticlesTask.execute();

    }


    protected void updateList(final List<Article> articleList){

        adapter=new CustomListAdapter(this, articleList);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Intent to details activity
                String Slecteditem="Item selected";
                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();

                Article a = articleList.get(position);

                Intent myIntent = new Intent(getApplicationContext(),NewsDetails.class);
                myIntent.putExtra("key",a.getId());
                NewsListActivity.this.startActivity(myIntent);

            }
        });

    }
}
