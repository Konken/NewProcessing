ArrayList<Raindrop> r= new ArrayList<Raindrop>();
PImage photo;

void setup(){
	fullScreen(P3D);
	for(int i=0;i<100;i++){
		r.add(new Raindrop(random(-width/2,width/2),random(-height,height/2),random(500,1500),100));
	}
	photo = loadImage("usachan.png");
	smooth();
}

void draw() {
	background(#383838);
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
	void changeM(float m){
		this.m=m;
	}
	boolean draw(){
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
		stroke(#d65577,alph);
		strokeWeight(10);
		fill(#d65577,alph);
		line(x,y,z,x,y,z+len);
		return b;
	}
	boolean ripple(){
		boolean b=false;
		beginShape();
		stroke(#d65577,255-nowRad*5);
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