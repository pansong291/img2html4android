package pansong291.img2html4android.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import pansong291.crash.ASControl;

public class Zactivity extends Activity
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
 
 public void toast(String s)
 {
  toast(s,0);
 }
 
 public void toast(String s,int i)
 {
  Toast.makeText(this,s,i).show();
 }
 
}
