import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 
import ddf.minim.analysis.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class ISCA2018_SoundWaves extends PApplet {




//\u68d2\u30b0\u30e9\u30d5\u3092\u63cf\u304f\u6570
//\u4e0b\u8a18 bandHz\u306e\u8981\u7d20\u6570\u4ee5\u4e0b\u3067\u3042\u308b\u5fc5\u8981\u304c\u3042\u308a\u307e\u3059\u3002
final int MAXBAND = 48;          
float[]yy;
float[]ver_y;
//\u30b0\u30ed\u30fc\u30d0\u30eb\u5909\u6570
Minim       minim;               //minim\u30aa\u30d6\u30b8\u30a7\u30af\u30c8
AudioPlayer player;              //\u6f14\u594f\u30af\u30e9\u30b9
FFT         fft;                 //FFT\u89e3\u6790\u30af\u30e9\u30b9
float     specSize;              //\u5468\u6ce2\u6570\u5206\u89e3\u5e45
float     initBand;              //\u5206\u89e3Hz
int       stepCount;             //\u8868\u793a\u3059\u308b\u30b5\u30f3\u30d7\u30ea\u30f3\u30b0\u5358\u4f4d\u5e45
int       wakuX, wakuY;          //\u5916\u67a0\u306e\u63cf\u753b\u539f\u70b9
int       wakuWidth, wakuHeight; //\u5916\u67a0\u306e\u5e45\u3068\u9ad8\u3055
int       barWidth;              //\u68d2\u30b0\u30e9\u30d5\u306e\u5e45
PImage    back;                  //\u80cc\u666f\u753b\u50cf
float rad=1.2f;
float min=200;
float rNoise;  //\u8d64\u7528\u30ce\u30a4\u30ba\u5909\u6570 
float gNoise;  //\u7dd1\u7528\u30ce\u30a4\u30ba\u5909\u6570
float bNoise;  //\u9752\u7528\u30ce\u30a4\u30ba\u5909\u6570
float rNoise_2;  //\u8d64\u7528\u30ce\u30a4\u30ba\u5909\u6570
float gNoise_2;  //\u7dd1\u7528\u30ce\u30a4\u30ba\u5909\u6570
float bNoise_2;  //\u9752\u7528\u30ce\u30a4\u30ba\u5909\u6570
float step = 0.0002f;
int count=0;
int bool=1;
Cell c;
Cell c2;
Cell c3;
Cell c4;
float averageY=0;
float countOfFloat;
float easingTime=0;
float[]sphX;
float[]sphY;
float[]sphZ;
int[]col;
Boolean moveOrNot=false;
float moveStep;
PVector[]moveLenght;
PVector[]oldPVector;
float[]radStep;
float[]oldRad;
Shape s;
//\u4ee5\u4e0b\u306e48\u500b\u306eHz\u5358\u4f4d\u306b\u68d2\u30b0\u30e9\u30d5\u3092\u63cf\u304f
int       bandHz[] =  {   43, 43, 43, 43, 43, 43, 43, 43, 
	43, 43, 43, 43, 43, 43, 43, 43, 
	86, 86, 86, 86, 86, 86, 86, 86, 
	172, 172, 172, 172, 688, 688, 688, 688, 
	215, 215, 344, 344, 516, 516, 688, 688, 
	1032, 1032, 1204, 1376, 1548, 1720, 1892, 2064 };
	public void setup() {
		
		rectMode(CENTER);
  //fullScreen();
  //\u753b\u9762\u4e2d\u592e\u306b\u30b9\u30da\u30af\u30c8\u30eb\u30b0\u30e9\u30d5111111111\u3092\u63cf\u753b\u3059\u308b
  yy=new float[bandHz.length];
  ver_y=new float[bandHz.length];
  for (int i=0; i<yy.length; i++) {
  	yy[i]=0;
  	ver_y[i]=0;
  }
  wakuX = 10;
  wakuY = 10;
  wakuWidth = 620;
  wakuHeight = 460;

  //back = loadImage( "ensoukai.jpg" );
  background(0);
  textSize(20);
  text("SELECT FILE", width/2-100,height/2-10);
  //\u97f3\u697d\u8aad\u307f\u8fbc\u307f
  minim = new Minim(this);
  /*selectInput("Select a *.mesh file:", "fileSelected");
  noLoop();
  interrupt();*/
  player = minim.loadFile("TheFatRat+-+Unity.mp3"); 
  //FFT\u8a2d\u5b9a
  fft = new FFT( player.bufferSize(), player.sampleRate()); 

  //\u5468\u6ce2\u6570\u5206\u89e3\u5e45\u3092\u53d6\u5f97
  //\u5168\u5468\u6ce2\u6570\u3092\u3001\u3053\u306e\u6570\u306e\u500b\u6570\u306b\u5206\u5272\u3057\u3066\u5206\u6790\u53ef\u80fd\u3002\u901a\u5e38\u306f513
  specSize = fft.specSize();

  //HANN\u7a93\u6307\u5b9a\u3002minim\u3067\u306fHAMMING\u3088\u308a\u7cbe\u5ea6\u304c\u9ad8\u305d\u3046\u30fb\u30fb\u30fb
  fft.window( FFT.HANN );

  //\u521d\u671f\u5206\u89e3Hz(getBandWidth)\u3092\u6c42\u3081\u308b
  //\u5206\u89e3\u5e451\u3064\u5358\u4f4d\u306eHz\u3002
  //\u4f8b\u3048\u3070\u5206\u89e3Hz\u304c 43 \u306a\u3089\u300143 \u00d7 513 = 22059Hz\u307e\u3067\u89e3\u6790\u53ef\u80fd
  initBand = fft.getBandWidth();

  //\u68d2\u30b0\u30e9\u30d5\u306e\u5e45
  barWidth = wakuWidth / MAXBAND;
  PVector tp=new PVector(width/4,height/2-height/4);
  PVector tp2=new PVector(width/4,height/2+height/4+height/16);
  PVector tp3=new PVector(width/4+width/2,height/2-height/4);
  PVector tp4=new PVector(width/4+width/2,height/2+height/4);
  c=new Cell(0,rad,tp);
  c2=new Cell(0,rad,tp2);
  c3=new Cell(2,rad,tp3);
  c4=new Cell(0,rad,tp4);
  countOfFloat=0;
  sphX=new float[30];
  sphY=new float[30];
  sphZ=new float[30];
  for(int i=0;i<30;i++){
  	float t=random(180);
  	float p=random(-180,180);
  	sphX[i]=rad*min*sin(radians(t)*cos(radians(p)));
  	sphY[i]=rad*min*sin(radians(t)*sin(radians(p)));
  	sphZ[i]=rad*min*cos(radians(t));
  }
  col=new int[5];
  col[0]=0xff33658A;
  col[1]=0xff86BBD8;
  col[2]=0xff758E4F;
  col[3]=0xffF6AE2D;
  col[4]=0xffF26419;
  moveLenght=new PVector[4];
  oldPVector=new PVector[4];
  oldRad=new float[4];
  radStep=new float[4];
  s=new Shape(height-150);
}
public void fileSelected(File selection) {
	player = minim.loadFile(selection.getAbsolutePath()); 
}
public void interrupt() {
	while (player == null) {
		delay(200);
	}
	loop();
}
public void draw() {
	if(60*6>frameCount){
		translate(width/2,height/2);
		background(0);
		noStroke();
		fill(255);
		rect(0,-height/4,width,height/2);
		fill(0);
		rect(0,height/4,width,height/2);
		s.move();
		s.update();
		s.updateField();
	}
	if(60*6==frameCount)player.play(0);
	if(60*6<frameCount){
		if ( player.isPlaying() == false ) {
			player.play(0);
		}
		background(col[0]);
		fft.forward( player.mix );
		setupFFT();
		c.one();
		c2.two();
		c3.thr();
		c4.four();
		averageY=0;
		strokeWeight(2);
		for(int i=0;i<4;i++){
			if(60*i+50-20<mouseX&&mouseX<60*i+50+40-20&&50-20<mouseY&&mouseY<50+20){
				fill(255);
				stroke(255);
				rect(60*i+50,50,40,40);
				textSize(30);
				fill(col[0]);
				text(i+1,60*i+50-10,50+12);
			}
			else{
				stroke(255);
				noFill();
				rect(60*i+50,50,40,40);
				textSize(30);
				fill(255);
				text(i+1,60*i+50-10,50+12);
			}
		}
		if(140-110<mouseX&&mouseX<140+110&&100-20<mouseY&&mouseY<100+20){
			fill(255);
			stroke(255);
			rect(140,100,220,40);
			textSize(30);
			fill(col[0]);
			text("Default",140-50,100+12);
		}
		else{
			stroke(255);
			noFill();
			rect(140,100,220,40);
			textSize(30);
			fill(255);
			text("Default",140-50,100+12);
		}
		if(moveOrNot){
			PVector[]newP=new PVector[4];
			for(int i=0;i<4;i++){
				newP[i]=new PVector(oldPVector[i].x+moveLenght[i].x*get2(moveStep),oldPVector[i].y+moveLenght[i].y*get2(moveStep));
			}
			c.p=newP[0];
			c2.p=newP[1];
			c3.p=newP[2];
			c4.p=newP[3];
			c.rad=oldRad[0]+radStep[0]*get2(moveStep);
			c2.rad=oldRad[1]+radStep[1]*get2(moveStep);
			c3.rad=oldRad[2]+radStep[2]*get2(moveStep);
			c4.rad=oldRad[3]+radStep[3]*get2(moveStep);
			moveStep+=0.005f;
			if(moveStep>1){
				moveStep=1;
				moveOrNot=false;
			}
		}
	}
}
public void mousePressed(){
	if(60*0+50-20<mouseX&&mouseX<60*0+50+40-20&&50-20<mouseY&&mouseY<50+20){
		moveOrNot=true;
		moveLenght[0]=new PVector(-c.p.x+width/4+width/8+width/32,-c.p.y+height/2-height/8);
		moveLenght[1]=new PVector(-c2.p.x+width/8,-c2.p.y+height/2+height/4+height/16);
		moveLenght[2]=new PVector(-c3.p.x+width/4+width/2+width/16,-c3.p.y+height/2-height/4-height/32);
		moveLenght[3]=new PVector(-c4.p.x+width/4+width/2+width/32,-c4.p.y+height/2+height/4+height/16);
		oldPVector[0]=c.p;
		oldPVector[1]=c2.p;
		oldPVector[2]=c3.p;
		oldPVector[3]=c4.p;
		oldRad[0]=c.rad;
		oldRad[1]=c2.rad;
		oldRad[2]=c3.rad;
		oldRad[3]=c4.rad;
		radStep[0]=-c.rad+1.8f;
		radStep[1]=-c2.rad+0.8f;
		radStep[2]=-c3.rad+0.8f;
		radStep[3]=-c4.rad+0.8f;
		moveStep=0;
	}
	if(60*1+50-20<mouseX&&mouseX<60*1+50+40-20&&50-20<mouseY&&mouseY<50+20){
		moveOrNot=true;
		moveLenght[0]=new PVector(-c.p.x+width/8,-c.p.y+height/2-height/4-height/16);
		moveLenght[1]=new PVector(-c2.p.x+width/4+width/8+width/32,-c2.p.y+height/2+height/8);
		moveLenght[2]=new PVector(-c3.p.x+width/4+width/2+width/16,-c3.p.y+height/2-height/4-height/32);
		moveLenght[3]=new PVector(-c4.p.x+width/4+width/2+width/32,-c4.p.y+height/2+height/4+height/16);
		oldPVector[0]=c.p;
		oldPVector[1]=c2.p;
		oldPVector[2]=c3.p;
		oldPVector[3]=c4.p;
		oldRad[0]=c.rad;
		oldRad[1]=c2.rad;
		oldRad[2]=c3.rad;
		oldRad[3]=c4.rad;
		radStep[0]=-c.rad+0.8f;
		radStep[1]=-c2.rad+1.8f;
		radStep[2]=-c3.rad+0.8f;
		radStep[3]=-c4.rad+0.8f;
		moveStep=0;
	}
	if(60*2+50-20<mouseX&&mouseX<60*2+50+40-20&&50-20<mouseY&&mouseY<50+20){
		moveOrNot=true;
		moveLenght[0]=new PVector(-c.p.x-width/4+width/2-width/16,-c.p.y+height/2-height/4-height/32);
		moveLenght[1]=new PVector(-c2.p.x-width/4+width/2-width/16,-c2.p.y+height/2+height/4+height/16);
		moveLenght[2]=new PVector(-c3.p.x+width/4+width/2-width/8,-c3.p.y+height/2-height/8-height/16);
		moveLenght[3]=new PVector(-c4.p.x+width/4+width/2+width/32,-c4.p.y+height/2+height/4+height/16);
		oldPVector[0]=c.p;
		oldPVector[1]=c2.p;
		oldPVector[2]=c3.p;
		oldPVector[3]=c4.p;
		oldRad[0]=c.rad;
		oldRad[1]=c2.rad;
		oldRad[2]=c3.rad;
		oldRad[3]=c4.rad;
		radStep[0]=-c.rad+0.8f;
		radStep[1]=-c2.rad+0.8f;
		radStep[2]=-c3.rad+1.8f;
		radStep[3]=-c4.rad+0.8f;
		moveStep=0;
	}
	if(60*3+50-20<mouseX&&mouseX<60*3+50+40-20&&50-20<mouseY&&mouseY<50+20){
		moveOrNot=true;
		moveLenght[0]=new PVector(-c.p.x-width/4+width/2-width/16,-c.p.y+height/2-height/4-height/32);
		moveLenght[1]=new PVector(-c2.p.x-width/4+width/2-width/16,-c2.p.y+height/2+height/4+height/16);
		moveLenght[2]=new PVector(-c3.p.x+width/4+width/2+width/32,-c3.p.y+height/2-height/4-height/16);
		moveLenght[3]=new PVector(-c4.p.x+width/4+width/2-width/8,-c4.p.y+height/2+height/8);
		oldPVector[0]=c.p;
		oldPVector[1]=c2.p;
		oldPVector[2]=c3.p;
		oldPVector[3]=c4.p;
		oldRad[0]=c.rad;
		oldRad[1]=c2.rad;
		oldRad[2]=c3.rad;
		oldRad[3]=c4.rad;
		radStep[0]=-c.rad+0.8f;
		radStep[1]=-c2.rad+0.8f;
		radStep[2]=-c3.rad+0.8f;
		radStep[3]=-c4.rad+1.8f;
		moveStep=0;
	}
	if(140-110<mouseX&&mouseX<140+110&&100-20<mouseY&&mouseY<100+20){
		moveOrNot=true;
		moveLenght[0]=new PVector(-c.p.x+width/4,-c.p.y+height/2-height/4);
		moveLenght[1]=new PVector(-c2.p.x+width/4,-c2.p.y+height/2+height/4+height/16);
		moveLenght[2]=new PVector(-c3.p.x+width/4+width/2,-c3.p.y+height/2-height/4);
		moveLenght[3]=new PVector(-c4.p.x+width/4+width/2,-c4.p.y+height/2+height/4);
		oldPVector[0]=c.p;
		oldPVector[0]=c.p;
		oldPVector[1]=c2.p;
		oldPVector[2]=c3.p;
		oldPVector[3]=c4.p;
		oldRad[0]=c.rad;
		oldRad[1]=c2.rad;
		oldRad[2]=c3.rad;
		oldRad[3]=c4.rad;
		radStep[0]=-c.rad+1.2f;
		radStep[1]=-c2.rad+1.2f;
		radStep[2]=-c3.rad+1.2f;
		radStep[3]=-c4.rad+1.2f;
		moveStep=0;
	}
}
public void setupFFT(){
	int ToStep = 0;
	int FromStep = 0;
	for ( int index = 0; index < MAXBAND; index++ ) {
		stroke(255);
		fill(0);
		int bandStep = (int)( bandHz[index] / initBand );
		if ( bandStep < 1 ) { 
			bandStep = 1;
		}

		ToStep = ToStep + Math.round( bandStep );
		if ( ToStep > specSize ) { 
			ToStep = (int)specSize;
		}  

		float bandAv = 0;
		for ( int j = FromStep; j < ToStep; j++ ) {      
			float bandDB = 0; 
			if ( fft.getBand( j ) != 0 ) {   
				bandDB = 2 * ( 20 * ((float)Math.log10(fft.getBand(j))) + 40);
				if ( bandDB < 0 ) { 
					bandDB = 0;
				}
			}      
			bandAv = bandAv + bandDB;
		}    
		bandAv = bandAv / bandStep;
		FromStep = ToStep;


		float y = map( bandAv, 0, 250, 0, wakuHeight ); 
		if ( y < 0) { 
			y = 0;
		}
		averageY+=y;
		float tempy=y;
		if (y<min)y=min;
		yy[index]=y;
		ver_y[index]=tempy-100;
	}
}
class Cell{
	int num;
	float rad;
	float[]oldx;
	float[]oldy;
	int howmany;
	PVector p;
	Cell(int num,float rad,PVector p){
		this.num=num;
		this.rad=rad;
		howmany=4;
		oldx=new float[howmany];
		oldy=new float[howmany];
		this.p=p;
		if(num==2){
			println(p.x);
		}
	}
	public void one(){
		for(int index=0;index<MAXBAND;index++){
			drawOne(index);
		}
		strokeWeight(2);
		fill(col[4]);
		noStroke();
		ellipse(p.x,p.y,averageY/30*0.7f*rad,averageY/30*0.7f*rad);
		float tm=MAXBAND;
		countOfFloat+=averageY/tm/180;
	}
	public void drawOne(int index){
		pushMatrix();
		translate(p.x,p.y);
		rotate(countOfFloat/60);
		for(int i=0;i<howmany;i++){
			strokeWeight(10);
			stroke(col[2]);
			float r=random(1,1.1f);
			if (index!=0) {
				line(oldx[i], 
					oldy[i], 
					cos(radians(7.5f*(index+1)))*yy[index]*rad*r, 
					sin(radians(7.5f*(index+1)))*yy[index]*rad*r);
				oldx[i]=cos(radians(7.5f*(index+1)))*yy[index]*rad*r;
				oldy[i]=sin(radians(7.5f*(index+1)))*yy[index]*rad*r;
			} else {
				line(cos(radians(7.5f*index))*yy[yy.length-1]*rad*r, 
					yy[yy.length-1]*sin(radians(7.5f*index))*rad*r, 
					cos(radians(7.5f*(index+1)))*yy[index]*rad*r, 
					sin(radians(7.5f*(index+1)))*yy[index]*rad*r);
				oldx[i]=cos(radians(7.5f*(index+1)))*yy[index]*rad*r;
				oldy[i]=sin(radians(7.5f*(index+1)))*yy[index]*rad*r;
			}
		}
		strokeWeight(10);
		stroke(col[3]);
		line(cos(radians(7.5f*(index)))*rad*min,
			sin(radians(7.5f*(index)))*rad*min,
			cos(radians(7.5f*(index)))*(rad*0.6f)*yy[index],
			sin(radians(7.5f*(index)))*(rad*0.6f)*yy[index]);
		strokeWeight(10);
		stroke(col[1]);
		fill(col[1]);
		float r2=random(1,1.07f);
		ellipse(cos(radians(7.5f*(index)))*rad*min*r2,
			sin(radians(7.5f*(index)))*rad*min*r2,
			yy[index]-190,yy[index]-190);
		popMatrix();
	}
	public void two(){
		float tm=MAXBAND;
		float addR=averageY/tm/500+rad;
		for(int i=0;i<MAXBAND;i++){
			drawTwo(i,addR);
		}
		strokeWeight(5);
		noFill();
		stroke(col[2]);
		pushMatrix();
		translate(p.x,p.y);
		rotate(radians(get3(easingTime,1)*120));
		triangle(cos(radians(150+120))*min*addR,
			sin(radians(150+120))*min*addR,
			cos(radians(150+120+120))*min*addR,
			sin(radians(150+120+120))*min*addR,
			cos(radians(150+120+120*2))*min*addR,
			sin(radians(150+120+120*2))*min*addR);
		popMatrix();
		strokeWeight(5);
		int count=0;
		for(float i=0;i<8;i++){
			pushMatrix();
			stroke(lerpColor(col[2], col[3], i/8));
			translate(p.x,p.y);
			rotate(radians(get3(easingTime,count+2)*120));
			triangle(cos(radians(150+120))*addR*(min-(i+1)*15),
				sin(radians(150+120))*addR*(min-(i+1)*15),
				cos(radians(150+120+120))*addR*(min-(i+1)*15),
				sin(radians(150+120+120))*addR*(min-(i+1)*15),
				cos(radians(150+120+120*2))*addR*(min-(i+1)*15),
				sin(radians(150+120+120*2))*addR*(min-(i+1)*15));
			popMatrix();
			count++;
		}
		easingTime+=0.005f+(addR-0.6f)/100;
		if(easingTime>1){
			easingTime=0;
		}
	}
	public void drawTwo(int index,float addR){
		pushMatrix();
		translate(p.x,p.y);
		rotate(radians(get3(easingTime,1)*120));
		float tr=rad*min/3/32*sqrt(3)*2;
		for(int i=0;i<3;i++){
			float x=cos(radians(150+120+120*i))*min*addR+cos(radians(120+120*i))*tr*(index+1);
			float y=sin(radians(150+120+120*i))*min*addR+sin(radians(120+120*i))*tr*(index+1);
			noFill();
			stroke(col[4]);
			strokeWeight(5);
			line(x,y,x+cos(radians(180+30+120*i))*(yy[index]-min),y+sin(radians(180+30+120*i))*(yy[index]-min));
		}
		popMatrix();
	}
	public void thr(){
		float R=11;
		float tm=MAXBAND;
		float addR=averageY/tm/500+rad;
		float count=0;

		for(int i=0;i<6;i++){
			pushMatrix();
			translate(p.x,p.y);
			R-=0.8f*get2(easingTime);
			int index=1;
			for (int theta = 0; theta < 360; theta+=8) {
				float x = R * (16 * sin(radians(theta)) * sin(radians(theta)) * sin(radians(theta)))*addR*yy[index]/200;
				float y = (-1) * R * (13 * cos(radians(theta)) - 5 * cos(radians(2 * theta))
					- 2 * cos(radians(3 * theta)) - cos(radians(4 * theta)))*addR*yy[index]/200;
				stroke(lerpColor(col[2], col[3], count/6));
				strokeWeight(18-i*2);
				point(x,y);
				index++;
			}
			count++;
			popMatrix();
		}
		pushMatrix();
		translate(p.x,p.y);
		R-=2.5f;
		int index=1;
		beginShape();
		for(int theta = 0; theta < 360; theta+=8) {
			float x = R * (16 * sin(radians(theta)) * sin(radians(theta)) * sin(radians(theta)))*addR*yy[index]/200;
			float y = (-1) * R * (13 * cos(radians(theta)) - 5 * cos(radians(2 * theta))
				- 2 * cos(radians(3 * theta)) - cos(radians(4 * theta)))*addR*yy[index]/200+20;
			fill(col[4]);
			noStroke();
			curveVertex(x, y);
			index++;
		}
		endShape(CLOSE);
		popMatrix();
	}
	public void four(){
		pushMatrix();
		translate(p.x,p.y);
		float tm=MAXBAND;
		float addR=averageY/tm/500+rad;
		for(int ang=0;ang<4;ang++){
			for(int i=0;i<MAXBAND-12;i++){
				strokeWeight(5);
				stroke(col[ang+1]);
				noFill();
				rect(cos(radians(90*ang+i*2+get2(easingTime)*90))*(i+1)*addR*min/36+cos(radians(90*ang))*50,
					sin(radians(90*ang+i*2+get2(easingTime)*90))*(i+1)*addR*min/36+sin(radians(90*ang))*50,
					yy[i]-200,yy[i]-200);
			}
			rect(cos(radians(90*ang+(MAXBAND-12)*2+get2(easingTime)*90))*(MAXBAND+1-12)*addR*min/36+cos(radians(90*ang))*50,
				sin(radians(90*ang+(MAXBAND-12)*2+get2(easingTime)*90))*(MAXBAND+1-12)*addR*min/36+sin(radians(90*ang))*50,
				20*rad,20*rad);
		}
		popMatrix();
	}
}
public float get2(float t) {
	float value=(t==1.0f)?1.0f:-pow(4, -6 * t) + 1;
	return value;
}
public float get(float t) {
	return (t == 1.0f)
	? 1.0f
	: (-pow(2, -10 * t) + 1);
}
public float get3(float t,int i) {
	float value=(t==1.0f)?1.0f:-pow((i*2+2), -6 * t) + 1;
	return value;
}

class Shape{
	float x1;
	float y1;
	float x2;
	float y2;
	float nx;
	float ny;
	float rad;
	float bool;
	float step;
	float step2;
	float len;
	float[]angRad;
	int c1,c2;
	Shape(float len){
		x1=0;
		x2=0;
		y1=height/2-75;
		y2=-height/2+75;
		bool=1;
		this.len=len;
		step=0;
		rad=100;
		angRad=new float[361];
		for(int i=0;i<angRad.length;i++){
			angRad[i]=rad;
		}
		step2=1;
		c1=255;
		c2=0;
	}
	public void move(){
		ny=(get4(step)*len-y1)*bool;
		if(ny==y1||ny==y2){
			step=0;
			bool*=-1;
		}
		step+=0.008f;
	}
	public void update(){
		noStroke();
		pushMatrix();
		translate(nx,ny);
		rotate(radians(get4(step)*360));
		if(ny<0){
			fill(c2);
			beginShape();
			for(int i=0;i<4;i++){
				vertex(cos(radians(90*i))*75,sin(radians(90*i))*75);
			}
			endShape();
		}else{
			fill(c1);
			beginShape();
			for(int i=0;i<6;i++){
				vertex(cos(radians(60*i))*75,sin(radians(60*i))*75);
			}
			endShape();
		}
		popMatrix();
		pushMatrix();
		translate(-width/2,-height/2);
		textSize(150);
		if(ny>0){
			fill(0);
			translate(width/2-150*3,height/4);
			text("Now Loading",0,0);
		}else{
			fill(255);
			translate(width/2-150*3,height/4+height/2+height/8);
			text("Now Loading",0,0);
		}
		popMatrix();
	}
	public void updateField(){
		if(bool>0&&-75<ny&&ny<75){
			noStroke();
			fill(c1);
			beginShape();
			for(int i=-90;i<90;i++){
				vertex(i*12,sin(radians(i+90))*(ny+75)-1);
			}
			endShape();
		}
		else if(bool>0&&75<ny&&ny<500){
			noStroke();
			fill(c1);
			beginShape();
			vertex(-180,-10);
			for(int i=-90;i<90;i++){
				vertex(i*12,sin(radians(i+90))*(300-ny)-1);
			}
			vertex(180+360,-10);
			endShape(CLOSE);
		}
		if(bool<0&&-75<ny&&ny<75){
			noStroke();
			fill(c2);
			beginShape();
			for(int i=-90;i<90;i++){
				vertex(i*12,-sin(radians(i+90))*(ny-75)+1);
			}
			endShape();
		}
		else if(bool<0&&-75>ny&&ny>-500){
			noStroke();
			fill(c2);
			beginShape();
			vertex(-180,0);
			for(int i=-90;i<90;i++){
				vertex(i*12,-sin(radians(i+90))*(300+ny)+1);
			}
			vertex(180,0);
			endShape(CLOSE);
		}
	}
}
public float get4(float t) {
	return get5(t, 1.70158f);
}
public float get5(float t, float s) {
	--t;
	return t * t * ((s + 1.0f) * t + s) + 1.0f;
}
  public void settings() { 	fullScreen(2); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "ISCA2018_SoundWaves" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
