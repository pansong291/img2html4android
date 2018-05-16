package pansong291.img2html4android.other;

import android.app.AlertDialog;
import android.text.util.Linkify;
import android.widget.ScrollView;
import android.widget.TextView;
import pansong291.img2html4android.ui.Zactivity;
import pansong291.network.Update;
import android.graphics.Color;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;

public class MyUpdate extends Update
{
 private Zactivity acti;
 private MenuItem process;
 private TextView ntxt;
 private ScrollView slv;
 private AlertDialog adb;
 private MyUpdateDialogListener li;
 private boolean showToast;
 public boolean isHasNew;
 public String dialogMessage=null;
 private String versionN;
 private boolean checkSuccess;
 
 public MyUpdate(Zactivity a,String u,MyUpdateDialogListener li)
 {
  super(a,"t.cn/"+u);
  acti=a;
  slv=new ScrollView(a);
  ntxt=new TextView(a);
  ntxt.setPadding(64,16,64,16);
  ntxt.setAutoLinkMask(Linkify.WEB_URLS);
  ntxt.setTextSize(16);
  ntxt.setTextColor(Color.BLACK);
  slv.addView(ntxt);
  LayoutParams txtlp=ntxt.getLayoutParams();
  txtlp.height=LayoutParams.WRAP_CONTENT;
  txtlp.width=LayoutParams.MATCH_PARENT;
  adb=new AlertDialog.Builder(a)
   .setTitle("发现新版本")
   .setCancelable(false)
   .setIcon(pansong291.img2html4android.R.drawable.ic_info)
   .setNegativeButton("下次再说",li)
   .setPositiveButton("立即下载",li)
   .setView(slv)
   .create();
//  LayoutParams slvlp=slv.getLayoutParams();
//  slvlp.height=LayoutParams.MATCH_PARENT;
//  slvlp.width=LayoutParams.MATCH_PARENT;
  this.li=li;
  li.setUpDa(this);
  isHasNew=true;
  versionN="";
  checkSuccess=false;
 }

 public void setCurrentVersion(String vn)
 {
  versionN=vn;
 }
 
 public void checkNow(boolean b,MenuItem v)
 {
  showToast=b;process=v;
  
  //new Thread(myRube).
  if(!checkSuccess)
  {
   start();
   setProcessStart(true);
  }else if(!isHasNew)
  {
   acti.snack("已是最新版本");
  }else if(dialogMessage!=null)
  {
   showDialog();
  }else
  {
   start();
   setProcessStart(true);
  }
 }
 
 public void showDialog()
 {
  adb.show();
 }
 
 @Override
 protected void thenDo(boolean success)
 {
  // TODO: Implement this method
  checkSuccess=success;
  if(!success&&showToast)
  {
   acti.snack("网络延时，请稍候再试");
   dialogMessage=null;
  }else if(success&&(isHasNew=getHasNew()))
  {
   dialogMessage=String.format("%1$s\n\n最新版本：%2$s\n当前版本：%3$s\n软件大小：%4$s",
   String.format(getUpdateMessage()),
   getNewVersion(),getOldVersion(),getFileSize());
   ntxt.setText(dialogMessage);
   showDialog();
  }else if(success&&showToast)
   acti.snack("已是最新版本");
  setProcessFinish(true);
 }
 
 private void setProcessStart(boolean b)
 {
  setProcessFinish(false);
 }
 
 private void setProcessFinish(boolean b)
 {
  if(process!=null)
  {
   if(b)process.setTitle("版本："+versionN);
   else process.setTitle("正在检测更新中，请稍候...");
   process.setEnabled(b);
  }
 }
 
}
