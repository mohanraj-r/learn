CREATE TABLE game_throws (
    id serial,
    player varchar(100) NOT NULL,
    roll integer,
	CONSTRAINT pk_game_throws PRIMARY KEY (id)
);

/** INSERT INTO game_throws(player,roll)
SELECT f.player, 1 + (random()*4)::integer
FROM (values ('regina'), ('leo'), ('sonia'), ('alex')) AS f(player)
    , generate_series(1,4) As roll; **/
    
    
INSERT INTO game_throws (id, player, roll) VALUES (1, 'regina', 3);
INSERT INTO game_throws (id, player, roll) VALUES (2, 'leo', 2);
INSERT INTO game_throws (id, player, roll) VALUES (3, 'sonia', 1);
INSERT INTO game_throws (id, player, roll) VALUES (4, 'alex', 3);
INSERT INTO game_throws (id, player, roll) VALUES (5, 'regina', 2);
INSERT INTO game_throws (id, player, roll) VALUES (6, 'leo', 4);
INSERT INTO game_throws (id, player, roll) VALUES (7, 'sonia', 3);
INSERT INTO game_throws (id, player, roll) VALUES (8, 'alex', 3);
INSERT INTO game_throws (id, player, roll) VALUES (9, 'regina', 1);
INSERT INTO game_throws (id, player, roll) VALUES (10, 'leo', 2);
INSERT INTO game_throws (id, player, roll) VALUES (11, 'sonia', 3);
INSERT INTO game_throws (id, player, roll) VALUES (12, 'alex', 3);
INSERT INTO game_throws (id, player, roll) VALUES (13, 'regina', 3);
INSERT INTO game_throws (id, player, roll) VALUES (14, 'leo', 4);
INSERT INTO game_throws (id, player, roll) VALUES (15, 'sonia', 1);
INSERT INTO game_throws (id, player, roll) VALUES (16, 'alex', 3);

SELECT id, player, roll, 
    run_begin(roll,3) OVER (PARTITION BY player ORDER BY id) AS rb
FROM game_throws
ORDER BY player, id;