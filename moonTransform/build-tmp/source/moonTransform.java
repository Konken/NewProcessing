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

public class moonTransform extends PApplet {

ArrayList<Cloud> c;
ArrayList<Cloud> c2;
Moon m;
Star[]s;
public void setup() {
	
	c=new ArrayList<Cloud>();
	c2=new ArrayList<Cloud>();
	m=new Moon(width/2,height/2);
	s=new Star[50];
	for(int i=0;i<s.length;i++){
		s[i]=new Star(random(width),random(height));
	}
	for(int i=0;i<50;i++){
		float tx=random(0,width);
		float ty=random(height-500,height);
		float step=random(4,10);
		Cloud t=new Cloud(tx,ty,step,150);
		c.add(t);
	}
	for(int i=0;i<100;i++){
		float tx=random(0,width);
		float ty=random(0,height);
		float step=random(4,10);
		Cloud t=new Cloud(tx,ty,step,120);
		c2.add(t);
	}
	background(9,33,64);
}
public void draw() {
	//background(9,33,64);
	smooth();
	strokeWeight(5);
	for(int i=0;i<s.length;i++){
		s[i].update();	
	}
	strokeWeight(1);
	for(int i=0;i<c2.size();i++){
		if(c2.get(i).update()){
			c2.remove(i);
			float tx=random(0,20);
			float ty=random(0,height);
			float step=random(4,10);
			Cloud t=new Cloud(tx,ty,step,100);
			c2.add(t);
		}
	}
	m.update();
	for(int i=0;i<c.size();i++){
		if(c.get(i).update()){
			c.remove(i);
			float tx=random(0,20);
			float ty=random(height-500,height);
			float step=random(4,10);
			Cloud t=new Cloud(tx,ty,step,150);
			c.add(t);
		}
	}
	//filter(BLUR,1);
}
class Star{
	float x,y;
	Star(float x,float y){
		this.x=x;
		this.y=y;
	}
	public void update(){
		stroke(242, 199, 119);
		point(x,y);
	}
}
class Moon{
	float x,y;
	Moon(float x,float y){
		this.x=x;
		this.y=y;
	}
	public void update(){
		stroke(242, 199, 119, 30);
		fill(9,33,64);
		ellipse(x, y, 600, 600);
		stroke(242, 199, 119, 30);
		fill(242, 199, 119, 30);
		ellipse(x, y,600, 600);
		pushMatrix();
		translate(x, y);
		popMatirx();
	}
}
class Cloud{
	float x,y;
	float step;
	float rad;
	int c;
	Cloud(float x,float y,float step,float rad){
		this.x=x;
		this.y=y;
		this.step=step;
		this.rad=rad;
		int r=(int)(random(3));
		switch (r) {
			case 0:c=color(46,68,130,60);
			break;
			case 1:c=color(2, 73, 89, 60);
			break;
			case 2:c=color(84,107,171,60);
			break;
		}
		c=color(2, 73, 89, 60);
	}
	public boolean update(){
		boolean b=false;
		noStroke();
		fill(c);
		ellipse(x, y,rad,rad);
		x+=step;
		if(x<0||width<x){
			b=true;
		}
		return b;
	}
}
  public void settings() { 	fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "moonTransform" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
