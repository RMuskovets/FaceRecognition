package org.roman.face;

import org.openimaj.image.MBFImage;
import org.openimaj.image.processing.face.detection.keypoints.FKEFaceDetector;
import org.openimaj.image.processing.face.detection.keypoints.KEDetectedFace;
import org.openimaj.video.*;
import org.openimaj.video.capture.VideoCapture;
import org.openimaj.video.capture.VideoCaptureException;

import javax.swing.*;

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

        HaarFaces<KEDetectedFace> faces = new HaarFaces<KEDetectedFace>();
        int width = 600, height = 500;
        try {
            faces.setCapture(new VideoCapture(width, height));
            faces.setDetector(new FKEFaceDetector());
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
