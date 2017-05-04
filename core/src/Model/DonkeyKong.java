package Model;

/**
 * Created by asus on 04/05/2017.
 */

public class DonkeyKong extends Entity {
    private DonkeyKong instance = null;
    private DonkeyKong(){};


    public DonkeyKong getInstance(){
        if (this.instance == null)
            return (this.instance = new DonkeyKong());
        else
            return this.instance;
    }
}
