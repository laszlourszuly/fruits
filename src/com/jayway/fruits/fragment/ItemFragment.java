package com.jayway.fruits.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jayway.fruits.R;
import com.jayway.fruits.content.Content;
import com.jayway.fruits.content.Content.Fruit;

public final class ItemFragment extends Fragment {

    public static final String EXTRA_ITEM_ID = "ItemFragment.ITEM_ID";

    private static final String TAG = ItemFragment.class.getName();

    private OnRequestListener<Fruit> listener;

    private TextView title;
    private TextView description;
    private ImageView image;

    private long itemId;

    public void showItem(long id) {
        itemId = id;
        populateUi(itemId);
    }

    public void reloadCurrentItem() {
        populateUi(itemId);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (OnRequestListener<Fruit>) activity;
        } catch (ClassCastException e) {
            Log.d(TAG, "Couldn't initialize callback listener", e);
            throw new IllegalArgumentException();
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
        menuInflater.inflate(R.menu.edit, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, null, false);
        title = (TextView) view.findViewById(R.id.title);
        description = (TextView) view.findViewById(R.id.description);
        image = (ImageView) view.findViewById(R.id.image);

        Bundle arguments = getArguments();
        itemId = arguments != null ? arguments.getLong(EXTRA_ITEM_ID, -1L) : -1L;
        populateUi(itemId);

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.action_edit:
            listener.onRequestItemEdit(itemId);
            return true;
        case R.id.action_delete:
            listener.onRequestItemDelete(itemId);
            return true;
        default:
            return false;
        }
    }

    private void populateUi(long id) {
        Fruit item = id > 0 ? listener.onRequestItem(id) : null;
        boolean isValid = item != null;

        title.setText(isValid ? item.name : "");
        description.setText(isValid ? item.description : "");

        (new AsyncTask<String, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(String... urls) {
                Bitmap bitmap = null;
                String url = urls[0];
                if (url != null) {
                    Uri uri = Uri.parse(url);
                    Context context = ItemFragment.this.getActivity();
                    bitmap = Content.getBitmap(context, uri);
                }
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                image.setImageBitmap(result);
            }
        }).execute(isValid ? item.image : null);
    }
}
