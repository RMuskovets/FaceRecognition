package org.roman.face;

import org.openimaj.image.FImage;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.processing.face.detection.*;
import org.openimaj.image.processing.face.detection.keypoints.*;
import org.openimaj.math.geometry.point.Point2dImpl;
import org.openimaj.math.geometry.shape.Rectangle;
import org.openimaj.math.geometry.shape.Shape;
import org.openimaj.video.*;
import org.openimaj.video.capture.VideoCapture;
import org.openimaj.video.xuggle.XuggleVideo;

import java.awt.*;
import java.net.URL;
import java.util.List;

import static org.openimaj.image.processing.face.detection.keypoints.FacialKeypoint.*;

@SuppressWarnings("all")
public class Faces<T extends DetectedFace> {

    private Video<MBFImage> video;

    private FaceDetector<T, FImage> detector;

    private Color rect = Color.RED;

    private Color point = Color.GREEN;
    private int sizePoint = 2;

    private Faces(Video<MBFImage> v) {
        video = v;
    }

    public static <T extends DetectedFace> Faces<T> createWebcamCapture(VideoCapture capt) {
        return new Faces(capt);
    }

    public static <T extends DetectedFace> Faces<T> createFileCapture(URL url) {
        return new Faces(new XuggleVideo(url));
    }

    public Video<MBFImage> getVideo() {
        return video;
    }

    public FaceDetector<T, FImage> getDetector() {
        return detector;
    }

    public void setDetector(FaceDetector<T, FImage> detector) {
        this.detector = detector;
    }

    private static final Float[] white = {1f, 1f, 1f};

    public void install(VideoDisplay<MBFImage> display) {
        display.changeVideo(video);
        display.addVideoListener(new VideoDisplayListener<MBFImage>() {
            public void afterUpdate(VideoDisplay<MBFImage> videoDisplay) {

            }

            public void beforeUpdate(MBFImage img) {
                img.getBand(0);
                List<T> faces = null;
                for (T face : faces) {
                    Rectangle rectFace = face.getBounds();
                    System.out.println(rectFace.x + " - " + rectFace.y);
                    System.out.println(rectFace.width + ", " + rectFace.height);
                    img.drawShape(rectFace, RGBColour.fromColor(rect));
                    if (face instanceof KEDetectedFace) {
                        KEDetectedFace points = (KEDetectedFace) face;
                        FacialKeypoint left_eye_left = points.getKeypoint(FacialKeypointType.EYE_LEFT_LEFT);
                        FacialKeypoint left_eye_right = points.getKeypoint(FacialKeypointType.EYE_LEFT_RIGHT);
                        float left_eye = DistanceBetween.getDistanceBetweenPoints(rectFace.x + left_eye_left.position.x,
                                rectFace.y + left_eye_left.position.y,
                                rectFace.x + left_eye_right.position.x,
                                rectFace.y + left_eye_right.position.y);
                        float left_eye_cm = DistanceBetween.toMetric(left_eye);
                        img.drawShape(new Rectangle(
                                rectFace.x + left_eye_left.position.x,
                                rectFace.y + left_eye_left.position.y - 1.5f,
                                left_eye,
                                3
                        ), white);
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
