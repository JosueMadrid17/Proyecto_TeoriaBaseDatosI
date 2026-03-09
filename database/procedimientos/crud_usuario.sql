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
	if p_primer_nombre is null or trim(p_primer_nombre) = '' then 
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el primer nombre es obligatorio escribirlo';
	end if;

	if p_primer_apellido is null or trim(p_primer_apellido) = '' then 
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el primer apellido es obligatorio escribirlo';
	end if;
	
	if p_email is null or trim(p_email) = '' then 
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el correo electronico es obligatorio escribirlo';
	end if;
	
	if exists(
		select 
			1
		from 	
			usuario
		where 
			correo_electronico = p_email
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, ese correo electronico ya existe';
	end if;

	if p_clave is null or trim(p_clave) = '' then 
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, la clave es obligatoria escribirla';
	end if;
	
	if p_salario_mensual is null or p_salario_mensual < 0 then 
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el salario no puede ser negativo';
	end if;
	
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
call sp_insertar_usuario('jorge', 'leonel', 'paz', 'morales', 'jorgepaz@gmail.com','jorge_1234',  20000, 'admin');
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
	if not exists(
		select 
			1
		from 	
			usuario
		where 
			id_usuario = p_id_usuario
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el usuario no existe';
	end if;
	
	if p_primer_nombre is null or trim(p_primer_nombre) = '' then 
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el primer nombre es obligatorio escribirlo';
	end if;

	if p_primer_apellido is null or trim(p_primer_apellido) = '' then 
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el primer apellido es obligatorio escribirlo';
	end if;
	
	if p_clave is null or trim(p_clave) = '' then 
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, la clave es obligatoria escribirla';
	end if;
	
	if p_salario_mensual is null or p_salario_mensual < 0 then 
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el salario no puede ser negativo';
	end if;

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
call sp_actualizar_usuario(5, 'jorge', 'fernando', 'paz', 'morales', 'jorge2026',  21000, 'admin');
select * from usuario;

drop procedure if exists sp_eliminar_usuario;
create procedure sp_eliminar_usuario(
in p_id_usuario int
)
begin
	if not exists(
		select 
			1
		from 	
			usuario
		where 
			id_usuario = p_id_usuario
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el usuario no existe';
	end if;
	
	if exists(
		select 
			1
		from 	
			usuario
		where 
			id_usuario = p_id_usuario
		and estado_usuario = 0
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el usuario ya esta inactivo';
	end if;

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
	if not exists(
		select 
			1
		from 	
			usuario
		where 
			id_usuario = p_id_usuario
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el usuario no existe';
	end if;

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