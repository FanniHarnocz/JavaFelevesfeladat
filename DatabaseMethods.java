import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DatabaseMethods {
	
	private Statement statement = null;
	private Connection connection= null;
	private ResultSet resultSet = null;
	
	//Registration
	public void driverRegistration() {
		try {
			Class.forName("org.sqlite.JDBC");
			//SM("Sikeres driver regisztráció" ,1);
		} catch (ClassNotFoundException exception) {
			displayMessage("Hibás driver regisztráció!" + exception.getMessage(), 0);
		}
	}
	
	public void displayMessage(String message, int messageType) {
		JOptionPane.showMessageDialog(null, message, "Program üzenet", messageType);
	}
	
	//Connection
	public void connect() {
		try {
			String urlLocation= "jdbc:sqlite:data/moviedatabase";
			connection = DriverManager.getConnection(urlLocation);
			//SM("Connection OK",1);
		} catch (SQLException exception) {
			displayMessage("JDBC Connect: "+exception.getMessage(), 0);	
		}
		
	}
	
	//Disconnection
	public void DisConnect() {
		try {
			connection.close();
			//SM("DisConnection OK!", 1);
		} catch (SQLException exception) {
			displayMessage(exception.getMessage(), 0);
		}
		
	}
	
	//Get list
	public MovieTableModel readData() {
		Object movieTemplate[]= {"Jel", "Azonosító", "Cím", "Hossz (perc)", "Megjelenési idõ", "Rendezõ"};
		MovieTableModel movieTableModel = new MovieTableModel(movieTemplate, 0);
		
		String movieTitle = "", movieReleaseDate = "", movieDirector = "";
		int movieID = 0, movieLength = 0; 
		
		String sqlCommand= "select id, title, length, release, director from movie";
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sqlCommand);
			while (resultSet.next()) {
				movieID = resultSet.getInt("id");
				movieTitle = resultSet.getString("title");
				movieLength = resultSet.getInt("length");
				movieReleaseDate = resultSet.getString("release");
				movieDirector = resultSet.getString("director");
				movieTableModel.addRow(new Object[] {false, movieID, movieTitle, movieLength, movieReleaseDate, movieDirector});
			}
			resultSet.close();
		} catch (SQLException exception) {
			displayMessage(exception.getMessage(), 0); 
		}
		return movieTableModel;
	}
	
	public void insertData(String movieID, String title, String length, String releaseDate, String director ) {
		String sqlCommand ="insert into movie values("+movieID+",\""+title +"\","+length+",\""+releaseDate+"\",\""+director+"\")";
		
		try {
			statement = connection.createStatement();
			statement.execute(sqlCommand);
			displayMessage("Film hozzáadva!", 1);
		} catch(SQLException exception) {
			displayMessage("JDBC insert: " + exception.getMessage(), 0);
		}
	}
	
	public void deleteData(String movieID) {
		String sqlCommand = "delete from movie where id =" +movieID;
		try {
			statement = connection.createStatement();
			statement.execute(sqlCommand);
		} catch (SQLException exception) {
			displayMessage("JDBC Delete: " + exception.getMessage(), 0);
		}
	}
	
	public void updateData(String movieID, String movieProperty, String movieData) {
		String sqlCommand = "update movie set " + movieProperty + "='" + movieData + "' where id=" + movieID;
		try {
			statement = connection.createStatement();
			statement.execute(sqlCommand);
		} catch (SQLException exception) {
			displayMessage("JDBC Update: " + exception.getMessage(), 0);	
		}
	}
}