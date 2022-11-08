BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "question" (
	"id"	INTEGER NOT NULL UNIQUE,
	"question"	TEXT NOT NULL,
	"answer_a"	TEXT NOT NULL,
	"answer_b"	TEXT NOT NULL,
	"answer_c"	TEXT NOT NULL,
	"answer_d"	TEXT NOT NULL,
	"correct_answer"	TEXT NOT NULL,
	"difficulty"	INTEGER NOT NULL DEFAULT 1,
	PRIMARY KEY("id" AUTOINCREMENT)
);

INSERT INTO "question" VALUES (1,'cau hoi ','dap an a','dap an b','dap an c ','dap an d','dap an a',1);
INSERT INTO "question" VALUES (2,'testb','test a','test b','test c ','test d','test b',1);
INSERT INTO "question" VALUES (3,'testc','test a','test b','test c ','test d','test c',1);
INSERT INTO "question" VALUES (4,'testd','test a','test b','test c ','test d','test d',1);
INSERT INTO "question" VALUES (5,'testa','test a','test b','test c ','test d','test a',1);
INSERT INTO "question" VALUES (6,'testb','test a','test b','test c ','test d','test b',2);
INSERT INTO "question" VALUES (7,'testb','test a','test b','test c ','test d','test c',2);
INSERT INTO "question" VALUES (8,'testc','test a','test b','test c ','test d','test d',2);
INSERT INTO "question" VALUES (9,'testd','test a','test b','test c ','test d','test d',2);
INSERT INTO "question" VALUES (10,'testa','test a','test b','test c ','test d','test a',2);
INSERT INTO "question" VALUES (11,'testb','test a','test b','test c ','test d','test b',3);
INSERT INTO "question" VALUES (12,'testc','test a','test b','test c ','test d','test c',3);
INSERT INTO "question" VALUES (13,'testd','test a','test b','test c ','test d','test d',3);
INSERT INTO "question" VALUES (14,'testa','test a','test b','test c','test d','test a',3);
INSERT INTO "question" VALUES (15,'testb','test a','test b','test c','test d','test b',3);
COMMIT;
