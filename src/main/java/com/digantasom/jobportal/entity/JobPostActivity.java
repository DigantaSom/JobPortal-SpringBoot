package com.digantasom.jobportal.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class JobPostActivity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer jobPostId;

  @ManyToOne
  @JoinColumn(name = "postedById", referencedColumnName = "userId")
  private Users postedById;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "jobLocationId", referencedColumnName = "Id")
  private JobLocation jobLocationId;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "jobCompanyId", referencedColumnName = "Id")
  private JobCompany jobCompanyId;

  @Transient
  private boolean isActive;

  @Transient
  private boolean isSaved;

  @Length(max = 10000)
  private String descriptionOfJob;

  private String jobType;
  private String salary;
  private String remote;

  @DateTimeFormat(pattern = "dd-MM-yyyy")
  private Date postedDate;

  private String jobTitle;
}