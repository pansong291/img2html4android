package pansong291.img2html4android.other;

import android.graphics.Bitmap;
import java.util.ArrayList;

public class BL
{
 
 //数据
 public String htmlString="<html><head><meta charset=\"%1$s\"><title>%2$s</title><style type=\"text/css\">body{margin: 0px; padding: 0px; line-height:100%%; letter-spacing:0px; text-align: center; min-width: %3$s;width: auto !important; font-size: %4$s; background-color: %5$s; font-family: %6$s;}</style></head><body><div>%7$s</div></body></html>",
 pixelString="<font color=\"%1$s\">%2$s</font>",pxlsString,
 picPathString,outPathString,wordString,
 codeString,titleString,fontSizeString,
 backColorString,fontTypeString;
 
 //单例模式
 private static BL bl=new BL();
 private BL()
 {}

 public static BL getBL()
 {
  return bl;
 }
 
 //当前目录
 private String strCurrentPath="/storage";//"/storage";
 public String getCurrentPath(){return strCurrentPath;}
 public void addToCurrentPath(String s)
 {
  strCurrentPath=strCurrentPath+"/"+s;
 }
 public void removeLastFromCurrentPath()
 {
  if(strCurrentPath.length()>9)
   strCurrentPath=strCurrentPath.substring(0,strCurrentPath.lastIndexOf("/"));
 }
 public void setCurrentPath(String s)
 {
  strCurrentPath=s;
 }
 
 
 
}
