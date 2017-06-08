package pansong291.img2html4android.other;

import android.content.DialogInterface;
import pansong291.crash.ActivityControl;
import pansong291.img2html4android.ui.Zactivity;

public class MyUpdataDialogListener implements DialogInterface.OnClickListener
{
 Zactivity act;
 MyUpdata upd;
 
 public MyUpdataDialogListener(Zactivity a)
 {
  act=a;
 }
 
 public void setUpDa(MyUpdata u)
 {
  upd=u;
 }
 
 @Override
 public void onClick(DialogInterface p1,int p2)
 {
  switch(p2)
  {
   case -1:
//	MyWebView.downloadfile(act,upd.getDownloadUrl(),
//	act.sp.getString("edt1","/mnt/sdcard/"),
//	act.getString(R.string.app_name)+"v"+upd.getNewVersion()+".apk",true);
    act.toast("下载地址为"+upd.getDownloadUrl());
	break;
   case -2:
	if(act.sp.getBoolean("qzGX",false))
	{
	 ActivityControl.getActivityControl().finishProgrom();
	}
	break;
//   case -3:
//	sp.edit().putBoolean("checkBox3",false).commit();
//	break;
  }
 }
}
