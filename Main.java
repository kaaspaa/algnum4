import classes.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
    	Test test = new Test();

    	try {
    		test.countAndWriteTimeOfExecition();
		} catch (Exception e) {
    		System.out.println("błąd przy zapisie pliku czasow");
		}
		try {
			test.countApproxAndWrite();
		} catch (Exception e){
			System.out.println("blad w approx zapisie");
		}

		//test.countResultsSparse(8);

    }
}
