create database communication_db;

create schema templates;

CREATE SEQUENCE templates.email_template_email_template_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

create table templates.email_template(
    email_template_id integer default nextval('templates.email_template_email_template_id_seq'::regclass) not null constraint email_template_pkey primary key,
    email_template_code integer not null,
    email_template_file_name varchar,
    email_template_subject varchar,
    email_template_text text
);

comment on column templates.email_template.email_template_id is '[emailTemplateId] ID шаблона email сообщения';
comment on column templates.email_template.email_template_code is '[emailTemplateCode] Код шаблона email сообщения';
comment on column templates.email_template.email_template_file_name is '[emailTemplateFileName] Название файла шаблона email сообщения (опционально)';
comment on column templates.email_template.email_template_subject is '[emailTemplateSubject] Тема письма для шаблона email сообщения (опционально - указывается при статической теме письма)';
comment on column templates.email_template.email_template_text is '[email_template_text] Текст шаблона (при динамической генерации использовать формат thymeleaf)';

create user tst with encrypted password '123';
grant all privileges on database communication_db to tst;