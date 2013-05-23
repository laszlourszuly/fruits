package com.jayway.fruits.fragment;

import java.util.List;

public interface OnRequestListener<T> {
    public void onRequestItemSave(T item);

    public void onRequestItemEdit(long id);

    public void onRequestItemDelete(long id);

    public void onRequestItemClick(long id);

    public T onRequestItem(long id);

    public List<T> onRequestItemList();

}
