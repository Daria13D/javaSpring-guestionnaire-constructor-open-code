package ru.arharova.questionnarie_constructor.controllers;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import ru.arharova.questionnarie_constructor.models.Status;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDTO {

    private Boolean success;
    private String message;
    private LocalDateTime timestamp;
    private Enum status;
    private String cause;
    private String stacktrace;
    private Object data;
    public static ResultDTO SUCCESS_RESULT = new ResultDTO(true, "", LocalDateTime.now(), HttpStatus.OK, null, null, null);
    public static ResultDTO FAILURE_RESULT = new ResultDTO(false, "", LocalDateTime.now(), HttpStatus.BAD_REQUEST, null, null, null);
}
