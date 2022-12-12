package com.example.bookManagement.model;

// Importing required classes
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


// Annotations
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  private String title;
  private String authors;
  private float average_rating;
  private String isbn;
  private String language_code;
  private int num_pages;
  private String publication_date;
  private String publisher;
  private String imageURL;
}