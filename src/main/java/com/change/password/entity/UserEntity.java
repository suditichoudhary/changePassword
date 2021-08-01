package com.change.password.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="user_details")
public class UserEntity {

    /* Created by suditi on 2021-08-01 */
    @Column(name = "user_id",nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name= "name")
    private String name;
    @Column(name= "user_email_address")
    private String email;
    @Column(name= "user_password")
    private String password;

}
