package org.roman.face;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestHaarFaces {

    @Test
    public void testHaarClass() {
        new HaarFaces();
    }

    @Test
    public void testData() {
        HaarFaces faces = new HaarFaces();
        assertNull(faces.getCapture());
        assertNull(faces.getDetector());
    }
}
