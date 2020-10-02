import java.io.*;

public class HistoryTest {
	public static void main(String[] args) {


		UserHistory uh = new UserHistory(new File("user_dictionary.txt"));

		for(String s : uh.nextWords("", 20)) {
			if(s!=null)System.out.println(s);
		}

		try {
			uh.addToFile(new File("new_dict.txt"));
		} catch(IOException ioe) {
			ioe.printStackTrace();
			System.exit(1);
		}

	}
}