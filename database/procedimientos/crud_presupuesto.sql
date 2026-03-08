drop procedure if exists sp_insertar_presupuesto;
create procedure sp_insertar_presupuesto(
in p_id_usuario int,
in p_nombre varchar(100),
in p_anio_inicio int,
in p_mes_inicio int,
in p_anio_fin int,
in p_mes_fin int,
in p_creado_por varchar(100)
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

	if p_nombre is null or trim(p_nombre) = '' then
		signal sqlstate '45000'
		set 
		message_text = 'El nombre del presupuesto es obligatorio escribirlo';
	end if;
	
	if p_mes_inicio < 1 or p_mes_inicio > 12 then
		signal sqlstate '45000'
		set 
		message_text = 'El mes de inicio es invalido, debe ser de 1 al 12';
	end if;

	if p_mes_fin < 1 or p_mes_fin > 12 then
		signal sqlstate '45000'
		set 
		message_text = 'El mes de fin es invalido, debe ser de 1 al 12';
	end if;

	if (p_anio_inicio > p_anio_fin) 
	or (p_anio_inicio = p_anio_fin and p_mes_inicio > p_mes_fin)then
		signal sqlstate '45000'
		set 
		message_text = 'El periodo de inicio no puede ser mayor que el final';
	end if;

	insert into presupuesto(
	id_usuario,
	nombre,
	anio_inicio,
	mes_inicio,
	anio_fin,
	mes_fin,
	estado_presupuesto,
	creado_por
	)
	values(
	p_id_usuario,
	p_nombre,
	p_anio_inicio,
	p_mes_inicio,
	p_anio_fin,
	p_mes_fin,
	1,
    p_creado_por
	);
end;
call sp_insertar_presupuesto(3, 'Presupuesto Mayo', '2026', 5, 2026, 5, 'admin');
select * from presupuesto;

drop procedure if exists sp_actualizar_presupuesto;
create procedure sp_actualizar_presupuesto(
in p_id_presupuesto int,
in p_nombre varchar(100),
in p_anio_inicio int,
in p_mes_inicio int,
in p_anio_fin int,
in p_mes_fin int,
in p_modificado_por varchar(100)
)
begin
	if not exists(
		select 
			1
		from 	
			presupuesto
		where 
			id_presupuesto = p_id_presupuesto
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el presupuesto no existe';
	end if;

	if p_nombre is null or trim(p_nombre) = '' then
		signal sqlstate '45000'
		set 
		message_text = 'El nombre del presupuesto es obligatorio escribirlo';
	end if;
	
	if p_mes_inicio < 1 or p_mes_inicio > 12 then
		signal sqlstate '45000'
		set 
		message_text = 'El mes de inicio es invalido, debe ser de 1 al 12';
	end if;

	if p_mes_fin < 1 or p_mes_fin > 12 then
		signal sqlstate '45000'
		set 
		message_text = 'El mes de fin es invalido, debe ser de 1 al 12';
	end if;

	if (p_anio_inicio > p_anio_fin) 
	or (p_anio_inicio = p_anio_fin and p_mes_inicio > p_mes_fin)then
		signal sqlstate '45000'
		set 
		message_text = 'El periodo de inicio no puede ser mayor que el final';
	end if;

	update presupuesto set
		nombre = p_nombre,
		anio_inicio = p_anio_inicio,
		mes_inicio = p_mes_inicio,
		anio_fin = p_anio_fin,
		mes_fin = p_mes_fin,
		modificado_por = p_modificado_por
	where id_presupuesto = p_id_presupuesto;
end;
call sp_actualizar_presupuesto(1, 'Presupuesto 1', '2026', 2, 2026, 5, 'admin');
select * from presupuesto;

drop procedure if exists sp_eliminar_presupuesto;
create procedure sp_eliminar_presupuesto(
in p_id_presupuesto int 
)
begin
	if not exists(
		select 
			1
		from 	
			presupuesto
		where 
			id_presupuesto = p_id_presupuesto
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el presupuesto no existe';
	end if;
	
	if exists(
		select
			1
		from
			presupuesto_detalle pd 
		inner join transaccion t 
			on t.id_detalle = pd.id_detalle 
		where pd.id_presupuesto = p_id_presupuesto
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, no se puede eliminar el porque tiene transacciones asociadas';
	end if;
	
	delete from presupuesto 
	where id_presupuesto = p_id_presupuesto;
end;
call sp_eliminar_presupuesto(2);
select * from presupuesto;

drop procedure if exists sp_consultar_presupuesto;
create procedure sp_consultar_presupuesto(
in p_id_presupuesto int 
)
begin
	if not exists(
		select 
			1
		from 	
			presupuesto
		where 
			id_presupuesto = p_id_presupuesto
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el presupuesto no existe';
	end if;

	select 
		id_presupuesto,
		id_usuario,
		nombre,
		anio_inicio,
		mes_inicio,
		anio_fin,
		mes_fin,
		total_ingresos,
		total_gastos,
		total_ahorros,
		fecha_hora_creacion,
		estado_presupuesto,
		creado_en,
		modificado_en,
		creado_por,
		modificado_por
	from 
		presupuesto
	where id_presupuesto = p_id_presupuesto;
end;
call sp_consultar_presupuesto(1);

drop procedure if exists sp_listar_presupuestos_usuario;
create procedure sp_listar_presupuestos_usuario(
in p_id_usuario int,
in p_estado tinyint
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

	select 
		id_presupuesto,
		id_usuario,
		nombre,
		anio_inicio,
		mes_inicio,
		anio_fin,
		mes_fin,
		total_ingresos,
		total_gastos,
		total_ahorros,
		fecha_hora_creacion,
		estado_presupuesto
	from 
		presupuesto
	where id_usuario = p_id_usuario
		and (p_estado is null or estado_presupuesto = p_estado);
end;
call sp_listar_presupuestos_usuario(1,1);




