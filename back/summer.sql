/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2018/7/19 9:44:59                            */
/*==============================================================*/


drop table if exists camera;

drop table if exists mapphoto;

drop table if exists user;

drop table if exists video;

/*==============================================================*/
/* Table: camera                                                */
/*==============================================================*/
create table camera
(
   camera_id            int not null,
   map_id               int,
   x                    int,
   y                    int,
   direction            float,
   angle                float,
   redius               int,
   primary key (camera_id)
);

/*==============================================================*/
/* Table: mapphoto                                              */
/*==============================================================*/
create table mapphoto
(
   map_name             varchar(1024),
   map_path             varchar(1024),
   width                int,
   height               int,
   map_id               int not null,
   user_id              int,
   primary key (map_id)
);

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   username             varchar(1024),
   password             varchar(1024),
   user_id              int not null,
   primary key (user_id)
);

/*==============================================================*/
/* Table: video                                                 */
/*==============================================================*/
create table video
(
   video_name           varchar(1024),
   video_path           varchar(1024),
   time                 date,
   video_id             int not null,
   camera_id            int,
   hour                 int,
   primary key (video_id)
);

alter table camera add constraint FK_Relationship_2 foreign key (map_id)
      references mapphoto (map_id) on delete restrict on update restrict;

alter table mapphoto add constraint FK_Relationship_1 foreign key (user_id)
      references user (user_id) on delete restrict on update restrict;

alter table video add constraint FK_Relationship_3 foreign key (camera_id)
      references camera (camera_id) on delete restrict on update restrict;

