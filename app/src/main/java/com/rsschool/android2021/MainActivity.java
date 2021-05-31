package com.rsschool.android2021;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements FirstFragment.ClickGenerateButton, SecondFragment.ClickBackButton  {
    private FragmentType actualFragment;
    private static int previousNumber = 0;
    private static int previousMax = 0;
    private static int previousMin = 0;

    private enum FragmentType {
        FIRST_FRAGMENT, SECOND_FRAGMENT;
    }

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

    @Override
    public void onBackPressed() {
        if (actualFragment == FragmentType.SECOND_FRAGMENT ){
            openFirstFragment(previousNumber);
        } else{
            super.onBackPressed();
        }
    }

    private void openFirstFragment(int previousNumber) {
        final Fragment firstFragment = FirstFragment.newInstance(previousNumber);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, firstFragment);
        actualFragment = FragmentType.FIRST_FRAGMENT;
        transaction.commit();
    }

    private void openSecondFragment(int min, int max) {
        final Fragment secondFragment = SecondFragment.newInstance(min, max);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, secondFragment);
        actualFragment = FragmentType.SECOND_FRAGMENT;
        transaction.commit();
    }

    @Override
    public void onFirstFragmentButtonClick(int min, int max) {
        previousMin = min;
        previousMax = max;
        openSecondFragment(min, max);
    }

    @Override
    public void onFirstFragmentExceptionClick(@NotNull String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSecondFragmentButtonClick() {
        openFirstFragment(previousNumber);
    }

    @Override
    public void previousGenerated(int number){
        previousNumber = number;
    }

}
