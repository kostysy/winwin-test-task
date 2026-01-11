package org.example.authapi.entities;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "logs")
public class ProcessingLog {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "log_seq"
    )
    @SequenceGenerator(
            name = "log_seq",
            sequenceName = "log_sequence",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String inputText;
    private String transformedText;
    private Date timestamp;

    public ProcessingLog() {}

    public ProcessingLog(User user, String inputText, String transformedText) {
        this.user = user;
        this.inputText = inputText;
        this.transformedText = transformedText;
        this.timestamp = new Date(new java.util.Date().getTime());
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
