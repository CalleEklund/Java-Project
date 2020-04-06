package classes;

public class Validator
{
    public boolean validateEmptyInput(String input) {
	return input.isEmpty();
    }

    public boolean validateIsString(String input) {
	try {
	    Integer.parseInt(input);
	    return false;
	} catch (NumberFormatException e) {
	    e.printStackTrace();
	    return true;
	}
    }

    public boolean validateEmail(String input) {
	String regex = "^[\\w-_.+]*[\\w-_.]@([\\w]+[.])+[\\w]+[\\w]$";
	return input.matches(regex);
    }
}
