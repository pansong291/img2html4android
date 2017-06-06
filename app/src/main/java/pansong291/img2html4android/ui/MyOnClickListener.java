package pansong291.img2html4android.ui;

import android.content.Intent;
import android.view.View;
import pansong291.img2html4android.other.BL;
import pansong291.img2html4android.R;
import android.provider.MediaStore;

public class MyOnClickListener implements View.OnClickListener
{
 MainActivity m;
 public MyOnClickListener(MainActivity n)
 {
  m=n;
 }

 @Override
 public void onClick(View p1)
 {
  if(p1.getId()==R.id.button_dialog_other)
  {
   //调用其它文件选择器选择图片
   //使用startActivityForResult是为了获取用户选择的文件
   Intent it=new Intent(Intent.ACTION_PICK, 
                      MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
   it.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"video/*");
   m.startActivityForResult(Intent.createChooser(it,"选择图片"),0);
  }else
  {
   BL.getBL().removeLastFromCurrentPath();
   //m.setListDataChange();//刷新列表
  }
 }

}
