package org.roman.face;

import org.openimaj.image.MBFImage;
import org.openimaj.image.processing.face.detection.DetectedFace;
import org.openimaj.image.processing.face.detection.HaarCascadeDetector;
import org.openimaj.video.Video;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.capture.VideoCapture;

import javax.swing.*;
import java.awt.*;

public class intoComponent {

    public static void main(String[] args) {
        new JFrame("Test - Capture into Component") {
            {
                setVisible(true);
                setSize(600, 538);
                setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                setLayout(new BorderLayout());
                JPanel content = new JPanel();
                HaarFaces<DetectedFace> faces = new HaarFaces<DetectedFace>();
                try {
                    faces.setCapture(new VideoCapture(600, 500));
                    faces.setDetector(new HaarCascadeDetector(10));
                } catch (Exception e) {}
                Video<MBFImage> video = faces.getCapture();
                add(content, "Center");
                VideoDisplay<MBFImage> disp = VideoDisplay.createVideoDisplay(video, content);
                faces.install(disp);
                repaint();
            }
        };
    }
}
