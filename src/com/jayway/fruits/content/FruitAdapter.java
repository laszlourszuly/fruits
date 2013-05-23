package com.jayway.fruits.content;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jayway.fruits.content.Content.Fruit;

public class FruitAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private List<Fruit> items;

    public FruitAdapter(Context context, List<Fruit> items) {
        this.inflater = LayoutInflater.from(context);
        this.items = items;
    }

    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return (position >= 0) && (position < getCount()) ? items.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        Fruit item = (Fruit) getItem(position);
        return item != null ? item.id : -1L;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = convertView != null ?
                (TextView) convertView :
                (TextView) inflater.inflate(android.R.layout.simple_list_item_1, null, false);

        Fruit item = (Fruit) getItem(position);
        view.setText(item.name);

        return view;
    }
}
