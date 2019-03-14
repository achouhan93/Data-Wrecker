package com.data.columnStatistics.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "questions")
public class QuestionsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "question_id")
	private int questionId;

	@Column(name = "question_category_id")
	private int questionCategoryId;

	@Column(name = "question_description")
	private String questionDescription;

	@Column(name = "question_posted_by_user_id")
	private int questionPostedByUserId;

	@Column(name = "question_creation_date_time")
	private Timestamp questionCreationDateTime;

	@Column(name = "question_is_active")
	private boolean isQuestionActive;
	
	@Column(name = "discussion_is_active")
	private boolean discussionThreadActive;
}
