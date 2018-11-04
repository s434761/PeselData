import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Timetable {
    public enum Type {
        LESSON, BREAK
    }

    public Type getType() {
        return type;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    private Type type;
    private Date startTime;
    private Date endTime;

    private Boolean isBetween(Date candidate, Date start, Date end){
        try {
            SimpleDateFormat parser = new SimpleDateFormat("HH:mm:ss");
            candidate = parser.parse(parser.format(candidate));
            start = parser.parse(parser.format(start));
            end = parser.parse(parser.format(end));


            return !candidate.after(end) && !candidate.before(start);

        }
        catch (ParseException a){
            return false;
        }
    }

    private Integer toMinutes(Date date){
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
        String[] splited = parser.format(date).split(":");
        Integer hh = Integer.parseInt(splited[0]);
        Integer mm = Integer.parseInt(splited[1]);
        return mm+hh*60;
    }

    public Integer toTheEnd(Date candidate)
    {
        if(isBetween(candidate, this.startTime, this.endTime)){
            return toMinutes(this.endTime)-toMinutes(candidate);
        }
        else
            return -1;
    }

    public Timetable(String startTime, String endTime, Type type){
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
        try {
            this.startTime = parser.parse(startTime);
            this.endTime = parser.parse(endTime);
            this.type = type;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
