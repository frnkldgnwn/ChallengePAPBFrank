package com.example.myapplication.ui.settings;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;

public class SettingsFragment extends Fragment {

    private SettingsViewModel mViewModel;
    private SharedPreferences sharedPreferences;
    private EditText interval;
    private EditText jackpotNum;
    private Button saveButton;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.settings_fragment, container, false);

        sharedPreferences = getActivity().getSharedPreferences(getActivity().getApplication().toString(), Context.MODE_PRIVATE);

        initializeUI(root);

        interval.setText(Integer.toString(sharedPreferences.getInt("INTERVAL", 0)));
        jackpotNum.setText(Integer.toString(sharedPreferences.getInt("JACKPOT_NUM", 0)));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getActivity().getApplication().toString(), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.putInt("INTERVAL", Integer.parseInt(interval.getText().toString()));
                editor.putInt("JACKPOT_NUM", Integer.parseInt(jackpotNum.getText().toString()));
                editor.apply();

                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).popBackStack();
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
        // TODO: Use the ViewModel
    }

    private void initializeUI(View root) {
        interval = root.findViewById(R.id.interval);
        jackpotNum = root.findViewById(R.id.jackpot_num);
        saveButton = root.findViewById(R.id.save_button);
    }

}
