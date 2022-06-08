package uz.pdp.creatingrestfullwebservicetaskscodingbat.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "Please enter language name!")
    @Column(nullable = false)
    private String name;
    @ManyToMany
    private List<Category> categories;

    public Language(String name) {
        this.name = name;
    }
}
