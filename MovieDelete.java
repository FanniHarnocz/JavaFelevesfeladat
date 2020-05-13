import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/*
 * Movie delete window
 */
public  class MovieDelete extends JDialog {
	
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private MovieTableModel movieTableMovie;
	private Checker checker = new Checker();
	private DatabaseMethods databaseMethods = new DatabaseMethods(); 
 
	public MovieDelete(JFrame frame, MovieTableModel movieTableModel) {
		super(frame, "Film t�rl�se", true);
		this.movieTableMovie = movieTableModel;
		
		setBounds(100, 100, 679, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5,5,5,5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		//Add close button
		contentPanel.setLayout(null); {
			JButton buttonClose = new JButton("Bez�r�s");
			buttonClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent argument0) {
					dispose();
				}
			});
			buttonClose.setBounds(280, 210, 89, 23);
			contentPanel.add(buttonClose);
		}
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 643, 183);
		contentPanel.add(scrollPane);
		
		table = new JTable(movieTableMovie);
		scrollPane.setViewportView(table);
		
		JButton buttonDelete = new JButton("T�rl�s");
		buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int database = 0, sign = 0, counter = 0;
				for (counter = 0; counter < movieTableMovie.getRowCount(); counter++) {
					if ((Boolean)movieTableMovie.getValueAt(counter,0)) {database++; sign = counter;}
				}
				if (database == 0) checker.sendMessage("Nincs kijel�lve t�rlend� film!", 0);
				if (database > 1) checker.sendMessage("T�bb film van kijel�lve! (Egyszerre csak egy t�r�lhet�)", 0); 
				if (database == 1) {
					String movieID = movieTableMovie.getValueAt(sign, 1).toString();
					databaseMethods.connect();
					databaseMethods.deleteData(movieID);
					databaseMethods.DisConnect();
					movieTableMovie.removeRow(sign);
					checker.sendMessage("Film t�r�lve",1);
				}
			}
		});
		buttonDelete.setBounds(20, 210, 116, 23);
		contentPanel.add(buttonDelete);
		
		TableColumn tableColumn= null;
		for (int i = 0; i < 6; i++) {
			tableColumn = table.getColumnModel().getColumn(i);
			
			if (i == 0 || i == 1 || i == 4) {
				tableColumn.setPreferredWidth(30);
			} else { 
				tableColumn.setPreferredWidth(100);
			}
			
			table.setAutoCreateRowSorter(true);
			TableRowSorter<MovieTableModel> tableRowSorter= (TableRowSorter< MovieTableModel>)table.getRowSorter();
			tableRowSorter.setSortable(0, false);	
		}
	}
}
