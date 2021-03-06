Shape s;
color c1;
color c2;
void setup() {
	size(960,960);
	s=new Shape(height-150);
	c1=(#EDC7CF);
	c2=(#6A8E7F);
}

void draw() {
	translate(width/2,height/2);
	background(0);
	noStroke();
	fill(c1);
	rect(-width/2,-height/2,width,height/2);
	fill(c2);
	rect(-width/2,0,width,height/2);
	s.move();
	s.update();
	s.updateField();
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
	}
	void move(){
		ny=(get2(step)*len-y1)*bool;
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
		rotate(radians(get2(step)*360));
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
	}
	void updateField(){
		if(bool>0&&-75<ny&&ny<75){
			noStroke();
			fill(c1);
			beginShape();
			for(int i=-90;i<90;i++){
				vertex(i*6,sin(radians(i+90))*(ny+75)-1);
			}
			endShape();
		}
		else if(bool>0&&75<ny&&ny<500){
			noStroke();
			fill(c1);
			beginShape();
			vertex(-180,-10);
			for(int i=-90;i<90;i++){
				vertex(i*6,sin(radians(i+90))*(300-ny)-1);
			}
			vertex(180,-10);
			endShape(CLOSE);
		}
		if(bool<0&&-75<ny&&ny<75){
			noStroke();
			fill(c2);
			beginShape();
			for(int i=-90;i<90;i++){
				vertex(i*6,-sin(radians(i+90))*(ny-75)+1);
			}
			endShape();
		}
		else if(bool<0&&-75>ny&&ny>-500){
			noStroke();
			fill(c2);
			beginShape();
			vertex(-180,0);
			for(int i=-90;i<90;i++){
				vertex(i*6,-sin(radians(i+90))*(300+ny)+1);
			}
			vertex(180,0);
			endShape(CLOSE);
		}
	}
}
public float get2(float t) {
	return get(t, 1.70158);
}
float get5(float t) {
	float value=(t==1.0)?1.0:-pow(4, -6 * t) + 1;
	return value;
}
public float get(float t, float s) {
	--t;
	return t * t * ((s + 1.0) * t + s) + 1.0;
}


public float get3(float t) {
	return t * t * t;
}
public float get4(float t) {
	return -cos(t * (PI / 2)) + 1.0;
}