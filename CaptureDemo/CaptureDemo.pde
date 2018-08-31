import processing.video.*;

Capture myCapture;

int imageIndex = 0;

void setup() {  
  fullScreen();
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
    int width = int(size[0]);
    int height = int(size[1]);


    myCapture = new Capture(this, cameras[0]);
    myCapture.start();
  }
}

void draw() {
  if (myCapture.available() == true) {
    myCapture.read();
  }

  image(myCapture, 0, 0); 

  if(mousePressed) {
    String imageName = "images/" + imageIndex++ + ".jpg";
    save(imageName);
  }
}