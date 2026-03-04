drop procedure if exists sp_insertar_usuario;
create procedure sp_insertar_usuario(
in p_primer_nombre varchar(100),
in p_segundo_nombre varchar(100),
in p_primer_apellido varchar(100),
in p_segundo_apellido varchar(100),
in p_email varchar(100), 
in p_clave varchar(100),
in p_salario_mensual decimal(20,5),
in p_creado_por varchar(100)
)
begin
	insert into usuario(
	primer_nombre,
	segundo_nombre,
	primer_apellido,
	segundo_apellido,
	correo_electronico,
	clave,
	salario_mensual,
	estado_usuario,
	creado_por
	)
	values(
	p_primer_nombre,
	p_segundo_nombre,
    p_primer_apellido,
    p_segundo_apellido,
    p_email,
    p_clave,
    p_salario_mensual,
    1,
    p_creado_por
	);
end;
call sp_insertar_usuario('marco', 'andres', 'rodas', 'perez', 'andres@gmail.com','and_1234',  15000, 'admin');
select * from usuario;

drop procedure if exists sp_actualizar_usuario;
create procedure sp_actualizar_usuario(
in p_id_usuario int,
in p_primer_nombre varchar(100),
in p_segundo_nombre varchar(100),
in p_primer_apellido varchar(100),
in p_segundo_apellido varchar(100),
in p_clave varchar(100),
in p_salario_mensual decimal(20,5),
in p_modificado_por varchar(100)
)
begin
	update usuario set 
		primer_nombre = p_primer_nombre,
		segundo_nombre = p_segundo_nombre,
		primer_apellido = p_primer_apellido,
		segundo_apellido = p_segundo_apellido,
		clave = p_clave,
		salario_mensual = p_salario_mensual,
		modificado_por = p_modificado_por
	where id_usuario = p_id_usuario;
end;
call sp_actualizar_usuario(1, 'josue', 'fernando', 'madrid', 'paz', 'jos_2026',  14000, 'admin');
select * from usuario;

drop procedure if exists sp_eliminar_usuario;
create procedure sp_eliminar_usuario(
in p_id_usuario int
)
begin
	update usuario set 
		estado_usuario = 0
	where id_usuario = p_id_usuario;
end;
call sp_eliminar_usuario(3);
select * from usuario;

drop procedure if exists sp_consultar_usuario;
create procedure sp_consultar_usuario(
in p_id_usuario int
)
begin
	select *  
	from usuario
	where id_usuario = p_id_usuario;
end;
call sp_consultar_usuario(1);

drop procedure if exists sp_listar_usuarios;
create procedure sp_listar_usuarios()
begin
	select *  
	from usuario;
end;
call sp_listar_usuarios();

