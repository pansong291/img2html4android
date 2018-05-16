package pansong291.img2html4android.ui;

import android.os.Bundle;
import android.view.View;
import de.psdev.licensesdialog.LicensesDialog;
import de.psdev.licensesdialog.LicensesDialogFragment;
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20;
import de.psdev.licensesdialog.licenses.GnuLesserGeneralPublicLicense21;
import de.psdev.licensesdialog.licenses.License;
import de.psdev.licensesdialog.model.Notice;
import de.psdev.licensesdialog.model.Notices;
import pansong291.img2html4android.R;

public class TestActivity extends Zactivity
{
 public void onCreate(final Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_testdialog);
  
 }

 // ==========================================================================================================================
 // Public API
 // ==========================================================================================================================

 public void onSingleClick(final View view) {
  final String name = "LicensesDialog";
  final String url = "http://psdev.de";
  final String copyright = "Copyright 2013 Philip Schiffer <admin@psdev.de>";
  final License license = new ApacheSoftwareLicense20();
  final Notice notice = new Notice(name, url, copyright, license);
  new LicensesDialog.Builder(this)
   .setNotices(notice)
   .build()
   .show();
 }

 public void onMultipleClick(final View view) {
  new LicensesDialog.Builder(this)
   .setNotices(R.raw.notices)
   .build()
   .show();
 }

 public void onMultipleIncludeOwnClick(final View view) {
  new LicensesDialog.Builder(this)
   .setNotices(R.raw.notices)
   .setIncludeOwnLicense(true)
   .build()
   .show();
 }

 public void onMultipleProgrammaticClick(final View view) {
  final Notices notices = new Notices();
  notices.addNotice(new Notice("Test 1", "http://example.org", "Example Person", new ApacheSoftwareLicense20()));
  notices.addNotice(new Notice("Test 2", "http://example.org", "Example Person 2", new GnuLesserGeneralPublicLicense21()));

  new LicensesDialog.Builder(this)
   .setNotices(notices)
   .setIncludeOwnLicense(true)
   .build()
   .show();
 }

 public void onSingleFragmentClick(final View view) {
  final String name = "LicensesDialog";
  final String url = "http://psdev.de";
  final String copyright = "Copyright 2013 Philip Schiffer <admin@psdev.de>";
  final License license = new ApacheSoftwareLicense20();
  final Notice notice = new Notice(name, url, copyright, license);

  final LicensesDialogFragment fragment = new LicensesDialogFragment.Builder(this)
   .setNotice(notice)
   .setIncludeOwnLicense(false)
   .build();

  fragment.show(getSupportFragmentManager(), null);
 }

 public void onMultipleFragmentClick(final View view) throws Exception {
  final LicensesDialogFragment fragment = new LicensesDialogFragment.Builder(this)
   .setNotices(R.raw.notices)
   .build();

  fragment.show(getSupportFragmentManager(), null);
 }

 public void onMultipleIncludeOwnFragmentClick(final View view) throws Exception {
  final LicensesDialogFragment fragment = new LicensesDialogFragment.Builder(this)
   .setNotices(R.raw.notices)
   .setShowFullLicenseText(false)
   .setIncludeOwnLicense(true)
   .build();

  fragment.show(getSupportFragmentManager(), null);
 }

 public void onMultipleProgrammaticFragmentClick(final View view) {
  final Notices notices = new Notices();
  notices.addNotice(new Notice("Test 1", "http://example.org", "Example Person", new ApacheSoftwareLicense20()));
  notices.addNotice(new Notice("Test 2", "http://example.org", "Example Person 2", new GnuLesserGeneralPublicLicense21()));

  final LicensesDialogFragment fragment = new LicensesDialogFragment.Builder(this)
   .setNotices(notices)
   .setShowFullLicenseText(false)
   .setIncludeOwnLicense(true)
   .build();

  fragment.show(getSupportFragmentManager(), null);
 }

}
