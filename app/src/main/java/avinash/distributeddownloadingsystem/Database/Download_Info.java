package avinash.distributeddownloadingsystem.Database;

/**
 * Created by Avinash Sharma on 14-Apr-17.
 */

public class Download_Info {

    private String FileName;
    private long id;
    private String URL;
    private int IsAdmin;
    private String key;

    public Download_Info(long id, String fileName, String URL, int  isAdmin, String key) {
        this.id = id;
        FileName = fileName;
        this.URL = URL;
        IsAdmin = isAdmin;
        this.key = key;
    }
    public Download_Info(long id, String fileName, String URL, String key) {
        this.id = id;
        FileName = fileName;
        this.URL = URL;

        this.key = key;
    }
    public Download_Info( String fileName, String URL, int  isAdmin, String key) {
        FileName = fileName;
        this.URL = URL;
        IsAdmin = isAdmin;
        this.key = key;
    }
    public Download_Info()
    {}

            public long getId() {
        return id;

    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFileName() {
        return FileName;

    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public int getAdmin() {
        return IsAdmin;
    }

    public void setAdmin(int admin) {
        IsAdmin = admin;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
