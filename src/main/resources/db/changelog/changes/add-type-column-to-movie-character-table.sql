--liquibase formatted sql
--changeset <postgres>:<add-type-column-to-movie-character-table>
ALTER TABLE public.movie_character ADD type character varying(256);

--rollback ALTER TABLE DROP COLUMN type;