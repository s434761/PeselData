import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class PeselData {
    public static List<Person> personList = new ArrayList<Person>(){};

    private static void updateaddToPersonList(Person o) {
        Boolean was = false;
        for(Person person: personList){
            if(person.getPesel().contains(o.getPesel())){
                person.setCity(o.getCity());
                person.setName(o.getName());
                was = true;
            }
        }

        if(!was) personList.add(o);
    }

    public static String checkPesel(String pesel){
        pesel.length();
        if(pesel.length()!=11){ return "pesel błedny, musi mieć 11 znaków"; }
        int sumapesel = Character.getNumericValue(pesel.charAt(0)) + 3*Character.getNumericValue(pesel.charAt(1)) + 7*Character.getNumericValue(pesel.charAt(2))
                + 9*Character.getNumericValue(pesel.charAt(3)) + Character.getNumericValue(pesel.charAt(4)) + 3*Character.getNumericValue(pesel.charAt(5))
                + 7*Character.getNumericValue(pesel.charAt(6)) + 9*Character.getNumericValue(pesel.charAt(7)) + Character.getNumericValue(pesel.charAt(8)) +3*Character.getNumericValue(pesel.charAt(9))
                + Character.getNumericValue(pesel.charAt(10));
        if(sumapesel%10!=0) { return "pesel błedny"; }
        return "";
    }

    public static  void main(String[] args) {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            JobDetail saveFileJob = newJob(Savefile.class)
                    .withIdentity("saveFileJob")
                    .build();

            JobDetail lessonTimerJob = newJob(LessonTimer.class)
                    .withIdentity("lessonTimerJob")
                    .build();

            CronTrigger saveFileTrigger = newTrigger()
                    .withIdentity("saveFileTrigger","group1")
                    .withSchedule(cronSchedule("0/30 * * * * ?"))
                    .build();

            Date dNow = new Date();
            String startDateStr = new SimpleDateFormat("yyyy-MM-dd").format(dNow);
            Date lessonStartDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(startDateStr+" 8:15");
            Date lessonEndDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(startDateStr+" 18:45");

            CronTrigger lessonTimerTrigger = newTrigger()
                    .withIdentity("lessonTimerTrigger","group1")
                    .startAt(lessonStartDate)
                    .withSchedule(cronSchedule("0 * 8-18 ? * MON-FRI"))
                    .endAt(lessonEndDate)
                    .build();

            scheduler.scheduleJob(saveFileJob,saveFileTrigger);
            scheduler.scheduleJob(lessonTimerJob,lessonTimerTrigger);

            scheduler.start();

            Scanner odczyt = new Scanner(System.in);
            String stringToExit = ":exit";
            System.out.println("Aby zakończyć wpisz "+stringToExit);
            String wpisane = "";
            while (!wpisane.equals(stringToExit)) {
                String city, name, pesel;

                System.out.println("Wpisz miasto");
                wpisane = odczyt.nextLine();
                if (wpisane.equals(stringToExit)) break;
                city = wpisane;
                System.out.println("Wpisz imie i nazwisko ");
                wpisane = odczyt.nextLine();
                if (wpisane.equals(stringToExit)) break;
                name = wpisane;

                System.out.println("Wpisz pesel ");
                wpisane = odczyt.nextLine();
                if (wpisane.equals(stringToExit)) break;
                pesel = wpisane;

                String wynikQuery = checkPesel(pesel);
                if (wynikQuery.length() > 0) {
                    System.out.println(wynikQuery);
                    System.out.println("Nie dodano osoby !");
                } else {
                    updateaddToPersonList(new Person(city, name, pesel));
                    System.out.println("Pomyślnie dodano osobe " + name);
                }
            }

            scheduler.triggerJob(saveFileJob.getKey());
            scheduler.shutdown();

        } catch (SchedulerException se){
            se.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}
