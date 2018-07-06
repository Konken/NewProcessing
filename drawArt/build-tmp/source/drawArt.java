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

public class drawArt extends PApplet {

Brush[][]brush1;
int b1=5;
int b2=20;
int b3=20;
PImage img;
int tf = 1;
public void setup() {
	
	img = loadImage("africa-african-animal-33045.jpg");
	image(img,0,0,width,height);
	loadPixels();
	brush1=new Brush[b3][b3];
	translate(width/2,height/2);
	for(int i=0;i<brush1.length;i++){
		for(int j=0;j<b3;j++){
			brush1[i][j]=new Brush(i*width/b3,j*height/b3,random(360),200,20,400);
		}
	}
	/*for(int i=0;i<brush2.length;i++){
		for(int j=0;j<int(height/b2);j++){
			brush2[i][j]=new Brush(i*width/b2,j*height/b2,width/b2,height/b2,get(i*width/b2,j*height/b2));
		}
	}
	for(int i=0;i<brush2.length;i++){
		for(int j=0;j<int(height/b1);j++){
			brush3[i][j]=new Brush(i*width/b1,j*height/b1,width/b1,height/b1,get(i*width/b1,j*height/b1));
		}
	}*/
	background(0);
	for(int i=0;i<brush1.length;i++){
		for(int j=0;j<b3;j++){
			brush1[i][j].drawBrush();
		}
	}
}
public void mousePressed(){
	tf*=1;
	//image(img,0,0,width,height);
}
public void draw(){
	println(1);
	if(tf==1){
	}
	//image(img,width, height);
}

class Brush{
	float x,y,ang,len,howMany,howLong;
	float sx=random(10);
	float sy=random(10);
	float ny=sy;
	Brush(float x,float y,float ang,float len,float howMany,float howLong){
		this.x=x;
		this.y=y;
		this.ang=ang;
		this.len=len+random(-5,5);
		this.howMany=howMany;
		this.howLong=howLong;
	}
	public void drawBrush(){
		pushMatrix();
		translate(x,y);
		rotate(radians(ang));
		for(int i=0;i<howMany;i++){
			ny=sy;
			float x1=len+random(-30,30);
			float y1=random(-30,30);
			float oldx=x1;
			float oldy=y1;
			for(int j=0;j<howLong;j++){
				x1-=len/howLong*2;
				y1+=(0.5f-noise(ny));
				ny+=0.008f;
				strokeWeight(5);
				line(x1,y1,oldx,oldy) ;
				oldx=x1;
				oldy=y1;
			}
		}
		popMatrix();
	}
}

/*class Brush{
	float x,y,sizeX,sizeY;
	float defX,defY;
	float oldX,oldY;
	float nx,ny;
	float stepX;
	float stepY;
	float angle;
	color c;
	Brush(float x,float y,float sizeX,float sizeY,color c){
		this.sizeX=sizeX;
		this.sizeY=sizeY;
		defX=x;
		defY=y;
		this.x=x;
		this.y=y;
		oldX=x;
		oldY=y;
		nx=random(10);
		ny=random(10);
		angle=random(360);
		stepX=5;
		stepY=5;
		this.c=c;
	}
	void drawBrush(){
		stroke(c);
		strokeWeight(2);
		x+=cos(radians(angle+(0.5-noise(nx))*360))*stepX;
		y+=sin(radians(angle+(0.5-noise(ny))*360))*stepY;
		nx+=5;
		ny+=5;
		if(x<defX-sizeX/2||defX+sizeX<x){
			stepX*=-1;
		}
		if(y<defY-sizeY/2||defY+sizeY<y){
			stepY*=-1;
		}
		line(x,y,oldX,oldY);
		oldX=x;
		oldY=y;
	}
}*/

  public void settings() { 	fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "drawArt" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
