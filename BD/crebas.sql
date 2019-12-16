/*==============================================================*/
/* DBMS name:      Sybase SQL Anywhere 12                       */
/* Created on:     16/12/2019 16:26:23                          */
/*==============================================================*/


if exists(select 1 from sys.sysforeignkey where role='FK_CLIENTE_RELATIONS_VENDEDOR') then
    alter table CLIENTE
       delete foreign key FK_CLIENTE_RELATIONS_VENDEDOR
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_PERSONA_INHERITAN_USUARIO') then
    alter table PERSONA
       delete foreign key FK_PERSONA_INHERITAN_USUARIO
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_PERSONA_INHERITAN_VENDEDOR') then
    alter table PERSONA
       delete foreign key FK_PERSONA_INHERITAN_VENDEDOR
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_PRODUCTO_RELATIONS_CLIENTE') then
    alter table PRODUCTO
       delete foreign key FK_PRODUCTO_RELATIONS_CLIENTE
end if;

drop index if exists CLIENTE.RELATIONSHIP_1_FK;

drop index if exists CLIENTE.CLIENTE_PK;

drop table if exists CLIENTE;

drop index if exists PERSONA.INHERITANCE_2_FK;

drop index if exists PERSONA.INHERITANCE_1_FK;

drop index if exists PERSONA.PERSONA_PK;

drop table if exists PERSONA;

drop index if exists PRODUCTO.RELATIONSHIP_2_FK;

drop index if exists PRODUCTO.PRODUCTO_PK;

drop table if exists PRODUCTO;

drop index if exists USUARIO.USUARIO_PK;

drop table if exists USUARIO;

drop index if exists VENDEDOR.VENDEDOR_PK;

drop table if exists VENDEDOR;

if exists(select 1 from sys.syssequence s
   where sequence_name='S_CLIENTE') then
      drop sequence S_CLIENTE
end if;

if exists(select 1 from sys.syssequence s
   where sequence_name='S_PERSONA') then
      drop sequence S_PERSONA
end if;

if exists(select 1 from sys.syssequence s
   where sequence_name='S_PRODUCTO') then
      drop sequence S_PRODUCTO
end if;

if exists(select 1 from sys.syssequence s
   where sequence_name='S_USUARIO') then
      drop sequence S_USUARIO
end if;

create sequence S_CLIENTE;

create sequence S_PERSONA;

create sequence S_PRODUCTO;

create sequence S_USUARIO;

/*==============================================================*/
/* Table: CLIENTE                                               */
/*==============================================================*/
create table CLIENTE 
(
   CLI_CODIGO           integer                        not null default (S_CLIENTE.nextval),
   VEN_CODIGO           char(10)                       not null,
   CLI_NOMBRE           varchar(64)                    not null,
   CLI_DIRECCION        varchar(64)                    not null,
   CLI_TELEFONO         varchar(16)                    not null,
   CLI_EMAIL            varchar(64)                    not null,
   constraint PK_CLIENTE primary key (CLI_CODIGO)
);

/*==============================================================*/
/* Index: CLIENTE_PK                                            */
/*==============================================================*/
create unique index CLIENTE_PK on CLIENTE (
CLI_CODIGO ASC
);

/*==============================================================*/
/* Index: RELATIONSHIP_1_FK                                     */
/*==============================================================*/
create index RELATIONSHIP_1_FK on CLIENTE (
VEN_CODIGO ASC
);

/*==============================================================*/
/* Table: PERSONA                                               */
/*==============================================================*/
create table PERSONA 
(
   USU_CODIGO           integer                        not null,
   VEN_CODIGO           char(10)                       not null,
   PER_CODIGO           integer                        not null default (S_PERSONA.nextval),
   VEN_DIRECCION        char(10)                       not null,
   VEN_RUTA             char(10)                       not null,
   USU_EMAIL            varchar(64)                    not null,
   USU_CLAVE            varchar(16)                    not null,
   PER_NOMBRE           varchar(64)                    null,
   PER_APELLIDO         varchar(64)                    null,
   PER_TELEFONO         varchar(16)                    null,
   PER_SEXO             varchar(16)                    null,
   constraint PK_PERSONA primary key (USU_CODIGO, VEN_CODIGO, PER_CODIGO)
);

/*==============================================================*/
/* Index: PERSONA_PK                                            */
/*==============================================================*/
create unique index PERSONA_PK on PERSONA (
USU_CODIGO ASC,
VEN_CODIGO ASC,
PER_CODIGO ASC
);

/*==============================================================*/
/* Index: INHERITANCE_1_FK                                      */
/*==============================================================*/
create index INHERITANCE_1_FK on PERSONA (
USU_CODIGO ASC
);

/*==============================================================*/
/* Index: INHERITANCE_2_FK                                      */
/*==============================================================*/
create index INHERITANCE_2_FK on PERSONA (
VEN_CODIGO ASC
);

/*==============================================================*/
/* Table: PRODUCTO                                              */
/*==============================================================*/
create table PRODUCTO 
(
   PRO_CODIGO           integer                        not null default (S_PRODUCTO.nextval),
   CLI_CODIGO           integer                        not null,
   PRO_NOMBRE           varchar(64)                    null,
   PRO_PRECIO           numeric(10,2)                  null,
   PRO_CATEGORIA        varchar(32)                    null,
   PRO_STOCK            integer                        not null,
   constraint PK_PRODUCTO primary key (PRO_CODIGO)
);

/*==============================================================*/
/* Index: PRODUCTO_PK                                           */
/*==============================================================*/
create unique index PRODUCTO_PK on PRODUCTO (
PRO_CODIGO ASC
);

/*==============================================================*/
/* Index: RELATIONSHIP_2_FK                                     */
/*==============================================================*/
create index RELATIONSHIP_2_FK on PRODUCTO (
CLI_CODIGO ASC
);

/*==============================================================*/
/* Table: USUARIO                                               */
/*==============================================================*/
create table USUARIO 
(
   USU_CODIGO           integer                        not null default (S_USUARIO.nextval),
   USU_EMAIL            varchar(64)                    not null,
   USU_CLAVE            varchar(16)                    not null,
   constraint PK_USUARIO primary key (USU_CODIGO)
);

/*==============================================================*/
/* Index: USUARIO_PK                                            */
/*==============================================================*/
create unique index USUARIO_PK on USUARIO (
USU_CODIGO ASC
);

/*==============================================================*/
/* Table: VENDEDOR                                              */
/*==============================================================*/
create table VENDEDOR 
(
   VEN_CODIGO           char(10)                       not null,
   VEN_DIRECCION        char(10)                       not null,
   VEN_RUTA             char(10)                       not null,
   constraint PK_VENDEDOR primary key (VEN_CODIGO)
);

/*==============================================================*/
/* Index: VENDEDOR_PK                                           */
/*==============================================================*/
create unique index VENDEDOR_PK on VENDEDOR (
VEN_CODIGO ASC
);

alter table CLIENTE
   add constraint FK_CLIENTE_RELATIONS_VENDEDOR foreign key (VEN_CODIGO)
      references VENDEDOR (VEN_CODIGO)
      on update restrict
      on delete restrict;

alter table PERSONA
   add constraint FK_PERSONA_INHERITAN_USUARIO foreign key (USU_CODIGO)
      references USUARIO (USU_CODIGO)
      on update restrict
      on delete restrict;

alter table PERSONA
   add constraint FK_PERSONA_INHERITAN_VENDEDOR foreign key (VEN_CODIGO)
      references VENDEDOR (VEN_CODIGO)
      on update restrict
      on delete restrict;

alter table PRODUCTO
   add constraint FK_PRODUCTO_RELATIONS_CLIENTE foreign key (CLI_CODIGO)
      references CLIENTE (CLI_CODIGO)
      on update restrict
      on delete restrict;

