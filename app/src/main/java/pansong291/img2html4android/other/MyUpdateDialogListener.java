package pansong291.img2html4android.other;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import pansong291.crash.ASControl;
import pansong291.img2html4android.ui.Zactivity;

public class MyUpdateDialogListener implements DialogInterface.OnClickListener
{
 Zactivity act;
 MyUpdate upd;
 
 public MyUpdateDialogListener(Zactivity a)
 {
  act=a;
 }
 
 public void setUpDa(MyUpdate u)
 {
  upd=u;
 }
 
 @Override
 public void onClick(DialogInterface p1,int p2)
 {
  switch(p2)
  {
   case -1:
//    act.toast("下载地址为"+upd.getDownloadUrl());
    act.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(upd.getDownloadUrl())));
   case -2:
	if(act.sp.getBoolean(act.QZGX,false))
	{
	 ASControl.getASControl().finishProgrom();
	}
	break;
//   case -3:
//	sp.edit().putBoolean("checkBox3",false).commit();
//	break;
  }
 }
}
