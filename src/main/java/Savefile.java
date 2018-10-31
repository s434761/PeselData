import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collections;

public class Savefile implements org.quartz.Job {
    public Savefile(){

    }
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            PrintWriter zapis = new PrintWriter("odp.txt");
            Collections.sort(PeselData.personList);
            for(Person person: PeselData.personList){
                zapis.println(person.toString());
            }
            zapis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //System.err.println(SQLchecker.myQueryList.size());
    }
}
