package com.nasyarobby.droidnewsreader;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AddTopicDialogFragment extends DialogFragment {
    /**
     * The activity that creates an instance of this dialog fragment must implement this interface
     * in order to receive event callbacks. Each method passes the DialogFragment in case the host
     * needs to query it.
     */
    public interface AddTopicDialogListener {
        void onClickAddTopicButton(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    AddTopicDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.add_topic_dialog_fragment, null));
        builder.setTitle("Add Topic");
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (AddTopicDialogListener) context;
            //Toast.makeText(get, "Test", Toast.LENGTH_SHORT).show();
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement AddItemDialogListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // find the add button
        Button addButton = (Button) getDialog().findViewById(R.id.add_topic_button);

        // set the listener to both of them
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickAddTopicButton(AddTopicDialogFragment.this);
            }
        });
    }
}
