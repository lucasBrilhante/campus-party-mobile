package model;

public class Activity {

    private String name;
    private String startDate;

    public Activity(String name, String startDate, String endDate) {
        setName(name);
        setStartDate(startDate);
        setEndDate(endDate);
    }

    private String endDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
