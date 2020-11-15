package Model;

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

import com.example.projekt_bd.R;

public class WorkoutDialog extends AppCompatDialogFragment {

    private EditText workoutName;
    private EditText workoutCategory;
    private WorkoutDialogListener workoutDialogListener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.workout_dialog,null);

        builder.setView(view)
                .setTitle(getResources().getString(R.string.addExer))
                .setNegativeButton(getResources().getString(R.string.back), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton(getResources().getString(R.string.addExer), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = workoutName.getText().toString();
                        String category = workoutCategory.getText().toString();
                        workoutDialogListener.getTexts(name,category);
                    }
                });

        workoutName = (EditText)view.findViewById(R.id.workoutAddName);
        workoutCategory = (EditText) view.findViewById(R.id.workoutAddCategory);
        return builder.create();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            workoutDialogListener = (WorkoutDialogListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface WorkoutDialogListener{
        void getTexts(String name,String category);
    }
}
