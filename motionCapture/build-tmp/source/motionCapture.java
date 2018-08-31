import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import gab.opencv.*; 
import processing.video.*; 
import java.awt.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class motionCapture extends PApplet {





Capture video;
OpenCV opencv;
PImage b;
int[][]c;
float[][]rArray;
float[][]gArray;
float[][]bArray;
float difference=30;
public void setup() {
	
	video = new Capture(this, 640/4, 480/4);
	opencv = new OpenCV(this, 640/4, 480/4);
	video.start();
	b=video;
	opencv.loadImage(video);
	b.loadPixels();
	image(b, 0, 0);
	rArray=new float[b.width][b.height];
	gArray=new float[b.width][b.height];
	bArray=new float[b.width][b.height];
	for(int i=0;i<b.width;i++){
		for(int j=0;j<b.height;j++){
			int pos = i + j*b.width;
			int c = b.pixels[pos];
			float r = red( c );
			float g = green( c );
			float b = blue( c );
			rArray[i][j]=r;
			gArray[i][j]=g;
			bArray[i][j]=b;
		}
	}
}

public void draw() {
	opencv.loadImage(video);
	b=video;
	b.loadPixels();
	image(b, 0, 0);
	float[][]rTempArray=new float[b.width][b.height];
	float[][]gTempArray=new float[b.width][b.height];
	float[][]bTempArray=new float[b.width][b.height];
	for(int i=0;i<b.width;i++){
		for(int j=0;j<b.height;j++){
			int pos = i + j*b.width;
			int c = b.pixels[pos];
			rTempArray[i][j]=red(c);
			gTempArray[i][j]=green(c);
			bTempArray[i][j]=blue(c);
		}
	}
	background(0);
	for(int i=0;i<b.width;i++){
		for(int j=0;j<b.height;j++){
			float r = rTempArray[i][j]-rArray[i][j];
			float g = gTempArray[i][j]-gArray[i][j];
			float b = bTempArray[i][j]-bArray[i][j];
			if(r>difference&&g>difference&&b>difference){
				noStroke();
				fill(255);
				ellipse(i*12,j*12,10,10);
			}
			rArray[i][j]=rTempArray[i][j];
			gArray[i][j]=gTempArray[i][j];
			bArray[i][j]=bTempArray[i][j];
		}
	}
}

public void captureEvent(Capture c) {
	c.read();
}
  public void settings() { 	fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "motionCapture" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
