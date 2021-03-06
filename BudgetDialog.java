package com.example.budgetsoftware;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class BudgetDialog extends AppCompatDialogFragment{
    private EditText edit_budget;
    private BudgetDialogListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
         View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view)
                .setTitle("Weekly Budget")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String budget = edit_budget.getText().toString();
                        listener.applyBudget(budget);

                    }
                });

        edit_budget = view.findViewById(R.id.edit_budget);
        return builder.create();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (BudgetDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement BudgetDialogListener");
        }
    }

    public interface BudgetDialogListener {

        void applyBudget(String budget);
    }

}
