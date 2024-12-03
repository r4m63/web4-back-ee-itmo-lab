package dev.ramil21.web4back.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokensDto {

    public String jwtToken;
    public String refreshToken;

}
