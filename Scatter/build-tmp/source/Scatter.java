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

public class Scatter extends PApplet {

Wave[]w;
int[]c=new int[5];
ArrayList<Ship> s = new ArrayList<Ship>();
int count;
float r1=160;
float g1=216;
float b1=239;
float r2=255;
float g2=99;
float b2=71;
float r3=48;
float g3=61;
float b3=139;
float difR1;
float difG1;
float difB1;
float difR2;
float difG2;
float difB2;
float rad_angle;
public void setup(){
	
	rectMode(CENTER);
	w=new Wave[4];
	
	s.add(new Ship(PApplet.parseInt(random(4))));
	rad_angle=180.0f/width;
	difR1=(r1-r2)/(width/2);
	difG1=(g1-g2)/(width/2);
	difB1=(b1-b2)/(width/2);
	difR2=(r2-r3)/(width/2);
	difG2=(g2-g3)/(width/2);
	difB2=(b2-b3)/(width/2);
	c[0]=color(0xff0080FF);
	c[1]=color(0xff0073E6);
	c[2]=color(0xff0066CC);
	c[3]=color(0xff0059B3);
	c[4]=color(0xff004D99);
	for(int i=0;i<w.length;i++){
		w[i]=new Wave(c[i],height/8*i+400);
	}
}

public void draw() {
	background(sky(mouseX));
	sunAndMoon();
	for(int i=0;i<w.length;i++){
		w[i].update();
	}
	if((int)random(500)==5){
		s.add(new Ship(PApplet.parseInt(random(4))));
	}
	for(int i=0;i<s.size();i++){
		int count=s.get(i).getCount();
		if(width-1<count){
			s.remove(i);
		}else{
			float temp=w[s.get(i).getIndex()].returnPointValue(count);
			s.get(i).update_ship(count,temp,w[s.get(i).getIndex()].getAngle(count));
		}
	}
}
public void sunAndMoon(){
	float len=200;
	pushMatrix();
	translate(width/2,height/2);
	rotate(radians(rad_angle*mouseX));
	fill(255);
	ellipse(0,-height/2+len,80,80);
	fill(255,100);
	ellipse(0,-height/2+len,90,90);
	fill(255,100);
	ellipse(0,-height/2+len,100,100);

	fill(255);
	arc(0, height/2-len, 90, 90, 0, PI*4);
	fill(sky(mouseX));
	arc(20, height/2-len+20, 90, 90, 0, PI*4);
	popMatrix();
}
public int sky(float x){
	int temp=color(0);
	if(x<width/2){
		temp=color(r1-difR1*x,g1-difG1*x,b1-difB1*x);
	}
	else{
		temp=color(r2-difR2*(x-width/2),g2-difG2*(x-width/2),b2-difB2*(x-width/2));
	}
	return temp;
}
public void keyPressed() {
	s.add(new Ship(PApplet.parseInt(random(4))));
}
class Wave{
	int c;
	float n;
	float step;
	float step_noise=0.002f;





	
	float how=200;
	ArrayList<Float> list = new ArrayList<Float>();
	Wave(int c,float s){
		this.c=c;
		n=random(10);
		step=s;
		for(int i=0;i<width;i++){
			list.add(noise(n));
			n+=step_noise;
		}
	}
	public void update(){
		list.add(noise(n));
		list.remove(0);
		n+=step_noise;
		beginShape();
		vertex(width,height);
		vertex(0,height);
		for(int i=0;i<width;i+=2){
			noStroke();
			fill(c);
			vertex(i,list.get(i)*how+step);
		}
		vertex(width,height);
		endShape();
	}
	public float returnPointValue(int index){
		float tempY=list.get(index)*how+step;
		return tempY;
	}
	public float getAngle(int index){
		float ang;
		if(index==0){
			ang=0;
		}
		else {
			ang=degrees(atan2(list.get(index)-list.get(index-1),1))%360;
		}
		return ang;
	}
}
class Ship{
	float x,y;
	float ang;
	int index;
	int count=0;
	Ship(int index){
		newAllocation(index);
	}
	public void newAllocation(int index){
		this.index=index;
	}
	public int getIndex(){
		return index;
	}
	public int getCount(){
		return count;
	}
	public void update_ship(float x,float y,float ang){
		pushMatrix();
		translate(x,y);
		fill(255);
		rotate(ang);
		rect(0,-15, 60, 30);
		popMatrix();
		count++;
	}
}
  public void settings() { 	fullScreen(); 	smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Scatter" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
