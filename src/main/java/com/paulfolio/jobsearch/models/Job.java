package com.paulfolio.jobsearch.models;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;


@Repository
@Document(collection = "jobs")
public class Job  implements Comparable<Job>{

    @ApiModelProperty(accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    @Id
    private String id;
    @ApiModelProperty(required = true)
    private String title;
    @ApiModelProperty(required = true)
    private String link;
    private Boolean isActive;
    @ApiModelProperty(required = false)
    private Set<Update> updates = new HashSet<Update>();

    public Job() {
        super();
    }

    public Job(String title, String link) {
        this.title = title;
        this.link = link;
        this.updates.add(new Update("Applied"));
        this.isActive = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Set<Update> getUpdates() {
        return updates;
    }

    public void setUpdates(Set<Update> updates) {
        this.updates = updates;
    }

    public Job addUpdate(String description) {
        Update update =  new Update(description);
        this.updates.add(update);
        return this;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public int compareTo(Job j) {
        return this.getUpdates().iterator().next().compareTo(j.getUpdates().iterator().next());
    }
}
