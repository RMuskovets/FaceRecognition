package org.roman.face;

public class DistanceBetween {

    public static float getDistanceBetweenPoints(float x1, float y1, float x2, float y2) {
        return (float) Math.hypot(x1 - x2, y1 - y2);
    }

    public static float toMetric(float eye, float length) {
        return (length / eye) * 3;
    }

    public static float toMetric(float eye) {
        return toMetric(eye, eye);
    }

    public static float fromMetric(float eye, float len_cm) {
        return len_cm / 3 * eye;
    }
}
