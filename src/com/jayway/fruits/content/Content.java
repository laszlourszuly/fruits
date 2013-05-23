package com.jayway.fruits.content;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;

public final class Content {

    private static final String TAG = Content.class.getName();

    private static FruitList instance = null;

    private Content() {
    }

    public static void load(Context context) {
        if ((instance == null) && (context != null)) {
            AssetManager manager = context.getAssets();
            InputStreamReader reader = null;
            InputStream inputStream = null;

            try {
                inputStream = manager.open("items.json");
                reader = new InputStreamReader(inputStream);
                Gson gson = new Gson();
                instance = gson.fromJson(reader, FruitList.class);
            } catch (IOException e) {
                Log.e(TAG, "Couldn't read fruit");
            } finally {
                closeSilently(inputStream);
                closeSilently(reader);
            }
        }
    }

    public static void deleteItem(long id) {
        if (instance != null) {
            for (Fruit f : instance.fruit) {
                if (f.id == id) {
                    instance.fruit.remove(f);
                    break;
                }
            }
        }
    }

    public static Fruit createEmptyItem() {
        return new Fruit();
    }

    public static void saveItem(Fruit item) {
        if ((instance != null) && (item != null)) {
            if (item.id > 0L) {
                instance.fruit.remove(item);
            } else {
                item.id = System.currentTimeMillis();
            }
            instance.fruit.add(item);
        }
    }

    public static List<Fruit> getItems() {
        return instance != null ? instance.fruit : null;
    }

    public static Fruit getItemAt(int position) {
        return instance != null ? instance.fruit.get(position) : null;
    }

    public static Fruit getItem(long id) {
        if (instance != null) {
            for (Fruit f : instance.fruit) {
                if (f.id == id) {
                    return f;
                }
            }
        }

        return null;
    }

    public static Bitmap getBitmap(Context context, Uri uri) {
        Bitmap bitmap = null;

        if ((context != null) && (uri != null)) {
            String scheme = uri.getScheme();

            if (scheme != null) {
                if ("file".equals(scheme)) {
                    bitmap = decodeAssetsResource(context, uri);
                } else if ("http".equals(scheme)) {
                    // bitmap = decodeWebResource(context, uri);
                }
            }
        }

        return bitmap;
    }

    private static Bitmap decodeAssetsResource(Context context, Uri uri) {
        AssetManager manager = context.getAssets();
        InputStream inputStream = null;
        Bitmap bitmap = null;
        String file = uri.getAuthority();

        try {
            inputStream = manager.open(file);
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            Log.e(TAG, "Couldn't read bitmap: " + file);
            bitmap = null;
        } finally {
            closeSilently(inputStream);
        }

        return bitmap;
    }

    public static void unload() {
        if (instance != null) {
            instance.fruit.clear();
            instance.fruit = null;
            instance = null;
        }
    }

    private static void closeSilently(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                Log.d(TAG, "Couldn't close resource");
            }
        }
    }

    private static final class FruitList {
        private List<Fruit> fruit;
    }

    public static final class Fruit implements Serializable {
        private static final long serialVersionUID = -6861038465275311934L;

        public long id;
        public String name;
        public String description;
        public String image;

        private Fruit() {
        }

        @Override
        public boolean equals(Object other) {
            return (other instanceof Fruit) && (((Fruit) other).id == this.id);
        }

        @Override
        public int hashCode() {
            return Long.valueOf(id).hashCode();
        }
    }
}
