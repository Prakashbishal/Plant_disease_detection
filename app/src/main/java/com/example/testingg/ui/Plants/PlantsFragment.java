package com.example.testingg.ui.Plants;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testingg.R;

public class PlantsFragment extends Fragment {

        // Required empty public constructor
        public PlantsFragment() {
        }

        // Optional: You can include any initialization logic here
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        // Inflate the fragment's layout and set up any view components or event listeners
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_plants, container, false);
        }
    }