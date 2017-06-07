package pansong291.img2html4android.other;

import android.graphics.Bitmap;
import java.util.ArrayList;

public class BL
{
 
 //数据
 public String htmlString="<html><head><meta charset=\"%1$s\"><title>%2$s</title><style type=\"text/css\">body{margin: 0px; padding: 0px; line-height:100%%; letter-spacing:0px; text-align: center; min-width: %3$s;width: auto !important; font-size: %4$s; background-color: %5$s; font-family: %6$s;}</style></head><body><div>%7$s</div></body></html>",
 pixelString="<font color=\"#%02x%02x%02x\">%s</font>",
 picPathString,outPathString,wordString,
 codeString,titleString,fontSizeString,
 backColorString,fontTypeString,fileNameString;
 public StringBuilder pxlsString;
 
 public boolean isAnyOneNull()
 {
  return (picPathString.length()<1||outPathString.length()<1
  ||wordString.length()<1||codeString.length()<1||
  titleString.length()<1||fontSizeString.length()<1||
  backColorString.length()<1||fontTypeString.length()<1);
 }
 
 //单例模式
 private static BL bl=new BL();
 private BL()
 {}

 public static BL getBL()
 {
  return bl;
 }
 
 //路径
 private String strPicPath="/storage/sdcard0/x.jpg",strOutPath="/storage/sdcard0";
 public String getPicPath(){return strPicPath;}
 public void setPicPath(String s)
 {
  strPicPath=s;
 }
 public String getOutPath(){return strOutPath;}
 public void setOutPath(String s)
 {
  strOutPath=s;
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
