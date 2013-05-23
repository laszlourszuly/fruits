package com.jayway.fruits.activity;

import java.util.List;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

import com.jayway.fruits.ParcelApplication;
import com.jayway.fruits.content.Content.Fruit;
import com.jayway.fruits.fragment.EditDialogFragment;
import com.jayway.fruits.fragment.OnRequestListener;

public abstract class AbstractRequestActivity extends Activity implements OnRequestListener<Fruit> {

    private ParcelApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (ParcelApplication) getApplication();
    }

    @Override
    public void onRequestItemClick(long id) {
    }

    @Override
    public void onRequestItemDelete(long id) {
        application.deleteItem(id);
    }

    @Override
    public void onRequestItemEdit(long id) {
        Fruit item = application.getItem(id);
        if (item == null) {
            item = application.createNewItem();
        }
        FragmentManager manager = getFragmentManager();
        EditDialogFragment.show(manager, item);
    }

    @Override
    public Fruit onRequestItem(long id) {
        return application.getItem(id);
    }

    @Override
    public List<Fruit> onRequestItemList() {
        return application.getItems();
    }

    @Override
    public void onRequestItemSave(Fruit item) {
        application.saveItem(item);
        reloadContent();
    }

    protected abstract void reloadContent();
}
