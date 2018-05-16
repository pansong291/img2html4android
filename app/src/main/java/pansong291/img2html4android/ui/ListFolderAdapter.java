package pansong291.img2html4android.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.File;
import java.util.ArrayList;
import pansong291.img2html4android.R;
import pansong291.img2html4android.other.BL;

public class ListFolderAdapter extends RecyclerView.Adapter<ListFolderAdapter.ItemViewHolder>
{
 private MainActivity context;
 private LayoutInflater vinflate;
 
 private OnItemClickListener itemClickListener=null;
 
 private ArrayList<String>dataList=null;
 
 public ListFolderAdapter(MainActivity c)
 {
  context=c;
  vinflate=LayoutInflater.from(context);
 }
 
 @Override
 public ItemViewHolder onCreateViewHolder(ViewGroup parent,int viewType)
 {
  //实例化展示的view
  View v=vinflate.inflate(R.layout.list_item_folder,parent,false);
  //实例化viewholder
  ItemViewHolder viewHolder=new ItemViewHolder(v);
  return viewHolder;
 }

 @Override
 public void onBindViewHolder(ItemViewHolder holder,int position)
 {
  // 绑定数据
  String ss=dataList.get(position);
  holder.txtFolderName.setText(ss);
  if(!new File(BL.getBL().getCurrentPath()+"/"+ss).isDirectory())
   holder.imgFolderPic.setImageResource(R.drawable.file);
  else if(ss.startsWith("."))
   holder.imgFolderPic.setImageResource(R.drawable.folder_hidden);
  else
   holder.imgFolderPic.setImageResource(R.drawable.folder);
   
  if(itemClickListener!=null)
  {
   holder.itemView.setOnClickListener(new OnClickListener()
   {
    View v;int i;
    protected OnClickListener setData(View p1,int p2)
    {
     v=p1;i=p2;
     return this;
    }
    @Override
    public void onClick(View v)
    {
     itemClickListener.onItemClick(v,i);
    }
   }.setData(holder.itemView,position));
  }
   
 }

 @Override
 public int getItemCount()
 {
  return dataList==null?0:dataList.size();
 }
 
 public void setOnItemClickListener(OnItemClickListener itemClickListener)
 {
  this.itemClickListener=itemClickListener;
 }
 
 public void clearFolderInfo()
 {
  if(dataList!=null)dataList.clear();
 }
 
 public void addFolderInfo(String a)
 {
  if(dataList==null)dataList=new ArrayList<String>();
  dataList.add(a);
 }
 
 public String getFolderInfo(int i)
 {
  if(dataList!=null)return dataList.get(i);
  return null;
 }
 
 public void setFolderInfo(ArrayList<String>l)
 {
  dataList=l;
 }
 
 //item的回调接口
 public interface OnItemClickListener
 {
  void onItemClick(View view,int Position);
 }
 
 
 /***
 public int getCount()
 {
  return dataList.size();
 }
 public Object getItem(int p1)
 {
  return dataList.get(p1);
 }
 public long getItemId(int p1)
 {
  return p1;
 }
 public View getView(int i,View v,ViewGroup p)
 {
  ItemViewHolder liv;
  if(v==null)
  {
   v=vinflate.inflate(R.layout.list_item_folder,null);
   liv=new ItemViewHolder(v);
   //这句要注释掉，否则item不能点击
   //v.setClickable(false);
   v.setTag(liv);
  }else
  {
   liv=(ItemViewHolder)v.getTag();
  }
  String ss=dataList.get(i);
  liv.txtFolderName.setText(ss);
  if(!new File(BL.getBL().getCurrentPath()+"/"+ss).isDirectory())
   liv.imgFolderPic.setImageResource(R.drawable.file);
  else if(ss.startsWith("."))
   liv.imgFolderPic.setImageResource(R.drawable.folder_hidden);
  else
   liv.imgFolderPic.setImageResource(R.drawable.folder);
  return v;
 }
 /***/
 protected class ItemViewHolder extends RecyclerView.ViewHolder
 {
  protected ImageView imgFolderPic;
  protected TextView txtFolderName;
  
  protected ItemViewHolder(View v)
  {
   super(v);
   txtFolderName=(TextView)v.findViewById(R.id.textview_list_item);
   imgFolderPic=(ImageView)v.findViewById(R.id.imageview_list_item_folder);
  }
  
 }
 
// public class FolderItemValue
// {
//  public String strFolderName;
//  public FolderItemValue(String a)
//  {
//   strFolderName=a;
//  }
// }
 
}
