package uz.pdp.creatingrestfullwebservicetaskscodingbat.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class QuestionDto {
    @NotNull(message = "Please fill the name of question!")
    private String name;
    @NotNull(message = "Please fill the question!")
    private String question;
    @NotNull(message = "Please fill the solution of question!")
    private String solution;
    @NotNull(message = "Please fill the method of question!")
    private String method;
    @NotNull(message = "Please fill the hint of question!")
    private String hint;
    private boolean hasStar;
    @NotNull(message = "Please choose the language to question!")
    private Integer languageId;
    @NotNull(message = "Please enter the example of question!")
    private String text;
}
