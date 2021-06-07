package com.example.justcook.customview;

import java.util.List;
import com.example.justcook.tflite.Detector.Recognition;

public interface ResultsView {
    public void setResults(final List<Recognition> results);
}
