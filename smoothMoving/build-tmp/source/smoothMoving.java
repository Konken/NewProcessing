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

public class smoothMoving extends PApplet {

Circle[]c;
float[]time;
float[]step;
float rad=0;
float tArea=0;
float bool=1;
public void setup() {
	
	rectMode(RADIUS);
	c=new Circle[6];
	time=new float[c.length];
	step=new float[c.length];
	c[0]=new Circle(width/2,height/2,500);
	c[1]=new Circle(width/2,height/2,460);
	c[2]=new Circle(width/2,height/2,420);
	c[3]=new Circle(width/2,height/2,420);
	c[4]=new Circle(width/2,height/2,380);
	c[5]=new Circle(width/2,height/2,340);
	for(int i=0;i<c.length;i++){
		time[i]=0;
	}
	step[0]=0.022f;
	step[1]=0.026f;
	step[2]=0.024f;
	step[3]=0.022f;
	step[4]=0.026f;
	step[5]=0.024f;
}

public void draw() {
	background(0xff72A98F);
	noStroke();
	fill(0xff3D5A6C);
	ellipse(width/2, height/2,rad,rad);
	rad=get2(tArea)*320*2;
	tArea+=0.05f*bool;
	if(tArea>=1){
		tArea=1;
	}
	if(tArea<=0){
		tArea=0;
	}
	for(int i=0;i<3;i++){
		if(i%2==0){
			c[i].update(time[i]);
		}
		else{
			c[i].update2(time[i]);
		}
		time[i]+=step[i];
		if(time[i]>1){
			time[i]=1;
		}
	}
	for(int i=3;i<6;i++){
		if(time[2]>0.5f){
			if(i%2==1){
				c[i].update(time[i]);
			}
			else{
				c[i].update2(time[i]);
			}
			time[i]+=step[i];
			if(time[i]>1){
				time[i]=1;
			}
		}
	}
	if(time[5]==1&&tArea==1){
		bool*=-1;
	}
	smooth();
}

public void mousePressed(){
	for(int i=0;i<c.length;i++){
		time[i]=0;
	}
	bool*=-1;
}
class Circle{
	float x;
	float y;
	float rad;
	Circle(float x,float y,float rad){
		this.x=x;
		this.y=y;
		this.rad=rad;
	}
	public void update(float t){
		float f=get2(t);
		//f=t;
		println(f);
		for(float i=t*360;i<f*360;i++){
			stroke(0xff3D5A6C);
			fill(0xff3D5A6C);
			strokeWeight(10);
			line(cos(radians(i-1))*rad+x,sin(radians(i-1))*rad+y,
				cos(radians(i))*rad+x,sin(radians(i))*rad+y);
		}
	}
	public void update2(float t){
		float f=get2(t);
		//f=t;
		println(f);
		for(float i=t*360;i<f*360;i+=10){
			stroke(0xff3D5A6C);
			fill(0xff3D5A6C);
			strokeWeight(10);
			pushMatrix();
			translate(cos(radians(i-1))*rad+x,sin(radians(i-1))*rad+y);
			rotate(radians(i));
			rect(0,0,10,10);
			popMatrix();
		}
	}
}

public float get2(float t) {
	float value=(t==1.0f)?1.0f:-pow(4, -6 * t) + 1;
	return value;
}
  public void settings() { 	fullScreen(2); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#EA0202", "--stop-color=#E81C1C", "smoothMoving" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
