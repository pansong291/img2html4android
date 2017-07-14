package pansong291.img2html4android.other;

import android.graphics.Bitmap;
import java.util.ArrayList;

public class BL
{
 
 //数据
 public String htmlString="<html><head><meta charset=\"%1$s\"><title>%2$s</title><style type=\"text/css\">body{margin: 0px; padding: 0px; line-height:100%%; letter-spacing:0px; text-align: center; min-width: %3$s;width: auto !important; font-size: %4$s; background-color: %5$s; font-family: %6$s;}img{max-width: 100%%; height: auto;}</style></head><body><script type=\"text/javascript\">function is_weixin(){var ua=navigator.userAgent.toLowerCase();if(ua.match(/MicroMessenger/i)==\"micromessenger\"){return true;}if(ua.match(/WeiBo/i)==\"weibo\"){return true;}if(ua.match(/QQ/i)==\"qq\"){return true;}return false;}var isWeixin=is_weixin();var winHeight=typeof window.innerHeight!=\'undefined\'?window.innerHeight:document.documentElement.clientHeight;console.log(winHeight);function loadHtml(){var div=document.createElement(\'div\');div.id=\'weixin-tip\';div.innerHTML=\'<p><img src=\"https://pansongps.wodemo.net/down/20170611/443443/weixin2liulanqi.png\" alt=\"请用其它浏览器打开\"/></p>\';document.body.appendChild(div);}function loadStyleText(cssText){var style=document.createElement(\'style\');style.rel=\'stylesheet\';style.type=\'text/css\';try{style.appendChild(document.createTextNode(cssText));}catch(e){style.styleSheet.cssText=cssText;}var head=document.getElementsByTagName(\"head\")[0];head.appendChild(style);}function stopload(){if(!!(window.attachEvent&&!window.opera)){document.execCommand(\"stop\");}else{window.stop();}}var cssText=\"#weixin-tip{position: fixed; left:0; top:0; background: rgba(0,0,0,0.8); filter:alpha(opacity=80); width: 100%%; height:100%%; z-index: 100;} #weixin-tip p{text-align: center; margin-top: 10%%; padding:0 5%%;}\";if(isWeixin){loadHtml();loadStyleText(cssText);stopload();}</script><div>%7$s</div></body></html>",
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
 private String strPicPath="/storage/emulated/0/x.jpg",strOutPath="/storage/emulated/0";
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
