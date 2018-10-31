public class Person implements Comparable<Person>{
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    String city;
    String name;
    String pesel;

    public Person(String city, String name, String pesel){
        this.city = city;
        this.name = name;
        this.pesel = pesel;
    }


    @Override
    public String toString() { return city+" "+ name +" "+ pesel; }

    @Override
    public int compareTo(Person o) {
        return city.compareTo(o.city);
    }
}
