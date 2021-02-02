package com.paulfolio.jobsearch.models;

import java.time.LocalDate;

public class Update implements Comparable<Update> {
    private String description;
    private LocalDate updateTime = LocalDate.now();

    public Update() {
        this.description = "Initial application";
    }

    public Update(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDate updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public int compareTo(Update o) {
        return this.getUpdateTime().compareTo(o.getUpdateTime());
    }
}
