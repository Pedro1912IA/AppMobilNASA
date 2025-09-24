package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Random;

public class FirstFragment extends Fragment {

    private ImageView nasaImageView;
    private Button randomImageButton;
    private TextView imageDescriptionTextView;
    private LinearLayout imageContainer;

    private final int[] nasaImages = {
            R.drawable.imgnasa1,
            R.drawable.imgnasa2,
            R.drawable.imgnasa3,
            R.drawable.imgnasa4,
            R.drawable.imgnasa5,
            R.drawable.imgnasa6,
            R.drawable.imgnasa7
    };

    private final String[] nasaImageDescriptions = {
            "The Hubble Space Telescope captured this image of the spiral galaxy NGC 1300.",
            "The Pillars of Creation in the Eagle Nebula, taken by the James Webb Space Telescope.",
            "Mars surface captured by the Perseverance rover.",
            "The International Space Station orbiting Earth.",
            "Saturn and its rings, captured by the Cassini spacecraft.",
            "This could be a description for imgnasa6.",
            "This could be a description for imgnasa7."
    };

    private Random random = new Random();
    private int currentImageIndex = 0;
    private int counterId = -1; // Para el TextView dinámico

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nasaImageView = view.findViewById(R.id.nasaImageView);
        randomImageButton = view.findViewById(R.id.buttonFirst); // ID corregido
        imageDescriptionTextView = view.findViewById(R.id.imageDescriptionTextView);
        imageContainer = view.findViewById(R.id.imageContainer);

        updateImageAndDescription(0); // Mostrar la primera imagen

        randomImageButton.setOnClickListener(v -> {
            int newIndex;
            do {
                newIndex = random.nextInt(nasaImages.length);
            } while (newIndex == currentImageIndex && nasaImages.length > 1);

            updateImageAndDescription(newIndex);
            addDynamicImageCounter();
        });
    }

    private void updateImageAndDescription(int index) {
        currentImageIndex = index;
        if (nasaImages.length > 0 && index >= 0 && index < nasaImages.length) {
            nasaImageView.setImageResource(nasaImages[index]);
            imageDescriptionTextView.setText(nasaImageDescriptions[index]);
        } else {
            nasaImageView.setImageResource(android.R.drawable.ic_menu_gallery);
            imageDescriptionTextView.setText("No image available");
        }
    }

    @SuppressLint("SetTextI18n")
    private void addDynamicImageCounter() {
        if (getView() == null) return;

        // Eliminar contador anterior
        if (counterId != -1) {
            View existingCounter = getView().findViewById(counterId);
            if (existingCounter != null) imageContainer.removeView(existingCounter);
        }

        // Crear TextView dinámico
        TextView counterTextView = new TextView(getContext());
        counterId = View.generateViewId(); // Genera un ID único
        counterTextView.setId(counterId);
        counterTextView.setText("Image changed " + (currentImageIndex + 1) + " times");
        counterTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        imageContainer.addView(counterTextView, params);

        Toast.makeText(getContext(), "Displaying image " + (currentImageIndex + 1), Toast.LENGTH_SHORT).show();
    }
}
