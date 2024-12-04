package dev.ramil21.web4back.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokensDto implements Serializable {

    public String jwtToken;
    public String refreshToken;

}
