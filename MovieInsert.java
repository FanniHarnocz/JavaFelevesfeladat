import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/*
 * Movie insert window
 */
public class MovieInsert extends JDialog {
	
	private JTextField movieID;
	private JTextField movieTitle;
	private JTextField movieLength;
	private JTextField movieReleaseDate;
	private JTextField movieDirector;
	
	private DatabaseMethods databaseMethods = new DatabaseMethods();
	
	public MovieInsert(JFrame frame) {
		super(frame, "Új film", true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel labelID = new JLabel("Film azonosító");
		labelID.setBounds(51, 21, 126, 25);
		getContentPane().add(labelID);
		
		JLabel labelTitle = new JLabel("Cím");
		labelTitle.setBounds(51, 58, 126, 14);
		getContentPane().add(labelTitle);
		
		JLabel labelLength = new JLabel("Hossz (perc)");
		labelLength.setBounds(51, 89, 126, 14);
		getContentPane().add(labelLength);
		
		JLabel labelReleaseDate = new JLabel("Megjelenési idõ");
		labelReleaseDate.setBounds(51, 120, 126, 14);
		getContentPane().add(labelReleaseDate);
		
		JLabel labelDirector = new JLabel("Rendezõ");
		labelDirector.setBounds(51, 157, 142, 14);
		getContentPane().add(labelDirector);
		
		movieID = new JTextField();
		movieID.setBounds(228, 23, 172, 20);
		getContentPane().add(movieID);
		movieID.setColumns(10);
		
		movieTitle = new JTextField();
		movieTitle.setBounds(228, 55, 172, 20);
		getContentPane().add(movieTitle);
		movieTitle.setColumns(10);
		
		movieLength = new JTextField();
		movieLength.setColumns(10);
		movieLength.setBounds(228, 86, 172, 20);
		getContentPane().add(movieLength);
		
		movieReleaseDate = new JTextField();
		movieReleaseDate.setBounds(228, 117, 172, 20);
		getContentPane().add(movieReleaseDate);
		movieReleaseDate.setColumns(10);
		
		movieDirector = new JTextField();
		movieDirector.setColumns(10);
		movieDirector.setBounds(228, 154, 172, 20);
		getContentPane().add(movieDirector);
		
		JButton buttonAdd = new JButton("Hozzáadás");
		
		buttonAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Checker checker = new Checker();
				if (checker.goodInt(movieID, "Film azonosító"))
					if (checker.isFilled(movieTitle, "Cím"))
						if (checker.goodInt(movieLength, "Hossz (perc)"))
							if(checker.goodDate(movieReleaseDate, "Megjelenési idõ"))
								if (checker.isFilled(movieDirector, "Rendezõ")) {
						
				databaseMethods.connect();
				databaseMethods.insertData(getFieldText(movieID), getFieldText(movieTitle), getFieldText(movieLength), getFieldText(movieReleaseDate), getFieldText(movieDirector));
				databaseMethods.DisConnect();
				
				dispose();
				}
			}
		});
		
		buttonAdd.setBounds(157, 205, 107, 23);
		getContentPane().add(buttonAdd);

	}
	
	public String getFieldText (JTextField textField) {
		return textField.getText();
	}
}
