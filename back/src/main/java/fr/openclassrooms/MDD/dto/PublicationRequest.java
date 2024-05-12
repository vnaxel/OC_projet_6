package fr.openclassrooms.MDD.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicationRequest {

    @NotNull
    @NotBlank
    public String title;

    @NotNull
    @NotBlank
    public String content;

    @NotNull
    public String topic;
}
