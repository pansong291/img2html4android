package pansong291.img2html4android.other;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.ClipboardManager;
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
  long startTime=System.currentTimeMillis();
  int fontSize=Integer.parseInt(BL.getBL().fontSizeString);
  BL.getBL().pxlsString=new StringBuilder("");
  
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
      if(isCancelled())return "false";
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
    BL.getBL().pxlsString.append(String.format(BL.getBL().pixelString,rgb[0],rgb[1],rgb[2],BL.getBL().wordString));
    if((x+1)%(xb/10)==0)
     publishProgress(0,max,y*xb+x,(int)(System.currentTimeMillis()-startTime),count1,xb*yb*fontSize*fontSize,count2,xb*yb);
    rgb[0]=0;rgb[1]=0;rgb[2]=0;
   }
   BL.getBL().pxlsString.append("<br>");
  }
  publishProgress(0,max,max,(int)(System.currentTimeMillis()-startTime),count1,xb*yb*fontSize*fontSize,count2,xb*yb);
  String result=String.format(BL.getBL().htmlString,BL.getBL().codeString,BL.getBL().titleString,2*picWidth+"px",BL.getBL().fontSizeString+"px",BL.getBL().backColorString,BL.getBL().fontTypeString,BL.getBL().pxlsString.toString());
  String r=""+Utils.createHtmlFile(result,BL.getBL().outPathString);
  publishProgress(1,max,max,(int)(System.currentTimeMillis()-startTime),count1,xb*yb*fontSize*fontSize,count2,xb*yb);
  return r;
 }

 @Override
 protected void onProgressUpdate(Integer[] values)
 {
  String str="";
  switch(values[0])
  {
   case 0:
    str=String.format("已耗时%.3f秒\n正在获取第(%d/%d)个像素点\n已记录第(%d/%d)个像素字",values[3]/1000.0,values[4],values[5],values[6],values[7]);
   break;
   case 1:
    str=String.format("本次共耗时%.3f秒\n共获取%d个像素点\n共记录%d个像素字",values[3]/1000.0,values[5],values[7]);
   break;
  }
  ma.getProgressBar().setMax(values[1]);
  ma.getProgressBar().setProgress(values[2]);
  ma.getProtxt().setText(str);
  
 }

 @Override
 protected void onCancelled(String result)
 {
  ma.toast("已取消");
 }

 @Override
 protected void onPostExecute(String result)
 {
  if(result.startsWith("t"))
  {
   ma.getProtxt().append("\nHTML生成完毕，链接已复制到剪贴板，请在浏览器中粘贴打开");
   ClipboardManager cm=(ClipboardManager)ma.getSystemService(ma.CLIPBOARD_SERVICE);
   cm.setText("file://"+BL.getBL().outPathString+"/"+BL.getBL().fileNameString);
  }else
  {
   ma.toast("生成失败");
  }
  ma.changeBtnVisibility();
 }
 
 
}
