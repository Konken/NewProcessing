import de.bezier.data.*;
XlsReader reader;
void setup ()
{
    reader = new XlsReader( this, "workbook.xls" );    //ファイル名を指定して読み込む（Excelシートはdataフォルダの中に入れる）
 
    println( reader.getString( 1, 0 ) );    // 1つ目の値は行、2つ目の値は列。どちらも0から始まる（0行目と0列目）ことに注意。
    println( reader.getInt( 2, 0 ) );
    println( reader.getFloat( 3, 0 ) );
}