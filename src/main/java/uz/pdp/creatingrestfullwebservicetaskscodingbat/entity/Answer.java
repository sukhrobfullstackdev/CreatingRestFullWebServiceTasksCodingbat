package uz.pdp.creatingrestfullwebservicetaskscodingbat.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String text;
    @ManyToOne(optional = false)
    private Question question;
    private boolean isCorrect = false;
    @ManyToOne
    private User user;

    public Answer(String text, Question question, boolean isCorrect, User user) {
        this.text = text;
        this.question = question;
        this.isCorrect = isCorrect;
        this.user = user;
    }
}
