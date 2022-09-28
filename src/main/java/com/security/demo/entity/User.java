package com.security.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "Member")
public class User {

    @Id @GeneratedValue
    private Long id;
    private String password;
    private String name;
    @Enumerated(EnumType.STRING)
    private Role role;
}
