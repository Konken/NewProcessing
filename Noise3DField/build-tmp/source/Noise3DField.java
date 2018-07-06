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

public class Noise3DField extends PApplet {

Agent[]a=new Agent[40000];
int agentsCount = 20000;
float noiseScale = 600, noiseStrength = 10; 
float overlayAlpha = 10, agentsAlpha = 90, strokeWidth = 0.5f;
int drawMode = 1;
boolean showGUI = false;
float[][]z;
float nx;float ny;
float start=random(10);
float step=0.006f;
int count=0;
public void setup() {
	
	z=new float[width+500][10*(height+100)];
	for(int i=0;i<a.length;i++){
		a[i]=new Agent();
	}
	background(0);
	zMake();
}
public void draw() {
	background(0);
	translate(width/2,height/2);
	rotateX(radians(60));
	translate(-width/2,-height/2);
	for(int i=0;i<a.length;i++){
		a[i].update1();
	}
	count++;
	//saveFrame("data/#######.tif");
}
public void mousePressed(){
	noiseStrength=random(5,20);
}
public void zMake(){
	start=random(10);
	ny=start;
	for(int y=0;y<10*(height+100);y++){
		nx=start;
		for(int x=0;x<width+100;x++){
			float n=(0.5f-noise(nx,ny))*200;
			z[x][y]=n;
			nx+=step;
		}
		ny+=step;
	}
}
class Agent {
	PVector p, pOld;
	float stepSize, angle;
	boolean isOutside = false;
	Agent() {
		p = new PVector(random(width), random(height));
		pOld = new PVector(p.x, p.y);
		stepSize = random(1, 5);
	}
	public void update1(){
		angle = noise(p.x/noiseScale,p.y/noiseScale) * noiseStrength;
		p.x += cos(angle) * stepSize;
		p.y += sin(angle) * stepSize;
		if (p.x < -10) isOutside = true;
		else if (p.x > width+10) isOutside = true;
		else if (p.y < -10) isOutside = true;
		else if (p.y > height+10) isOutside = true;
		if (isOutside) {
			p.x = random(width);
			p.y = random(height);
			pOld.set(p);
		}
		stroke(map(pOld.x,0,width,0,255),map(pOld.y,0,height,100,255),230);
		strokeWeight(strokeWidth*stepSize);
		line(pOld.x,pOld.y,z[round(pOld.x)+50][round(pOld.y)+50+count],p.x,p.y,z[round(p.x)+50][round(p.y)+50+count]);
		//line(pOld.x,pOld.y,p.x,p.y);
		//line(pOld.x,pOld.y,z[round(pOld.x)+50].get(round(pOld.y)+50),p.x,p.y,z[round(p.x)+50].get(round(p.y)+50));
		pOld.set(p);
		isOutside = false;
	}
}
  public void settings() { 	fullScreen(P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Noise3DField" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
