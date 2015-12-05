import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by sunny on 11/7/15.
 */
public class TestGenerator {
    public static void main(String[] args) {
        String fileName = "~/dyninst/testsuite-code/src/instruction/in.txt";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            String line;
            while((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            reader.close();
        } catch (Exception e) {
            //
        }
    }
}
