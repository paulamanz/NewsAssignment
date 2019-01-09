package com.eit.paulamanz.firstapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import es.upm.hcid.pui.assignment.Article;
import es.upm.hcid.pui.assignment.Utils;


public class CustomListAdapter extends ArrayAdapter<Article> {

    private final Activity context;
    private final List<Article> itemlist;


    public CustomListAdapter(Activity context, List<Article> itemlist) {
        super(context, R.layout.mylist, itemlist);

        this.context = context;
        this.itemlist = itemlist;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.mylist, null, true);

        TextView txtTitle = rowView.findViewById(R.id.item);
        ImageView imageView = rowView.findViewById(R.id.icon);

        Article article = itemlist.get(position);

        txtTitle.setText(article.getTitleText());
        imageView.setImageBitmap(Utils.base64StringToImg(article.getImage().getImage()));

        return rowView;
    }

    ;
}