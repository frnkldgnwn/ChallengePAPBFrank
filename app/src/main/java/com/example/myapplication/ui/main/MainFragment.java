package com.example.myapplication.ui.main;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProviders;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private TextView number1, number2, number3;
    private Button start, stop;
    private boolean isRunningNum1 = false;
    private boolean isRunningNum2 = false;
    private boolean isRunningNum3 = false;
    private SharedPreferences mPreferences;
    int interval, jackpotNum;
    int randomNum1, randomNum2, randomNum3;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        final View root = inflater.inflate(R.layout.main_fragment, container, false);

        initializeUI(root);
        initChannels(getActivity());
        mPreferences = getActivity().getSharedPreferences(getActivity().getApplication().toString(), Context.MODE_PRIVATE);

        final Handler handler = new Handler();
        interval = mPreferences.getInt("INTERVAL", 0) * 100;
        final Runnable runnableNum1 = new Runnable() {
            @Override
            public void run() {
                randomNum1 = (int) (Math.random() * 10);
                number1.setText(Integer.toString(randomNum1));
                handler.postDelayed(this, interval);
            }
        };

        final Runnable runnableNum2 = new Runnable() {
            @Override
            public void run() {
                randomNum2 = (int) (Math.random() * 10);
                number2.setText(Integer.toString(randomNum2));

                handler.postDelayed(this, interval);
            }
        };

        final Runnable runnableNum3 = new Runnable() {
            @Override
            public void run() {
                randomNum3 = (int) (Math.random() * 10);
                number3.setText(Integer.toString(randomNum3));

                handler.postDelayed(this, interval);
            }
        };

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interval = mPreferences.getInt("INTERVAL", 0) * 100;
                handler.removeCallbacks(runnableNum1);
                handler.removeCallbacks(runnableNum2);
                handler.removeCallbacks(runnableNum3);

                if (!isRunningNum1) {
                    isRunningNum1 = true;
                    handler.post(runnableNum1);
                } else {
                    handler.postDelayed(runnableNum1, interval);
                }

                if (!isRunningNum2) {
                    isRunningNum2 = true;
                    handler.post(runnableNum2);
                } else {
                    handler.postDelayed(runnableNum2, interval);
                }

                if (!isRunningNum3) {
                    isRunningNum3 = true;
                    handler.post(runnableNum3);
                } else {
                    handler.postDelayed(runnableNum3, interval);
                }

                Toast.makeText(getActivity(), "Handler running..", Toast.LENGTH_SHORT).show();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunningNum1) {
                    isRunningNum1 = false;
                    handler.removeCallbacks(runnableNum1);
                    Toast.makeText(getActivity(), "Number 1 stopped..", Toast.LENGTH_SHORT).show();
                } else if (isRunningNum2) {
                    isRunningNum2 = false;
                    handler.removeCallbacks(runnableNum2);
                    Toast.makeText(getActivity(), "Number 2 stopped..", Toast.LENGTH_SHORT).show();
                } else if (isRunningNum3) {
                    isRunningNum3 = false;
                    handler.removeCallbacks(runnableNum3);
                    Toast.makeText(getActivity(), "Number 3 stopped..", Toast.LENGTH_SHORT).show();
                }

                if (!isRunningNum1 & !isRunningNum2 & !isRunningNum3) {
                    jackpotNum = mPreferences.getInt("JACKPOT_NUM", 0);
                    if (randomNum1 == randomNum2 && randomNum2 == randomNum3 && randomNum1 == randomNum3) {
                        if (randomNum1 == jackpotNum) {
                            sendNotification();
                        }
                    }
                }
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }

    private void initializeUI(View root) {
        number1 = root.findViewById(R.id.text_number1);
        number2 = root.findViewById(R.id.text_number2);
        number3 = root.findViewById(R.id.text_number3);
        start = root.findViewById(R.id.start_button);
        stop = root.findViewById(R.id.stop_button);
    }

    public void sendNotification() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getActivity(), "default")
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("HORRRAYYYY")
                        .setContentText("You're one lucky guy!");
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(01, mBuilder.build());

    }

    public void initChannels(Context context) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel("default",
                "Channel name",
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Channel description");
        notificationManager.createNotificationChannel(channel);
    }

}
