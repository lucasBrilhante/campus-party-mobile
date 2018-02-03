package model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Activity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String slug;
    private String description;
    private String startDate;
    private String endDate;
    private String type;
    private String stage;
    private boolean isOnAgenda;

    public Activity(String name, String slug, String description, String startDate, String endDate,
                    String type, String stage) {
        setName(name);
        setSlug(slug);
        setDescription(description);
        setStartDate(startDate);
        setEndDate(endDate);
        setType(type);
        setStage(stage);
        setIsOnAgenda(false);
    }

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

    public boolean getIsOnAgenda() {
        return isOnAgenda;
    }

    public void setIsOnAgenda(boolean isOnAgenda) {
        this.isOnAgenda = isOnAgenda;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
