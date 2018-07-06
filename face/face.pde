import hypermedia.video.;    ライブラリを使う
OpenCV opencv;    OpenCV型の変数を宣言
 
void setup() {
  size( 640, 480 );
 
   open video stream
  opencv = new OpenCV( this );
  opencv.capture( 640, 480 );    キャプチャを行う
}
 
void draw() {
 
  opencv.read();    カメラから映像を取り込む
  opencv.convert(OpenCV.GRAY);    グレーに変更する
  opencv.threshold(80);    80から255までの間を白にする
 
  image( opencv.image(), 0, 0 );  映像を表示する。0, 0は映像の左上座標
}