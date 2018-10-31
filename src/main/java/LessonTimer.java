import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public class LessonTimer implements org.quartz.Job{
    public LessonTimer(){}

    public void execute(JobExecutionContext context) throws JobExecutionException {
        Date dNow = new Date();
        Timetables timetables = new Timetables();
        String leftTime=timetables.timeToTheEndString(dNow);
        if(leftTime.length()>0) System.out.println(leftTime);
    }
}
