package com.jayway.fruits;

import java.util.List;

import android.app.Application;

import com.jayway.fruits.content.Content;
import com.jayway.fruits.content.Content.Fruit;

public final class ParcelApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Content.load(getApplicationContext());
    }

    public void deleteItem(long id) {
        Content.deleteItem(id);
    }

    public Fruit createNewItem() {
        return Content.createEmptyItem();
    }

    public void saveItem(Fruit item) {
        Content.saveItem(item);
    }

    public List<Fruit> getItems() {
        return Content.getItems();
    }

    public Fruit getItem(long id) {
        return Content.getItem(id);
    }

    public void unloadContent() {
        Content.unload();
    }
}
