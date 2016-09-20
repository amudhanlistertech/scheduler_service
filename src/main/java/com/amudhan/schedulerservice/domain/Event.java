package com.amudhan.schedulerservice.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Event.
 */
@Entity
@Table(name = "event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "worker")
    private String worker;

    @Column(name = "role")
    private String role;

    @Column(name = "team")
    private String team;

    @Column(name = "location")
    private String location;

    @Column(name = "status")
    private String status;

    @Column(name = "outcome")
    private String outcome;

    @Column(name = "start")
    private LocalDate start;

    @Column(name = "end")
    private LocalDate end;

    @OneToOne
    @JoinColumn(unique = true)
    private Patient patient;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorker() {
        return worker;
    }

    public Event worker(String worker) {
        this.worker = worker;
        return this;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getRole() {
        return role;
    }

    public Event role(String role) {
        this.role = role;
        return this;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTeam() {
        return team;
    }

    public Event team(String team) {
        this.team = team;
        return this;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getLocation() {
        return location;
    }

    public Event location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public Event status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOutcome() {
        return outcome;
    }

    public Event outcome(String outcome) {
        this.outcome = outcome;
        return this;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public LocalDate getStart() {
        return start;
    }

    public Event start(LocalDate start) {
        this.start = start;
        return this;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public Event end(LocalDate end) {
        this.end = end;
        return this;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public Patient getPatient() {
        return patient;
    }

    public Event patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Event event = (Event) o;
        if(event.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Event{" +
            "id=" + id +
            ", worker='" + worker + "'" +
            ", role='" + role + "'" +
            ", team='" + team + "'" +
            ", location='" + location + "'" +
            ", status='" + status + "'" +
            ", outcome='" + outcome + "'" +
            ", start='" + start + "'" +
            ", end='" + end + "'" +
            '}';
    }
}
