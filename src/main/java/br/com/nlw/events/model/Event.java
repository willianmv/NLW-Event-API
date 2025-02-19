package br.com.nlw.events.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "tbl_event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Integer eventId;

    @NotBlank(message = "Título do evento é obrigatório.")
    @Size(min = 2, max = 255, message = "Título do evento deve ter entre 2 e 255 caracteres.")
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "pretty_name", nullable = false, unique = true)
    private String prettyName;

    @NotBlank(message = "Local do evento é obrigatório.")
    @Size(min = 2, max = 255, message = "Local do evento deve ter entre 2 e 255 caracteres.")
    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "price", nullable = false)
    private Double price;

    @NotNull(message = "Data obrigatória")
    @Future(message = "A data de início deve ser uma data futura")
    @Column(name = "start_date")
    private LocalDate startDate;

    @NotNull(message = "Data obrigatória")
    @Future(message = "A data de fim deve ser uma data futura")
    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "start_time")
    private LocalTime starTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrettyName() {
        return prettyName;
    }

    public void setPrettyName(String prettyName) {
        this.prettyName = prettyName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalTime getStarTime() {
        return starTime;
    }

    public void setStarTime(LocalTime starTime) {
        this.starTime = starTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
