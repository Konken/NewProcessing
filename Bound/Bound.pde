final float gravity=9.8;
Ball[]b=new Ball[500];
Obj o=new Obj(400,400,200);
void setup() {
	fullScreen();
	for(int i=0;i<b.length;i++){
		b[i]=new Ball(random(width),random(500),10,random(5,10),random(5,10));
	}
}

void draw() {
	background(0);
	o.drawEllipse();
	for(int i=0;i<b.length;i++){
		b[i].update();	
		if(o.collision(b[i].x,b[i].y)){
			b[i].angle=o.getAngle(b[i].x,b[i].y);
			b[i].x=o.x+cos(radians(o.give(b[i].x,b[i].y)))*o.rad/2;
			b[i].y=o.y+sin(radians(o.give(b[i].x,b[i].y)))*o.rad/2;
		}
	}
}

class Ball{
	float x,y,size,spx,spy;
	float nowy;
	float angle=90;
	float xb=1;
	float yb=1;
	Ball(float x,float y,float size,float spy,float spx){
		this.x=x;
		this.y=y;
		this.size=size;
		this.spy=spy;
		spx=0;
		nowy=y;
	}
	void update(){
		fill(255);
		stroke(255);
		ellipse(x, y,10,10);
		spx=cos(radians(angle))*5*xb;
		spy=sin(radians(angle))*5*yb;
		x+=spx;
		y+=spy;
		if(60<frameCount){
			if(width<x){
				xb*=-0.6;
				x=width;
			}
			else if(x<0){
				xb*=-0.6;
				x=0;
			}
			if(height<y){
				yb*=-.6;
				y=height;
			}
			else if(y<0){
				yb*=-0.6;
				y=0;
			}
		}
	}
}
class Obj{
	float x,y,rad;
	Obj(float x,float y,float rad){
		this.x=x;
		this.y=y;
		this.rad=rad;
	}
	void drawEllipse(){
		noFill();
		stroke(255);
		ellipse(x,y,rad,rad);
	}
	boolean collision(float ballX,float ballY){
		return dist(x,y,ballX,ballY)<rad/2;
	}
	float getAngle(float ballX,float ballY){
		float temp=give(ballX,ballY);
		if(temp<90){
			//temp+=90;
		}
		else if(temp==90){
			temp=0;
		}
		else{
			//temp-=90;
		}
		return temp;
	}
	float give(float ballX,float ballY){
		float temp=-degrees(atan2(abs(y-ballY),abs(x-ballX)));
		println("xは"+ballX+" yは"+ballY+" 角度は"+temp);
		return temp;
	}
}