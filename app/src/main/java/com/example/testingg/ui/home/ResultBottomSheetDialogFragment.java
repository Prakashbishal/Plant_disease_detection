package com.example.testingg.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.testingg.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.HashMap;
import java.util.Map;

public class ResultBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private static final String ARG_RESULT = "result";

    private static final Map<String, String> symptomMap = new HashMap<>();

    static {
        symptomMap.put("Corn (maize) \n Disease: Cercospora leaf spot \n and Gray leaf spot", " 1. Small, rectangular lesions that are gray to tan in color.\n" + " 2. Affects photosynthesis, reducing yield.");
        symptomMap.put("Corn (maize)" + "\n" + "Disease: Common rust", "1. Orange to reddish-brown pustules on leaves.\n" + "2. Can cause significant yield loss if severe.");
        symptomMap.put("Corn (maize) \n It is healthy", "No visible disease symptoms.");
        symptomMap.put("Potato \n Disease: Early blight", "1. Dark brown spots with concentric rings on leaves and stems.\n" + "2. Can lead to significant yield losses.");
        symptomMap.put("Potato \n It is healthy", "No visible disease symptoms.");
        symptomMap.put("Tomato \n Disease: Bacterial spot", "1. Small, water-soaked spots on leaves, stems, and fruits.\n" + "2. Can lead to significant fruit blemishes and loss.");
        symptomMap.put("Tomato \n Disease: Spider mites \n Two-spotted spidermite", "1. Yellowing and stippling on leaves, webbing under severe infestation.\n" + "2. Can reduce plant vigor and yield.");
        symptomMap.put("Tomato \n Disease: Yellow Leaf Curl Virus", "1. Yellowing and upward curling of leaves, stunted growth.\n" + "2. Transmitted by whiteflies.");
        symptomMap.put("Tomato \n Disease: Mosaic virus", "1. Mottled leaves, stunted growth, and reduced fruit quality.\n" + "2. Highly contagious and can spread through mechanical means.");
        symptomMap.put("Tomato \n It is healthy","No visible disease symptoms.");
    }

    private String result;

    public static ResultBottomSheetDialogFragment newInstance(String result) {
        ResultBottomSheetDialogFragment fragment = new ResultBottomSheetDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_RESULT, result);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_bottom_sheet, container, false);

        TextView resultTextView = view.findViewById(R.id.result_text_view);
        TextView symptomTextView = view.findViewById(R.id.symptoms_text_view);
        Button preventionButton = view.findViewById(R.id.prevention_bt);

        if (getArguments() != null) {
            result = getArguments().getString(ARG_RESULT);
            resultTextView.setText(result);

            String symptoms = symptomMap.get(result);
            if (symptoms != null) {
                symptomTextView.setText(symptoms);
            } else {
                symptomTextView.setText("Symptoms not available.");
            }

            preventionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Open PreventionsActivity and pass the disease result
                    Intent intent = new Intent(getContext(), LoadingActivity.class);
                    intent.putExtra("result", result);
                    startActivity(intent);
                }
            });
        }

        return view;
    }
}
