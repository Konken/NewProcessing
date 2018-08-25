Tenta[]t=new Tenta[1000];
int temp=1;
void setup(){
  //strokeCap(PROJECT);
  fullScreen(2);
  float step=255/t.length;
  for(int i=0;i<t.length;i++){
    int a=(int)(random(4));
    switch (a) {
      case 0:t[i]=new Tenta(4,5,46);break;
      case 1:t[i]=new Tenta(20,1,82);break;
      case 2:t[i]=new Tenta(34,0,124);break;
      case 3:t[i]=new Tenta(13,0,164);break;
    }
  }
}
void draw(){
  noStroke();
  fill(2, 1, 10);
  rect(0,0,width,height);
  //background(255);
  translate(width/2,height/2);
  if(frameCount<400-5){
    temp=frameCount;
  }
  for(int i=0;i<temp;i++){
    t[i].rot();
    t[i].ext();
  }
  //saveFrame("frames/######.tif");
  if(frameCount==600){
    saveFrame("frames/######.png");
    exit();
  }
}

class Tenta{
  float ro;
  float r;
  float g;
  float b;
  float nx;
  float ny;
  int howmany;
  float[]x;
  float[]y;
  float[]tan;
  float[][]minix;
  float[][]miniy;
  int len;
  float stro;
  Tenta(float r,float g,float b){
    this.r=r;
    this.g=g;
    this.b=b;
    ro=random(360);
    nx=random(10);
    ny=random(10);

    len=0;
    howmany=150;

    x=new float[howmany];
    y=new float[howmany];
    for(int i=0;i<howmany;i++){
      float temp_nx=random(10);
      float temp_ny=random(10);
      if(i!=0){
        x[i]=(0.5-noise(nx))*12+x[i-1];
        y[i]=(noise(ny))*4+y[i-1];
      }
      else {
        x[i]=(0.5-noise(nx))*5;
        y[i]=(0.5-noise(ny))*5;
      }
      nx+=0.02;
      ny+=0.01;
    }
  }
  void rot(){
    rotate(ro);
  }
  void ext(){
    for(int i=0;i<len;i++){
      stroke(r,g,b);
      // if(i%1==0){
      //   makeMini(60,i,x[i],y[i]);
      //   makeMini(-60,i,x[i],y[i]);
      // }
      strokeWeight(20);
      if(howmany-i<100){
        strokeWeight((howmany-i)/5+2);
      }
      if(i<len-1){
        line(x[i],y[i],x[i+1],y[i+1]);
      }
    }
    addLength();
    //println(len);
  }

  void addLength(){
    if(len<howmany-1){
      len++;
    }
  }
}
