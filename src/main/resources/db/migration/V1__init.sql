CREATE TABLE judges (
    id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email varchar(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name varchar(255) NOT NULL,
    is_active boolean NOT NULL DEFAULT TRUE,
    created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_judges_email ON judges(email);

CREATE TABLE admins (
    id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    login varchar(255) NOT NULL UNIQUE,
    password varchar(255) NOT NULL,
    is_active boolean NOT NULL DEFAULT TRUE,
    created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_admin_login ON admins(login);

CREATE TABLE bhm_amounts (
    id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    amount bigint NOT NULL,
    is_active boolean NOT NULL DEFAULT TRUE,
    created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE external_services (
    id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    login varchar(255) NOT NULL UNIQUE,
    password varchar(255) NOT NULL,
    webhook_url varchar(255) NOT NULL,
    webhook_secret varchar(255) NOT NULL,
    is_active boolean NOT NULL DEFAULT TRUE,
    created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_external_services_login ON external_services(login);

CREATE TABLE users (
    id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    pinfl varchar(255) NOT NULL UNIQUE,
    full_name varchar(255) NOT NULL,
    age int NOT NULL,
    created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_users_pinfl ON users(pinfl);

CREATE TYPE offense_status AS ENUM (
    'PENDING',
    'PROCESSING_AI_DECISION',
    'DRAFT_PENALTY',
    'COMPLETED'
);

CREATE TABLE offenses (
    id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    external_id bigint NOT NULL UNIQUE,
    external_service_id bigint NOT NULL,
    judge_id bigint NOT NULL,
    user_id bigint NOT NULL,
    offense_location varchar(255) NOT NULL,
    status offense_status NOT NULL DEFAULT 'PENDING',
    offender_explanation text,
    description text NOT NULL,
    protocol_number varchar(255) NOT NULL UNIQUE,
    court_case_number varchar(255) NOT NULL UNIQUE,
    code_article_number integer NOT NULL,
    offense_date_time timestamptz NOT NULL,
    created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_offense_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE RESTRICT,
    CONSTRAINT fk_offense_external_service FOREIGN KEY (external_service_id) REFERENCES external_services (id) ON DELETE RESTRICT,
    CONSTRAINT fk_penalties_judge FOREIGN KEY (judge_id) REFERENCES judges (id) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE INDEX idx_offenses_external_id ON offenses(external_id);

CREATE INDEX idx_offenses_judge_id ON offenses(judge_id);

CREATE INDEX idx_offenses_user_id ON offenses(user_id);

CREATE INDEX idx_court_case_number ON offenses(court_case_number);


CREATE TYPE penalty_type AS ENUM (
    'FINE',
    'CONFISCATION',
    'PAID_WITHDRAWAL',
    'DEPRIVATION_OF_DRIVING_RIGHT',
    'DEPRIVATION_OF_OTHER_RIGHT',
    'ADMINISTRATIVE_ARREST',
    'EXPULSION',
    'COMPULSORY_WORKS'
);

CREATE TYPE penalty_status AS ENUM (
   'DRAFT',
   'CONFIRMED',
   'SENT',
   'FAILED'
);

CREATE TABLE penalties (
    id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    offense_id bigint NOT NULL UNIQUE,
    type penalty_type NOT NULL,
    status penalty_status NOT NULL,
    bhm_amount_at_time bigint NOT NULL,
    bhm_multiplier numeric(10, 2) NOT NULL,
    qualification varchar(255) NOT NULL,
    due_date timestamp with time zone NOT NULL,
    court_decision_text text NOT NULL,
    deprivation_duration_months integer NOT NULL,
    created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_penalties_offense FOREIGN KEY (offense_id) REFERENCES offenses (id) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE INDEX idx_penalties_offense_id ON penalties(offense_id);


CREATE TYPE user_role AS ENUM (
    'JUDGE',
    'ADMIN',
    'SERVICE'
);

CREATE TABLE refresh_tokens (
    id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    token text NOT NULL,
    expiry_date timestamptz NOT NULL,
    subject varchar(255) NOT NULL,
    user_role user_role NOT NULL
);

CREATE INDEX idx_refresh_tokens_token ON refresh_tokens (token);


CREATE EXTENSION IF NOT EXISTS vector SCHEMA public;
CREATE EXTENSION IF NOT EXISTS hstore SCHEMA public;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp" SCHEMA public;

CREATE TABLE public.vector_store (
	id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
	content text,
	metadata json,
	embedding vector(768)
);

CREATE INDEX ON vector_store USING HNSW (embedding vector_cosine_ops);

