package re.working.reworking;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteFile {
    public static void writeFileScores(String data){
        try{
            FileWriter stream = new FileWriter("src\\Scores.txt",true);
            BufferedWriter out = new BufferedWriter(stream);
            out.write(dateNow() + data + "\n");
            out.close();
        }catch (IOException e){
            System.err.println("Error while writing to file: " + e.getMessage());
        }
    }
    private static java.util.Date dateNow(){
        return new java.util.Date();
    }
}
