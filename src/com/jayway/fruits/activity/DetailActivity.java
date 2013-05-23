package com.jayway.fruits.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.jayway.fruits.R;
import com.jayway.fruits.fragment.ItemFragment;

public class DetailActivity extends AbstractRequestActivity {

    public static final String EXTRA_ITEM_ID = "DetailActivity.ITEM_ID";
    private static final String TAG = DetailActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        long itemId = intent.getLongExtra(EXTRA_ITEM_ID, -1L);
        FragmentManager fragmentManager = getFragmentManager();
        ItemFragment itemFragment = (ItemFragment) fragmentManager.findFragmentById(R.id.fragment_detail);
        itemFragment.showItem(itemId);
    }

    @Override
    protected void reloadContent() {
        FragmentManager fragmentManager = getFragmentManager();
        ItemFragment itemFragment = (ItemFragment) fragmentManager.findFragmentById(R.id.fragment_detail);
        itemFragment.reloadCurrentItem();
    }

    @Override
    public void onRequestItemDelete(long id) {
        super.onRequestItemDelete(id);
        finish();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation != Configuration.ORIENTATION_PORTRAIT) {
            Log.d(TAG, "Not portrait mode, shutting down");
            setResult(RESULT_OK, getIntent());
            finish();
        }
    }
}
