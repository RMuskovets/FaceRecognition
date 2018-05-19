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
                    if (face instanceof KEDetectedFace) {
                        KEDetectedFace keFace = (KEDetectedFace) face;
                        for (FacialKeypoint point : keFace.getKeypoints()) {
                            Rectangle pointRect = new Rectangle();
                            pointRect.x = point.position.x;
                            pointRect.y = point.position.y;
                            pointRect.width = 1;
                            pointRect.height = 1;
                            y.drawShape(pointRect,
                                    new Float[]{HaarFaces.this.point.getRed()/255F,HaarFaces.this.point.getGreen()/255F,HaarFaces.this.point.getBlue()/255F});
                        }
                    }
                    y.drawShape(face.getBounds(), new Float[]{rect.getRed() / 255F, rect.getGreen() / 255F, rect.getBlue() / 255F});
                }
            }
        });
    }
}
