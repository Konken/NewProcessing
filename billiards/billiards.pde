float SPEED = 5;
float R = 20	;
int NUMBER = 100;
Ball[] balls = new Ball[NUMBER];
float des=1	;
void setup() {
	fullScreen();
	background(0);

	float angle = TWO_PI / NUMBER;
	for (int i = 0; i < NUMBER; i++) {
		float addx = cos(angle * i);
		float addy = sin(angle * i);
		balls[i] = new Ball(
			random(width),random(height), 
			SPEED * addx + 1, SPEED * addy  + 1, i, balls);
	}
}

void draw() {
	background(#64F58D);
	for (int i = 0; i < NUMBER; i++) {
		balls[i].clearVector();
	}
	for (int i = 0; i < NUMBER; i++) {
		Ball ball = (Ball) balls[i];
		ball.collide();
		ball.move();
		ball.draw();
	}
}

class Ball
{
	float x, y;
	float vx, vy;
	PVector target = new PVector();
	PVector impulse = new PVector(1, 1);
	int id;
	Ball[] others;
	int count=0;
	Ball(
		float _x, float _y, float _vx, float _vy, int _id, Ball[] _others) {
		x = _x;
		y = _y;
		vx = _vx;
		vy = _vy;
		id = _id;
		others = _others;
	}

	void move() {
		vx *= impulse.x;   
		x = x + vx + target.x;

		if (x - R <= 0) {
			x = R;
			vx *= -1*des;
			count=60;
		}
		if (x + R >= width) {
			x = width - R;
			vx *= -1*des;
			count=60;
		}

		vy *= impulse.y;
		y = y + vy + target.y;

		if (y - R <= 0) {
			y = R;
			vy *= -1*des;
			count=60;
		}
		if (y + R >= height) {
			y = height - R;
			vy *= -1*des;
			count=60;
		}
	}

	void draw() {
		if(0<count){
			fill(#8D80AD);
			noStroke();
			count--;
		}
		else{
			noFill();
		}
		ellipse(x, y, R * 2, R * 2);
	}

	void clearVector() {
		target.x = 0;
		target.y = 0;
		impulse.x = 1;
		impulse.y = 1;
	}
	void collide() {
		for (int i = id + 1; i < NUMBER; i++) {
			Ball otherBall = (Ball) others[i];
			float dx = otherBall.x - x;
			float dy = otherBall.y - y;
			float distance =sqrt(dx * dx + dy * dy);
			if (distance <= R * 2) {
				count=60;
				vx*=des;
				vy*=des;
				float angle = atan2(dy, dx);
				float push_distance = R * 2 - distance;
				float push_x = push_distance * cos(angle);
				float push_y = push_distance * sin(angle);

				target.x -= push_x;
				target.y -= push_y;        
				otherBall.target.x += push_x;
				otherBall.target.y += push_y;
				if ((vx >= 0 && vx - otherBall.vx >= 0) || (vx < 0 && vx - otherBall.vx < 0)) {
					impulse.x = -1;
				}

				if (vx * otherBall.vx <= 0) {
					otherBall.impulse.x = -1;
				}

				if ((vy >= 0 && vy - otherBall.vy >= 0) || (vy < 0 && vy - otherBall.vy < 0)) {
					impulse.y = -0.1;
				}

				if (vy *  otherBall.vy <= 0) {
					otherBall.impulse.y = -1;
				}
			}
		}
	}
}
/*float yoah=1;
float kdn=390;
float kun=10;
float krn=510;
float kln=10;
Ball[]b;
float rad=50;
int howMany=50;
void setup() {
	fullScreen();
	ellipseMode(CENTER);
	b=new Ball[howMany];
	for(int i=0;i<b.length;i++){
		b[i]=new Ball(random(rad,width-rad),random(rad, height-rad),random(360),i,b);
	}
}

void draw() {
	background(255);
	for(int i=0;i<b.length;i++){
		b[i].update();
	}
}

class Ball{
	float vx, vy;
	PVector target = new PVector();
	float a;
	float xstep,ystep;
	int id;
	Ball[]other
	Ball(float x,float y,float a,int id,Ball[]other){
		p=new PVector(x,y);
		this.a=a;
		xstep=2;
		ystep=2;
		this.id=id;
		this.other=other;
	}
	void update(){
		stroke(0);
		p.x+=cos(radians(a))*xstep;
		p.y+=sin(radians(a))*ystep;
		if(p.x<rad/2||width-rad/2<p.x){
			xstep*=-1;
		}
		if(p.y<rad/2||height-rad/2<p.y){
			ystep*=-1;
		}
		ellipse(p.x,p.y,rad,rad);
	}
	void collider(){
		for(int i=id+1;i<howMany;i++){
			Ball otherBall = (Ball) others[i];
			float dx = otherBall.p.x - p.x;
			float dy = otherBall.p.y - p.y;
			float distance =sqrt(dx * dx + dy * dy);
			if (distance <= R * 2) {
				float angle = atan2(dy, dx);
				float push_distance = R * 2 - distance;
				float push_x = push_distance * cos(radians(a));
				float push_y = push_distance * sin(radians(a));

				target.x -= push_x;
				target.y -= push_y;
				otherBall.target.x += push_x;
				otherBall.target.y += push_y;

				if ((vx >= 0 && vx - otherBall.vx >= 0) || (vx < 0 && vx - otherBall.vx < 0)) {
					impulse.x = -1;
				}

				if (vx * otherBall.vx <= 0) {
					otherBall.impulse.x = -1;
				}

				if ((vy >= 0 && vy - otherBall.vy >= 0) || (vy < 0 && vy - otherBall.vy < 0)) {
					impulse.y = -1;
				}

				if (vy *  otherBall.vy <= 0) {
					otherBall.impulse.y = -1;
				}
			}
		}
	}
}*/