CREATE TABLE test_scores (
    student character varying(100) NOT NULL,
    subject character varying(100) NOT NULL,
    score numeric(5),
    test_date date NOT NULL,
	CONSTRAINT pk_test_scores PRIMARY KEY (student, subject, test_date)
);
INSERT INTO test_scores VALUES ('regina', 'algebra', 68, '2014-01-15');
INSERT INTO test_scores VALUES ('regina', 'physics', 83, '2014-01-15');
INSERT INTO test_scores VALUES ('regina', 'chemistry', 71, '2014-01-15');
INSERT INTO test_scores VALUES ('regina', 'calculus', 68, '2014-01-15');
INSERT INTO test_scores VALUES ('regina', 'scheme', 90, '2015-01-15');
INSERT INTO test_scores VALUES ('leo', 'algebra', 84, '2014-01-15');
INSERT INTO test_scores VALUES ('leo', 'physics', 72, '2014-01-15');
INSERT INTO test_scores VALUES ('leo', 'chemistry', 71, '2014-01-15');
INSERT INTO test_scores VALUES ('leo', 'calculus', 69, '2014-01-15');
INSERT INTO test_scores VALUES ('alex', 'algebra', 74, '2014-01-15');
INSERT INTO test_scores VALUES ('alex', 'physics', 83, '2014-01-15');
INSERT INTO test_scores VALUES ('alex', 'chemistry', 80, '2014-01-15');
INSERT INTO test_scores VALUES ('alex', 'calculus', 70, '2014-01-15');
INSERT INTO test_scores VALUES ('sonia', 'algebra', 75, '2014-01-15');
INSERT INTO test_scores VALUES ('sonia', 'physics', 72, '2014-01-15');
INSERT INTO test_scores VALUES ('sonia', 'chemistry', 82, '2014-01-15');
INSERT INTO test_scores VALUES ('sonia', 'calculus', 65, '2014-01-15');
INSERT INTO test_scores VALUES ('regina', 'algebra', 77, '2014-01-29');
INSERT INTO test_scores VALUES ('regina', 'physics', 85, '2014-01-29');
INSERT INTO test_scores VALUES ('regina', 'chemistry', 76, '2014-01-29');
INSERT INTO test_scores VALUES ('regina', 'calculus', 61, '2014-01-29');
INSERT INTO test_scores VALUES ('leo', 'algebra', 80, '2014-01-29');
INSERT INTO test_scores VALUES ('leo', 'physics', 72, '2014-01-29');
INSERT INTO test_scores VALUES ('leo', 'chemistry', 80, '2014-01-29');
INSERT INTO test_scores VALUES ('leo', 'calculus', 62, '2014-01-29');
INSERT INTO test_scores VALUES ('alex', 'algebra', 74, '2014-01-29');
INSERT INTO test_scores VALUES ('alex', 'physics', 79, '2014-01-29');
INSERT INTO test_scores VALUES ('alex', 'chemistry', 84, '2014-01-29');
INSERT INTO test_scores VALUES ('alex', 'calculus', 77, '2014-01-29');
INSERT INTO test_scores VALUES ('sonia', 'algebra', 78, '2014-01-29');
INSERT INTO test_scores VALUES ('sonia', 'physics', 72, '2014-01-29');
INSERT INTO test_scores VALUES ('sonia', 'chemistry', 86, '2014-01-29');
INSERT INTO test_scores VALUES ('sonia', 'calculus', 70, '2014-01-29');
