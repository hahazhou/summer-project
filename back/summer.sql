/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2018/7/4 15:50:34                            */
/*==============================================================*/


drop table if exists camera;

drop table if exists map;

drop table if exists user;

drop table if exists video;

/*==============================================================*/
/* Table: camera                                                */
/*==============================================================*/
create table camera
(
   camera_id            int not null,
   map_id               int,
   camera_name          varchar(1024),
   x                    int,
   y                    int,
   r                    int,
   primary key (camera_id)
);

/*==============================================================*/
/* Table: map                                                   */
/*==============================================================*/
create table map
(
   map_id               int not null,
   user_id              int,
   map_path             varchar(1024),
   map_name             varchar(1024),
   h                    int,
   w                    int,
   primary key (map_id)
);

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   user_id              int not null,
   username             varchar(1024),
   password             varchar(1024),
   role                 bool,
   primary key (user_id)
);

/*==============================================================*/
/* Table: video                                                 */
/*==============================================================*/
create table video
(
   video_id             int not null,
   camera_id            int,
   video_name           varchar(1024),
   video_path           varchar(1024),
   time                 date,
   primary key (video_id)
);

alter table camera add constraint FK_mc foreign key (map_id)
      references map (map_id) on delete restrict on update restrict;

alter table map add constraint FK_Relationship_3 foreign key (user_id)
      references user (user_id) on delete restrict on update restrict;

alter table video add constraint FK_cv foreign key (camera_id)
      references camera (camera_id) on delete restrict on update restrict;

