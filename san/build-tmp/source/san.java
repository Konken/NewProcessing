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

public class san extends PApplet {


XlsReader reader;
public void setup ()
{
    reader = new XlsReader( this, "workbook.xls" );    //\u30d5\u30a1\u30a4\u30eb\u540d\u3092\u6307\u5b9a\u3057\u3066\u8aad\u307f\u8fbc\u3080\uff08Excel\u30b7\u30fc\u30c8\u306fdata\u30d5\u30a9\u30eb\u30c0\u306e\u4e2d\u306b\u5165\u308c\u308b\uff09
 
    println( reader.getString( 1, 0 ) );    // 1\u3064\u76ee\u306e\u5024\u306f\u884c\u30012\u3064\u76ee\u306e\u5024\u306f\u5217\u3002\u3069\u3061\u3089\u30820\u304b\u3089\u59cb\u307e\u308b\uff080\u884c\u76ee\u30680\u5217\u76ee\uff09\u3053\u3068\u306b\u6ce8\u610f\u3002
    println( reader.getInt( 2, 0 ) );
    println( reader.getFloat( 3, 0 ) );
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "san" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
