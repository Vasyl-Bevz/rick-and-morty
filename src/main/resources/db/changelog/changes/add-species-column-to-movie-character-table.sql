--liquibase formatted sql
--changeset <postgres>:<add-species-column-to-movie-character-table>
ALTER TABLE public.movie_character ADD species character varying(256);

--rollback ALTER TABLE DROP COLUMN species;