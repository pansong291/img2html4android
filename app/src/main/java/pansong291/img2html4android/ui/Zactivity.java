package pansong291.img2html4android.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import pansong291.crash.ASControl;

public class Zactivity extends AppCompatActivity
{

 public static final String PIC_PATH="picPath",OUT_PATH="outPath",
        WORD="word",CODE="code",TITLE="title",
        FONT_SIZE="fontSize",BACK_COLOR="backColor",
        FONT_TYPE="fontType",CUT_COUNT="cutCount";
 public static final String V_CODE="v_code",QZGX="qzGX";
 
 public SharedPreferences sp;
 
 public boolean spPutString(String s1,String s2)
 {
  return sp.edit().putString(s1,s2).commit();
 }
 
 @Override
 protected void onResume()
 {
  super.onResume();
  
 }
 
 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
  super.onCreate(savedInstanceState);
  ASControl.getASControl().addActivity(this);
  sp=getSharedPreferences(getPackageName()+"_preferences",0);
  
 }

 @Override
 protected void onDestroy()
 {
  super.onDestroy();
  ASControl.getASControl().removeActivity(this);
 }

 //@Override
 //public void onPointerCaptureChanged(boolean hasCapture)
 //{
  // TODO: Implement this method
 //}
 
 public void toast(String s)
 {
  toast(s,0);
 }
 
 public void toast(String s,int i)
 {
  Toast.makeText(this,s,i).show();
 }
 
 public void snack(String s)
 {
  snack(((ViewGroup)findViewById(android.R.id.content)).getChildAt(0),s);
 }
 
 public void snack(View v,String s)
 {
  snack(v,s,Snackbar.LENGTH_LONG);
 }
 
 public void snack(View v,String s,int i)
 {
  Snackbar.make(v,s,i).show();
 }

 public void snack(String s,String text,View.OnClickListener listener)
 {
  snack(((ViewGroup)findViewById(android.R.id.content)).getChildAt(0),s,Snackbar.LENGTH_SHORT,text,listener);
 }
 
 public void snack(View v,String s,String text,View.OnClickListener listener)
 {
  snack(v,s,Snackbar.LENGTH_LONG,text,listener);
 }
 
 public void snack(View v,String s,int i,String text,View.OnClickListener listener)
 {
  Snackbar.make(v,s,i).setAction(text,listener).show();
 }
 
}
