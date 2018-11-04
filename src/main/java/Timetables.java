import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Timetables {
    public List<Timetable> getTimetableList() {
        return timetableList;
    }

    public void setTimetableList(List<Timetable> timetableList) {
        this.timetableList = timetableList;
    }

    private List<Timetable> timetableList;
    public Timetables(){
        this.timetableList = new ArrayList<Timetable>();
        this.timetableList.add(new Timetable("8:15","9:45",Timetable.Type.LESSON));
        this.timetableList.add(new Timetable("9:45","10:00",Timetable.Type.BREAK));
        this.timetableList.add(new Timetable("10:00","11:30",Timetable.Type.LESSON));
        this.timetableList.add(new Timetable("11:30","11:45",Timetable.Type.BREAK));
        this.timetableList.add(new Timetable("11:45","13:15",Timetable.Type.LESSON));
        this.timetableList.add(new Timetable("13:15","13:45",Timetable.Type.BREAK));
        this.timetableList.add(new Timetable("13:45","15:15",Timetable.Type.LESSON));
        this.timetableList.add(new Timetable("15:15","15:30",Timetable.Type.BREAK));
        this.timetableList.add(new Timetable("15:30","16:45",Timetable.Type.LESSON));
        this.timetableList.add(new Timetable("16:45","17:00",Timetable.Type.BREAK));
        this.timetableList.add(new Timetable("17:00","18:30",Timetable.Type.LESSON));
    }
    public void Timetable(List<Timetable> timetableList){
        this.timetableList = timetableList;
    }

    public Integer timeToTheEnd(Date dNow){
        Integer left=-1;
        for(Timetable timetable:this.timetableList){
            Integer minutesLeft=timetable.toTheEnd(dNow);
            if(minutesLeft!=-1){
                left=minutesLeft;
            }
        }
        return left;
    }

    public String timeToTheEndString(Date dNow){
        String left="";
        for(Timetable timetable:this.timetableList){
            Integer minutesLeft=timetable.toTheEnd(dNow);
            if(minutesLeft!=-1){
                String name;
                name = (timetable.getType()==Timetable.Type.LESSON) ? "lekcji" : "przerwy";
                left=minutesLeft.toString()+" minut do końca "+ name ;
            }
        }
        return left;
    }
}
