package ru.antonovmikhail.book.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static ru.antonovmikhail.util.Constants.DATE_TIME_PATTERN;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDtoIn {
    @org.hibernate.validator.constraints.UUID
    private UUID id;
    @NotNull
    @org.hibernate.validator.constraints.UUID
    @JsonProperty(value = "author_uuid")
    private UUID authorId;
    private String title;
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    private LocalDateTime publishDate;
}
