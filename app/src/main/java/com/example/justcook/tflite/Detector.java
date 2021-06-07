package com.example.justcook.tflite;

import android.graphics.Bitmap;
import android.graphics.RectF;
import java.util.List;

/** 다른 인식 엔진과 상호작용하기 위한 제너릭 인터페이스입니다. */
public interface Detector {
    List<Recognition> recognizeImage(Bitmap bitmap);

    void enableStatLogging(final boolean debug);

    String getStatString();

    void close();

    void setNumThreads(int numThreads);

    void setUseNNAPI(boolean isChecked);

    /** 인식된 내용을 설명하는 변하지 않는 결과가 Detector에 의해 리턴됩니다. */
    public class Recognition {
        /**
         * 인식된 내용에 대한 고유 식별자입니다.
         * 객체의 인스턴스가 아닌 클래스에만 해당됩니다.
         */
        private final String id;

        /** 인식에 대한 이름을 표시합니다. */
        private final String title;

        /**
         * 다른 것과 비교하여 인식이 얼마나 좋은지 알 수 있는 점수입니다. 높은 것이 더 나은 것입니다.
         */
        private final Float confidence;

        /** 인식된 객체의 위치에 대한 원본 이미지 내의 선택적 위치입니다. */
        private RectF location;

        public Recognition(
                final String id, final String title, final Float confidence, final RectF location) {
            this.id = id;
            this.title = title;
            this.confidence = confidence;
            this.location = location;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public Float getConfidence() {
            return confidence;
        }

        public RectF getLocation() {
            return new RectF(location);
        }

        public void setLocation(RectF location) {
            this.location = location;
        }

        @Override
        public String toString() {
            String resultString = "";
            if (id != null) {
                resultString += "[" + id + "] ";
            }

            if (title != null) {
                resultString += title + " ";
            }

            if (confidence != null) {
                resultString += String.format("(%.1f%%) ", confidence * 100.0f);
            }

            if (location != null) {
                resultString += location + " ";
            }

            return resultString.trim();
        }
    }
}