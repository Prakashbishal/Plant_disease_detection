package com.example.testingg.ui.home;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testingg.R;

import java.util.HashMap;
import java.util.Map;

public class PreventionsActivity extends AppCompatActivity {

    private TextView preventionTextView;

    private static final Map<String, String> preventionsMap = new HashMap<>();

    static {
        preventionsMap.put("Corn (maize) \n Disease: Cercospora leaf spot \n and Gray leaf spot",
                "Rotate crops to avoid the build-up of pathogens.\n" +
                        "Use resistant hybrids.\n" +
                        "Apply fungicides as necessary.\n" +
                        "Maintain proper plant density to improve air circulation."
        );
        preventionsMap.put("Corn (maize)" + "\n" + "Disease: Common rust",
                "Plant resistant varieties.\n" +
                        "Apply fungicides if rust appears.\n" +
                        "Rotate crops and manage debris to reduce inoculum."
        );
        preventionsMap.put("Corn (maize) \n It is healthy",
                "Follow best agricultural practices.\n" +
                        "Regular monitoring and early detection.\n" +
                        "Use certified disease-free seeds.\n" +
                        "Maintain good soil health and nutrient management."
        );
        preventionsMap.put("Potato \n Disease: Early blight",
                "Use resistant varieties.\n" +
                        "Apply fungicides early in the season.\n" +
                        "Practice crop rotation and destroy infected plant debris.\n" +
                        "Avoid overhead irrigation to reduce leaf wetness."
        );
        preventionsMap.put("Potato \n It is healthy",
                "Ensure good agricultural practices.\n" +
                        "Use disease-free seeds.\n" +
                        "Regular monitoring and early intervention.\n" +
                        "Maintain balanced soil fertility and proper irrigation."
        );
        preventionsMap.put("Tomato \n Disease: Bacterial spot",
                "Use certified disease-free seeds and transplants.\n" +
                        "Apply copper-based bactericides.\n" +
                        "Rotate crops and avoid overhead watering.\n" +
                        "Remove and destroy infected plant debris."
        );
        preventionsMap.put("Tomato \n Disease: Spider mites \n Two-spotted spidermite",
                "Regular monitoring and early detection.\n" +
                        "Use miticides or insecticidal soaps.\n" +
                        "Encourage natural predators like ladybugs.\n" +
                        "Maintain proper irrigation to reduce plant stress."
        );
        preventionsMap.put("Tomato \n Disease: Yellow Leaf Curl Virus",
                "Use resistant varieties.\n" +
                        "Control whitefly population with insecticides.\n" +
                        "Use reflective mulches to repel whiteflies.\n" +
                        "Remove and destroy infected plants."
        );
        preventionsMap.put("Tomato \n Disease: Mosaic virus",
                "Use virus-free seeds and resistant varieties.\n" +
                        "Disinfect tools and hands regularly.\n" +
                        "Avoid working with wet plants to reduce spread.\n" +
                        "Remove and destroy infected plants."
        );
        preventionsMap.put("Tomato \n It is healthy",
                "Implement best agricultural practices.\n" +
                        "Regular monitoring and early detection.\n" +
                        "Use certified disease-free seeds and transplants.\n" +
                        "Maintain good soil health and proper plant spacing."
        );
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preventions);

        preventionTextView = findViewById(R.id.prevention_text_view);
        TextView resultTextView = findViewById(R.id.result_text_view);

        String result = getIntent().getStringExtra("result");
        if (result != null) {
            resultTextView.setText(result);
            String preventions = preventionsMap.get(result);
            if (preventions != null) {
                String numberedPreventions = addNumberingToPreventions(preventions);
                preventionTextView.setText(numberedPreventions);
            } else {
                preventionTextView.setText("No preventions available");
            }
        }
    }

    private String addNumberingToPreventions(String preventions) {
        String[] lines = preventions.split("\n");
        StringBuilder numberedPreventions = new StringBuilder();
        int number = 1;
        for (String line : lines) {
            numberedPreventions.append(number).append(". ").append(line.trim()).append("\n");
            number++;
        }
        return numberedPreventions.toString().trim();
    }
}
