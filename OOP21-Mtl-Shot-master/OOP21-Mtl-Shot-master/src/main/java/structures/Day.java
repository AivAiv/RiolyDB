package structures;

import java.util.Date;
import java.util.Optional;

public class Day {
    
    private Date date;
    private Optional<String> description;
    
    public Day(Date date, String description) {
        this.date = date;
        this.description = Optional.ofNullable(description);
    }
    
    public Date getDate() {
        return this.date;
    }
    
    public String getDescription() {
        String descStr;
        try {
            descStr = this.description.get();
        } catch(Exception e) {
            descStr = " ";
        }
        return descStr;
    }
    
}
