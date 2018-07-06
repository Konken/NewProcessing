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

public class rain extends PApplet {

ArrayList<Raindrop> r= new ArrayList<Raindrop>();
PImage photo;

public void setup(){
	
	for(int i=0;i<100;i++){
		r.add(new Raindrop(random(-width/2,width/2),random(-height,height/2),random(500,1500),100));
	}
	photo = loadImage("usachan.png");
	
}

public void draw() {
	background(0xff383838);
	translate(width/2,height/2);
	rotateX(radians(60));
	fill(255,20);
	//box(500);
	for(int i=0;i<r.size();i++){
		if(r.get(i).draw()){
			r.remove(i);
			r.add(new Raindrop(random(-width/2,width/2),random(-height/2,height/2),500+random(500),50));
		}
		r.get(i).changeM(map(mouseY,0,height,1,20));
	}
}

class Raindrop{
	float x,y,z;
	float rad;
	float nowRad=0;
	float len=-	200;
	float m=10;
	Raindrop(float x,float y,float z,float rad){
		this.x=x;
		this.y=y;
		this.z=z;
		this.rad=rad;
	}
	public void changeM(float m){
		this.m=m;
	}
	public boolean draw(){
		boolean b=false;
		println(m);
		float alph=255;
		z-=m;
		len=-m*10;
		if(m*10>z){
			b=ripple();
			len=-z;
		}
		if(5>z){
			z=0;
			alph=0;
		}
		stroke(0xffd65577,alph);
		strokeWeight(10);
		fill(0xffd65577,alph);
		line(x,y,z,x,y,z+len);
		return b;
	}
	public boolean ripple(){
		boolean b=false;
		beginShape();
		stroke(0xffd65577,255-nowRad*5);
		noFill();
		for(int i=0;i<360;i++){
			vertex(x+nowRad*cos(radians(i)),y+nowRad*sin(radians(i)),0);
		}
		endShape();	
		nowRad+=1;
		if(rad<nowRad){
			b=true;
		}
		return b;
	}
}
  public void settings() { 	fullScreen(P3D); 	smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "rain" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
