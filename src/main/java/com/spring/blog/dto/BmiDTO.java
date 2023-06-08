package com.spring.blog.dto;

import lombok.*;

@Getter @Setter @AllArgsConstructor
@NoArgsConstructor @ToString @Builder
public class BmiDTO {
    private double height;
    private double weight;
}
