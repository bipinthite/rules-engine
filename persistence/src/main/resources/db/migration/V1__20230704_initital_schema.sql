CREATE OR REPLACE FUNCTION trigger_set_timestamp()
RETURNS TRIGGER AS $$
BEGIN
  NEW.updated_at = NOW();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DO $$ BEGIN
    CREATE TYPE rules_resource_type AS ENUM ('DRL', 'GDRL', 'RDRL', 'XDRL', 'DSL', 'DSLR', 'RDSLR',
    	'DRF', 'BPMN2', 'CMMN', 'DTABLE', 'PKG', 'BRL', 'CHANGE_SET', 'XSD', 'PMML', 'DESCR',
    	'JAVA', 'PROPERTIES', 'SCARD', 'BAYES', 'TDRL', 'TEMPLATE', 'DRT', 'GDST', 'SCGD',
    	'SOLVER', 'DMN', 'FEEL');
EXCEPTION
    WHEN duplicate_object THEN null;
END $$;

CREATE TABLE IF NOT EXISTS knowledge_bases (
	id SERIAL NOT NULL,
	"name" VARCHAR(255) NOT NULL,
	packages VARCHAR(255) NOT NULL,
	created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
	updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
	CONSTRAINT knowledge_bases_pkey PRIMARY KEY (id),
	CONSTRAINT knowledge_bases_name_uk UNIQUE (name)
);

CREATE TRIGGER set_timestamp
BEFORE
UPDATE
    ON
    knowledge_bases
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

CREATE TABLE IF NOT EXISTS rules (
	id SERIAL NOT NULL,
	"name" VARCHAR(255) NOT NULL,
	package_name VARCHAR(255) NOT NULL,
	resource_contents VARCHAR(4000) NULL,
	resource_data oid NULL,
	resource_name VARCHAR(255) NOT NULL,
	resource_type rules_resource_type NOT NULL,
	"version" VARCHAR(10) NOT NULL,
	knowledge_base_id INTEGER NULL,
	created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
	CONSTRAINT rules_pkey PRIMARY KEY (id),
	CONSTRAINT knowledge_base_id_fkey FOREIGN KEY(knowledge_base_id) REFERENCES knowledge_bases(id),
	CONSTRAINT rules_name_uk UNIQUE (name)
);

CREATE TRIGGER set_timestamp
BEFORE
UPDATE
    ON
    rules
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();
