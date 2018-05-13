package pansong291.img2html4android.other;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.ClipboardManager;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import pansong291.img2html4android.ui.MainActivity;
import java.io.IOException;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

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
  ma.getProgressBar().setVisibility(0);
  ma.getProgressBar().setProgress(0);
  ma.getProtxt().setText("");
  ma.changeBtnVisibility();
 }
 
 @Override
 protected String doInBackground(String[] p1)
 {
  String result="false";
  long startTime=System.currentTimeMillis();
  long publishTime=startTime;
  int fontSize=Integer.parseInt(BL.getBL().fontSizeString);
  
  Bitmap bp=BitmapFactory.decodeFile(BL.getBL().picPathString);
  if(bp==null)return result+="读取图片失败，请检查图片路径";
  int picWidth=bp.getWidth(),picHeight=bp.getHeight();
  int xb=picWidth/fontSize,yb=picHeight/fontSize;
  
  int strBlderLength=yb*(xb*(BL.getBL().pixelString.length()-7)+4);
  if(strBlderLength/BL.getBL().cutCount!=0)
   strBlderLength/=BL.getBL().cutCount;
  
  try{
   BL.getBL().pxlsString=new StringBuilder(strBlderLength);
  }catch(OutOfMemoryError oome)
  {
   return result+="内存溢出异常01，请分多次重试\n"+oome.getMessage();
  }
  
  File dir=new File(BL.getBL().outPathString);
  if(!dir.exists())
  {
   if(!dir.mkdir())return result+="输出文件夹创建失败";
   result+="输出文件夹创建成功\n";
  }

  BL.getBL().fileNameString=BL.getBL().getPicName()+".html";
  File file=new File(dir,BL.getBL().fileNameString);
  if(file.exists())
  {
   if(!file.delete())return result+="旧文件删除失败";
   result+="旧文件删除成功\n";
  }
  BufferedWriter bw=null;
  try{
   bw=new BufferedWriter(new FileWriter(file));
  }catch(IOException e)
  {
   return result+="无法写入外部文件\n"+e.getMessage();
  }
  
  BL.getBL().pxlsString.append(String.format(BL.getBL().htmlString,BL.getBL().codeString,BL.getBL().titleString,2*picWidth+"px",BL.getBL().fontSizeString+"px",BL.getBL().backColorString,BL.getBL().fontTypeString));
  
  int cutColor,rgb[]=new int[]{0,0,0},x2,y2,max=xb*yb*fontSize*fontSize,count1=0,count2=0;
  
  for(int y=0;y<yb;y++)
  {
   for(int x=0;x<xb;x++)
   {
    for(int y1=0;y1<fontSize;y1++)
    {
     for(int x1=0;x1<fontSize;x1++)
     {
      if(isCancelled())return result+="已取消操作";
      x2=x*fontSize+x1;y2=y*fontSize+y1;
      if(x2>=picWidth||y2>=picHeight)
       continue;
      cutColor=bp.getPixel(x2,y2);
      rgb[0]+=Color.red(cutColor);
      rgb[1]+=Color.green(cutColor);
      rgb[2]+=Color.blue(cutColor);
      ++count1;
      if(System.currentTimeMillis()-publishTime>51)
       try{
        publishTime=System.currentTimeMillis();
        //publishProgress(-1,x2,y2,picWidth,picHeight,xb,yb,y2*xb*fontSize+x2+1,max,count1,count2);
        publishProgress(0,max,y2*xb*fontSize+x2+1,(int)(System.currentTimeMillis()-startTime),count1,max,count2,xb*yb);
       }catch(ArithmeticException ae)
       {
       }
     }
    }
    rgb[0]=rgb[0]/(fontSize*fontSize);
    rgb[1]=rgb[1]/(fontSize*fontSize);
    rgb[2]=rgb[2]/(fontSize*fontSize);
    ++count2;
    try{
     BL.getBL().pxlsString.append(String.format(BL.getBL().pixelString,rgb[0],rgb[1],rgb[2],BL.getBL().wordString));
    }catch(OutOfMemoryError oome)
    {
     return result+="内存溢出异常02，请分多次重试\n"+oome.getMessage();
    }
    
    int cutLength=yb*xb/BL.getBL().cutCount;
    if(cutLength==0||count2%cutLength==0)
    {
     try{
      bw.write(BL.getBL().pxlsString.toString());
      bw.flush();
      BL.getBL().pxlsString.delete(0,BL.getBL().pxlsString.length());
     }catch(IOException e)
     {}
    }
    rgb[0]=0;rgb[1]=0;rgb[2]=0;
   }
   BL.getBL().pxlsString.append("<br>");
  }
  //publishProgress(0,max,max,(int)(System.currentTimeMillis()-startTime),count1,max,count2,xb*yb);
  if(bp!=null&&!bp.isRecycled())
  {
   // 回收并且置为null
   bp.recycle();
   bp=null;
  }
  try{
   BL.getBL().pxlsString.append("</div></body></html>");
  }catch(OutOfMemoryError oome)
  {
   return result+="内存溢出异常03，请分多次重试\n"+oome.getMessage();
  }
  
  try{
   bw.write(BL.getBL().pxlsString.toString());
   bw.close();
  }catch(IOException e)
  {
   return result+=e.getMessage();
  }

  publishProgress(1,max,max,(int)(System.currentTimeMillis()-startTime),count1,count2);
  result=result.replace("false","true")+"写入外部文件成功\n";
  return result;
 }

 @Override
 protected void onProgressUpdate(Integer[] values)
 {
  String str="";
  switch(values[0])
  {
   case -1:
    str=String.format("x2=%d,y2=%d\npicW=%d,picH=%d\nxb=%d,yb=%d\np=%d,m=%d\n像素:%d,字:%d",values[1],values[2],values[3],values[4],values[5],values[6],values[7],values[8],values[9],values[10]);
   break;
   case 0:
    str=String.format("已耗时%.3f秒\n正在获取第(%d/%d)个像素点\n已记录第(%d/%d)个像素字",values[3]/1000.0f,values[4],values[5],values[6],values[7]);
   break;
   case 1:
    str=String.format("本次共耗时%.3f秒\n共获取%d个像素点\n共记录%d个像素字",values[3]/1000.0f,values[4],values[5]);
   break;
  }
  if(ma.getProgressBar().getMax()!=values[1])
   ma.getProgressBar().setMax(values[1]);
  ma.getProgressBar().setProgress(values[2]);
  ma.getProtxt().setText(str);
  
 }

 @Override
 protected void onCancelled(String result)
 {
  ma.getProtxt().append("\n"+result.substring(result.indexOf("e")+1));
  ma.changeBtnVisibility();
 }

 @Override
 protected void onPostExecute(String result)
 {
  String msg=result.substring(result.indexOf("e")+1);
  if(result.startsWith("true"))
  {
   ma.getProtxt().append("\n"+msg+"\nHTML生成完毕，链接已复制到剪贴板，请在浏览器中粘贴打开");
   String filePath="file://"+BL.getBL().outPathString+"/"+BL.getBL().fileNameString;
   ClipboardManager cm=(ClipboardManager)ma.getSystemService(ma.CLIPBOARD_SERVICE);
   cm.setText(filePath);
   new AlertDialog.Builder(ma)
    .setTitle("生成完毕")
    .setMessage("HTML生成完毕，链接已复制到剪贴板，您可以在浏览器中粘贴打开，也可以现在选择直接打开。")
    .setCancelable(false)
    .setPositiveButton("直接打开",new DialogInterface.OnClickListener()
    {
     String data;
     public DialogInterface.OnClickListener setData(String d){data=d;return this;}
     @Override
     public void onClick(DialogInterface p1,int p2)
     {
      try{
       Intent it=new Intent();
       it.setClassName("com.android.htmlviewer","com.android.htmlviewer.HTMLViewerActivity");
       it.setData(Uri.parse(data));
       ma.startActivity(it);
      }catch(Exception e)
      {
       Intent it=new Intent(Intent.ACTION_VIEW);
       //it.setClassName("com.android.browser","com.android.browser.BrowserActivity");
       it.addCategory(Intent.CATEGORY_DEFAULT);  
       it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       it.setDataAndType(Uri.parse(data),"text/html");
       ma.startActivity(Intent.createChooser(it,"选择浏览器"));
      }
     }
    }.setData(filePath))
    .setNegativeButton("取消",null)
    .show();
    BL.getBL().setPicPath(BL.getBL().picPathString);
    ma.spPutString(MainActivity.PIC_PATH,BL.getBL().getPicPath());
  }else
  {
   ma.getProtxt().append("\n生成失败\n"+msg);
  }
  ma.changeBtnVisibility();
  BL.getBL().setOutPath(BL.getBL().outPathString);
  ma.spPutString(MainActivity.OUT_PATH,BL.getBL().getOutPath());
 }
 
 
}
