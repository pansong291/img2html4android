package pansong291.img2html4android.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import pansong291.img2html4android.R;
import pansong291.img2html4android.other.BL;
import pansong291.img2html4android.other.Utils;

public class MyOnClickListener implements View.OnClickListener
{
 MainActivity m;
 Dialog dialogNewFolder;
 EditText edtNewFolder;
 public MyOnClickListener(MainActivity n)
 {
  m=n;
  edtNewFolder=(EditText)LayoutInflater.from(m).inflate(R.layout.dialog_new_folder,null);
  dialogNewFolder=new AlertDialog.Builder(m)
   .setTitle("新建文件夹")
   .setView(edtNewFolder)
   .setNegativeButton("取消",null)
   .setPositiveButton("确定",new DialogInterface.OnClickListener()
   {
    @Override
    public void onClick(DialogInterface p1,int p2)
    {
     if(Utils.createFolder(BL.getBL().getCurrentPath()+"/"+edtNewFolder.getText().toString()))
     {
      Toast.makeText(m,"创建成功",0).show();
      //  (刷新list)
      m.setListDataChange();
     }
     else Toast.makeText(m,"创建失败",0).show();
     edtNewFolder.setText("");
    }
   }).create();
 }

 @Override
 public void onClick(View p1)
 {
  if(p1.getId()==R.id.imageview_dialog_new_folder)
  {
   dialogNewFolder.show();
  }else
  {
   BL.getBL().removeLastFromCurrentPath();
   m.setListDataChange();
  }
 }

}
