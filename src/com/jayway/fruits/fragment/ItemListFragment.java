package com.jayway.fruits.fragment;

import java.util.List;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jayway.fruits.R;
import com.jayway.fruits.content.Content.Fruit;
import com.jayway.fruits.content.FruitAdapter;

public final class ItemListFragment extends ListFragment {

    private static final String TAG = ItemListFragment.class.getName();

    private OnRequestListener<Fruit> listener;
    private FruitAdapter adapter;

    @Override
    @SuppressWarnings("unchecked")
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (OnRequestListener<Fruit>) activity;
        } catch (ClassCastException e) {
            listener = null;
            Log.d(TAG, "Couldn't initialize callback listener", e);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Activity activity = getActivity();
        MenuInflater menuInflater = activity.getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(android.R.layout.list_content, null, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshItems();
    }

    public void refreshItems() {
        List<Fruit> items = listener.onRequestItemList();
        Context context = getActivity();
        adapter = new FruitAdapter(context, items);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView list, View view, int position, long id) {
        if (listener != null) {
            listener.onRequestItemClick(id);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.action_add:
            listener.onRequestItemEdit(-1L);
            return true;
        default:
            return false;
        }
    }
}
