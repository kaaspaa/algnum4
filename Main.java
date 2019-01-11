import classes.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
    	Test t1 = new Test();
    	try {
    		t1.countAndWriteTimeOfExecition("Wyniki czasow");
		} catch (Exception e) {
    		System.out.println("błąd przy zapisie pliku");
		}
    }
}
