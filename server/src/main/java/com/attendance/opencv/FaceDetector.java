package com.attendance.opencv;

import org.bytedeco.opencv.opencv_core.*;
import java.io.File;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import static org.bytedeco.opencv.global.opencv_imgcodecs.*;
import static org.bytedeco.opencv.global.opencv_objdetect.*;

public class FaceDetector {

    private final CascadeClassifier classifier;

    public FaceDetector() {
        String resourcePath = System.getenv("OPENCV_RESOURCE_PATH");
        if (resourcePath == null) {
            resourcePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "opencv" + File.separator + "haarcascade_frontalface.xml";
            if (!new File(resourcePath).exists()) {
                // Fallback for JAR deployment - look in the current directory if src is not available
                resourcePath = System.getProperty("user.dir") + File.separator + "opencv" + File.separator + "haarcascade_frontalface.xml";
            }
        }
        this.classifier = new CascadeClassifier(resourcePath);
    }

    public Rect detectFaces(String imagePath) {
        Mat image = imread(imagePath);
        if (image.empty())
            return null;

        RectVector faceDetections = new RectVector();
        classifier.detectMultiScale(image, faceDetections);

        if (faceDetections.size() > 0) {
            return faceDetections.get(0); // Return first face detected for simplicity
        }
        return null;
    }
}
