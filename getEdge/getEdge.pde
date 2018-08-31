import gab.opencv.*;
import controlP5.*;

ControlP5 cp5;
OpenCV cv;
PImage im, im2;
int th1=0;  // 閾値1
int th2=0;  // 閾値2
int x;
int y;
color[][]c;
ArrayList<PVector>p= new ArrayList<PVector>();
ArrayList<Tenta>t= new ArrayList<Tenta>();
PVector[]p_a;
float[]step;
float[]angle;
color[]col;
void settings() {
	im = loadImage("10.jpg");
	x=im.width;
	y=im.height;
	size(x,y);
}
void setup() {
	im = loadImage("10.jpg");
	x=im.width;
	y=im.height;
	c=new color[x][y];
	p_a=new PVector[500];
	step=new float[p_a.length];
	angle=new float[p_a.length];
	col=new color[p_a.length];
	for(int i=0;i<p_a.length;i++){
		p_a[i]=new PVector(random(width),random(height));
		step[i]=0.5;
		angle[i]=random(360);
		col[i]=color(random(255),random(255),random(255));
	}
	cp5 = new ControlP5(this);
	cp5.addSlider("th1",0,255,128,10,10,100,30);
	cp5.addSlider("th2",0,255,128,10,50,100,30);
	cv = new OpenCV(this, im); 
  // 画像のエッジを検出
  //cv.findCannyEdges(94,255);
  //cv.findCannyEdges(25,102);
  cv.findCannyEdges(163,79);
  
  // エッジ画像を取得
  im2 = cv.getSnapshot();
  // エッジ画像を表示
  image(im2, 0, 0);
  for(int i=0;i<width;i++){
  	for(int j=0;j<height;j++){
  		c[i][j]=im2.get(i,j);
  		if(c[i][j]==-1){
  			p.add(new PVector(i,j));
  			t.add(new Tenta(255,255,255));
  		}
  	}
  }
  background(0);
}

void draw() {
	//background(0);
	/*for(int i=0;i<p.size();i++){
		//line(p.get(i).x+rx,p.get(i).y+ry,p.get(i).x-rx,p.get(i).y-ry);
		pushMatrix();
		translate(p.get(i).x,p.get(i).y);
		t.get(i).rot();
		t.get(i).ext();
		popMatrix();
	}*/
	for(int i=0;i<p_a.length;i++){
		int tx=round(p_a[i].x);
		int ty=round(p_a[i].y);
		if(width<=tx){
			tx=width-1;
		}
		if(height<=ty){
			ty=height-1;
		}
		if(c[tx][ty]==-1){
			noStroke();
			fill(col[i]);
			ellipse(p_a[i].x,p_a[i].y,10,10);
		}
		p_a[i].x+=cos(radians(angle[i]))*step[i];
		p_a[i].y+=sin(radians(angle[i]))*step[i];
		if(p_a[i].x<0||p_a[i].x>width||p_a[i].y<0||p_a[i].y>height){
			p_a[i].x=random(width);
			p_a[i].y=random(height);
		}
	}
	/*for(int i=0;i<width;i++){
		for(int j=0;j<height;j++){
			stroke(c[i][j]);
			point(i,j);
		}
	}
	/*cv = new OpenCV(this, im);
  	// 画像のエッジを検出
  	cv.findCannyEdges(th1,th2);
  	// エッジ画像を取得
  	im2 = cv.getSnapshot();
  	// エッジ画像を表示
  	image(im2, 0, 0);*/
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
  				x[i]=(0.5-noise(nx))*6+x[i-1];
  				y[i]=(noise(ny))*2+y[i-1];
  			}
  			else {
  				x[i]=(0.5-noise(nx))*5;
  				y[i]=(0.5-noise(ny))*5;
  			}
  			nx+=0.02;
  			ny+=0.01;
  		}
  	}
  	void rot(){
  		rotate(ro);
  	}
  	void ext(){
  		for(int i=0;i<len;i++){
  			fill(r,g,b);
  			stroke(100);
      // if(i%1==0){
      //   makeMini(60,i,x[i],y[i]);
      //   makeMini(-60,i,x[i],y[i]);
      // }
      strokeWeight(2);
      if(howmany-i<100){
      	strokeWeight((howmany-i)/5+2);
      }
      if(i<len-1){
      	line(x[i],y[i],x[i+1],y[i+1]);
      }
  }
  addLength();
    //println(len);
}

void addLength(){
	if(len<howmany-1){
		len++;
	}
}
}
