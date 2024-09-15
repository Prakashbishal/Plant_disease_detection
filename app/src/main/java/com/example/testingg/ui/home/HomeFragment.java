package com.example.testingg.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.example.testingg.R;
import com.example.testingg.databinding.FragmentHomeBinding;
import com.example.testingg.ml.CompressedModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;

public class HomeFragment extends Fragment {

    private ImageView imgview;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private Button predict;
    private FloatingActionButton fab, fab2;
    private Bitmap img;
    private FragmentHomeBinding binding;
    private Uri imageUri;

    private LottieAnimationView loadingAnimation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        imgview = binding.imageView3;
        predict = binding.predictBtn;
        fab2 = binding.fab2;
        fab = binding.fab;
        loadingAnimation = root.findViewById(R.id.loadingAnimationView);

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 100);
            }
        });

        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (img == null) {
                    showAlertDialog();
                } else {
                    loadingAnimation.setVisibility(View.VISIBLE);
                    loadingAnimation.playAnimation();

                    img = Bitmap.createScaledBitmap(img, 128, 128, true);
                    try {
                        CompressedModel model = CompressedModel.newInstance(getContext().getApplicationContext());

                        TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 128, 128, 3}, DataType.FLOAT32);
                        TensorImage tensorImage = new TensorImage(DataType.FLOAT32);
                        tensorImage.load(img);
                        ByteBuffer byteBuffer = tensorImage.getBuffer();
                        inputFeature0.loadBuffer(byteBuffer);

                        CompressedModel.Outputs outputs = model.process(inputFeature0);
                        TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                        float[] outputArray = outputFeature0.getFloatArray();
                        float maxOutput = Float.NEGATIVE_INFINITY;
                        int maxIndex = -1;
                        for (int i = 0; i < outputArray.length; i++) {
                            if (outputArray[i] > maxOutput) {
                                maxOutput = outputArray[i];
                                maxIndex = i;
                            }
                        }

                        String[] classNames = {
                                "Corn (maize) \n Disease: Cercospora leaf spot \n and Gray leaf spot",
                                "Corn (maize)" + "\n" + "Disease: Common rust",
                                "Corn (maize) \n It is healthy",
                                "Potato \n Disease: Early blight",
                                "Potato \n It is healthy",
                                "Tomato \n Disease: Bacterial spot",
                                "Tomato \n Disease: Spider mites \n Two-spotted spidermite",
                                "Tomato \n Disease: Yellow Leaf Curl Virus",
                                "Tomato \n Disease: Mosaic virus",
                                "Tomato \n It is healthy"
                        };

                        String predictedClassName = classNames[maxIndex];

                        // Hide the loading animation
                        loadingAnimation.setVisibility(View.GONE);
                        loadingAnimation.pauseAnimation();

                        // Display the result in a BottomSheetDialogFragment
                        showResultInBottomSheet(predictedClassName, imageUri);

                        // Releases model resources if no longer used.
                        model.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return root;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            if (imageBitmap != null) {
                imgview.setImageBitmap(imageBitmap);
                img = imageBitmap;
            }
        } else if (requestCode == 100 && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imgview.setImageURI(imageUri);
            try {
                img = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showResultInBottomSheet(String result, Uri imageUri) {
        ResultBottomSheetDialogFragment bottomSheetFragment = ResultBottomSheetDialogFragment.newInstance(result);
        bottomSheetFragment.show(getParentFragmentManager(), bottomSheetFragment.getTag());
    }

    private void showAlertDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("No Image Selected")
                .setMessage("Please select or capture an image before predicting.")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
