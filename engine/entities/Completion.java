package engine.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Completion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @NotNull
    private LocalDateTime completedAt;

    @NotNull
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private int quizId;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int userId;

    public Completion(@NotNull int quizId, int userId) {
        completedAt = LocalDateTime.now();
        this.quizId = quizId;
        this.userId = userId;
    }

    public Completion() {
        completedAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
