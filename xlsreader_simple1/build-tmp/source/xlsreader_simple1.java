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

public class xlsreader_simple1 extends PApplet {

float tx,ty;
Node[][]n;
Node[][]n2;
Node[][]n3;
Node[][]n4;
int len=10;
int len2=40;
int len3=60;
int len4=80;
float ranSize=len/2;
float ranSize2=len2/2;
float ranSize3=len3/2;
float ranSize4=len4/2;
PImage img;
int now=3;
public void setup() {
	
	img = loadImage("\u6771\u96f2\u3081\u3050\u3055\u3093.png");
	image(img,0,0,width,height);
	loadPixels();
	tx=0;
	ty=height/2;
	n=new Node[width/len][height/len];
	n2=new Node[width/len2][height/len2];
	n3=new Node[width/len3][height/len3];
	n4=new Node[width/len4][height/len4];
	for(int i=0;i<width/len;i++){
		for(int j=0;j<height/len;j++){
			n[i][j]=new Node(len,pixels[j*width*len + i*len],200);
		}
	}
	for(int i=0;i<width/len2;i++){
		for(int j=0;j<height/len2;j++){
			n2[i][j]=new Node(len2,pixels[j*width*len2 + i*len2],100);
		}
	}
	for(int i=0;i<width/len3;i++){
		for(int j=0;j<height/len3;j++){
			n3[i][j]=new Node(len3,pixels[j*width*len3 + i*len3],40);
		}
	}
	for(int i=0;i<width/len4;i++){
		for(int j=0;j<height/len4;j++){
			n4[i][j]=new Node(len4,pixels[j*width*len4 + i*len4],20);
		}
	}
	background(255);
}

public void draw() {
	/*fill(255,10);
	noStroke();
	rect(0,0,width,height);*/
	if(now==0){
		for(int i=0;i<width/len;i++){
			for(int j=0;j<height/len;j++){
				n[i][j].make(random(i*len-ranSize,i*len+ranSize),random(j*len-ranSize,j*len+ranSize));
			}
		}
	}
	else if(now==1){
		for(int i=0;i<width/len2;i++){
			for(int j=0;j<height/len2;j++){
				n2[i][j].make(random(i*len2-ranSize2,i*len2+ranSize2),random(j*len2-ranSize2,j*len2+ranSize2));
			}
		}
	}
	else if(now==2){
		for(int i=0;i<width/len3;i++){	
			for(int j=0;j<height/len3;j++){
				n3[i][j].make(random(i*len3-ranSize3,i*len3+ranSize3),random(j*len3-ranSize3,j*len+ranSize3));
			}
		}
	}
	else if(now==3)
{		for(int i=0;i<width/len4;i++){
			for(int j=0;j<height/len4;j++){
				n4[i][j].make(random(i*len4-ranSize4,i*len4+ranSize4),random(j*len4-ranSize4,j*len+ranSize4));
			}
		}
	}
	if(frameCount%150==0){
		now--;
	}
	//saveFrame("pic/line-####.tif"); 
	if(frameCount==200*5){
		exit();
	}
}

public void mouseDragged() {
	for(int i=0;i<width/len;i++){
		for(int j=0;j<height/len;j++){
			n[i][j].make(mouseX,mouseY);
		}
	}
}

public void keyPressed() {
	now--;
}
class Node{
	ArrayList hist = new ArrayList();
	float joinDist = 90;
	float s;
	int c;
	float al;
	Node(float s,int c,float al){
		this.c=c;
		this.s=s;
		this.al=al;
	}
	public void make(float x,float y){
		noFill();
		if(5<hist.size()){
			hist.remove(0);
		}
		stroke(c, al);
		strokeWeight(1);
		PVector d = new PVector(x+random(-s,s),y+random(-s,s), 0);
		hist.add(0,d);
		for (int p = 0; p < hist.size(); p++) {
			PVector v = (PVector) hist.get(p);
			float joinChance = p/hist.size() +
			d.dist(v)/joinDist;
			if (joinChance < random(1))
				line(d.x, d.y, v.x, v.y);
		}
	}
}











/*ArrayList hist = new ArrayList();
float joinDist = 90;
float tx,ty;
float xstep=2;
float n=random(10);
void setup() {
	fullScreen();
	background(#1AC8ED);
	tx=0;
	ty=height/2;
}

void draw() {
	make(tx,ty);
	tx+=xstep;
	ty+=(0.5-noise(n))*10;
	n+=0.1;
}

void mouseDragged() {
	make(mouseX,mouseY);
}
void make(float x,float y){
	stroke(#8C2155, 25);
	strokeWeight(1);
	PVector d = new PVector(x,y, 0);
	hist.add(0,d);
	for (int p = 0; p < hist.size(); p++) {
		PVector v = (PVector) hist.get(p);
		float joinChance = p/hist.size() +
		d.dist(v)/joinDist;
		if (joinChance < random(1))
			line(d.x, d.y, v.x, v.y);
	}
}
void keyPressed() {
	if (key == ' ') {
		background(#1AC8ED);
		hist.clear();
	}
}*/
  public void settings() { 	fullScreen(2); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "xlsreader_simple1" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
