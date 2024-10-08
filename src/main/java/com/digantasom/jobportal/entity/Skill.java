package com.digantasom.jobportal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "skills")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Skill {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;
  private String experienceLevel;
  private String yearsOfExperience;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "job_seeker_profile")
  private JobSeekerProfile jobSeekerProfile;
}
