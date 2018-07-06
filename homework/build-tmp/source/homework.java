import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import de.bezier.data.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class homework extends PApplet {


Cell[]c;
Country[]con;
int howManyContry=6;
PVector[]xy;
int[]tempColor;
float rad=50;
float h1=0;
float h2=0;
int triangleColorLeft=color(157,197,191);
int triangleColorRight=color(157,197,191);
XlsReader reader;
int[]date;
int[][]per;
String[]countryName;
int when=0;
int len=520;
public void setup() {
	
	c=new Cell[100];
	date=new int[4];
	per=new int[4][6];
	countryName=new String[6];
	textSize(30);
	con=new Country[howManyContry];
	xy=new PVector[howManyContry];
	tempColor=new int[howManyContry];
	tempColor[0]=color(186,208,185);
	tempColor[1]=color(112,88,110);
	tempColor[2]=color(139,139,152);
	tempColor[3]=color(212,218,210);
	tempColor[4]=color(96,124,115);
	tempColor[5]=color(179,175,158);
	int count=0;
	for(int i=0;i<3;i++){
		for(int j=0;j<2;j++){
			xy[count]=new PVector(width/3*(i+1)-width/4,height/2*(j+1)-height/3);
			count++;
		}
	}
	for(int i=0;i<howManyContry;i++){
		con[i]=new Country(xy[i].x,xy[i].y,tempColor[i]);
	}
	for(int i=0;i<c.length;i++){
		c[i]=new Cell(random(width),random(height));
	}
	reader = new XlsReader( this, "data.xls" );
	for(int i=0;i<6;i++){
		int temp=i+1;
		countryName[i]=reader.getString(1,temp);
	}
	float[][]tempPer=new float[4][6];
	float[]sum=new float[4];
	for(int i=0;i<4;i++){
		int temp=5+i*4;
		date[i]=reader.getInt(temp,0);
		for(int j=0;j<6;j++){
			int temp1=j+1;
			int temp2=7+i*4;
			tempPer[i][j]=reader.getFloat(temp2,temp1);
			sum[i]+=tempPer[i][j];
		}

	}
	for(int i=0;i<4;i++){
		for(int j=0;j<6;j++){
			int temp1=j+1;
			int temp2=7+i*4;
			per[i][j]=ceil((tempPer[i][j])*(100/sum[i]));
		}
	}
}

public void draw() {
	background(206, 183, 181);
	translate(0,-40);
	stroke(255);
	fill(triangleColorRight);
	triangle(225,50,225,50+rad,225-rad*sqrt(3)/2,50+rad-rad/2);
	fill(triangleColorLeft);
	triangle(width-225,50,width-225,50+rad,width-225+rad*sqrt(3)/2,50+rad-rad/2);
	noStroke();
	for(int i=0;i<c.length;i++){
		c[i].move();
		c[i].draw();
	}
	if((225>mouseX&&mouseX>225-rad*sqrt(3)/2)&&(30<mouseY&&mouseY<30+rad)){
		triangleColorRight=color(0xff23A4F0);
	}
	else{
		triangleColorRight=color(157,197,191);
	}
	if((width-225<mouseX&&mouseX<width-225+rad*sqrt(3)/2)&&(30<mouseY&&mouseY<30+rad)){
		triangleColorLeft=color(0xff23A4F0);
	}
	else{
		triangleColorLeft=color(157,197,191);
	}
	textSize(50);
	text(date[when],width/2-50,70+rad-rad/2);
	text(countryName[0],len,650);
	text(countryName[2],len*2,650);
	text(countryName[2],len*3,650);
	text(countryName[1],len,1080);
	text(countryName[3],len*2,1080);
	text(countryName[5],len*3,1080);
	textSize(30);
}
/*void mousePressed(){
	if((225<mouseX&&mouseX<225-rad)&&(100<mouseY&&mouseY<100+rad)){
		background(0);
	}
}*/
public void mousePressed(){
	if((225>mouseX&&mouseX>225-rad*sqrt(3)/2)&&(30<mouseY&&mouseY<30+rad)){
		if(0<when){
			when--;
		}
	}
	if((width-225<mouseX&&mouseX<width-225+rad*sqrt(3)/2)&&(30<mouseY&&mouseY<30+rad)){
		if(when<3){
			when++;
		}
	}
	println(when);
	int count=0;
	int now=0;
	for(int i=0;i<c.length;i++){
		int t=now;
		count++;
		if(per[when][now]==count){
			count=0;
			now++;
		}
		PVector temp=con[t].change();
		PVector addTemp=con[t].add();
		temp.x+=addTemp.x;
		temp.y+=addTemp.y;
		c[i].update(temp,con[t].getNumber());
		c[i].propertiesColor(con[t].getColor());
	}
	for(int i=0;i<con.length;i++){
		con[i].refresh();
	}
}
class Cell{
	PVector pp;
	PVector target;
	float disx=0;
	float disy=0;
	float how=30;
	int c;
	int count=0;
	int number=-1;
	Cell(float x,float y){
		pp=new PVector(x,y);
		target=new PVector(x,y);
	}
	public void propertiesColor(int c){
		this.c=c;
	}
	public void update(PVector otaku,int number){
		target.x=otaku.x;
		target.y=otaku.y;
		disx=(pp.x-target.x)/60;
		disy=(pp.y-target.y)/60;
		this.number=number;
	}
	public void move(){
		if((target.x-2<pp.x&&pp.x<target.x+2)||(target.y-2<pp.y&&pp.y<target.y+2)){
			pp.x=target.x;
			pp.y=target.y;
		}
		else{
			pp.x-=disx;
			pp.y-=disy;
		}
	}
	public void draw(){
		fill(c);
		stroke(255);
		strokeWeight(2);
		ellipse(pp.x, pp.y, 60, 60);
		fill(255);
		text(number,pp.x-15,pp.y+15);
	}
	public void refresh(){
		target.x=0;
		target.y=0;
		disx=0;
		disy=0;
	}
}
class Country{
	int c;
	PVector step=new PVector(-180,0);
	int count=0;
	int countNumber=0;
	float x;
	float y;
	Country(float x,float y,int c){
		this.x=x;
		this.y=y;
		this.c=c;
	}
	public PVector change(){
		return new PVector(x,y);
	}
	public int getColor(){
		return c;
	}
	public void refresh(){
		step=new PVector(-180,0);
		count=0;
		countNumber=0;
	}
	public int getNumber(){
		countNumber++;
		return countNumber;
	}
	public PVector add(){
		if(count<10){
			step.x+=60;
			count++;
		}
		else{
			step.x=-120;
			step.y+=60;
			count=1;
		}
		return step;
	}
}
  public void settings() { 	fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "homework" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
