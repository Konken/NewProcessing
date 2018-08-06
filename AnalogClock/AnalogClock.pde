Frame f;
Frame f2;
float n;
Clock c;
void setup() {
	size(980,980);
	background(0);
	smooth();
	n=random(10);
	f=new Frame(500,1,80,360*3);
	c=new Clock();
	translate(width/2,height/2);
}

void draw() {
	background(0);
	translate(width/2,height/2);
	f.updateFrame();
	noFill();
	for(int i=0;i<10;i++){
		ellipse(random(-5,5),random(-5,5), 30, 30);
	}
	c.updateTime(hour(),minute());
}
class Clock{
	void updateTime(int h,int m){
		stroke(255,50);
		strokeWeight(1.5);
		for(int i=0;i<100;i++){
			float tempR=random(-0.8,0.8);
			line(cos(radians(i*30+tempR))*330,sin(radians(i*30+tempR))*330,cos(radians(i*30+tempR))*380,sin(radians(i*30+tempR))*380);
		}
		float mAngle=h*30+m/2;
		float hAngle=m*6;
		stroke(255,5);
		strokeWeight(1.5);
		line(cos(radians(hAngle+270))*50,sin(radians(hAngle+270))*50,cos(radians(hAngle+270))*380,sin(radians(hAngle+270))*380);
		line(cos(radians(mAngle+270))*50,sin(radians(mAngle+270))*50,cos(radians(mAngle+270))*280,sin(radians(mAngle+270))*280);
		float nn=random(10);
		for(int i=0;i<100;i++){
			float na=(0.5-noise(nn))*5;
			line(cos(radians(hAngle+270+na))*50,sin(radians(hAngle+270+na))*50,cos(radians(hAngle+270+na))*350,sin(radians(hAngle+270+na))*350);
			line(cos(radians(mAngle+270+na))*50,sin(radians(mAngle+270+na))*50,cos(radians(mAngle+270+na))*200,sin(radians(mAngle+270+na))*200);
			nn+=0.5;
		}
		/*stroke(0,5);
		strokeWeight(1);
		for(int i=0;i<100;i++){
			float tempR2=random(-1,1);
			line(cos(radians(hAngle+270+tempR2))*50,sin(radians(hAngle+270+tempR2))*50,cos(radians(hAngle+270+tempR2))*380,sin(radians(hAngle+270+tempR2))*380);
			line(cos(radians(mAngle+270+tempR2))*50,sin(radians(mAngle+270+tempR2))*50,cos(radians(mAngle+270+tempR2))*280,sin(radians(mAngle+270+tempR2))*280);
		}*/
	}
}
class Frame{
	float angle,rad,step;
	int howMany;
	float oldX,oldY;
	float nowX,nowY;
	float nRad;
	float[]n;
	float noiseStep=0.4;
	float noiseTimestep=0.05;
	ArrayList<ArrayList<Float>>nList;
	float[]nArray;
	float[]x=new float[howMany];
	float[]y=new float[howMany];
	Frame(float rad,float step,float nRad,int howMany){
		angle=0;
		this.rad=rad;
		this.nRad=nRad;
		this.step=step;
		this.howMany=howMany;
		n=new float[howMany/360];
		nList= new ArrayList<ArrayList<Float>>();
		nArray=new float[howMany];
		int count=0;
		for(int j=0;j<howMany/360;j++){
			n[j]=random(100);
			ArrayList<Float>temp= new ArrayList<Float>();
			for(int i=0;i<360;i++){
				temp.add(n[j]);
				nArray[count]=n[j];
				n[j]+=noiseStep;
				count++;
			}
			nList.add(temp);
		}
		x=new float[howMany];
		y=new float[howMany];
		for(int i=0;i<howMany;i++){
			x[i]=cos(radians(i))*rad;
			y[i]=sin(radians(i))*rad;
		}
	}
	void updateFrame(){
		int c=0;
		float nTime=random(10);
		for(int j=0;j<howMany/360;j++){
			for(int i=0;i<360;i++){
				angle=i;
				//nowX=cos(radians(angle))*(0.5-noise(nList.get(j).get(i)),nTime)*nRad;
				//nowY=sin(radians(angle))*(0.5-noise(nList.get(j).get(i)),nTime)*nRad;
				float nTempX=(0.5-noise(nArray[c],nTime));
				float nTempY=(0.5-noise(nArray[c],nTime));
				nowX=cos(radians(angle))*nTempX*nRad;
				nowY=sin(radians(angle))*nTempY*nRad;
				x[c]+=nowX;
				y[c]+=nowY;
				if(450<abs(mag(x[c],y[c]))){
					x[c]=cos(radians(i))*450;
					y[c]=sin(radians(i))*450;
				}
				if(350>abs(mag(x[c],y[c]))){
					x[c]=cos(radians(i))*350;
					y[c]=sin(radians(i))*350;
				}
				c++;
			}
		}
		strokeWeight(0.8);
		stroke(255,80);
		for(int i=0;i<howMany-20;i++){
			for(int j=0;j<4;j++){
				line(x[i],y[i],x[i+j*5],y[i+j*5]);
			}
		}
		strokeWeight(0.1);
		stroke(255);
		/*for(int i=0;i<howMany/360;i++){
			n[i]+=noiseStep;
			nList.get(i).add(0,n[i]);
			nList.get(i).remove(nList.get(i).size()-1);
		}*/
		for(int i=0;i<howMany;i++){
			nArray[i]+=noiseStep;
		}
		nTime+=noiseTimestep;
	}
}