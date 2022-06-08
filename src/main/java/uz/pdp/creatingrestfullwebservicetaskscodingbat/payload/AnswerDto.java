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
public class AnswerDto {
    @NotNull(message = "Please fill the text of answer!")
    private String text;
    @NotNull(message = "Please choose the question of answer!")
    private Integer questionId;
    private boolean isCorrect;
    @NotNull(message = "Please choose the user of answer!")
    private Integer userId;
}
