package com.digantasom.jobportal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "job_seeker_profile")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class JobSeekerProfile {
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
  private String workAuthorization;
  private String employmentType;
  private String resume;

  @Column(nullable = true, length = 64)
  private String profilePhoto;

  @OneToMany(targetEntity = Skill.class, cascade = CascadeType.ALL, mappedBy = "jobSeekerProfile")
  private List<Skill> skills;

  public JobSeekerProfile(Users user) {
    this.userId = user;
  }
}
