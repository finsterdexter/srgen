package cz.eowyn.srgen.io;

// Portions taken from pcgen

//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
//import java.io.InputStreamReader;
import java.io.Writer;

//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;

class FileAccess {

	private static int maxLength = -1;
	public FileAccess() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void encodeWrite(Writer output, String aString) {
		write(output, filterString(aString));
	}

	public static String filterString(String aString) {
		return aString;
	}
		
	public static void maxLength(int anInt) {
		maxLength = anInt;
	}

	public static void newLine(BufferedWriter output)
	{
		try
		{
			output.newLine();
		}
		catch (IOException exception)
		{
			//ShowMessageDelegate.showMessageDialog(exception.getMessage(), Constants.s_APPNAME, MessageType.ERROR);
			System.err.println(exception.getMessage());
		}
	}

	public static void write(Writer output, String aString) {
		if ((maxLength > 0) && (aString.length() > maxLength)) {
			aString = aString.substring(0, maxLength);
		}

		try {
			output.write(aString);
		}
		catch (IOException exception) {
			//ShowMessageDelegate.showMessageDialog(exception.getMessage(), Constants.s_APPNAME, MessageType.ERROR);
			System.err.println(exception.getMessage());
		}
	}
}
