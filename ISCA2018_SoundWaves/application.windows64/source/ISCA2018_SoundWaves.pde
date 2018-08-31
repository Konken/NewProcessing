import ddf.minim.*;
import ddf.minim.analysis.*;

//棒グラフを描く数
//下記 bandHzの要素数以下である必要があります。
final int MAXBAND = 48;          
float[]yy;
float[]ver_y;
//グローバル変数
Minim       minim;               //minimオブジェクト
AudioPlayer player;              //演奏クラス
FFT         fft;                 //FFT解析クラス
float     specSize;              //周波数分解幅
float     initBand;              //分解Hz
int       stepCount;             //表示するサンプリング単位幅
int       wakuX, wakuY;          //外枠の描画原点
int       wakuWidth, wakuHeight; //外枠の幅と高さ
int       barWidth;              //棒グラフの幅
PImage    back;                  //背景画像
float rad=1.2;
float min=200;
float rNoise;  //赤用ノイズ変数 
float gNoise;  //緑用ノイズ変数
float bNoise;  //青用ノイズ変数
float rNoise_2;  //赤用ノイズ変数
float gNoise_2;  //緑用ノイズ変数
float bNoise_2;  //青用ノイズ変数
float step = 0.0002;
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
color[]col;
Boolean moveOrNot=false;
float moveStep;
PVector[]moveLenght;
PVector[]oldPVector;
float[]radStep;
float[]oldRad;
Shape s;
//以下の48個のHz単位に棒グラフを描く
int       bandHz[] =  {   43, 43, 43, 43, 43, 43, 43, 43, 
	43, 43, 43, 43, 43, 43, 43, 43, 
	86, 86, 86, 86, 86, 86, 86, 86, 
	172, 172, 172, 172, 688, 688, 688, 688, 
	215, 215, 344, 344, 516, 516, 688, 688, 
	1032, 1032, 1204, 1376, 1548, 1720, 1892, 2064 };
	void setup() {
		fullScreen(2);
		rectMode(CENTER);
  //fullScreen();
  //画面中央にスペクトルグラフ111111111を描画する
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
  //音楽読み込み
  minim = new Minim(this);
  /*selectInput("Select a *.mesh file:", "fileSelected");
  noLoop();
  interrupt();*/
  player = minim.loadFile("TheFatRat+-+Unity.mp3"); 
  //FFT設定
  fft = new FFT( player.bufferSize(), player.sampleRate()); 

  //周波数分解幅を取得
  //全周波数を、この数の個数に分割して分析可能。通常は513
  specSize = fft.specSize();

  //HANN窓指定。minimではHAMMINGより精度が高そう・・・
  fft.window( FFT.HANN );

  //初期分解Hz(getBandWidth)を求める
  //分解幅1つ単位のHz。
  //例えば分解Hzが 43 なら、43 × 513 = 22059Hzまで解析可能
  initBand = fft.getBandWidth();

  //棒グラフの幅
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
  col=new color[5];
  col[0]=#33658A;
  col[1]=#86BBD8;
  col[2]=#758E4F;
  col[3]=#F6AE2D;
  col[4]=#F26419;
  moveLenght=new PVector[4];
  oldPVector=new PVector[4];
  oldRad=new float[4];
  radStep=new float[4];
  s=new Shape(height-150);
}
void fileSelected(File selection) {
	player = minim.loadFile(selection.getAbsolutePath()); 
}
void interrupt() {
	while (player == null) {
		delay(200);
	}
	loop();
}
void draw() {
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
			moveStep+=0.005;
			if(moveStep>1){
				moveStep=1;
				moveOrNot=false;
			}
		}
	}
}
void mousePressed(){
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
		radStep[0]=-c.rad+1.8;
		radStep[1]=-c2.rad+0.8;
		radStep[2]=-c3.rad+0.8;
		radStep[3]=-c4.rad+0.8;
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
		radStep[0]=-c.rad+0.8;
		radStep[1]=-c2.rad+1.8;
		radStep[2]=-c3.rad+0.8;
		radStep[3]=-c4.rad+0.8;
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
		radStep[0]=-c.rad+0.8;
		radStep[1]=-c2.rad+0.8;
		radStep[2]=-c3.rad+1.8;
		radStep[3]=-c4.rad+0.8;
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
		radStep[0]=-c.rad+0.8;
		radStep[1]=-c2.rad+0.8;
		radStep[2]=-c3.rad+0.8;
		radStep[3]=-c4.rad+1.8;
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
		radStep[0]=-c.rad+1.2;
		radStep[1]=-c2.rad+1.2;
		radStep[2]=-c3.rad+1.2;
		radStep[3]=-c4.rad+1.2;
		moveStep=0;
	}
}
void setupFFT(){
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
	void one(){
		for(int index=0;index<MAXBAND;index++){
			drawOne(index);
		}
		strokeWeight(2);
		fill(col[4]);
		noStroke();
		ellipse(p.x,p.y,averageY/30*0.7*rad,averageY/30*0.7*rad);
		float tm=MAXBAND;
		countOfFloat+=averageY/tm/180;
	}
	void drawOne(int index){
		pushMatrix();
		translate(p.x,p.y);
		rotate(countOfFloat/60);
		for(int i=0;i<howmany;i++){
			strokeWeight(10);
			stroke(col[2]);
			float r=random(1,1.1);
			if (index!=0) {
				line(oldx[i], 
					oldy[i], 
					cos(radians(7.5*(index+1)))*yy[index]*rad*r, 
					sin(radians(7.5*(index+1)))*yy[index]*rad*r);
				oldx[i]=cos(radians(7.5*(index+1)))*yy[index]*rad*r;
				oldy[i]=sin(radians(7.5*(index+1)))*yy[index]*rad*r;
			} else {
				line(cos(radians(7.5*index))*yy[yy.length-1]*rad*r, 
					yy[yy.length-1]*sin(radians(7.5*index))*rad*r, 
					cos(radians(7.5*(index+1)))*yy[index]*rad*r, 
					sin(radians(7.5*(index+1)))*yy[index]*rad*r);
				oldx[i]=cos(radians(7.5*(index+1)))*yy[index]*rad*r;
				oldy[i]=sin(radians(7.5*(index+1)))*yy[index]*rad*r;
			}
		}
		strokeWeight(10);
		stroke(col[3]);
		line(cos(radians(7.5*(index)))*rad*min,
			sin(radians(7.5*(index)))*rad*min,
			cos(radians(7.5*(index)))*(rad*0.6)*yy[index],
			sin(radians(7.5*(index)))*(rad*0.6)*yy[index]);
		strokeWeight(10);
		stroke(col[1]);
		fill(col[1]);
		float r2=random(1,1.07);
		ellipse(cos(radians(7.5*(index)))*rad*min*r2,
			sin(radians(7.5*(index)))*rad*min*r2,
			yy[index]-190,yy[index]-190);
		popMatrix();
	}
	void two(){
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
		easingTime+=0.005+(addR-0.6)/100;
		if(easingTime>1){
			easingTime=0;
		}
	}
	void drawTwo(int index,float addR){
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
	void thr(){
		float R=11;
		float tm=MAXBAND;
		float addR=averageY/tm/500+rad;
		float count=0;

		for(int i=0;i<6;i++){
			pushMatrix();
			translate(p.x,p.y);
			R-=0.8*get2(easingTime);
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
		R-=2.5;
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
	void four(){
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
float get2(float t) {
	float value=(t==1.0)?1.0:-pow(4, -6 * t) + 1;
	return value;
}
float get(float t) {
	return (t == 1.0)
	? 1.0
	: (-pow(2, -10 * t) + 1);
}
float get3(float t,int i) {
	float value=(t==1.0)?1.0:-pow((i*2+2), -6 * t) + 1;
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
	color c1,c2;
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
	void move(){
		ny=(get4(step)*len-y1)*bool;
		if(ny==y1||ny==y2){
			step=0;
			bool*=-1;
		}
		step+=0.008;
	}
	void update(){
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
	void updateField(){
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
	return get5(t, 1.70158);
}
public float get5(float t, float s) {
	--t;
	return t * t * ((s + 1.0) * t + s) + 1.0;
}