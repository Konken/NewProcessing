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

public class LengthClock extends PApplet {

float thold = 5;
float spifac = 1.05f;
float drag = 0.01f;
int big = 500;
ball bodies[] = new ball[big];
float mX;
float mY;
public void setup() {
  
  strokeWeight(1);
  
  stroke(0, 0, 0, 10);
  for(int i = 0; i < big; i++) {
    bodies[i] = new ball();
  }
}
public void draw() {
  if(mousePressed) {
    background(255);
    
    mX += 0.3f * (mouseX - mX);
    mY += 0.3f * (mouseY - mY);
  }
   mX += 0.3f * (mouseX - mX);
    mY += 0.3f * (mouseY - mY);
  for(int i = 0; i < big; i++) {
    bodies[i].render();
  }
  filter(BLUR,1);
}
class ball {
  float X;
  float Y;
  float Xv;
  float Yv;
  float pX;
  float pY;
  float w;
  ball() {
    //X = random(screen.width);
   // Y = random(screen.height);
		X = random(width);
    Y = random(height);
    w = random(1 / thold, thold);
  }
  public void render() {
    if(!mousePressed) {
      Xv /= spifac;
      Yv /= spifac;
    }
    Xv += drag * (mX - X) * w;
    Yv += drag * (mY - Y) * w;
    X += Xv;
    Y += Yv;
    line(X, Y, pX, pY);
    pX = X;
    pY = Y;
  }
}
/*Cell c;
void setup() {
	fullScreen();
	c=new Cell(random(10),width/2,height/2,1000);
}

void draw() {
	background(0);
	c.update();
}
class Cell{
	float sx,sy;
	float nx,ny;
	ArrayList<Float> x;
	ArrayList<Float> y;
	int length;
	Cell(float n,float getx,float gety,int length){
		sx=getx;
		sy=gety;
		nx=random(10);
		ny=random(10);
		x=new ArrayList<Float>();
		y=new ArrayList<Float>();
		this.length=length;
		x.add(sx);
		y.add(sy);
	}
	void update(){
		stroke(255,20);
		strokeWeight(0.5);
		float oldx=x.get(x.size()-1);
		float oldy=y.get(y.size()-1);
		float nowx=oldx+(0.5-noise(nx))*100;
		float nowy=oldy+(0.5-noise(ny))*100;
		if(nowx>width){
			nowx=width;
		}
		if(nowx<0){
			nowx=0;
		}
		if(nowy>height){
			nowy=height;
		}
		if(nowy<0){
			nowy=0;
		}

		x.add(nowx);
		y.add(nowy);
		if(300<x.size()){
			for(int int=0;i<x.size()-300;i++){
				for(int j=1;j<300;j+=30){
					line(x.get(i),y.get(i),x.get(i+j),y.get(i+j));
				}
			}
		}
		nx+=0.05;
		ny+=0.05;
	}
}
/*Length[][]l;
int howMany=60;
float nx;float ny;float sn;float step=0.1;
void setup() {
	fullScreen();
	sn=random(10);
	nx=sn;
	ny=sn;
	l=new Length[width/howMany][height/howMany];
	for(int x=0;x<width/howMany;x++){
		for(int y=0;y<height/howMany;y++){
			l[x][y]=new Length(x*howMany+howMany/2,y*howMany+howMany/2,howMany/2,45);
		}
	}
}
void draw() {
	background(0);
	for(int x=0;x<width/howMany;x++){
		ny=sn;
		for(int y=0;y<height/howMany;y++){
			l[x][y].update2(noise(ny,nx)*5);
			strokeWeight(5);
			ny+=step;
		}
		nx+=step;
	}
	sn=random(10);
/*makeNumber(0,4);
makeNumber(8,4);
makeNumber(17,4);
makeNumber(25,4);
}

void makeNumber(int x,int y){
	l[x][y].update3(90,0);
	for(int i=1;i<6;i++){
		l[x+i][y].update3(0,180);
	}
	l[x+6][y].update3(180,90);
	for(int i=1;i<10;i++){
		l[x][y+i].update3(90,270);
	}
	l[x][y+10].update3(270,0);
	for(int i=1;i<6;i++){
		l[x+i][y+10].update3(0,180);
	}
	l[x+6][y+10].update3(270,180);
	for(int i=1;i<10;i++){
		l[x+6][y+i].update3(90,270);
	}
	//////
	l[x+1][y+1].update3(90,0);
	for(int i=1;i<4;i++){
		l[x+i+1][y+1].update3(0,180);
	}
	l[x+5][y+1].update3(180,90);
	for(int i=1;i<8;i++){
		l[x+1][y+i+1].update3(90,270);
	}
	l[x+1][y+9].update3(270,0);
	for(int i=1;i<4;i++){
		l[x+i+1][y+9].update3(0,180);
	}
	l[x+5][y+9].update3(270,180);
	for(int i=1;i<8;i++){
		l[x+5][y+i+1].update3(90,270);
	}
}
class Length{
	float x;
	float y;
	float rad;
	float angle;
	Length(float x,float y,float rad,float angle){
		this.x=x;
		this.y=y;
		this.rad=rad;
		this.angle=angle;
	}
	void update(){
		stroke(255);
		strokeWeight(1);
		line(cos(radians(angle))*rad+x,
			sin(radians(angle))*rad+y,
			cos(radians(angle+180))*rad+x,
			sin(radians(angle+180))*rad+y);
	}
	void update2(float a){
		this.angle+=a;
		stroke(255);
		strokeWeight(3);
		line(cos(radians(angle))*rad+x,
			sin(radians(angle))*rad+y,
			cos(radians(angle+180))*rad+x,
			sin(radians(angle+180))*rad+y);
	}
	void update3(float a1,float a2){
		this.angle=angle;
		stroke(255);
		strokeWeight(5);
		line(x,
			y,
			cos(radians(a1))*rad+x,
			sin(radians(a1))*rad+y);
		line(x,
			y,
			cos(radians(a2))*rad+x,
			sin(radians(a2))*rad+y);
	}
}*/
  public void settings() {  size(400, 300);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "LengthClock" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
