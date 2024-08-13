package com.digantasom.jobportal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users_type")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UsersType {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int userTypeId;

  private String userTypeName;

  @OneToMany(targetEntity = Users.class, mappedBy = "userTypeId", cascade = CascadeType.ALL)
  @ToString.Exclude
  private List<Users> users;
}
