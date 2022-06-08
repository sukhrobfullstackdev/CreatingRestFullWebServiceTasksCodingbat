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
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String question;
    @Column(nullable = false)
    private String solution;
    @Column(nullable = false)
    private String method;
    @Column(nullable = false)
    private String hint;
    private boolean hasStar = false;
    @ManyToOne(optional = false)
    private Language language;
    @OneToOne(optional = false)
    private Example example;

    public Question(String name, String question, String solution, String method, String hint, boolean hasStar, Language language, Example example) {
        this.name = name;
        this.question = question;
        this.solution = solution;
        this.method = method;
        this.hint = hint;
        this.hasStar = hasStar;
        this.language = language;
        this.example = example;
    }
}
