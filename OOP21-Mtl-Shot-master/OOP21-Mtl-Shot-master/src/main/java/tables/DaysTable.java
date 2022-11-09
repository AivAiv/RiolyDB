package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class DaysTable {
	
	public static final String TABLE_NAME = "giorni";

    private final Connection connection; 

    public DaysTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }
    
    public List<Date> getAllDays() {
    	String query = "SELECT data FROM " + TABLE_NAME + ";";
    	List<Date> res = new LinkedList<>();
    	try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
            	  Date day = resultSet.getDate("data");
            	  res.add(day);
            }
        } catch (final SQLException e) { 
            throw new IllegalStateException(e);
        }
    	return res;
    }

}
