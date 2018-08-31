import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.video.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class CaptureDemo extends PApplet {



Capture myCapture;

int imageIndex = 0;

public void setup() {  
  
  String[] cameras = Capture.list();

  if (cameras.length == 0) {
    println("There are no cameras available for capture.");
    exit();
  } else {
    for (int i = 0; i < cameras.length; i++) {
      println(i + ": " + cameras[i]);
    } 

    String camera = cameras[0];
    String[] size = splitTokens(splitTokens(splitTokens(camera, ",")[1], "=")[1], "x");
    int width = PApplet.parseInt(size[0]);
    int height = PApplet.parseInt(size[1]);


    myCapture = new Capture(this, cameras[0]);
    myCapture.start();
  }
}

public void draw() {
  if (myCapture.available() == true) {
    myCapture.read();
  }

  image(myCapture, 0, 0); 

  if(mousePressed) {
    String imageName = "images/" + imageIndex++ + ".jpg";
    save(imageName);
  }
}
  public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "CaptureDemo" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
