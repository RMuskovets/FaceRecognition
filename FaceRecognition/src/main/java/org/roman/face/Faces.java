package org.roman.face;

import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.processing.face.detection.*;
import org.openimaj.image.processing.face.detection.keypoints.*;
import org.openimaj.math.geometry.point.Point2dImpl;
import org.openimaj.math.geometry.shape.Rectangle;
import org.openimaj.video.*;
import org.openimaj.video.capture.VideoCapture;
import org.openimaj.video.xuggle.XuggleVideo;

import java.awt.*;
import java.net.URL;
import java.util.List;

public class Faces<T extends DetectedFace> {

    private Video<MBFImage> video;

    private FaceDetector<T, MBFImage> detector;

    private Color rect = Color.RED;

    private Color point = Color.GREEN;
    private int sizePoint = 2;

    private Faces(Video<MBFImage> v) {
        video = v;
    }

    public static Faces createWebcamCapture(VideoCapture capt) {
        return new Faces(capt);
    }

    public static Faces createFileCapture(URL url) {
        return new Faces(new XuggleVideo(url));
    }

    public Video<MBFImage> getVideo() {
        return video;
    }

    public FaceDetector<T, MBFImage> getDetector() {
        return detector;
    }

    public void setDetector(FaceDetector<T, MBFImage> detector) {
        this.detector = detector;
    }

    public void install(VideoDisplay<MBFImage> display) {
        display.changeVideo(video);
        display.addVideoListener(new VideoDisplayListener<MBFImage>() {
            public void afterUpdate(VideoDisplay<MBFImage> videoDisplay) {

            }

            public void beforeUpdate(MBFImage img) {
                List<T> faces = detector.detectFaces(img);
                for (T face : faces) {
                    Rectangle rectFace = face.getBounds();
                    img.drawShape(rectFace, RGBColour.fromColor(rect));
                    if (face instanceof KEDetectedFace) {
                        KEDetectedFace points = (KEDetectedFace) face;
                        for (FacialKeypoint point : points.getKeypoints()) {
                            Point2dImpl pos = point.position;
                            img.drawShape(new Rectangle(pos.x, pos.y, sizePoint, sizePoint), RGBColour.fromColor(Faces.this.point));
                        }
                    }
                }
            }
        });
    }

    public void setPointSize(int size) {
        this.sizePoint = size;
    }

    public int getPointSize() {
        return sizePoint;
    }

    public Float[] getPointRGB() {
        return RGBColour.fromColor(point);
    }

    public Color getPointColor() {
        return point;
    }

    public void setPointColor(Color c) {
        point = c;
    }
}
