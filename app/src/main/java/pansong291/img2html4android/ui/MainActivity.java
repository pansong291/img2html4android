package pansong291.img2html4android.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.File;
import pansong291.img2html4android.R;
import pansong291.img2html4android.other.BL;
import pansong291.img2html4android.other.MyProclamation;
import pansong291.img2html4android.other.MyTask;
import pansong291.img2html4android.other.MyUpdata;
import pansong291.img2html4android.other.Utils;
import pansong291.img2html4android.other.MyUpdataDialogListener;

public class MainActivity extends Zactivity 
{
 
 //主视图控件
 EditText picPathEditText,outPathEditText,
 wordEditText,codeEditText,titleEditText,fontSizeEditText,
 backColorEditText,fontTypeEditText;
 
 Button picPathBtn,outPathBtn,doBtn,cancelBtn;
 
 ProgressBar probar;
 
 TextView protxt;

 //选择路径对话框里的视图
 TextView txtCurrentPath;
 View viewDialog,viewGoBack,viewNewFolder;
 AlertDialog listDialog;
 ListView listvPath;
 ListFolderAdapter listAdapter;
 MyOnClickListener myOnClickListener;

 //判断选择路径的view
 boolean bolIsPicPath;
 
 //后台任务
 MyTask mt;
 
 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
  
  int oldVerCode=sp.getInt(V_CODE,99999999);
  if(oldVerCode<VERSION_CODE)
  {
   //用户更新了本应用
   new AlertDialog.Builder(this)
    .setTitle("版本升级")
    .setMessage(String.format("感谢您对本应用的支持！\n本应用已成功升级到%1$s版本。\n%2$s",VERSION_NAME,getString(R.string.update_msg)))
    .setPositiveButton("确定",null)
    .show();
  }else if(oldVerCode==99999999)
  {
   //用户第一次安装本应用
   new AlertDialog.Builder(this)
    .setTitle("声明")
    .setMessage(String.format("感谢您安装本应用！\n%s",getString(R.string.hello_user)))
    .setPositiveButton("确定",null)
    .show();
  }
  sp.edit().putInt(V_CODE,VERSION_CODE).commit();
  
  //公告相关
  LinearLayout llt=(LinearLayout)findViewById(R.id.main_gg);
  new MyProclamation(this,"RyOATtQ",llt).start();
  
  //更新相关
  if(sp.getBoolean("自动检查更新的选项",true)||sp.getBoolean("qzGX",false))
   new MyUpdata(this,"RyOAlwR",new MyUpdataDialogListener(this)).checkNow(false,null);
  
  picPathEditText=(EditText)findViewById(R.id.main_edit_picpath);
  outPathEditText=(EditText)findViewById(R.id.main_edit_outpath);
  wordEditText=(EditText)findViewById(R.id.main_edit_word);
  codeEditText=(EditText)findViewById(R.id.main_edit_code);
  titleEditText=(EditText)findViewById(R.id.main_edit_title);
  fontSizeEditText=(EditText)findViewById(R.id.main_edit_fontsize);
  backColorEditText=(EditText)findViewById(R.id.main_edit_backcolor);
  fontTypeEditText=(EditText)findViewById(R.id.main_edit_fonttype);
  
  picPathEditText.setText(sp.getString(PIC_PATH,""));
  outPathEditText.setText(sp.getString(OUT_PATH,""));
  BL.getBL().setPicPath(picPathEditText.getText().toString());
  BL.getBL().setOutPath(outPathEditText.getText().toString());
  wordEditText.setText(sp.getString(WORD,"燚"));
  codeEditText.setText(sp.getString(CODE,"utf-8"));
  titleEditText.setText(sp.getString(TITLE,""));
  fontSizeEditText.setText(sp.getString(FONT_SIZE,"10"));
  backColorEditText.setText(sp.getString(BACK_COLOR,"#000000"));
  fontTypeEditText.setText(sp.getString(FONT_TYPE,"monospace"));
  
  picPathBtn=(Button)findViewById(R.id.main_btn_picpath);
  outPathBtn=(Button)findViewById(R.id.main_btn_outpath);
  doBtn=(Button)findViewById(R.id.main_btn_do);
  cancelBtn=(Button)findViewById(R.id.main_btn_cancel);
  
  probar=(ProgressBar)findViewById(R.id.main_probar);
  
  protxt=(TextView)findViewById(R.id.main_txt_pro);
  
  viewDialog=LayoutInflater.from(getApplication()).inflate(R.layout.dialog_choose_folder,null);
  listvPath=(ListView)viewDialog.findViewById(R.id.listview_dialog_folder);
  txtCurrentPath=(TextView)viewDialog.findViewById(R.id.textview_dialog_current_folder);
  viewGoBack=viewDialog.findViewById(R.id.linearlayout_dialog_goback);
  viewNewFolder=viewDialog.findViewById(R.id.imageview_dialog_new_folder);

  myOnClickListener=new MyOnClickListener(this);
  viewNewFolder.setOnClickListener(myOnClickListener);
  viewGoBack.setOnClickListener(myOnClickListener);
  
  listAdapter=new ListFolderAdapter(this);
  listvPath.setAdapter(listAdapter);
  listvPath.setOnItemClickListener(new AdapterView.OnItemClickListener()
   {
    @Override
    public void onItemClick(AdapterView<?> p1,View p2,int p3,long p4)
    {
     String fn=listAdapter.getFolderInfo(p3);
     File f=new File(BL.getBL().getCurrentPath()+"/"+fn);
     if(f.isDirectory())
     {
      BL.getBL().addToCurrentPath(fn);
      setListDataChange();
     }else if(bolIsPicPath)
     {
      picPathEditText.setText(f.getAbsolutePath());
      BL.getBL().setPicPath(f.getAbsolutePath());
      spPutString(PIC_PATH,BL.getBL().getPicPath());
      listDialog.dismiss();
     }
    }
   });

  listDialog=new AlertDialog.Builder(this)
   .setTitle("选择路径")
   .setView(viewDialog)
   .setNegativeButton("取消",null)
   .setNeutralButton("其它选择器",new DialogInterface.OnClickListener()
   {
    @Override
    public void onClick(DialogInterface p1, int p2)
    {
     if(!bolIsPicPath)
     {
      toast("请点击确定来选取输出路径");
      return;
     }
     //调用其它文件选择器选择视频
     //使用startActivityForResult是为了获取用户选择的文件
     Intent it=new Intent(Intent.ACTION_PICK, 
               MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
     it.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
     startActivityForResult(Intent.createChooser(it,"选择图片"),0);
    }
   })
   .setPositiveButton("确定",new DialogInterface.OnClickListener()
   {
    @Override
    public void onClick(DialogInterface p1,int p2)
    {
     if(bolIsPicPath)
     {
      toast("你未选择有效的图片文件");
     }else
     {
      BL.getBL().setOutPath(BL.getBL().getCurrentPath());
      outPathEditText.setText(BL.getBL().getOutPath());
      spPutString(OUT_PATH,BL.getBL().getOutPath());
     }
    }
   })
   .create();
   
 }
 
 public void onChoosePathClick(View v)
 {
  bolIsPicPath=(v.getId()==R.id.main_btn_picpath);
  if(bolIsPicPath)
  {
   BL.getBL().setCurrentPath(BL.getBL().getPicPath().substring(0,BL.getBL().getPicPath().lastIndexOf("/")));
  }else
  {
   BL.getBL().setCurrentPath(BL.getBL().getOutPath());
  }
  setListDataChange();
  listDialog.show();
 }

 public void onDoBtnClick(View v)
 {
  BL.getBL().picPathString=picPathEditText.getText().toString();
  BL.getBL().outPathString=outPathEditText.getText().toString();
  BL.getBL().wordString=wordEditText.getText().toString();
  BL.getBL().codeString=codeEditText.getText().toString();
  BL.getBL().titleString=titleEditText.getText().toString();
  BL.getBL().fontSizeString=fontSizeEditText.getText().toString();
  BL.getBL().backColorString=backColorEditText.getText().toString();
  BL.getBL().fontTypeString=fontTypeEditText.getText().toString();
  
  if(BL.getBL().isAnyOneNull())
  {
   toast("每个参数都不能为空");
   return;
  }else if(BL.getBL().fontSizeString.equals("0"))
  {
   toast("字体大小不能为0");
   return;
  }else if(BL.getBL().backColorString.lastIndexOf("#")!=0||BL.getBL().backColorString.length()<7)
  {
   toast("背景颜色参数错误");
   return;
  }
  
  spPutString(WORD,BL.getBL().wordString);
  spPutString(CODE,BL.getBL().codeString);
  spPutString(TITLE,BL.getBL().titleString);
  spPutString(FONT_SIZE,BL.getBL().fontSizeString);
  spPutString(BACK_COLOR,BL.getBL().backColorString);
  spPutString(FONT_TYPE,BL.getBL().fontTypeString);

  mt=new MyTask(this);
  mt.execute();
  
 }
 
 public void onCancelBtnClick(View v)
 {
  if(!mt.isCancelled())mt.cancel(true);
  
  changeBtnVisibility();
 }
 
 //重写onActivityResult以获得你需要的信息
 @Override
 protected void onActivityResult(int requestCode,int resultCode,Intent data)
 {
  super.onActivityResult(requestCode,resultCode,data);
  //此处的requestCode用于判断接收的Activity是不是你想要的那个
  if(resultCode==RESULT_OK&&requestCode==0)
  {
   //获得图片的路径
   BL.getBL().setPicPath(Utils.getAbsolutePath(this,data.getData(),MediaStore.Images.Media.DATA));
   picPathEditText.setText(BL.getBL().getPicPath());
   spPutString(PIC_PATH,BL.getBL().getPicPath());
   listDialog.dismiss();
  }else if(resultCode==RESULT_OK)
  {
   toast("请重新选择图片");
  }
 }
 
 public void setListDataChange()
 {
  listAdapter.setFolderInfo(Utils.getAllSonFolder(BL.getBL().getCurrentPath()));
  listAdapter.notifyDataSetChanged();
  txtCurrentPath.setText(BL.getBL().getCurrentPath());
 }
 
 public void changeBtnVisibility()
 {
  doBtn.setVisibility(8-doBtn.getVisibility());
  cancelBtn.setVisibility(8-cancelBtn.getVisibility());
 }
 
 public ProgressBar getProgressBar()
 {
  return probar;
 }
 
 public TextView getProtxt()
 {
  return protxt;
 }
 
}
