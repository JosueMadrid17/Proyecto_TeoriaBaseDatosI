drop procedure if exists sp_insertar_presupuesto_detalle;
create procedure sp_insertar_presupuesto_detalle(
in p_id_presupuesto int,
in p_id_subcategoria int,
in p_monto_mensual decimal(20,5),
in p_observaciones varchar(300),
in p_creado_por varchar(100)
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
	
	if p_monto_mensual is null or p_monto_mensual <=0 then 
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el monto asignado debe ser mayor a 0';
	end if;
	
	if exists(
		select 
			1
		from 	
			presupuesto_detalle
		where 
			id_presupuesto = p_id_presupuesto
		and id_subcategoria = p_id_subcategoria 
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, ya existe un detalle para esa subcategoria en este presupuesto';
	end if;
	
	insert into presupuesto_detalle(
		id_presupuesto,
		id_subcategoria,
		monto_mensual_asignado,
		observaciones,
		creado_por
	)
	values(
		p_id_presupuesto,
		p_id_subcategoria,
		p_monto_mensual,
		p_observaciones,
		p_creado_por
	);
end;
call sp_insertar_presupuesto_detalle(1, 1, 5000, 'Gasto mensual de supermercado', 'admin');
select * from presupuesto_detalle;

drop procedure if exists sp_actualizar_presupuesto_detalle;
create procedure sp_actualizar_presupuesto_detalle(
in p_id_detalle int,
in p_monto_mensual decimal(20,5),
in p_observaciones varchar(300),
in p_modificado_por varchar(100)
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
	
	if p_monto_mensual is null or p_monto_mensual <=0 then 
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el monto asignado debe ser mayor a 0';
	end if;
	
	update presupuesto_detalle set
		monto_mensual_asignado = p_monto_mensual,
		observaciones = p_observaciones,
		modificado_por = p_modificado_por 
	where id_detalle = p_id_detalle;
end;
call sp_actualizar_presupuesto_detalle(2, 3000, 'Gasto mensual de la energia', 'admin');
select * from presupuesto_detalle;

drop procedure if exists sp_eliminar_presupuesto_detalle;
create procedure sp_eliminar_presupuesto_detalle(
in p_id_detalle int 
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

	delete from presupuesto_detalle 
	where id_detalle = p_id_detalle;
end;
call sp_eliminar_presupuesto_detalle(2);
select * from presupuesto_detalle;

drop procedure if exists sp_consultar_presupuesto_detalle;
create procedure sp_consultar_presupuesto_detalle(
in p_id_detalle int
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
	
	select 
		pd.id_detalle,
		pd.id_presupuesto,
		pd.id_subcategoria,
		pd.monto_mensual_asignado,
		pd.observaciones,
		pd.creado_en,
		pd.modificado_en,
		pd.creado_por,
		pd.modificado_por,
		s.nombre as nombre_subcategoria,
		s.descripcion_detallada as descripcion_subcategoria,
		s.activo,
		s.es_por_defecto,
		c.id_categoria,
		c.nombre as nombre_categoria,
		c.descripcion_detallada as descripcion_categoria,
		c.tipo_categoria
	from 
		presupuesto_detalle pd
	inner join subcategoria s
		on pd.id_subcategoria = s.id_subcategoria
	inner join categoria c
		on s.id_categoria = c.id_categoria
	where pd.id_detalle = p_id_detalle;
end;
call sp_consultar_presupuesto_detalle(3);

drop procedure if exists sp_listar_presupuesto_detalle;
create procedure sp_listar_presupuesto_detalle(
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
		pd.id_detalle,
		pd.id_presupuesto,
		pd.monto_mensual_asignado,
		pd.observaciones,
		pd.creado_por,
		pd.modificado_por,
		s.id_subcategoria,
		s.nombre as nombre_subcategoria,
		c.id_categoria,
		c.nombre as nombre_categoria,
		c.tipo_categoria
	from 
		presupuesto_detalle pd
	inner join subcategoria s
		on pd.id_subcategoria = s.id_subcategoria
	inner join categoria c
		on s.id_categoria = c.id_categoria
	where pd.id_presupuesto = p_id_presupuesto;
end;
call sp_listar_presupuesto_detalle(1);
