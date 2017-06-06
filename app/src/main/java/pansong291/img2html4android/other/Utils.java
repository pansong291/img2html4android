package pansong291.img2html4android.other;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;

public class Utils
{
 
 
 public static String getHexC(int r,int g,int b)
 {
  String s="";
  int i1=0;
  for(int i=0;i<3;i++)
  {
   switch(i)
   {
    case 0:i1=b;break;
    case 1:i1=g;break;
    case 2:i1=r;break;
   }
   s=Integer.toHexString(i1)+s;
   if(s.length()%2!=0)s="0"+s;
  }
  return "#"+s;
 }
 
 //将文本以文件写出
 public static boolean createHtmlFile(String s,String p)
 {
  File dir=new File(p);
  if(!dir.exists())
  {
   dir.mkdir();
  }

  String fileName="Img_"+System.currentTimeMillis()+".html";
  File file=new File(dir,fileName);
  try{
   BufferedWriter bw=new BufferedWriter(new FileWriter(file));
   bw.write(s);
   bw.close();
  }catch(Exception e)
  {
   return false;
  }
  return true;
 }
 
 //创建文件夹
 public static boolean createFolder(String s)
 {
  File f=new File(s);
  if(!f.exists())
  {
   return f.mkdir();
  }
  return false;
 }
 
 //获取所有子目录
 public static ArrayList<String> getAllSonFolder(String s)
 {
  File f=new File(s);
  //文件过滤器，只有当其不是文件夹或图片文件时才被过滤掉
  FileFilter myFileFliter=new FileFilter()
  {
   @Override
   public boolean accept(File p1)
   {
    if(p1.isDirectory())return true;
    return isImgFile(p1.getName());
   }
  };
  ArrayList<String>arrayFiles=new ArrayList<String>();
  File[]files=f.listFiles(myFileFliter);
  if(files!=null)
  {
   for(int d=0;d<files.length;d++)
   {
    arrayFiles.add(files[d].getName());
   }
   //排序
   Collections.sort(arrayFiles);
  }
  return arrayFiles;
 }
 
 public static boolean isImgFile(String fn)
 {
  boolean isImg=false;
  String fnes=fn.substring(fn.lastIndexOf(".")+1,fn.length());
  fnes=fnes.toLowerCase();
  switch(fnes)
  {
   case "jpg":
   case "png":
   case "gif":
    isImg=true;
   break;
  }
  return isImg;
 }
 
 //从Uri中获取文件路径
 public static String getAbsolutePath(Activity c,Uri uri,String s) 
 {
  try{
   String [] proj={s};
   Cursor cursor=c.managedQuery(uri,
                                proj,  // Which columns to return
                                null,  // WHERE clause; which rows to return (all rows)
                                null,  // WHERE clause selection arguments (none)
                                null); // Order-by clause (ascending by name)
   int column_index=cursor.getColumnIndexOrThrow(proj[0]);
   cursor.moveToFirst();
   return cursor.getString(column_index);
  }catch(Exception e)
  {
   return uri.getPath();
  }
 }
 
 
 
}
