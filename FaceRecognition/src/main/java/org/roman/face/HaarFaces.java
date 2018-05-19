package org.roman.face;

import lombok.*;
import org.openimaj.image.*;
import org.openimaj.image.colour.*;
import org.openimaj.image.processing.face.detection.*;
import org.openimaj.image.processing.face.detection.keypoints.*;
import org.openimaj.math.geometry.shape.Rectangle;
import org.openimaj.video.*;
import org.openimaj.video.capture.VideoCapture;

import java.awt.Color;
import java.util.List;

import static java.awt.Color.white;

@SuppressWarnings("all") @RequiredArgsConstructor @Data
public class HaarFaces<D extends DetectedFace> {

    private VideoCapture capture;

    private FaceDetector<D, FImage> detector;

    private Color rect = Color.RED;
    private Color point = Color.GREEN;

    public void install(VideoDisplay<MBFImage>x){
        x.addVideoListener(new VideoDisplayAdapter<MBFImage>() {
            public void beforeUpdate(MBFImage y) {
                List<D> faces = detector.detectFaces(Transforms.calculateIntensity(y));
                for (DetectedFace face : faces) {
                    Rectangle rectFace = face.getBounds();
                    y.drawShape(rectFace, new Float[]{1f, 0f, 0f});
                    if (face instanceof KEDetectedFace) {
                        KEDetectedFace points = (KEDetectedFace) face;
                        FacialKeypoint left_eye_left = points.getKeypoint(FacialKeypoint.FacialKeypointType.EYE_LEFT_LEFT);
                        FacialKeypoint left_eye_right = points.getKeypoint(FacialKeypoint.FacialKeypointType.EYE_LEFT_RIGHT);
                        float left_eye = DistanceBetween.getDistanceBetweenPoints(rectFace.x + left_eye_left.position.x,
                                rectFace.y + left_eye_left.position.y,
                                rectFace.x + left_eye_right.position.x,
                                rectFace.y + left_eye_right.position.y);
                        float left_eye_cm = DistanceBetween.toMetric(left_eye);
                        y.drawShape(new Rectangle(
                                rectFace.x + left_eye_left.position.x,
                                rectFace.y + left_eye_left.position.y - DistanceBetween.fromMetric(left_eye, 0.75f),
                                left_eye,
                                DistanceBetween.fromMetric(left_eye, 1.5f)
                        ), new Float[]{1f, 0f, 0f});
                    }
                }
            }
        });
    }
}
