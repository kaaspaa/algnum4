import classes.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
    	Test test = new Test();

    	/*
    	try {
    		test.countAndWriteTimeOfExecition();
		} catch (Exception e) {
    		System.out.println("błąd przy zapisie pliku czasow");
		}
*/

		try {
			test.countAndWriteEverything();
		} catch (Exception e){
			System.out.println("blad w approx zapisie");
		}

    }
}
