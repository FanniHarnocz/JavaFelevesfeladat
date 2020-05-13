import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/*
 * Movie database program 
 * with user interface
 */
public class Program extends JFrame {
	
	private JPanel contentPanel;
	private DatabaseMethods databaseMethods = new DatabaseMethods();
	private MovieTableModel movieTableModel;
	
	//Main entry point
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Program frame = new Program();
					frame.setVisible(true);
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
			
		});
	}
	
	/*
	 * Menu
	 * Functions: movie list, insert new movie, modify and delete. 
	 */
	public Program() {
		setTitle("Film adatbázis");
		
		databaseMethods.driverRegistration();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanel);
		contentPanel.setLayout(null);
		
		//Create button to select movie list
		JButton buttonList = new JButton("Filmek listája");
		buttonList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				databaseMethods.connect();
				movieTableModel = databaseMethods.readData();
				databaseMethods.DisConnect();
				MovieList movieList = new MovieList(Program.this, movieTableModel);
				movieList.setVisible(true);
			}
		});
		
		buttonList.setBounds(129, 42, 171, 23);
		contentPanel.add(buttonList);
		
		//Create button to add new movie
		JButton buttonNewMovie = new JButton("Film hozzáadása");
		buttonNewMovie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MovieInsert movieInsert = new MovieInsert(Program.this);
				movieInsert.setVisible(true);
			}
		});
		
		buttonNewMovie.setBounds(129, 91, 171, 23);
		contentPanel.add(buttonNewMovie);
		
		//Create button to delete movie
		JButton buttonDelete = new JButton("Film törlése");
		buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					databaseMethods.connect();
					movieTableModel= databaseMethods.readData();
					databaseMethods.DisConnect();
					MovieDelete movieDelete = new MovieDelete(Program.this, movieTableModel);
					movieDelete.setVisible(true);
			}
		});
		
		buttonDelete.setBounds(129, 138, 171, 23);
		contentPanel.add(buttonDelete);
		
		//Create button to modify movie
		JButton buttonModify = new JButton("Film módosítása");
		buttonModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				databaseMethods.connect();
				movieTableModel= databaseMethods.readData();
				databaseMethods.DisConnect();
				MovieModify movieModify = new MovieModify(Program.this, movieTableModel);
				movieModify.setVisible(true);
			}
		});
		
		buttonModify.setBounds(129, 190, 171, 23);
		contentPanel.add(buttonModify);
		}
	}