package org.roman.face;

import org.openimaj.image.MBFImage;
import org.openimaj.image.processing.face.detection.*;
import org.openimaj.image.processing.face.detection.keypoints.FKEFaceDetector;
import org.openimaj.image.processing.face.detection.keypoints.KEDetectedFace;
import org.openimaj.video.*;
import org.openimaj.video.capture.VideoCapture;
import org.openimaj.video.capture.VideoCaptureException;

import javax.swing.*;
import java.net.URL;

public class App extends JFrame {

    @SuppressWarnings("all")
    public App() {
        // Creating settings of frame
        {
            setVisible(true);
            setSize(620, 450);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setResizable(false);
        }

//        String input = JOptionPane.showInputDialog("Введіть файл для аналізу, або 'камера' для зчитування з камери");
//        boolean webcam = ("камера".equals(input));
//        Faces faces = null;
//        // Creating Faces object
//        {
//            try {
//                int width = 600, height = 400;
//                if (webcam) {
//                    faces = Faces.<DetectedFace>createWebcamCapture(new VideoCapture(width, height));
//                    faces.setDetector(new HaarCascadeDetector(10));
//                } else {
//                    faces = Faces.<DetectedFace>createFileCapture(new URL("file://" + input));
//                    faces.setDetector(new HaarCascadeDetector(10));
//                }
//            } catch (Exception e) {
//            }
//        }
//        if (faces != null) {
//            Video<MBFImage> video = faces.getVideo();
//            VideoDisplay<MBFImage> display = VideoDisplay.createVideoDisplay(video, this);
//            faces.install(display);
//        }
        HaarFaces<DetectedFace> faces = new HaarFaces<DetectedFace>();
        int width = 600, height = 500;
        try {
            faces.setCapture(new VideoCapture(width, height));
            faces.setDetector(new HaarCascadeDetector(10));
        } catch (VideoCaptureException e) {
        }

        Video<MBFImage> video = faces.getCapture();
        VideoDisplay<MBFImage> display = VideoDisplay.createVideoDisplay(video, this);
        faces.install(display);
    }

    public static void main(String[] args) {
        new App();
    }
}
