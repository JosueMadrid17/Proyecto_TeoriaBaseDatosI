drop procedure if exists sp_insertar_obligacion_fija;
create procedure sp_insertar_obligacion_fija(
in p_id_subcategoria int,
in p_nombre varchar(100),
in p_descripcion varchar(300),
in p_monto decimal(20,5),
in p_dia_vencimiento int,
in p_fecha_inicio timestamp,
in p_fecha_fin timestamp,
in p_creado_por varchar(100)
)
begin
	if not exists(
		select 
			1
		from 	
			subcategoria
		where 
			id_subcategoria = p_id_subcategoria
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, la subcategoria no existe';
	end if;

	if p_nombre is null or trim(p_nombre) = '' then
		signal sqlstate '45000'
		set 
		message_text = 'El nombre de la obligacion es obligatorio escribirlo';
	end if;
	
	if p_monto is null or p_monto <=0 then
		signal sqlstate '45000'
		set 
		message_text = 'El monto de la obligacion debe ser mayor a 0';
	end if;
	
	if p_dia_vencimiento < 1 or p_dia_vencimiento > 31 then
		signal sqlstate '45000'
		set 
		message_text = 'El dia de vencimiento es invalido, debe ser del 1 al 31';
	end if;

	if p_fecha_fin is not null and p_fecha_fin < p_fecha_inicio then
		signal sqlstate '45000'
		set 
		message_text = 'La fecha fin no puede ser menor que la de inicio';
	end if;
	
	insert into obligacion_fija(
	id_subcategoria,
	nombre,
	descripcion_detallada,
	monto_fijo_mensual,
	dia_vencimiento,
	vigente,
	fecha_inicio,
	fecha_fin,
	creado_por
	)
	values(
	p_id_subcategoria,
	p_nombre,
	p_descripcion,
	p_monto,
	p_dia_vencimiento,
	1,
	p_fecha_inicio,
	p_fecha_fin,
    p_creado_por
	);
end;
call sp_insertar_obligacion_fija(1, 'Compra Comida', 'Compra comida en super mensual', 5000, 15, '2026-01-01', '2026-12-31', 'admin');
select * from obligacion_fija;

drop procedure if exists sp_actualizar_obligacion_fija;
create procedure sp_actualizar_obligacion_fija(
in p_id_obligacion int,
in p_nombre varchar(100),
in p_descripcion varchar(300),
in p_monto decimal(20,5),
in p_dia_vencimiento int,
in p_fecha_fin timestamp,
in p_modificado_por varchar(100)
)
begin
	if not exists(
		select 
			1
		from 	
			obligacion_fija
		where 
			id_obligacion = p_id_obligacion
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, la obligacion no existe';
	end if;

	if p_nombre is null or trim(p_nombre) = '' then
		signal sqlstate '45000'
		set 
		message_text = 'El nombre de la obligacion es obligatorio escribirlo';
	end if;
	
	if p_monto is null or p_monto <=0 then
		signal sqlstate '45000'
		set 
		message_text = 'El monto de la obligacion debe ser mayor a 0';
	end if;
	
	if p_dia_vencimiento < 1 or p_dia_vencimiento > 31 then
		signal sqlstate '45000'
		set 
		message_text = 'El dia de vencimiento es invalido, debe ser del 1 al 31';
	end if;
	
	update obligacion_fija set
		nombre = p_nombre,
		descripcion_detallada = p_descripcion,
		monto_fijo_mensual = p_monto,
		dia_vencimiento = p_dia_vencimiento,
		fecha_fin = p_fecha_fin,
		modificado_por = p_modificado_por
	where id_obligacion = p_id_obligacion;
end;
call sp_actualizar_obligacion_fija(1, 'Pago Energia enee', 'Pago de recibo de energia electrica mensual', 2800, 30, '2026-12-31', 'admin');
select * from obligacion_fija;

drop procedure if exists sp_eliminar_obligacion_fija;
create procedure sp_eliminar_obligacion_fija(
in p_id_obligacion int 
)
begin
	if not exists(
		select 
			1
		from 	
			obligacion_fija
		where 
			id_obligacion = p_id_obligacion
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, la obligacion no existe';
	end if;
	
	update obligacion_fija set 
		vigente = 0
	where id_obligacion = p_id_obligacion;
end;
call sp_eliminar_obligacion_fija(2);
select * from obligacion_fija;

drop procedure if exists sp_consultar_obligacion_fija;
create procedure sp_consultar_obligacion_fija(
in p_id_obligacion int 
)
begin
	if not exists(
		select 
			1
		from 	
			obligacion_fija
		where 
			id_obligacion = p_id_obligacion
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, la obligacion no existe';
	end if;

	select
		o.id_obligacion,
		o.id_subcategoria,
		o.nombre,
		o.descripcion_detallada,
		o.monto_fijo_mensual,
		o.dia_vencimiento,
		o.vigente,
		o.fecha_inicio,
		o.fecha_fin,
		o.creado_en,
		o.modificado_en,
		o.creado_por,
		o.modificado_por,
		s.nombre as nombre_subcategoria,
		s.descripcion_detallada as descripcion_subcategoria,
		s.activo,
		s.es_por_defecto
	from 
		obligacion_fija o 
	inner join subcategoria s 
		on o.id_subcategoria = s.id_subcategoria
	where o.id_obligacion = p_id_obligacion;
end;
call sp_consultar_obligacion_fija(1);

drop procedure if exists sp_listar_obligacion_fija;
create procedure sp_listar_obligaciones_usuario(
in p_id_usuario int, 
in p_activo tinyint
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

	select distinct 
		o.id_obligacion,
		o.id_subcategoria,
		o.nombre,
		o.descripcion_detallada,
		o.monto_fijo_mensual,
		o.dia_vencimiento,
		o.vigente,
		o.fecha_inicio,
		o.fecha_fin,
		o.creado_en,
		o.modificado_en,
		o.creado_por,
		o.modificado_por,
		s.nombre as nombre_subcategoria
	from 
		obligacion_fija o 
	inner join subcategoria s 
		on o.id_subcategoria = s.id_subcategoria
	inner join presupuesto_detalle pd 
		on s.id_subcategoria = pd.id_subcategoria
	inner join presupuesto p
		on pd.id_presupuesto = p.id_presupuesto
	where p.id_usuario = p_id_usuario
		and (p_activo is null or o.vigente = p_activo);
end;
call sp_listar_obligaciones_usuario(1, null);
