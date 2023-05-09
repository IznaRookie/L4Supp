package com.example.l4supp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

public class InfoFragment extends Fragment {
    public InfoFragment() {
        super(R.layout.fragment_info);
    }

    public static InfoFragment newInstance() {
        return new InfoFragment();
    }
}