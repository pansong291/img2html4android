package pansong291.img2html4android.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import pansong291.img2html4android.R;
import pansong291.img2html4android.other.BL;
import pansong291.img2html4android.other.MyTask;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Zactivity 
{
 
 //主视图控件
 EditText picPathEditText,outPathEditText,
 wordEditText,codeEditText,titleEditText,fontSizeEditText,
 backColorEditText,fontTypeEditText;
 
 Button picPathBtn,outPathBtn,doBtn;
 
 ProgressBar probar;
 
 TextView protxt;
 
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
  
  probar=(ProgressBar)findViewById(R.id.main_probar);
  
  protxt=(TextView)findViewById(R.id.main_txt_pro);
  
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
  
  MyTask mt=new MyTask(this);
  mt.execute();
  
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
