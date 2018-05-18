package org.roman.face;

import org.openimaj.video.xuggle.XuggleVideo;
import org.openimaj.video.capture.VideoCapture;
import org.openimaj.video.Video;
import org.openimaj.image.MBFImage;

/**
 * OpenIMAJ Face Recognition!
 *
 */
public class App {

	private Video<MBFImage> video;

	private App(Video<MBFImage> video) {
		this.video = video;
	}

	public static App createWebcamCapture(VideoCapture capture) {
		return new App(capture);
	}

	public static App createFileCapture(java.io.File file) {
		return new App(new XuggleVideo(file));
	}

    public static void main( String[] args ) {
    	
    }
}
