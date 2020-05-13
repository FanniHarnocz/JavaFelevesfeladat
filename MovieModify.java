import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JTextField;

/*
 * Movie modify window
 */
public  class MovieModify extends JDialog {
	
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private MovieTableModel movieTableModel;
	private Checker checker = new Checker();
	private DatabaseMethods databaseMethods = new DatabaseMethods(); 
	
	private JTextField movieID;
	private JTextField movieTitle;
	private JTextField movieLength;
	private JTextField movieReleaseDate;
	private JTextField movieDirector;
	
	public MovieModify(JFrame frame, MovieTableModel movieTableModel) {
		super(frame, "Filmek módosítása", true);
		this.movieTableModel = movieTableModel;
		
		setBounds(100, 100, 680, 359);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5,5,5,5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		//Add close button
		contentPanel.setLayout(null); {
			JButton buttonClose = new JButton("Bezárás");
			buttonClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent argument0) {
					dispose ();
				}
			});
			buttonClose.setBounds(283, 263, 89, 23);
			contentPanel.add(buttonClose);
		}
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 643, 175);
		contentPanel.add(scrollPane);
		
		table = new JTable(movieTableModel);
		scrollPane.setViewportView(table);
		
		JButton buttonModify = new JButton("Módosítás");
		buttonModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int database = 0, sign = 0, counter = 0;
				for( counter = 0; counter < movieTableModel.getRowCount(); counter++ ) {
					if ((Boolean)movieTableModel.getValueAt(counter,0) ) { database++; sign = counter; }
				}
				
				if (database == 0) checker.sendMessage("Nincs kijelölve film!", 0);
					
				if (database > 1) checker.sendMessage("Több film van kijelölve!\nEgyszerre csak egy rekord módosítható!", 0); 
					
				if (database == 1) {
					if (modifiedDataCounter() > 0) {
						boolean valid = true;
							
						if (checker.filled(movieID)) valid = checker.goodInt(movieID,"Film azonosító");
						if (checker.filled(movieLength)) valid = checker.goodInt(movieLength,"Hossz (perc)");
						if (checker.filled(movieReleaseDate)) valid = checker.goodDate(movieReleaseDate, "Megjelenési idõ"); 
							
						if (valid) {
							String movieCode = movieTableModel.getValueAt(sign, 1).toString();
							
							databaseMethods.connect();
							
							if(checker.filled(movieID)) databaseMethods.updateData(movieCode, "id", checker.getJText(movieID));
							if(checker.filled(movieTitle)) databaseMethods.updateData(movieCode, "title", checker.getJText(movieTitle));
							if(checker.filled(movieLength)) databaseMethods.updateData(movieCode, "length", checker.getJText(movieLength));
							if(checker.filled(movieReleaseDate)) databaseMethods.updateData(movieCode, "release", checker.getJText(movieReleaseDate));
							if(checker.filled(movieDirector)) databaseMethods.updateData(movieCode, "director", checker.getJText(movieDirector));
							
							databaseMethods.DisConnect();
							
							if(checker.filled(movieID))movieTableModel.setValueAt(checker.stringToInt(checker.getJText(movieID)), sign, 1);
							if(checker.filled(movieTitle))movieTableModel.setValueAt(checker.getJText(movieTitle), sign, 2);
							if(checker.filled(movieLength))movieTableModel.setValueAt(checker.getJText(movieLength), sign, 3);
							if(checker.filled(movieReleaseDate))movieTableModel.setValueAt(checker.getJText(movieReleaseDate), sign, 4);
							if(checker.filled(movieDirector))movieTableModel.setValueAt(checker.getJText(movieDirector), sign, 5);
							
							checker.sendMessage("A film módosítva",1);
							
						} else {
							checker.sendMessage("Nincs kitöltve egy módosító mezõ sem!",0);
						} reset(sign);
					}
				}
			}
		});
		
		buttonModify.setBounds(23, 263, 116, 23);
		contentPanel.add(buttonModify);
		
		movieID = new JTextField();
		movieID.setBounds(62, 197, 42, 20);
		contentPanel.add(movieID);
		movieID.setColumns(10);
		
		movieTitle = new JTextField();
		movieTitle.setBounds(119, 197, 126, 20);
		contentPanel.add(movieTitle);
		movieTitle.setColumns(10);
		
		movieLength = new JTextField();
		movieLength.setBounds(260, 197, 97, 20);
		contentPanel.add(movieLength);
		movieLength.setColumns(10);
		
		movieReleaseDate = new JTextField();
		movieReleaseDate.setBounds(372, 197, 116, 20);
		contentPanel.add(movieReleaseDate);
		movieReleaseDate.setColumns(10);
		
		movieDirector = new JTextField();
		movieDirector.setBounds(503, 197, 150, 20);
		contentPanel.add(movieDirector);
		movieDirector.setColumns(10);
		
		JLabel labelNewID = new JLabel("Azonosító");
		labelNewID.setBounds(52, 228, 75, 14);
		contentPanel.add(labelNewID);
		
		JLabel labelNewTitle = new JLabel("Cím");
		labelNewTitle.setBounds(166, 228, 35, 14);
		contentPanel.add(labelNewTitle);
		
		JLabel labelNewLength = new JLabel("Hossz (perc)");
		labelNewLength.setBounds(270, 228, 89, 14);
		contentPanel.add(labelNewLength);
		
		JLabel labelNewReleaseDate = new JLabel("Megjelenési idõ");
		labelNewReleaseDate.setBounds(382, 228, 109, 14);
		contentPanel.add(labelNewReleaseDate);
		
		JLabel labelNewDirector = new JLabel("Rendezõ");
		labelNewDirector.setBounds(553, 228, 75, 14);
		contentPanel.add(labelNewDirector);
		
		TableColumn tableColumn = null;
		for (int i = 0; i < 6; i++) {
			tableColumn = table.getColumnModel().getColumn(i);
			if (i == 0 || i == 1 || i == 3) {
				tableColumn.setPreferredWidth(30);
			} else {
				tableColumn.setPreferredWidth(100);
			}
		}
		
		table.setAutoCreateRowSorter(true);
		TableRowSorter<MovieTableModel> tableRowSorter= (TableRowSorter<MovieTableModel>)table.getRowSorter();
		tableRowSorter.setSortable(0, false);
	}
	
	public int modifiedDataCounter() {
		int counter = 0;
		if (checker.filled(movieID)) counter++;
		if (checker.filled(movieTitle)) counter++;
		if (checker.filled(movieLength)) counter++;
		if (checker.filled(movieReleaseDate)) counter++;
		if (checker.filled(movieDirector)) counter++;
		return counter;
	}
	
	public void reset(int i) {
		movieID.setText("");
		movieTitle.setText("");
		movieLength.setText("");
		movieReleaseDate.setText("");
		movieDirector.setText("");
		movieTableModel.setValueAt(false, i, 0);
	}
	
}
