package com.jovana.nsibackend.model;

import com.jovana.nsibackend.util.Constants;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jovana on 05.11.2018
 */
@Entity
@Table(name = "polls")
public class Poll extends AuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = Constants.POLL_QUESTION_MAX_LENGTH)
    private String question;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Size(min = Constants.MIN_OPTIONS, max = Constants.MAX_OPTIONS)
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 30)
    private List<Option> options = new ArrayList<>();

    @NotNull
    private Instant expirationDateTime;

    public Poll() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public Instant getExpirationDateTime() {
        return expirationDateTime;
    }

    public void setExpirationDateTime(Instant expirationDateTime) {
        this.expirationDateTime = expirationDateTime;
    }

    public void addOption(Option option) {
        options.add(option);
        option.setPoll(this);
    }

    public void removeOption(Option option) {
        options.remove(option);
        option.setPoll(null);
    }
}
