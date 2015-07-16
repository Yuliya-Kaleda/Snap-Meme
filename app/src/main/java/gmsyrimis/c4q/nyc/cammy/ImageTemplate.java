package gmsyrimis.c4q.nyc.cammy;

/**
 * Created by July on 7/12/15.
 */
public class ImageTemplate {
    private int _ID;
    private String name;
    private byte[] image;

    public ImageTemplate() {

    }

    public ImageTemplate (int _ID, String name, byte[] image) {
        this._ID = _ID;
        this.name = name;
        this.image = image;
    }

    public ImageTemplate (String name, byte[] image) {
        this.name = name;
        this.image = image;
    }

    public int getId() {
        return _ID;
    }

    public void setId(int id) {
        this._ID = _ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
