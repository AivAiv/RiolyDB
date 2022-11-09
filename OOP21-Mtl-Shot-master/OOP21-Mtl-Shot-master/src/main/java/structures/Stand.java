package structures;

import java.sql.Time;
import java.util.Date;

public class Stand {
	
	private final int standCod;
    private final String specialization;
    private final Time open;
    private final Time close;
    private final Date children;
    private final int expTot;
    private final int expOcc;
    
	public Stand(int standCod, String specialization, Time open, Time close, Date children, int expTot, int expOcc) {
        this.standCod = standCod;
        this.specialization = specialization;
        this.open = open;
        this.close = close;
        this.children = children;
        this.expTot = expTot;
        this.expOcc = expOcc;
    }
    
    public int getStandCod() {
    	return this.standCod;
    }
    
    public String getSpecialization() {
        return this.specialization;
    }
    
	public Time getOpen() {
        return this.open;
    }
    
	public Time getClose() {
    	return this.close;
    }
    
    public Date getChildren() {
        return this.children;
    }
    
    public int getExpTot() {
    	return this.expTot;
    }
    
    public int getExpOcc() {
        return this.expOcc;
    }
    
}
