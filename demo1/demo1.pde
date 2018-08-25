float rad;
void setup() {
	fullScreen(2);
	rad=300;
}

void draw() {
	background(0);
	translate(width/2,height/2);
	for(int i=0;i<360;i++){
		fill(255);
		noStroke();
		float r=rad/(2*sin(PI/6));
		ellipse(cos(radians(i))*r,sin(radians(i))*r,10,10);
	}
}