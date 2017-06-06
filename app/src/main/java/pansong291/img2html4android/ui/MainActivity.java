package pansong291.img2html4android.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.File;
import pansong291.img2html4android.R;
import pansong291.img2html4android.other.BL;
import pansong291.img2html4android.other.MyTask;
import pansong291.img2html4android.other.Utils;

public class MainActivity extends Zactivity 
{
 
 //主视图控件
 EditText picPathEditText,outPathEditText,
 wordEditText,codeEditText,titleEditText,fontSizeEditText,
 backColorEditText,fontTypeEditText;
 
 Button picPathBtn,outPathBtn,doBtn,cancelBtn;
 
 ProgressBar probar;
 
 TextView protxt;

 //对话框里的视图
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
  
  picPathEditText=(EditText)findViewById(R.id.main_edit_picpath);
  outPathEditText=(EditText)findViewById(R.id.main_edit_outpath);
  wordEditText=(EditText)findViewById(R.id.main_edit_word);
  codeEditText=(EditText)findViewById(R.id.main_edit_code);
  titleEditText=(EditText)findViewById(R.id.main_edit_title);
  fontSizeEditText=(EditText)findViewById(R.id.main_edit_fontsize);
  backColorEditText=(EditText)findViewById(R.id.main_edit_backcolor);
  fontTypeEditText=(EditText)findViewById(R.id.main_edit_fonttype);
  
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
      listDialog.dismiss();
     }
    }
   });

  listDialog=new AlertDialog.Builder(this)
   .setTitle("选择路径")
   .setView(viewDialog)
   .setNegativeButton("取消",null)
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
  }
  
  changeBtnVisibility();
  
  mt=new MyTask(this);
  mt.execute();
  
 }
 
 public void onCancelBtnClick(View v)
 {
  mt.cancel(true);
  
  changeBtnVisibility();
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
