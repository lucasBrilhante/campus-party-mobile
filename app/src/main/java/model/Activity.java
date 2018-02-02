package model;

public class Activity {

    private String name;
    private String startDate;
    private String stage;

    public Activity(String name, String startDate, String endDate, String stage) {
        setName(name);
        setStartDate(startDate);
        setEndDate(endDate);
        setStage(stage);
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

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

}
