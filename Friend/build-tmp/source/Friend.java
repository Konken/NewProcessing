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

public class Friend extends PApplet {

Node[]n;
float[]no;
int howmany=30;
public void setup() {
	
	n=new Node[howmany];
	no=new float[howmany];
	for(int i=0;i<howmany;i++){
		n[i]=new Node(random(width),random(height),random(2,5));
		no[i]=random(10);
	}
}
public void draw() {
	background(0);
	for(int i=0;i<howmany;i++){
		n[i].update(no[i]);
		no[i]+=0.01f;
		fill(255,200);
		beginShape();
		vertex(n[i].x,n[i].y);
		for(int j=0;j<howmany;j++){
			if(i!=j){
				if(dist(n[i].x,n[i].y,n[j].x,n[j].y)<200){
					vertex(n[j].x,n[j].y);
				}
			}
		}
		endShape(CLOSE);
	}
}
public void mousePressed(){
	/*for(int i=0;i<howmany;i++){
		n[i].separate(mouseX,mouseY,200,200);
	}*/
}
class Node{
	float x,y;
	float step, rad;
	float bool_x=1;
	float bool_y=1;
	Node(float x,float y,float step){
		this.x=x;
		this.y=y;
		this.step=step;
		this.rad=rad;
	}
	public void update(float n){
		if(x<0||width<x){
			bool_x*=-1;
		}
		if(y<0||height<y){
			bool_y*=-1;
		}
		x+=cos(radians(rad))*step*bool_x;
		y+=sin(radians(rad))*step*bool_y;
		rad+=(0.5f-noise(n))*10;
		sketch();
	}
	public void sketch(){
		fill(255);
		noStroke();
		ellipse(x, y,10,10);
	}
	public void ver(){
	}
	public void separate(float x2,float y2,float r,float step){
		if((x2-x)*(x2-x)+(y2-y)*(y2-y)<=r*r){
			x+=cos((atan2(y2-y,x2-x)))*step;
			y+=sin((atan2(y2-y,x2-x)))*step;
			stroke(255);
			line(x,y,x2,y2);
		}
	}
}
  public void settings() { 	fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Friend" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
