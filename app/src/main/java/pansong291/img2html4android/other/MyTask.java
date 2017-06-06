package pansong291.img2html4android.other;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import pansong291.img2html4android.ui.MainActivity;

public class MyTask extends AsyncTask<String,Integer,String>
{
 
 MainActivity ma;
 
 public MyTask(MainActivity m)
 {
  ma=m;
 }
 
 @Override
 protected void onPreExecute()
 {
  super.onPreExecute();
 }
 
 @Override
 protected String doInBackground(String[] p1)
 {
  int fontSize=Integer.parseInt(BL.getBL().fontSizeString);
  BL.getBL().pxlsString="";
  
  Bitmap bp=BitmapFactory.decodeFile(BL.getBL().picPathString);
  int picWidth=bp.getWidth(),picHeight=bp.getHeight();
  int xb=picWidth/fontSize,yb=picHeight/fontSize;
  
  int cutColor,rgb[]=new int[]{0,0,0},x2,y2,max=xb*yb,count1=0,count2=0;
  
  for(int y=0;y<yb;y++)
  {
   for(int x=0;x<xb;x++)
   {
    for(int y1=0;y1<fontSize;y1++)
    {
     for(int x1=0;x1<fontSize;x1++)
     {
      x2=x*fontSize+x1;y2=y*fontSize+y1;
      if(x2>=picWidth||y2>=picHeight)
       continue;
      cutColor=bp.getPixel(x2,y2);
      rgb[0]+=Color.red(cutColor);
      rgb[1]+=Color.green(cutColor);
      rgb[2]+=Color.blue(cutColor);
      ++count1;
     }
    }
    rgb[0]=rgb[0]/(fontSize*fontSize);
    rgb[1]=rgb[1]/(fontSize*fontSize);
    rgb[2]=rgb[2]/(fontSize*fontSize);
    ++count2;
    BL.getBL().pxlsString+=String.format(BL.getBL().pixelString,Utils.getHexC(rgb[0],rgb[1],rgb[2]),BL.getBL().wordString);
    if((x+1)%57==0)
     publishProgress(1,max,y*xb+x,count1,xb*yb*fontSize*fontSize,count2,xb*yb);
    rgb[0]=0;rgb[1]=0;rgb[2]=0;
   }
   BL.getBL().pxlsString+="<br>";
  }
  publishProgress(1,max,max,count1,xb*yb*fontSize*fontSize,count2,xb*yb);
  String result=String.format(BL.getBL().htmlString,BL.getBL().codeString,BL.getBL().titleString,2*picWidth+"px",BL.getBL().fontSizeString+"px",BL.getBL().backColorString,BL.getBL().fontTypeString,BL.getBL().pxlsString);
  String r=""+Utils.createHtmlFile(result,BL.getBL().outPathString);
  return r;
 }

 @Override
 protected void onProgressUpdate(Integer[] values)
 {
  switch(values[0])
  {
   case 0:
   break;
   case 1:
    ma.getProgressBar().setMax(values[1]);
    ma.getProgressBar().setProgress(values[2]);
    ma.getProtxt().setText("正在获取第("+values[3]+"/"+values[4]+")个像素点\n已记录第("+values[5]+"/"+values[6]+")个像素字");
   break;
  }
 }

 @Override
 protected void onCancelled(String result)
 {
  super.onCancelled(result);
 }

 @Override
 protected void onPostExecute(String result)
 {
  if(result.startsWith("t"))
  {
   ma.toast("HTML生成完毕");
  }else
  {
   ma.toast("生成失败");
  }
 }
 
 
}
