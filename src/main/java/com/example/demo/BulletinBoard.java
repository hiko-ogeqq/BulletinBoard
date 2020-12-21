package com.example.demo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Entity
@Data
@Table(name = "bulletinboard")
public class BulletinBoard {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  private int id;
  @Column(nullable = false)
  @NotEmpty
  private String title;
  @Column
  private String content;
  @Column
  private String createUser;
  @Column
  private Date createdDate;
  @Column
  private int division;
}