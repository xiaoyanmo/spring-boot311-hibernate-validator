package com.example.demo.entities;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @NotBlank(message = "name,不允许为空")
    @NotNull(message = "name,不允许为空")
    private String name;
    @Min(value = 1, message = "age,至少为1")
    @Max(value = 200, message = "age,不能超过200")
    private int age;
    @NotNull(message = "birthday,不允许为空")
    private Date birthday;
}
