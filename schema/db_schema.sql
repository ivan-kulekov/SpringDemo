-- Drop table from database;

DROP TABLE client

-- Table: client

-- DROP TABLE client;

CREATE TABLE client
(
  id integer NOT NULL,
  first_name character(80),
  middle_name character(80),
  last_name character(80),
  CONSTRAINT pk_id PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE client
  OWNER TO postgres;

  -- Insert into table client setted values;
  INSERT INTO client VALUES(?, ?, ?, ?);

  --Select all clients where id is the entered id;
  SELECT * FROM client WHERE id = ?;

  -- Select all clients from the table;
  select * from client;

  --Update client table all fields , where id is the entered id;
  UPDATE client SET id = ?, first_name = ?, middle_name = ?, last_name = ? WHERE id = ?;

  --Delete from client where id is the entered id;
  DELETE FROM client WHERE id = ? ;

  --Delete all data in the client table;
  delete from client;


