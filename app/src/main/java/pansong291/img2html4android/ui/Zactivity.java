package pansong291.img2html4android.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import pansong291.crash.ActivityControl;

public class Zactivity extends Activity
{
 int VERSION_CODE;
 String VERSION_NAME;
 String PIC_PATH="picPath",OUT_PATH="outPath",
        WORD="word",CODE="code",TITLE="title",
        FONT_SIZE="fontSize",BACK_COLOR="backColor",
        FONT_TYPE="fontType";
 String V_CODE="v_code";
 
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
  ActivityControl.getActivityControl().addActivity(this);
  sp=getSharedPreferences(getPackageName()+"_preferences",0);
  try{
   PackageInfo pi=getPackageManager().getPackageInfo(getPackageName(),0);
   VERSION_CODE=pi.versionCode;
   VERSION_NAME=pi.versionName;
  }catch(Exception e)
  {
   Log.e("VersionInfo","Exception",e);    
  }
 }

 @Override
 protected void onDestroy()
 {
  super.onDestroy();
  ActivityControl.getActivityControl().removeActivity(this);
 }
 
 public void toast(String s)
 {
  toast(s,0);
 }
 
 public void toast(String s,int i)
 {
  Toast.makeText(this,s,i).show();
 }
 
}
