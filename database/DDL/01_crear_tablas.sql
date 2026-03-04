create database if not exists presupuesto_personal
default character set utf8mb4
default collate utf8mb4_0900_ai_ci;

use presupuesto_personal;

create table usuario (
  id_usuario int primary key,
  primer_nombre varchar(100),
  segundo_nombre varchar(100),
  primer_apellido varchar(100),
  segundo_apellido varchar(100),
  correo_electronico varchar(100) not null unique,
  clave varchar(100) not null,
  salario_mensual decimal(20,5),
  estado_usuario tinyint(1) not null default 1,

  creado_en timestamp not null default current_timestamp,
  modificado_en timestamp not null default current_timestamp on update current_timestamp,
  creado_por varchar(100),
  modificado_por varchar(100)
);

create table presupuesto (
  id_presupuesto int primary key,
  id_usuario int not null,
  nombre varchar(100) not null,
  anio_inicio int not null,
  mes_inicio int not null,
  anio_fin int not null,
  mes_fin int not null,
  total_ingresos decimal(20,5) default 0,
  total_gastos decimal(20,5) default 0,
  total_ahorros decimal(20,5) default 0,
  fecha_hora_creacion timestamp not null default current_timestamp,
  estado_presupuesto tinyint(1) not null default 1,

  creado_en timestamp not null default current_timestamp,
  modificado_en timestamp not null default current_timestamp on update current_timestamp,
  creado_por varchar(100),
  modificado_por varchar(100),

  foreign key (id_usuario)
  references usuario(id_usuario)
  on update cascade
  on delete restrict
);

create table categoria (
  id_categoria int primary key,
  nombre varchar(100) not null,
  descripcion_detallada varchar(100),
  tipo_categoria varchar(40) not null,

  creado_en timestamp not null default current_timestamp,
  modificado_en timestamp not null default current_timestamp on update current_timestamp,
  creado_por varchar(100),
  modificado_por varchar(100)
);

create table subcategoria (
  id_subcategoria int primary key,
  id_categoria int not null,
  nombre varchar(100) not null,
  descripcion_detallada varchar(300),
  activo tinyint(1) not null default 1,
  es_por_defecto tinyint(1) not null default 0,

  creado_en timestamp not null default current_timestamp,
  modificado_en timestamp not null default current_timestamp on update current_timestamp,
  creado_por varchar(100),
  modificado_por varchar(100),

  foreign key (id_categoria)
  references categoria(id_categoria)
  on update cascade
  on delete restrict
);

create table presupuesto_detalle (
  id_detalle int primary key,
  id_presupuesto int not null,
  id_subcategoria int not null,
  monto_mensual_asignado decimal(20,5) not null,
  observaciones varchar(300),

  creado_en timestamp not null default current_timestamp,
  modificado_en timestamp not null default current_timestamp on update current_timestamp,
  creado_por varchar(100),
  modificado_por varchar(100),

  foreign key (id_presupuesto)
  references presupuesto(id_presupuesto)
  on update cascade
  on delete restrict,

  foreign key (id_subcategoria)
  references subcategoria(id_subcategoria)
  on update cascade
  on delete restrict
);

create table obligacion_fija (
  id_obligacion int primary key,
  id_subcategoria int not null,
  nombre varchar(100) not null,
  descripcion_detallada varchar(300),
  monto_fijo_mensual decimal(20,5) not null,
  dia_vencimiento int not null,
  vigente tinyint(1) not null default 1,
  fecha_inicio datetime not null,
  fecha_fin datetime,

  creado_en timestamp not null default current_timestamp,
  modificado_en timestamp not null default current_timestamp on update current_timestamp,
  creado_por varchar(100),
  modificado_por varchar(100),

  foreign key (id_subcategoria)
  references subcategoria(id_subcategoria)
  on update cascade
  on delete restrict
);

create table transaccion (
  id_transaccion int primary key,
  id_detalle int not null,
  anio_transaccion int not null,
  mes_transaccion int not null,
  tipo_transaccion varchar(40) not null,
  descripcion varchar(300),
  monto_transaccion decimal(20,5) not null,
  fecha_transaccion datetime not null,
  metodo_pago varchar(40),
  num_factura int,
  observaciones varchar(300),
  fecha_hora_registro timestamp not null default current_timestamp,

  creado_en timestamp not null default current_timestamp,
  modificado_en timestamp not null default current_timestamp on update current_timestamp,
  creado_por varchar(100),
  modificado_por varchar(100),

  foreign key (id_detalle)
  references presupuesto_detalle(id_detalle)
  on update cascade
  on delete restrict
);

create table obligacionfija_transaccion (
  id_transaccion int not null,
  id_obligacion int not null,

  primary key (id_transaccion, id_obligacion),

  foreign key (id_transaccion)
  references transaccion(id_transaccion)
  on update cascade
  on delete cascade,

  foreign key (id_obligacion)
  references obligacion_fija(id_obligacion)
  on update cascade
  on delete cascade
);