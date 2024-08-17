package com.digantasom.jobportal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recruiter_profile")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RecruiterProfile {
  @Id
  private int userAccountId;

  @OneToOne
  @JoinColumn(name = "user_account_id")
  @MapsId
  private Users userId;

  private String firstName;
  private String lastName;
  private String city;
  private String state;
  private String country;
  private String company;

  @Column(nullable = true, length = 64)
  private String profilePhoto;

  public RecruiterProfile(Users user) {
    this.userId = user;
  }
}
