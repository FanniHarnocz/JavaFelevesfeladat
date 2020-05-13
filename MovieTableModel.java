import javax.swing.table.DefaultTableModel;

/*
 * Table model methods
 */
public class MovieTableModel extends DefaultTableModel {
	
	public MovieTableModel (Object fildNames[], int rows) {
		super(fildNames, rows);
	}
	
	public boolean isCelleditable(int rows, int column) {
		if (column == 0) { return true ; }
		return false;
	}
	
	public Class<?> getColumnClass (int index) {
		if (index == 0) return (Boolean.class);
			else if (index == 1 || index == 4) return (Integer.class);
		return(String.class);
	}	
}
