CREATE SEQUENCE mdm.et_et_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

create table mdm.email_template
(
    email_template_id        integer default nextval('mdm.et_et_id_seq'::regclass) not null
        constraint email_template_pkey
            primary key,
    email_template_code      integer                                               not null,
    email_template_file_name varchar,
    email_template_subject   varchar,
    email_template_string    text
);

comment on column mdm.email_template.email_template_id is '[emailTemplateId] ID шаблона email сообщения';

comment on column mdm.email_template.email_template_code is '[emailTemplateCode] Код шаблона email сообщения';

comment on column mdm.email_template.email_template_file_name is '[emailTemplateFileName] Название файла шаблона email сообщения';

comment on column mdm.email_template.email_template_subject is '[emailTemplateSubject] Тема письма для шаблона email сообщения';

