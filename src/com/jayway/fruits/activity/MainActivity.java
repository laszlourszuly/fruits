package com.jayway.fruits.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import com.jayway.fruits.R;
import com.jayway.fruits.fragment.ItemFragment;
import com.jayway.fruits.fragment.ItemListFragment;

public class MainActivity extends AbstractRequestActivity {

    private static final int ID_ACTIVITY_DETAIL = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == ID_ACTIVITY_DETAIL) && (resultCode == Activity.RESULT_OK)) {
            long itemId = data.getLongExtra(DetailActivity.EXTRA_ITEM_ID, -1L);
            showItem(itemId);
        }
    }

    @Override
    protected void reloadContent() {
        FragmentManager fragmentManager = getFragmentManager();
        ItemListFragment listFragment = (ItemListFragment) fragmentManager.findFragmentById(R.id.fragment_list);
        listFragment.refreshItems();

        ItemFragment itemFragment = (ItemFragment) fragmentManager.findFragmentById(R.id.fragment_detail);
        if (itemFragment != null) {
            itemFragment.reloadCurrentItem();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onRequestItemClick(long id) {
        showItem(id);
    }

    @Override
    public void onRequestItemDelete(long id) {
        super.onRequestItemDelete(id);
        reloadContent();
    }

    protected void showItem(long id) {
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();

        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            FragmentManager manager = getFragmentManager();
            ItemFragment fragment = (ItemFragment) manager.findFragmentById(R.id.fragment_detail);
            fragment.showItem(id);
        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_ITEM_ID, id);
            startActivity(intent);
        }
    }
}
