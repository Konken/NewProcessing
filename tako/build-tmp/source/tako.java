import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class tako extends PApplet {

Tenta[]t=new Tenta[1000];
int temp=1;
public void setup(){
  //strokeCap(PROJECT);
  
  float step=255/t.length;
  for(int i=0;i<t.length;i++){
    int a=(int)(random(4));
    switch (a) {
      case 0:t[i]=new Tenta(4,5,46);break;
      case 1:t[i]=new Tenta(20,1,82);break;
      case 2:t[i]=new Tenta(34,0,124);break;
      case 3:t[i]=new Tenta(13,0,164);break;
    }
  }
}

public void draw(){
  noStroke();
  fill(2, 1, 10);
  rect(0,0,width,height);
  translate(width/2,height/2);
  if(frameCount<300-5){
    temp=frameCount;
  }
  for(int i=0;i<temp;i++){
    t[i].rot();
    t[i].ext();
  }
  saveFrame("frames/######.tif");
  if(frameCount==330){
    saveFrame("frames/######.png");
    exit();
  }
}

class Tenta{
  float ro;
  float r;
  float g;
  float b;
  float nx;
  float ny;
  int howmany;
  float[]x;
  float[]y;
  float[]tan;
  float[][]minix;
  float[][]miniy;
  int len;
  float stro;
  Tenta(float r,float g,float b){
    this.r=r;
    this.g=g;
    this.b=b;
    ro=random(360);
    nx=random(10);
    ny=random(10);

    len=0;

    howmany=150;

    x=new float[howmany];
    y=new float[howmany];
    for(int i=0;i<howmany;i++){
      float temp_nx=random(10);
      float temp_ny=random(10);
      if(i!=0){
        x[i]=(0.5f-noise(nx))*12+x[i-1];
        y[i]=(noise(ny))*4+y[i-1];
      }
      else {
        x[i]=(0.5f-noise(nx))*5;
        y[i]=(0.5f-noise(ny))*5;
      }
      nx+=0.02f;
      ny+=0.01f;
    }
  }
  public void rot(){
    rotate(ro);
  }
  public void ext(){
    for(int i=0;i<len;i++){
      stroke(r,g,b);
      // if(i%1==0){
      //   makeMini(60,i,x[i],y[i]);
      //   makeMini(-60,i,x[i],y[i]);
      // }
      strokeWeight(20);
      if(howmany-i<100){
        strokeWeight((howmany-i)/5+2);
      }
      if(i<len-1){
        line(x[i],y[i],x[i+1],y[i+1]);
      }
    }
    addLength();
    println(len);
  }

  public void addLength(){
    if(len<howmany-1){
      len++;
    }
  }
}
  public void settings() {  fullScreen(2); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#EA0202", "--stop-color=#E81C1C", "tako" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
