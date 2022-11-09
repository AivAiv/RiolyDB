package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import Utilities.Utils;
import structures.Stand;

public class StandsTable {
	
	public static final String TABLE_NAME = "padiglioni";

    private final Connection connection; 

    public StandsTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }
    
    public void addStand(int standCod, String specialization, Time open, Time close, Date children, int expTot, int expOcc) {
    	String query = "INSERT INTO `fairdb`.`" + TABLE_NAME + "` (`codicePadiglione`, `specializzazioneRichiesta`, `orarioApertura`, `orarioChiusura`, `giornoAperturaAreaBambini`, `numSpaziEsposizioneTot`, `numSpaziEsposizioneOccupati`) VALUES (?, ?, ?, ?, ?, ?, ?);";
    	try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, standCod);
            statement.setString(2, specialization);
            statement.setTime(3, open);
            statement.setTime(4, close);
            statement.setDate(5, Utils.dateToSqlDate(children));
            statement.setInt(6, expTot);
            statement.setInt(7, expOcc);
            statement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }
    
    public List<Stand> getAllStands() {
        	String query = "SELECT * FROM " + TABLE_NAME + ";";
        	List<Stand> res = new LinkedList<>();
        	try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
                final ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
	                int standCod = resultSet.getInt("codicePadiglione");
	                String specialization = resultSet.getString("specializzazioneRichiesta");
	                Time open = resultSet.getTime("orarioApertura");
	                Time close = resultSet.getTime("orarioChiusura");
	                Date children = Utils.sqlDateToDate(resultSet.getDate("giornoAperturaAreaBambini"));
	                int expTot = resultSet.getInt("numSpaziEsposizioneTot");
	                int expOcc = resultSet.getInt("numSpaziEsposizioneOccupati");
	            	res.add(new Stand(standCod, specialization, open, close, children, expTot, expOcc));
                }
            } catch (final SQLException e) { 
                throw new IllegalStateException(e);
            }
        	return res;
    }
    
    public boolean checkId(int id) {
    	String query = "SELECT codicePadiglione FROM " + TABLE_NAME + " WHERE codicePadiglione = ?;";
    	boolean res = false;
    	try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
    		statement.setInt(1, id);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
            	  int foundId = resultSet.getInt("codicePadiglione");
            	  if (foundId != 0) {
            		  res = true;
            	  }
            }
        } catch (final SQLException e) { 
            throw new IllegalStateException(e);
        }
    	return res;
    }
    
    public void deleteStand(int code) {
    	String query = "DELETE FROM `fairdb`.`" + TABLE_NAME + "` WHERE (`codicePadiglione` = ?);";
    	try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, code);
            statement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }
    
    public int findStandNum(String spec) {
    	String query = "SELECT codicePadiglione FROM fairdb." + TABLE_NAME + " WHERE specializzazioneRichiesta = ?;";
    	int res = 0;
    	try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
    		statement.setString(1, spec);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
            	  int codPad = resultSet.getInt("codicePadiglione");
            	  res = codPad;
            }
        } catch (final SQLException e) { 
            throw new IllegalStateException(e);
        }
    	return res;
    }
    
}
