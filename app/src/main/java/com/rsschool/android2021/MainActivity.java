package com.rsschool.android2021;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements FirstFragment.ClickButton  {
    private static int previousNumber = 0;
    private static int previousMax = 0;
    private static int previousMin = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            previousNumber = savedInstanceState.getInt("previousNumber", 0);
            previousMax = savedInstanceState.getInt("previousMax", 0);
            previousMin = savedInstanceState.getInt("previousMin", 0);
        }
        this.getSupportFragmentManager().popBackStack();
        openFirstFragment(previousNumber);
    }

    private void openFirstFragment(int previousNumber) {
        final Fragment firstFragment = FirstFragment.newInstance(previousNumber);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, firstFragment);
        transaction.commit();
    }

    private void openSecondFragment(int min, int max) {
        // TODO: implement it
    }

    @Override
    public void onFirstFragmentButtonClick(Integer min, Integer max) {
       /* previousMin = min;
        previousMax = max;*/
        Toast.makeText(this, "min " + min + ",max " + max, Toast.LENGTH_LONG).show();
    }
}
