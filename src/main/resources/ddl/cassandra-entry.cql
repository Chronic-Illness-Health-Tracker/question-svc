CREATE KEYSPACE IF NOT EXISTS questions
WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 3 };

USE questions;

CREATE TYPE answer (
  answer_id bigint,
  answer_text text,
  answer_value int,
);

CREATE TABLE questions_by_condition (
  question_id bigint,
  condition_id text,
  answer answer,
  created_at timestamp,
  PRIMARY KEY (condition_id, question_id)
) WITH CLUSTERING ORDER BY (question_id DESC);

CREATE TABLE user_responses_by_condition (
  response_id bigint,
  condition_id text,
  question_id bigint,
  answer answer,
  created_at timestamp,
  bucket int,
  PRIMARY KEY ((condition_id, bucket), response_id)
) WITH CLUSTERING ORDER BY (response_id DESC);