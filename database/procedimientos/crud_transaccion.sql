drop procedure if exists sp_insertar_transaccion;
create procedure sp_insertar_transaccion(
in p_id_detalle int,
in p_anio int,
in p_mes int,
in p_tipo varchar(40),
in p_descripcion varchar(300),
in p_monto decimal(20,5),
in p_fecha timestamp,
in p_metodo_pago varchar(40),
in p_num_factura int,
in p_observaciones varchar(300),
in p_creado_por varchar(100)
)
begin
	if not exists(
		select 
			1
		from 	
			presupuesto_detalle
		where 
			id_detalle = p_id_detalle
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el detalle de presupuesto no existe';
	end if;

	if p_anio is null or p_anio <=0 then
		signal sqlstate '45000'
		set 
		message_text = 'El anio de la transaccion es obligatorio escribirlo y tiene que ser valido';
	end if;
	
	if p_mes < 1 or p_mes > 12 then
		signal sqlstate '45000'
		set 
		message_text = 'El mes de la transaccion es invalido, debe ser de 1 al 12';
	end if;
	
	if p_tipo is null or trim(p_tipo) = '' then
		signal sqlstate '45000'
		set 
		message_text = 'El tipo de la transaccion es obligatorio escribirlo';
	end if;

	if p_monto is null or p_monto <=0 then
		signal sqlstate '45000'
		set 
		message_text = 'El monto de la transaccion debe ser mayor a 0';
	end if;
	
	if p_fecha is null then
		signal sqlstate '45000'
		set 
		message_text = 'La fecha de la transaccion es obligatorio escribirlo';
	end if;

	if not exists(
		select 
			1
		from 
			presupuesto_detalle pd
		inner join subcategoria s
			on pd.id_subcategoria = s.id_subcategoria 
		inner join categoria c 
			on s.id_categoria = c.id_categoria 
		where pd.id_detalle = p_id_detalle
			and c.tipo_categoria = p_tipo
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el tipo de la transaccion no coincide con el tipo de categoria de la subcategoria';
	end if;
	
	if not exists(
		select 
			1
		from 
			presupuesto_detalle pd
		inner join presupuesto p
			on pd.id_presupuesto = p.id_presupuesto
		where pd.id_detalle = p_id_detalle
			and ((p_anio > p.anio_inicio or (p_anio = p.anio_inicio and p_mes >= p.mes_inicio))
			and (p_anio < p.anio_fin or (p_anio = p.anio_fin and p_mes <= p.mes_fin)))
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el anio y mes de la transaccion no estan dentro del periodo de presupuesto';
	end if;
		
	insert into transaccion(
		id_detalle,
		anio_transaccion,
		mes_transaccion,
		tipo_transaccion,
		descripcion,
		monto_transaccion,
		fecha_transaccion,
		metodo_pago,
		num_factura,
		observaciones,
		creado_por
	)
	values(
		p_id_detalle,
		p_anio,
		p_mes,
		p_tipo,
		p_descripcion,
		p_monto,
		p_fecha,
		p_metodo_pago,
		p_num_factura,
		p_observaciones,
		p_creado_por
	);
end;
call sp_insertar_transaccion(3, 2026, 4, 'gasto', 'Compra de supermercado', 1600, '2026-04-10', 'efectivo', 02, 'Se compro la comida del mes de abril', 'admin');
select * from transaccion;

drop procedure if exists sp_actualizar_transaccion;
create procedure sp_actualizar_transaccion(
in p_id_transaccion int,
in p_anio int,
in p_mes int,
in p_descripcion varchar(300),
in p_monto decimal(20,5),
in p_fecha timestamp,
in p_metodo_pago varchar(40),
in p_num_factura int,
in p_observaciones varchar(300),
in p_modificado_por varchar(100)
)
begin
	if not exists(
		select 
			1
		from 	
			transaccion
		where 
			id_transaccion = p_id_transaccion
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, la transaccion no existe';
	end if;

	if p_anio is null or p_anio <=0 then
		signal sqlstate '45000'
		set 
		message_text = 'El anio de la transaccion es obligatorio escribirlo y tiene que ser valido';
	end if;
	
	if p_mes < 1 or p_mes > 12 then
		signal sqlstate '45000'
		set 
		message_text = 'El mes de la transaccion es invalido, debe ser de 1 al 12';
	end if;

	if p_monto is null or p_monto <=0 then
		signal sqlstate '45000'
		set 
		message_text = 'El monto de la transaccion debe ser mayor a 0';
	end if;
	
	if p_fecha is null then
		signal sqlstate '45000'
		set 
		message_text = 'La fecha de la transaccion es obligatorio escribirlo';
	end if;
	
	update transaccion set
		anio_transaccion = p_anio,
		mes_transaccion = p_mes,
		descripcion = p_descripcion, 
		monto_transaccion = p_monto,
		fecha_transaccion = p_fecha,
		metodo_pago = p_metodo_pago,
		num_factura = p_num_factura,
		observaciones = p_observaciones,
		modificado_por = p_modificado_por
	where id_transaccion = p_id_transaccion;
end;
call sp_actualizar_transaccion(1, 2026, 4, 'Compra de super', 3250, '2026-04-10', 'tarjeta', 01, 'Se compro la comida del mes de abril', 'admin');
select * from transaccion;

drop procedure if exists sp_eliminar_transaccion;
create procedure sp_eliminar_transaccion(
in p_id_transaccion int
)
begin
	if not exists(
		select 
			1
		from 	
			transaccion
		where 
			id_transaccion = p_id_transaccion
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, la transaccion no existe';
	end if;
	
	delete from transaccion 
	where id_transaccion = p_id_transaccion;
end;
call sp_eliminar_transaccion(2);
select * from transaccion;

drop procedure if exists sp_consultar_transaccion;
create procedure sp_consultar_transaccion(
in p_id_transaccion int
)
begin
	if not exists(
		select 
			1
		from 	
			transaccion
		where 
			id_transaccion = p_id_transaccion
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, la transaccion no existe';
	end if;

	select 
		t.id_transaccion,
		t.anio_transaccion,
		t.mes_transaccion,
		t.tipo_transaccion,
		t.descripcion,
		t.monto_transaccion,
		t.fecha_transaccion,
		t.metodo_pago,
		t.num_factura,
		t.observaciones,
		t.creado_por,
		t.modificado_por,
		pd.id_detalle,
		p.id_presupuesto,
		p.nombre as nombre_presupuesto,
		u.id_usuario,
		u.primer_nombre,
		u.primer_apellido,
		s.id_subcategoria,
		s.nombre as nombre_subcategoria,
		c.id_categoria,
		c.nombre as nombre_categoria,
		c.tipo_categoria
	from  
		transaccion t
	inner join presupuesto_detalle pd 
		on t.id_detalle = pd.id_detalle
	inner join presupuesto p 
		on pd.id_presupuesto = p.id_presupuesto
	inner join usuario u
		on p.id_usuario = u.id_usuario
	inner join subcategoria s 
		on pd.id_subcategoria = s.id_subcategoria 
	inner join categoria c 
		on s.id_categoria = c.id_categoria
	where t.id_transaccion = p_id_transaccion;
end;
call sp_consultar_transaccion(1);

drop procedure if exists sp_listar_transacciones_presupuesto;
create procedure sp_listar_transacciones_presupuesto(
in p_id_presupuesto int, 
in p_anio int, 
in p_mes int, 
in p_tipo varchar(40)
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

	if p_mes is not null and (p_mes < 1 or p_mes > 12) then
		signal sqlstate '45000'
		set 
		message_text = 'El mes de la transaccion es invalido, debe ser de 1 al 12';
	end if;
	
	select  
		t.id_transaccion,
		t.anio_transaccion,
		t.mes_transaccion,
		t.tipo_transaccion,
		t.descripcion,
		t.monto_transaccion,
		t.fecha_transaccion,
		t.metodo_pago,
		t.num_factura,
		t.observaciones,
		t.creado_por,
		t.modificado_por,
		pd.id_presupuesto,
		s.id_subcategoria,
		s.nombre as nombre_subcategoria,
		c.id_categoria,
		c.nombre as nombre_categoria
	from  
		transaccion t
	inner join presupuesto_detalle pd 
		on t.id_detalle = pd.id_detalle
	inner join subcategoria s 
		on pd.id_subcategoria = s.id_subcategoria 
	inner join categoria c 
		on s.id_categoria = c.id_categoria
	where pd.id_presupuesto = p_id_presupuesto
		and (p_anio is null or t.anio_transaccion = p_anio)
		and (p_mes is null or t.mes_transaccion = p_mes)
		and (p_tipo is null or t.tipo_transaccion = p_tipo);
end;
call sp_listar_transacciones_presupuesto(1, 2026, 4, 'gasto');

drop procedure if exists sp_validar_detalle_transaccion_usuario;
create procedure sp_validar_detalle_transaccion_usuario(
in p_id_detalle int,
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

	select 
		case
			when exists(
				select 
					1
				from 
					presupuesto_detalle pd
				inner join presupuesto p
					on pd.id_presupuesto = p.id_presupuesto
				where 
					pd.id_detalle = p_id_detalle
					and p.id_usuario = p_id_usuario
			)
			then 1 else 0 end as pertenece;
end;

drop procedure if exists sp_validar_transaccion_usuario;
create procedure sp_validar_transaccion_usuario(
in p_id_transaccion int,
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

	select 
		case
			when exists(
				select 
					1
				from 
					transaccion t
				inner join presupuesto_detalle pd
					on t.id_detalle = pd.id_detalle
				inner join presupuesto p
					on pd.id_presupuesto = p.id_presupuesto
				where 
					t.id_transaccion = p_id_transaccion
					and p.id_usuario = p_id_usuario
			)
			then 1 else 0 end as pertenece;
end;