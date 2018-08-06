Cell[][]c;
int howMany=60;
float st=0;
float nx;float ny;float sn;float step=0.005;
int xOfSideBar;
int m;
boolean bool = false;
void setup() {
	fullScreen();
	sn=random(10);
	nx=sn;
	ny=sn;
	c=new Cell[width/howMany][height/howMany];
	for(int x=0;x<width/howMany;x++){
		for(int y=0;y<height/howMany;y++){
			c[x][y]=new Cell(x*howMany,y*howMany);
		}
	}
	m=minute();
	xOfSideBar=width/howMany-1;
}

void draw() {
	background(125);
	setupClock();
	int[]t=new int[4];
	t[1]=hour()%10;
	t[0]=(hour()/10)%10;
	t[3]=minute()%10;
	t[2]=(minute()/10)%10;
	makeNumber(t);
	updateTime();
}
void updateTime(){
	if(m!=minute()){
		m=minute();
		bool=true;
		xOfSideBar=width/howMany-1;
	}
	if(bool){
		makeWall2();
	}
}
void makeWall(){
	if(xOfSideBar<1){
		bool=false;
	}
	else{
		for(int i=0;i<xOfSideBar;i++){
			for(int j=0;j<height/howMany;j++){
				c[i][j].update0(255);
			}
		}
		xOfSideBar--;
	}
}
void makeWall2(){
	if(xOfSideBar<1){
		bool=false;
	}
	else{
		int count=0;
		for(int i=0;i<xOfSideBar;i++){
			if(count==0){count=1;}
			else{count=0;}
			for(int j=count;j<height/howMany;j+=2){
				c[i][j].update0(255);
			}
		}
		xOfSideBar--;
	}
}
void setupClock(){
	noStroke();
	for(int x=0;x<width/howMany;x++){
		ny=sn;
		for(int y=0;y<height/howMany;y++){
			c[x][y].update0(0);
			ny+=step;
		}
		nx+=step;
	}
	c[16][6].update3(255);
	c[16][12].update3(255);
}
class Cell{
	float x,y;
	Cell(float x,float y){
		this.x=x;
		this.y=y;
	}
	void update0(color col){
		fill(col);
		rect(x+st,y+st,howMany-st,howMany-st);
	}
	void update(color col){
		fill(col);
		rect(x+st,y+st,howMany-st,howMany-st);
	}
	void update2(float n){
		if(0.5<n){
			fill(0);
			rect(x+st,y+st,howMany-st,howMany-st);
		}
		else{
			fill(255);
			rect(x+st,y+st,howMany-st,howMany-st);
		}
	}
	void update3(color col){
		fill(col);
		float range=howMany-st;
		rect(x+st-range/2,y+st,range,range);
	}
}
void makeNumber(int[]temp){
	int c=0;
	for(int i=0;i<4;i++){
		if(1<i)c=1;
		switch (temp[i]) {
			case 0:make0(i*8+c,3);break;
			case 1:make1(i*8+c,3);break;
			case 2:make2(i*8+c,3);break;
			case 3:make3(i*8+c,3);break;
			case 4:make4(i*8+c,3);break;
			case 5:make5(i*8+c,3);break;
			case 6:make6(i*8+c,3);break;
			case 7:make7(i*8+c,3);break;
			case 8:make8(i*8+c,3);break;
			case 9:make9(i*8+c,3);break;
		}
	}
}
void make0(int x,int y){
	makeStickRow(x,y,0,0);
	makeStickRow(x,y,0,2);
	makeStickLine(x,y,0,0);
	makeStickLine(x,y,0,1);
	makeStickLine(x,y,1,0);
	makeStickLine(x,y,1,1);
	/*c[x][y].update(color(255));
	for(int i=1;i<6;i++){
		c[x+i][y].update(color(255));
	}
	c[x+6][y].update(color(255));
	for(int i=1;i<10;i++){
		c[x][y+i].update(color(255));
	}
	c[x][y+10].update(color(255));
	for(int i=1;i<6;i++){
		c[x+i][y+10].update(color(255));
	}
	c[x+6][y+10].update(color(255));
	for(int i=1;i<10;i++){
		c[x+6][y+i].update(color(255));
	}
	//////
	c[x+1][y+1].update(color(255));
	for(int i=1;i<4;i++){
		c[x+i+1][y+1].update(color(255));
	}
	c[x+5][y+1].update(color(255));
	for(int i=1;i<8;i++){
		c[x+1][y+i+1].update(color(255));
	}
	c[x+1][y+9].update(color(255));
	for(int i=1;i<4;i++){
		c[x+i+1][y+9].update(color(255));
	}
	c[x+5][y+9].update(color(255));
	for(int i=1;i<8;i++){
		c[x+5][y+i+1].update(color(255));
	}*/
}
void make1(int x,int y){
	makeStickLine(x,y,1,0);
	makeStickLine(x,y,1,1);
}
void make2(int x,int y){
	makeStickRow(x,y,0,0);
	makeStickRow(x,y,0,1);
	makeStickRow(x,y,0,2);
	makeStickLine(x,y,0,1);
	makeStickLine(x,y,1,0);
}
void make3(int x,int y){
	makeStickRow(x,y,0,0);
	makeStickRow(x,y,0,1);
	makeStickRow(x,y,0,2);
	makeStickLine(x,y,1,0);
	makeStickLine(x,y,1,1);
	makeStickRow(x,y,0,0);
	makeStickRow(x,y,0,1);
	makeStickRow(x,y,0,2);
	makeStickLine(x,y,1,0);
	makeStickLine(x,y,1,1);
}
void make4(int x,int y){
	makeStickRow(x,y,0,1);
	makeStickLine(x,y,0,0);
	makeStickLine(x,y,1,0);
	makeStickLine(x,y,1,1);
}
void make5(int x,int y){
	makeStickRow(x,y,0,0);
	makeStickRow(x,y,0,1);
	makeStickRow(x,y,0,2);
	makeStickLine(x,y,0,0);
	makeStickLine(x,y,1,1);
}
void make6(int x,int y){
	makeStickRow(x,y,0,0);
	makeStickRow(x,y,0,1);
	makeStickRow(x,y,0,2);
	makeStickLine(x,y,0,0);
	makeStickLine(x,y,1,1);
	makeStickLine(x,y,0,1);
}
void make7(int x,int y){
	makeStickRow(x,y,0,0);
	makeStickLine(x,y,1,1);
	makeStickLine(x,y,1,0);
}
void make8(int x,int y){
	makeStickRow(x,y,0,0);
	makeStickRow(x,y,0,1);
	makeStickRow(x,y,0,2);
	makeStickLine(x,y,0,0);
	makeStickLine(x,y,1,1);
	makeStickLine(x,y,0,1);
	makeStickLine(x,y,1,0);
}
void make9(int x,int y){
	makeStickRow(x,y,0,0);
	makeStickRow(x,y,0,1);
	makeStickRow(x,y,0,2);
	makeStickLine(x,y,0,0);
	makeStickLine(x,y,1,1);
	makeStickLine(x,y,1,0);
}
void makeStickRow(int x,int y,int binaryX,int binaryY){
	for(int i=0;i<7;i++){
		c[x+binaryX+i][y+binaryY*6].update(color(255));
	}
}
void makeStickLine(int x,int y,int binaryX,int binaryY){
	for(int i=0;i<7;i++){
		c[x+binaryX*6][y+binaryY*6+i].update(color(255));
	}
}