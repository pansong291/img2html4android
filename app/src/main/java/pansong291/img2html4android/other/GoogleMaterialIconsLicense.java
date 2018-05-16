package pansong291.img2html4android.other;

import de.psdev.licensesdialog.licenses.License;
import android.content.Context;
import pansong291.img2html4android.R;

public class GoogleMaterialIconsLicense extends License
{
  
 @Override
 public String getName()
 {
  return "Google Material Icons License";
 }

 @Override
 public String readSummaryTextFromResources(Context p1)
 {
  return getContent(p1,R.raw.gmil_summary);
 }

 @Override
 public String readFullTextFromResources(Context p1)
 {
  return "";
 }

 @Override
 public String getVersion()
 {
  return "";
 }

 @Override
 public String getUrl()
 {
  return "https://creativecommons.org/licenses/by/4.0/";
 }
 
}
