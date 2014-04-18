package org.addhen.smssync.models;

import org.addhen.smssync.database.Database;
import org.addhen.smssync.net.SyncScheme;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ubiquill on 4/18/14.
 */
public class CellyManager extends Model {
  private String title;

  private String keywords;

  private String url;

  private String secret;

  private int status;

  private int id;

  private SyncUrl mSyncUrl;

  private SyncScheme syncScheme;

  public CellyManager() {
    this.mSyncUrl = new SyncUrl();
    syncScheme = new SyncScheme();
  }

  /**
   * Delete all pending messages.
   *
   * @return boolean
   */
  public boolean deleteAllSyncUrl() {
    return Database.syncUrlContentProvider.deleteAllSyncUrl();
  }

  /**
   * Delete sync url by ID
   *
   * @param id The ID to delete by
   * @return boolean
   */
  public boolean deleteSyncUrlById(int id) {
    return Database.syncUrlContentProvider.deleteSyncUrlById(id);
  }

  @Override
  public boolean load() {
    mSyncUrl = new SyncUrl();
    mSyncUrl.setTitle("celly");
    mSyncUrl.setSecret("secret");
    mSyncUrl.setUrl("https://cel.ly/smssync");
    List<SyncUrl> syncUrlList = new ArrayList<SyncUrl>();
    syncUrlList.add(mSyncUrl);
    return mSyncUrl != null;

  }

  public List<SyncUrl> loadById(int id) {
    return Database.syncUrlContentProvider.fetchSyncUrlById(id);
  }

  public List<SyncUrl> loadByStatus(int status) {
    return Database.syncUrlContentProvider.fetchSyncUrlByStatus(status);
  }

  public boolean saveSyncUrls(List<SyncUrl> syncUrls) {
    return syncUrls != null && syncUrls.size() > 0 && Database.syncUrlContentProvider
        .addSyncUrl(syncUrls);
  }

  /**
   * Add sync url
   *
   * @return boolean
   */
  @Override
  public boolean save() {
    //return Database.syncUrlContentProvider.addSyncUrl(this);
    return true;
  }

  /**
   * Update an existing sync URL
   *
   * @return boolean
   */
  public boolean update() {
    //return Database.syncUrlContentProvider.updateSyncUrl(this);
    return true;
  }

  /**
   * Update status of a sync URL
   *
   * @param syncUrl The sync url to update
   * @return boolean
   */
  public boolean updateStatus(SyncUrl syncUrl) {
    return Database.syncUrlContentProvider.updateStatus(syncUrl);
  }

  /**
   * The total number of active or enabled Sync URLs.
   *
   * @return int The total number of Sync URLs that have been enabled.
   */
  public int totalActiveSynUrl() {
    return Database.syncUrlContentProvider.totalActiveSyncUrl();
  }

  public SyncUrl getSyncUrl() {
    return mSyncUrl;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getKeywords() {
    return keywords;
  }

  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

  public SyncScheme getSyncScheme() {
    return syncScheme;
  }

  public void setSyncScheme(SyncScheme scheme) {
    syncScheme = scheme;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;

  }

  @Override
  public String toString() {
    return "SynclUrl{" +
        "id:" + id +
        ", title:" + title +
        ", keywords:" + keywords +
        ", secret:" + secret +
        ", status:" + status +
        ", url:" + url +
        "}";
  }
}
