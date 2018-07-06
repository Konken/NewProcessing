float x,y;
float noiseX,noiseY;
float angle;
float noiseAngle;
float rad=300;
void setup() {
	fullScreen();
	background(0);
	x=width/2;
	y=height/2;
	noiseX=random(10);
	noiseY=random(10);
	angle=0;
	noiseAngle=random(10);
}

void draw() {
	stroke(255,10);
	angle+=(noise(noiseAngle));
	float tx1=cos(radians(angle))*rad;
	float ty1=sin(radians(angle))*rad;
	float tx2=cos(radians(180+angle))*rad;
	float ty2=sin(radians(180+angle))*rad;
	line(x+tx1,y+ty1,x+tx2,y+ty2);
	noiseX+=0.5;
	noiseY+=0.5;
	noiseAngle+=0.05;
	rad=noise(noiseX)*3000;
}