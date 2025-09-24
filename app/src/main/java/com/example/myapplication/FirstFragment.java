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
            R.drawable.imgnasa7,
            R.drawable.imgnasa8,
            R.drawable.imgnasa9,
            R.drawable.imgnasa10,
            R.drawable.imgnasa11,
            R.drawable.imgnasa12,
            R.drawable.imgnasa13,
            R.drawable.imgnasa14,
            R.drawable.imgnasa15,
    };

    private final String[] nasaImageDescriptions = {
            "Neptuno: Una vista del planeta Neptuno, mostrando su distintivo color azul y algunas bandas de nubes claras.",
            "Galaxia Espiral M51 (Whirlpool Galaxy): Una impresionante imagen de una galaxia espiral con brazos bien definidos y un núcleo brillante, destacando regiones de formación estelar en tonos rojizos.",
            "Galaxia de Andrómeda (M31): Una vista expansiva de la galaxia de Andrómeda, nuestra vecina galáctica, mostrando su forma espiral y sus brillantes estrellas.",
            "Nebulosa de la Burbuja (NGC 7635): Una nebulosa espectacular que parece una burbuja iridiscente, formada por el viento de una estrella masiva en su interior.",
            "Tierra: Una vista global del planeta Tierra desde el espacio, mostrando continentes, océanos y patrones de nubes.",
            "Región de formación estelar en la Gran Nube de Magallanes: Una vibrante región de estrellas jóvenes y nebulosas de emisión en la Gran Nube de Magallanes, una galaxia enana cercana.",
            "Nebulosa del Cangrejo (M1): Los restos de una supernova, mostrando una compleja red de filamentos de gas y polvo, con un centro brillante.",
            "La Luna en un cielo oscuro sobre la curvatura de la Tierra, con nubes blancas y una atmósfera azul.",
            "Una vista parcial del planeta Júpiter, revelando sus características bandas de nubes y tormentas.",
            "Una nebulosa vibrante con nubes de gas y polvo en tonos rojos y púrpuras, y estrellas brillantes.",
            "Una vista nocturna de la Tierra desde el espacio, con las luces de las ciudades brillando.",
            "Una imagen del planeta Venus, mostrando su superficie rocosa y de tonos ocres."
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
