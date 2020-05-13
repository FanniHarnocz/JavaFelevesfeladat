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

/*
 * Movie list window
 */
public  class MovieList extends JDialog {
	
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private MovieTableModel movieTableModel;
	
	public MovieList(JFrame frame, MovieTableModel movieTableModel) {
		super(frame, "Filmek listája", true);
		this.movieTableModel = movieTableModel;
		
		setBounds(100, 100, 679, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5,5,5,5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		//Add close button
		contentPanel.setLayout(null); {
			JButton buttonClose = new JButton("Bezárás");
			buttonClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent argument0) {
					dispose();
				}
			});
			buttonClose.setBounds(282, 210, 89, 23);
			contentPanel.add(buttonClose);
		}
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 643, 183);
		contentPanel.add(scrollPane);
		
		table = new JTable(movieTableModel);
		scrollPane.setViewportView(table);
		TableColumn tableColumn = null;
		
		for (int i=0; i<6; i++) {
			tableColumn = table.getColumnModel().getColumn(i);
			
			if (i==0 || i==1 || i==3) {
				tableColumn.setPreferredWidth(30);
			} else {
				tableColumn.setPreferredWidth(100);
			}
		}
		
		table.setAutoCreateRowSorter(true);
		TableRowSorter<MovieTableModel> tableRowSorter= (TableRowSorter<MovieTableModel>)table.getRowSorter();
		tableRowSorter.setSortable(0, false);	
	}
}
