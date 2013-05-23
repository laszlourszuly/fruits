package com.jayway.fruits.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.jayway.fruits.R;
import com.jayway.fruits.content.Content.Fruit;

public final class EditDialogFragment extends DialogFragment {

    public static final String EXTRA_ITEM = "EditDialogFragment.ITEM";

    private static final String TAG = EditDialogFragment.class.getName();

    private EditText title;
    private EditText description;

    private OnRequestListener<Fruit> listener;
    private Fruit item;

    private OnClickListener dialogListener = new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                item.name = title.getText().toString();
                item.description = description.getText().toString();
                listener.onRequestItemSave(item);
            }
            dialog.dismiss();
        }
    };

    public static void show(FragmentManager fragmentManager, Fruit item) {
        if (fragmentManager != null) {
            Bundle arguments = new Bundle();
            arguments.putSerializable(EditDialogFragment.EXTRA_ITEM, item);
            EditDialogFragment dialog = new EditDialogFragment();
            dialog.setArguments(arguments);
            dialog.show(fragmentManager, "EditDialogFragment");
        }
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
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_edit, null, false);
        title = (EditText) view.findViewById(R.id.title);
        description = (EditText) view.findViewById(R.id.description);

        Bundle arguments = getArguments();
        Fruit item = arguments != null ? (Fruit) arguments.getSerializable(EXTRA_ITEM) : null;
        loadItem(item);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(R.string.action_edit);
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setPositiveButton(R.string.action_save, dialogListener);
        alertDialogBuilder.setNegativeButton(R.string.action_cancel, dialogListener);

        return alertDialogBuilder.create();
    }

    private void loadItem(Fruit item) {
        this.item = item;
        boolean isValid = item != null;
        String t = isValid ? item.name : null;
        String d = isValid ? item.description : null;
        title.setText(t);
        description.setText(d);
    }
}
