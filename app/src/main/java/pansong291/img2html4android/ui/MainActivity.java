package pansong291.img2html4android.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import de.psdev.licensesdialog.LicensesDialog;
import de.psdev.licensesdialog.LicenseResolver;
import java.io.File;
import pansong291.img2html4android.R;
import pansong291.img2html4android.other.BL;
import pansong291.img2html4android.other.MyProclamation;
import pansong291.img2html4android.other.MyTask;
import pansong291.img2html4android.other.MyUpdate;
import pansong291.img2html4android.other.MyUpdateDialogListener;
import pansong291.img2html4android.other.Utils;
import pansong291.img2html4android.other.AndroidMaterialIconGeneratorLicense;
import pansong291.img2html4android.other.GoogleMaterialIconsLicense;

public class MainActivity extends Zactivity 
{
 int VERSION_CODE;
 String VERSION_NAME;
 
 //界面视图
 DrawerLayout drawerLayout;
 NavigationView navigationView;
 ActionBarDrawerToggle actionBarDrawerToggle;
 Toolbar toolbar;
 
 //主视图控件
 TextInputLayout picPathInputLayout,outPathInputLayout,
 wordInputLayout,codeInputLayout,titleInputLayout,fontSizeInputLayout,
 backColorInputLayout,fontTypeInputLayout;
 EditText picPathEditText,outPathEditText,
 wordEditText,codeEditText,titleEditText,fontSizeEditText,
 backColorEditText,fontTypeEditText;
 
 Button picPathBtn,outPathBtn,doBtn,cancelBtn;
 
 SeekBar cutCountSkb;
 
 ProgressBar proBar;
 
 TextView cutCountTxt,proTxt;

 //选择路径对话框里的视图
 TextView txtCurrentPath;
 View viewDialog,viewGoBack,viewNewFolder;
 AlertDialog listDialog;
 RecyclerView listvPath;
 ListFolderAdapter listAdapter;
 MyOnClickListener myOnClickListener;

 //判断选择路径的view
 boolean bolIsPicPath;
 
 //后台任务
 MyTask mt;
 
 MyUpdate myUpdate=null;
 
 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
  
  toolbar=(Toolbar)findViewById(R.id.main_v7_toolbar);
  setSupportActionBar(toolbar);

  drawerLayout=(DrawerLayout)findViewById(R.id.main_drawerlayout);
  navigationView=(NavigationView)findViewById(R.id.main_nav);
  
  //这两句显示左边的三条杠
  getSupportActionBar().setHomeButtonEnabled(true);
  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

  actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);

  //这两句实现toolbar和Drawer的联动：图标随着侧滑菜单的开关而变换
  actionBarDrawerToggle.syncState();
  drawerLayout.addDrawerListener(actionBarDrawerToggle);
  
  navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
  {
   @Override
   public boolean onNavigationItemSelected(MenuItem item)
   {
    //toast(item.getTitle().toString());
    //drawerLayout.closeDrawer(navigationView);
    
    switch(item.getItemId())
    {
     case R.id.menu_help:
      new AlertDialog.Builder(MainActivity.this)
       .setIcon(R.drawable.ic_help)
       .setTitle("帮助")
       .setMessage(R.string.help_user)
       .setPositiveButton("确定",null)
       .show();
      break;
     case R.id.menu_about:
      new AlertDialog.Builder(MainActivity.this)
       .setIcon(R.drawable.ic_info)
       .setTitle("关于")
       .setMessage(R.string.about_msg)
       .setPositiveButton("确定",null)
       .show();
      break;
     case R.id.menu_openresource:
      //Intent it=new Intent(MainActivity.this,TestActivity.class);
      //startActivity(it);
      
      LicenseResolver.registerLicense(new AndroidMaterialIconGeneratorLicense());
      LicenseResolver.registerLicense(new GoogleMaterialIconsLicense());
      
      new LicensesDialog.Builder(MainActivity.this)
       .setTitle("开放源代码许可")
       .setCloseText("确定")
       .setNotices(R.raw.notices)
       .setIncludeOwnLicense(true)
       .build()
       .show();
      break;
     case R.id.menu_version:
      if(myUpdate==null)
      {
       myUpdate=new MyUpdate(MainActivity.this,"R39j6on",new MyUpdateDialogListener(MainActivity.this));
       myUpdate.setCurrentVersion(VERSION_NAME);
      }
      myUpdate.checkNow(true,item);
      break;
    }
    
    return true;
   }
  });
  
  try{
   PackageInfo pi=getPackageManager().getPackageInfo(getPackageName(),0);
   VERSION_CODE=pi.versionCode;
   VERSION_NAME=pi.versionName;
  }catch(Exception e)
  {
  }
  
  int oldVerCode=sp.getInt(V_CODE,-1);
  if(oldVerCode<=0)
  {
   //用户第一次安装本应用
   new AlertDialog.Builder(this)
    .setCancelable(false)
    .setIcon(R.drawable.ic_info)
    .setTitle("声明")
    .setMessage(String.format("感谢您安装本应用！\n%s",getString(R.string.hello_user)))
    .setPositiveButton("确定",null)
    .show();
   sp.edit().putInt(V_CODE,VERSION_CODE).commit();
  }else if(oldVerCode<VERSION_CODE)
  {
   //用户更新了本应用
   new AlertDialog.Builder(this)
    .setCancelable(false)
    .setIcon(R.drawable.ic_update)
    .setTitle("版本升级")
    .setMessage(String.format("感谢您对本应用的支持！\n本应用已成功升级到%1$s版本。\n%2$s",VERSION_NAME,getString(R.string.update_msg)))
    .setPositiveButton("确定",null)
    .show();
   sp.edit().putInt(V_CODE,VERSION_CODE).commit();
  }
  
  //公告相关
  LinearLayout llt=(LinearLayout)findViewById(R.id.main_gg);
  new MyProclamation(this,"R39YCNj",llt).start();
  
  //更新相关
  if(sp.getBoolean("自动检查更新的选项",true)||sp.getBoolean(QZGX,false))
   new MyUpdate(this,"R39j6on",new MyUpdateDialogListener(this)).checkNow(false,null);
  
  picPathInputLayout=(TextInputLayout)findViewById(R.id.main_edit_picpath);
  picPathEditText=picPathInputLayout.getEditText();
  outPathInputLayout=(TextInputLayout)findViewById(R.id.main_edit_outpath);
  outPathEditText=outPathInputLayout.getEditText();
  wordInputLayout=(TextInputLayout)findViewById(R.id.main_edit_word);
  wordEditText=wordInputLayout.getEditText();
  codeInputLayout=(TextInputLayout)findViewById(R.id.main_edit_code);
  codeEditText=codeInputLayout.getEditText();
  titleInputLayout=(TextInputLayout)findViewById(R.id.main_edit_title);
  titleEditText=titleInputLayout.getEditText();
  fontSizeInputLayout=(TextInputLayout)findViewById(R.id.main_edit_fontsize);
  fontSizeEditText=fontSizeInputLayout.getEditText();
  backColorInputLayout=(TextInputLayout)findViewById(R.id.main_edit_backcolor);
  backColorEditText=backColorInputLayout.getEditText();
  fontTypeInputLayout=(TextInputLayout)findViewById(R.id.main_edit_fonttype);
  fontTypeEditText=fontTypeInputLayout.getEditText();
  
  picPathEditText.setText(sp.getString(PIC_PATH,""));
  outPathEditText.setText(sp.getString(OUT_PATH,""));
  if(!picPathEditText.getText().toString().equals(""))BL.getBL().setPicPath(picPathEditText.getText().toString());
  if(!outPathEditText.getText().toString().equals(""))BL.getBL().setOutPath(outPathEditText.getText().toString());
  wordEditText.setText(sp.getString(WORD,"龍"));
  codeEditText.setText(sp.getString(CODE,"utf-8"));
  titleEditText.setText(sp.getString(TITLE,""));
  fontSizeEditText.setText(sp.getString(FONT_SIZE,"4"));
  backColorEditText.setText(sp.getString(BACK_COLOR,"#000000"));
  fontTypeEditText.setText(sp.getString(FONT_TYPE,"monospace"));
  
  picPathBtn=(Button)findViewById(R.id.main_btn_picpath);
  outPathBtn=(Button)findViewById(R.id.main_btn_outpath);
  doBtn=(Button)findViewById(R.id.main_btn_do);
  cancelBtn=(Button)findViewById(R.id.main_btn_cancel);
  
  cutCountSkb=(SeekBar)findViewById(R.id.main_skb_cutcount);
  
  proBar=(ProgressBar)findViewById(R.id.main_probar);
  
  cutCountTxt=(TextView)findViewById(R.id.main_txt_cutcount);
  proTxt=(TextView)findViewById(R.id.main_txt_pro);
  
  viewDialog=LayoutInflater.from(getApplication()).inflate(R.layout.dialog_choose_folder,null);
  listvPath=(RecyclerView)viewDialog.findViewById(R.id.recyclerview_dialog_folder);
  txtCurrentPath=(TextView)viewDialog.findViewById(R.id.textview_dialog_current_folder);
  viewGoBack=viewDialog.findViewById(R.id.linearlayout_dialog_goback);
  viewNewFolder=viewDialog.findViewById(R.id.imageview_dialog_new_folder);

  myOnClickListener=new MyOnClickListener(this);
  viewNewFolder.setOnClickListener(myOnClickListener);
  //viewGoBack.setOnTouchListener(myOnClickListener.new RippleForegroundListener(viewGoBack));
  viewGoBack.setOnClickListener(myOnClickListener);
  
  cutCountSkb.setMax(29);
  cutCountSkb.setProgress(sp.getInt(CUT_COUNT,4)-1);
  cutCountTxt.setText(cutCountSkb.getProgress()+1+"");
  cutCountSkb.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
   {
    @Override
    public void onProgressChanged(SeekBar p1,int p2,boolean p3)
    {
     cutCountTxt.setText(p2+1+"");
    }

    @Override
    public void onStartTrackingTouch(SeekBar p1)
    {
    }

    @Override
    public void onStopTrackingTouch(SeekBar p1)
    {
    }
   });
  
  LinearLayoutManager llManager=new LinearLayoutManager(this);
  listvPath.setLayoutManager(llManager);
  //确定每个item高度固定时，设置为true可以提高性能
  listvPath.setHasFixedSize(true);
   
  listAdapter=new ListFolderAdapter(this);
  listvPath.setAdapter(listAdapter);
  //设置分隔线  
  //listvPath.addItemDecoration(new DividerGridItemDecoration(this));
  listAdapter.setOnItemClickListener(new ListFolderAdapter.OnItemClickListener()
   {
    @Override
    public void onItemClick(View p1,int p2)
    {
     String fn=listAdapter.getFolderInfo(p2);
     File f=new File(BL.getBL().getCurrentPath()+"/"+fn);
     if(f.isDirectory())
     {
      BL.getBL().addToCurrentPath(fn);
      setListDataChange();
     }else if(bolIsPicPath)
     {
      BL.getBL().setPicPath(f.getAbsolutePath());
      picPathEditText.setText(BL.getBL().getPicPath());
      listDialog.dismiss();
     }
    }
   });

  listDialog=new AlertDialog.Builder(this)
   .setCancelable(false)
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
      snack("请点击确定来选取输出路径");
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
      snack("你未选择有效的图片文件");
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
  File ft;
  if(bolIsPicPath)
  {
   ft=new File(picPathEditText.getText().toString());
   if(ft.exists())
   {
    BL.getBL().setPicPath(ft.getAbsolutePath());
    BL.getBL().setCurrentPath(BL.getBL().getPicPath().substring(0,BL.getBL().getPicPath().lastIndexOf("/")));
    setListDataChange();
    listDialog.show();
   }else if(BL.getBL().getPicPath().endsWith("/x.."))
   {
    setListDataChange();
    listDialog.show();
   }else new AlertDialog.Builder(this)
     .setCancelable(false)
     .setMessage("当前文件不存在，是否恢复之前的路径？")
     .setPositiveButton("是",new DialogInterface.OnClickListener(){
      @Override
      public void onClick(DialogInterface p1,int p2)
      {
       picPathEditText.setText(BL.getBL().getPicPath());
      }
     })
     .setNegativeButton("否",null)
     .show();
  }else
  {
   ft=new File(outPathEditText.getText().toString());
   if(ft.exists())
   {
    BL.getBL().setOutPath(ft.getAbsolutePath());
    BL.getBL().setCurrentPath(BL.getBL().getOutPath());
    setListDataChange();
    listDialog.show();
   }else if(BL.getBL().getOutPath().endsWith(""))
   {
    setListDataChange();
    listDialog.show();
   }else new AlertDialog.Builder(this)
     .setCancelable(false)
     .setMessage("当前目录不存在，是否恢复之前的路径？")
     .setPositiveButton("是",new DialogInterface.OnClickListener(){
      @Override
      public void onClick(DialogInterface p1,int p2)
      {
       outPathEditText.setText(BL.getBL().getOutPath());
      }
     })
     .setNegativeButton("否",null)
     .show();
  }
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
  BL.getBL().cutCount=cutCountSkb.getProgress()+1;
  
  String fp=BL.getBL().outPathString;
  if(!fp.endsWith("/"))fp+="/";
  fp+=BL.getBL().picPathString.substring(BL.getBL().picPathString.lastIndexOf("/")+1)+".html";
  if(new File(fp).exists())
  {
   new AlertDialog.Builder(this)
    .setMessage("该html文件已存在，请选择一个操作。")
    .setPositiveButton("继续并替换",new DialogInterface.OnClickListener(){
     @Override
     public void onClick(DialogInterface p1,int p2)
     {
      doBackgroundTask();
     }
    })
    .setNegativeButton("打开该文件",new DialogInterface.OnClickListener()
     {
      String data;
      public DialogInterface.OnClickListener setData(String d){data=d;return this;}
      @Override
      public void onClick(DialogInterface p1,int p2)
      {
       try{
        Intent it=new Intent();
        it.setClassName("com.android.htmlviewer","com.android.htmlviewer.HTMLViewerActivity");
        it.setData(Uri.parse(data));
        startActivity(it);
       }catch(Exception e)
       {
        Intent it=new Intent(Intent.ACTION_VIEW);
        //it.setClassName("com.android.browser","com.android.browser.BrowserActivity");
        it.addCategory(Intent.CATEGORY_DEFAULT);  
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        it.setDataAndType(Uri.parse(data),"text/html");
        startActivity(Intent.createChooser(it,"选择浏览器"));
       }
      }
     }.setData("file://"+fp))
    .setNeutralButton("取消",null)
    .show();
   return;
  }
  doBackgroundTask();
 }
 
 private void doBackgroundTask()
 {
  if(BL.getBL().isAnyOneNull())
  {
   snack("所有参数都不能为空");
   return;
  }else if(1>(BL.getBL().fontSizeInteger=Integer.parseInt(BL.getBL().fontSizeString)))
  {
   fontSizeEditText.setError("字体大小不能小于1");
   return;
  }else if(BL.getBL().backColorString.lastIndexOf("#")!=0||BL.getBL().backColorString.length()<7)
  {
   backColorInputLayout.setError("背景颜色参数错误");
   return;
  }

//  spPutString(PIC_PATH,BL.getBL().getPicPath());
//  spPutString(OUT_PATH,BL.getBL().getOutPath());
//  在后台任务完成后，再将path保存，保证path的正确性
  spPutString(WORD,BL.getBL().wordString);
  spPutString(CODE,BL.getBL().codeString);
  spPutString(TITLE,BL.getBL().titleString);
  spPutString(FONT_SIZE,BL.getBL().fontSizeString);
  spPutString(BACK_COLOR,BL.getBL().backColorString);
  spPutString(FONT_TYPE,BL.getBL().fontTypeString);
  sp.edit().putInt(CUT_COUNT,BL.getBL().cutCount).commit();
  
  if(BL.getBL().fontSizeInteger<=3)
  {
   new AlertDialog.Builder(this)
    .setCancelable(false)
    .setTitle("注意")
    .setMessage("当前字体大小设置过小，可能导致html无法被完全加载或者排版错乱，是否仍要继续？")
    .setPositiveButton("继续",new DialogInterface.OnClickListener()
    {
     @Override
     public void onClick(DialogInterface p1,int p2)
     {
      mt=new MyTask(MainActivity.this);
      mt.execute();
     }
    })
    .setNegativeButton("取消",null)
    .show();
   return;
  }
  
  mt=new MyTask(this);
  mt.execute();
  
 }
 
 public void onCancelBtnClick(View v)
 {
  if(!mt.isCancelled())mt.cancel(true);
  
  //changeBtnVisibility();
  //已在onCancelled()函数内调用
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
   snack("请重新选择图片");
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
  return proBar;
 }
 
 public TextView getProtxt()
 {
  return proTxt;
 }

 /***
 @Override
 public void snack(String s)
 {
  snack(drawerLayout.getChildAt(0),s);
 }
 /***/
 
}
