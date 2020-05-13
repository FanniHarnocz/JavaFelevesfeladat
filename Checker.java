import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

/*
 * Field validation methods
 */
public class Checker {
	
	public boolean isFilled (JTextField textField, String fieldData) {
		String text = getJText (textField);
		if (text.length() > 0) return true;
		else {
			sendMessage( "Hiba: a(z) "+fieldData+" mezõ üres", 0);
			return false;
		}
	}
	
	public boolean goodInt (JTextField textField ,String fieldData) {
		String text = getJText (textField);
		boolean valid = isFilled(textField,fieldData);
		if(valid) try {
			Integer.parseInt(text);
		} catch(NumberFormatException e) {
			sendMessage("A(z) "+fieldData+" mezõben hibás számadat!",0);
			valid = false;
		}
		return valid;
	}
	
	public String getJText (JTextField textField) {
		return textField.getText();
	}
	
	public void sendMessage (String message, int messageType) {
		JOptionPane.showMessageDialog(null, message, "Program üzenet", messageType);
	}
	
	public boolean dateFormatChecker (String date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
		try {
			java.util.Date parsedDate = simpleDateFormat.parse (date);
			
			if (!simpleDateFormat.format(parsedDate).equals(date)) { return false; }
			return true;
		} catch(ParseException parseException) {
			return false;
		}
	}
	
	public boolean goodDate (JTextField textField, String fieldData) {
		String text = getJText(textField);
		boolean valid = isFilled (textField,fieldData);
		if (valid && dateFormatChecker(text)) return true;
		else {
			sendMessage ("A(z) " + fieldData + " Mezõben hibás a dátum", 0);
			return false;	
		}
	}
	
	public boolean filled (JTextField textField) {
		String text = getJText (textField);
		if (text.length() > 0) { return true; }
		else { return false; }
	}
	
	public int stringToInt (String stringInput) {
		int number = -1;
		try { number = Integer.valueOf(stringInput); }
		catch (NumberFormatException numberFormatException) {
			sendMessage("stringToInt: " + numberFormatException.getMessage(), 0);
		}
		return number;
	}
}

