drop function if exists fn_calcular_monto_ejecutado;
create function fn_calcular_monto_ejecutado(
p_id_subcategoria int,
p_anio int,
p_mes int
)
returns decimal(20,5)
deterministic 
begin
	declare v_monto_ejecutado decimal(20,5);
	
	if p_anio is null or p_anio <=0 then
		signal sqlstate '45000'
		set 
		message_text = 'El anio es obligatorio escribirlo y tiene que ser valido';
	end if;
	
	if p_mes < 1 or p_mes > 12 then
		signal sqlstate '45000'
		set 
		message_text = 'El mes es invalido, debe ser de 1 al 12';
	end if;

	if not exists(
		select 
			1
		from 	
			subcategoria
		where 
			id_subcategoria = p_id_subcategoria
			and activo = 1
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, la subcategoria no existe o esta inactiva';
	end if;
	
	select 	
		ifnull(sum(t.monto_transaccion),0)
	into
		v_monto_ejecutado 
	from  
		transaccion t 
	inner join presupuesto_detalle pd 
		on t.id_detalle = pd.id_detalle
	where 
		pd.id_subcategoria = p_id_subcategoria 
		and t.anio_transaccion = p_anio
		and t.mes_transaccion = p_mes;
	
	return v_monto_ejecutado;
end;
select fn_calcular_monto_ejecutado(3, 2026, 9) as monto_ejecutado;

drop function if exists fn_calcular_porcentaje_ejecutado;
create function fn_calcular_porcentaje_ejecutado(
p_id_subcategoria int,
p_id_presupuesto int,
p_anio int,
p_mes int
)
returns decimal(20,5)
deterministic 
begin
	declare v_monto_ejecutado decimal(20,5) default 0;
	declare v_monto_presupuestado decimal(20,5) default 0;
	declare v_porcentaje decimal(20,5) default 0;
		
	if p_anio is null or p_anio <=0 then
		signal sqlstate '45000'
		set 
		message_text = 'El anio es obligatorio escribirlo y tiene que ser valido';
	end if;
	
	if p_mes < 1 or p_mes > 12 then
		signal sqlstate '45000'
		set 
		message_text = 'El mes es invalido, debe ser de 1 al 12';
	end if;

	if not exists(
		select 
			1
		from 	
			subcategoria
		where 
			id_subcategoria = p_id_subcategoria
			and activo = 1
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, la subcategoria no existe o esta inactiva';
	end if;
	
	if not exists(
		select 
			1
		from 	
			presupuesto
		where 
			id_presupuesto = p_id_presupuesto
			and estado_presupuesto = 1
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el presupuesto no existe o esta inactivo';
	end if;
	
	if not exists(
		select 
			1
		from 
			presupuesto
		where 
			id_presupuesto = p_id_presupuesto
			and ((p_anio > anio_inicio or (p_anio = anio_inicio and p_mes >= mes_inicio))
			and (p_anio < anio_fin or (p_anio = anio_fin and p_mes <= mes_fin)))
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el anio y mes no estan dentro del periodo de presupuesto';
	end if;
	
	if not exists(
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
		message_text = 'Lo siento, la subcategoria no pertenece a ese presupuesto';
	end if;
	
	select 	
		monto_mensual_asignado
	into 
		v_monto_presupuestado
	from 
		presupuesto_detalle
	where  
		id_presupuesto = p_id_presupuesto 
		and id_subcategoria = p_id_subcategoria;
	
	set v_monto_ejecutado = fn_calcular_monto_ejecutado(
	p_id_subcategoria,
	p_anio ,
	p_mes 
	);
	
	if v_monto_presupuestado = 0 then 
		set v_porcentaje = 0;
	else
		set v_porcentaje = (v_monto_ejecutado / v_monto_presupuestado) * 100;
	end if;
	
	return v_porcentaje;
end;
select fn_calcular_porcentaje_ejecutado(1, 1, 2026, 4) as porcentaje_ejecutado

drop function if exists fn_obtener_balance_subcategoria;
create function fn_obtener_balance_subcategoria(
p_id_presupuesto int,
p_id_subcategoria int,
p_anio int,
p_mes int
)
returns decimal(20,5)
deterministic 
begin
	declare v_monto_presupuestado decimal(20,5) default 0;
	declare v_monto_ejecutado decimal(20,5) default 0;
	declare v_balance decimal(20,5) default 0;
		
	if p_anio is null or p_anio <=0 then
		signal sqlstate '45000'
		set 
		message_text = 'El anio es obligatorio escribirlo y tiene que ser valido';
	end if;
	
	if p_mes < 1 or p_mes > 12 then
		signal sqlstate '45000'
		set 
		message_text = 'El mes es invalido, debe ser de 1 al 12';
	end if;

	if not exists(
		select 
			1
		from 	
			subcategoria
		where 
			id_subcategoria = p_id_subcategoria
			and activo = 1
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, la subcategoria no existe o esta inactiva';
	end if;
	
	if not exists(
		select 
			1
		from 	
			presupuesto
		where 
			id_presupuesto = p_id_presupuesto
			and estado_presupuesto = 1
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el presupuesto no existe o esta inactivo';
	end if;
	
	if not exists(
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
		message_text = 'Lo siento, la subcategoria no pertenece a ese presupuesto';
	end if;
	
	select 	
		monto_mensual_asignado
	into 
		v_monto_presupuestado
	from 
		presupuesto_detalle
	where  
		id_presupuesto = p_id_presupuesto 
		and id_subcategoria = p_id_subcategoria;
	
	set v_monto_ejecutado = fn_calcular_monto_ejecutado(
	p_id_subcategoria,
	p_anio ,
	p_mes 
	);
	
	set v_balance = v_monto_presupuestado - v_monto_ejecutado;
	
	return v_balance;
end;
select fn_obtener_balance_subcategoria(1, 1, 2026, 4) as balance_subcategoria;

drop function if exists fn_obtener_total_categoria_mes;
create function fn_obtener_total_categoria_mes(
p_id_categoria int,
p_id_presupuesto int,
p_anio int,
p_mes int
)
returns decimal(20,5)
deterministic 
begin
	declare v_total_categoria decimal(20,5) default 0;
	
	if p_anio is null or p_anio <=0 then
		signal sqlstate '45000'
		set 
		message_text = 'El anio es obligatorio escribirlo y tiene que ser valido';
	end if;
	
	if p_mes < 1 or p_mes > 12 then
		signal sqlstate '45000'
		set 
		message_text = 'El mes es invalido, debe ser de 1 al 12';
	end if;

	if not exists(
		select 
			1
		from 	
			categoria
		where 
			id_categoria = p_id_categoria
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, la categoria no existe';
	end if;
	
	if not exists(
		select 
			1
		from 	
			presupuesto
		where 
			id_presupuesto = p_id_presupuesto
			and estado_presupuesto = 1
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el presupuesto no existe o esta inactivo';
	end if;
	
	if not exists(
		select 
			1
		from 
			presupuesto
		where 
			id_presupuesto = p_id_presupuesto
			and ((p_anio > anio_inicio or (p_anio = anio_inicio and p_mes >= mes_inicio))
			and (p_anio < anio_fin or (p_anio = anio_fin and p_mes <= mes_fin)))
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el anio y mes no estan dentro del periodo de presupuesto';
	end if;
	
	if not exists(
		select 
			1
		from 	
			presupuesto_detalle pd
		inner join subcategoria s 
			on pd.id_subcategoria = s.id_subcategoria
		where 
			pd.id_presupuesto = p_id_presupuesto
			and s.id_categoria = p_id_categoria 
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, la categoria no tiene subcategorias asociadas en este presupuesto';
	end if;
	
	select 	
		ifnull(sum(pd.monto_mensual_asignado),0)
	into 
		v_total_categoria 
	from 
		presupuesto_detalle pd 
	inner join subcategoria s 
		on pd.id_subcategoria = s.id_subcategoria
	where 
		pd.id_presupuesto = p_id_presupuesto
		and s.id_categoria = p_id_categoria; 
	
	return v_total_categoria;
end;
select fn_obtener_total_categoria_mes(2, 5, 2026, 8) as total_categoria_mes;

drop function if exists fn_obtener_total_ejecutado_categoria_mes;
create function fn_obtener_total_ejecutado_categoria_mes(
p_id_categoria int,
p_anio int,
p_mes int
)
returns decimal(20,5)
deterministic 
begin
	declare v_total_ejecutado decimal(20,5) default 0;
	
	if p_anio is null or p_anio <=0 then
		signal sqlstate '45000'
		set 
		message_text = 'El anio es obligatorio escribirlo y tiene que ser valido';
	end if;
	
	if p_mes < 1 or p_mes > 12 then
		signal sqlstate '45000'
		set 
		message_text = 'El mes es invalido, debe ser de 1 al 12';
	end if;

	if not exists(
		select 
			1
		from 	
			categoria
		where 
			id_categoria = p_id_categoria
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, la categoria no existe';
	end if;
	
	if not exists(
		select 
			1
		from 	
			subcategoria 
		where 
			id_categoria = p_id_categoria
			and activo = 1 
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, la categoria no tiene subcategorias activas';
	end if;
	
	select 	
		ifnull(sum(t.monto_transaccion),0)
	into 
		v_total_ejecutado 
	from 
		transaccion t
	inner join presupuesto_detalle pd 
		on t.id_detalle = pd.id_detalle
	inner join subcategoria s 
		on pd.id_subcategoria = s.id_subcategoria
	where 
		s.id_categoria = p_id_categoria
		and t.anio_transaccion = p_anio
		and t.mes_transaccion = p_mes;
	
	return v_total_ejecutado;
end;	
select fn_obtener_total_ejecutado_categoria_mes(2, 2026, 9) as total_ejecutado_categoria;
	
drop function if exists fn_dias_hasta_vencimiento;
create function fn_dias_hasta_vencimiento(
p_id_obligacion int
)
returns int
deterministic 
begin
	declare v_dia_vencimiento int;
	declare v_fecha_vencimiento int;
	declare v_ultimo_dia_mes int;
	declare v_dias_restantes int;

	if not exists(
		select 
			1
		from 	
			obligacion_fija
		where 
			id_obligacion = p_id_obligacion
			and vigente = 1
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, la obligacion no existe o no esta vigente';
	end if;
	
	select 	
		dia_vencimiento 
	into 
		v_dia_vencimiento 
	from 
		obligacion_fija 
	where 
		id_obligacion = p_id_obligacion;
	
	set v_ultimo_dia_mes = day(last_day(current_date()));
	
	if v_dia_vencimiento > v_ultimo_dia_mes then 
		set v_dia_vencimiento = v_ultimo_dia_mes;
	end if;
	
	set v_fecha_vencimiento = date(
		concat(year(current_date()),'-', month(current_date()),'-', v_dia_vencimiento)
	);
	
	set v_dias_restantes = datediff(v_fecha_vencimiento, current_date());
	
	return v_dias_restantes;
end;
select fn_dias_hasta_vencimiento(1) as dias_restantes;

drop function if exists fn_validar_vigencia_presupuesto;
create function fn_validar_vigencia_presupuesto(
p_fecha date,
p_id_presupuesto int
)
returns tinyint
deterministic 
begin
	declare v_anio_fecha int;
	declare v_mes_fecha int;
	declare v_vigencia tinyint default 0;
	
	if p_fecha is null then
		signal sqlstate '45000'
		set 
		message_text = 'La fecha es obligatoria';
	end if;
	
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
	
	set v_anio_fecha = year(p_fecha);
	set v_mes_fecha = month(p_fecha);
	
	if exists(
		select 
			1	
		from 
			presupuesto
		where 
			id_presupuesto = p_id_presupuesto 
			and ((v_anio_fecha > anio_inicio or (v_anio_fecha = anio_inicio and v_mes_fecha >= mes_inicio))
			and (v_anio_fecha < anio_fin or (v_anio_fecha = anio_fin and v_mes_fecha <= mes_fin)))
	)then
		set v_vigencia = 1;
	else
		set v_vigencia = 0;
	end if;
	
	return v_vigencia;
end;
select fn_validar_vigencia_presupuesto('2026-04-10', 1) as vigencia_presupuesto;

drop function if exists fn_obtener_categoria_por_subcategoria;
create function fn_obtener_categoria_por_subcategoria(
p_id_subcategoria int
)
returns int
deterministic 
begin
	declare v_id_categoria int;

	if not exists(
		select 
			1
		from 	
			subcategoria
		where 
			id_subcategoria = p_id_subcategoria
			and activo = 1
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, la subcategoria no existe o esta inactiva';
	end if;
	
	select
		id_categoria 
	into  
		v_id_categoria 
	from 
		subcategoria
	where  
		id_subcategoria = p_id_subcategoria;
	
	return v_id_categoria;
end;
select fn_obtener_categoria_por_subcategoria(1) as id_categoria;

drop function if exists fn_calcular_proyeccion_gasto_mensual;
create function fn_calcular_proyeccion_gasto_mensual(
p_id_subcategoria int,
p_anio int,
p_mes int
)
returns decimal(20,5)
deterministic 
begin
	declare v_gasto_acumulado decimal(20,5) default 0;
	declare v_proyeccion decimal(20,5) default 0;
	declare v_dias_transcurridos int;
	declare v_dias_totales_mes int;
	declare v_fecha_actual date;

	set v_fecha_actual = current_date();
	
	if p_anio is null or p_anio <=0 then
		signal sqlstate '45000'
		set 
		message_text = 'El anio es obligatorio escribirlo y tiene que ser valido';
	end if;
	
	if p_mes < 1 or p_mes > 12 then
		signal sqlstate '45000'
		set 
		message_text = 'El mes es invalido, debe ser de 1 al 12';
	end if;

	if not exists(
		select 
			1
		from 	
			subcategoria
		where 
			id_subcategoria = p_id_subcategoria
			and activo = 1
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, la subcategoria no existe o esta inactiva';
	end if;
	
	if p_anio != year(v_fecha_actual) or p_mes != month(v_fecha_actual) then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, la proyeccion de gasto mensual solo se calcula para el mes actual';
	end if;

	select
		ifnull(sum(t.monto_transaccion),0)	
	into 
		v_gasto_acumulado 
	from  
		transaccion t 
	inner join presupuesto_detalle pd 
		on t.id_detalle = pd.id_detalle 
	inner join subcategoria s
		on pd.id_subcategoria = s.id_subcategoria 
	where
		s.id_subcategoria = p_id_subcategoria
		and lower(t.tipo_transaccion) = 'gasto'
		and t.anio_transaccion = p_anio
		and t.mes_transaccion = p_mes;
	
	set v_dias_totales_mes = day(last_day(date(concat(p_anio, '-', p_mes, '-01'))));
	set v_dias_transcurridos = day(v_fecha_actual);
		
	if v_dias_transcurridos <=0 then 
		set v_proyeccion = 0;
	else  
		set v_proyeccion = (v_gasto_acumulado / v_dias_transcurridos) * v_dias_totales_mes;
	end if;
		
	return v_proyeccion;
end;
select fn_calcular_proyeccion_gasto_mensual(1, 2026, 3) as proyeccion_gasto; 

drop function if exists fn_obtener_promedio_gasto_subcategoria;
create function fn_obtener_promedio_gasto_subcategoria(
p_id_usuario int,
p_id_subcategoria int,
p_cantidad_meses int
)
returns decimal(20,5)
deterministic 
begin
	declare v_promedio decimal(20,5) default 0;
	declare v_total_gastado decimal(20,5) default 0;
	declare v_periodo_actual int;
	declare v_periodo_inicio int;

	if p_cantidad_meses is null or p_cantidad_meses <=0 then
		signal sqlstate '45000'
		set 
		message_text = 'La cantidad de meses es obligatorio escribirlo y tiene que ser valido';
	end if;
	
	if not exists(
		select 
			1
		from 	
			usuario
		where 
			id_usuario = p_id_usuario
			and estado_usuario = 1
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el usuario no existe o esta inactivo';
	end if;
	
	if not exists(
		select 
			1
		from 	
			subcategoria
		where 
			id_subcategoria = p_id_subcategoria
			and activo = 1
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, la subcategoria no existe o esta inactiva';
	end if;
	
	set v_periodo_actual = year(current_date()) * 100 + month(current_date());
	set v_periodo_inicio = (year(date_sub(current_date(), interval(p_cantidad_meses - 1)month)) * 100
		+ month(date_sub(current_date(), interval(p_cantidad_meses - 1)month)));
	
	select 
		ifnull(sum(t.monto_transaccion),0)
	into 	
		v_total_gastado 
	from 
		transaccion t 
	inner join presupuesto_detalle pd 
		on t.id_detalle = pd.id_detalle 
	inner join presupuesto p 
		on pd.id_presupuesto = p.id_presupuesto 
	where  
		p.id_usuario = p_id_usuario 
		and pd.id_subcategoria = p_id_subcategoria 
		and lower(t.tipo_transaccion) = 'gasto'
		and (t.anio_transaccion * 100 + t.mes_transaccion) between v_periodo_inicio and v_periodo_actual;
	
	set v_promedio = v_total_gastado / p_cantidad_meses; 
	
	return v_promedio;
end;
select fn_obtener_promedio_gasto_subcategoria(1, 1, 6) as promedio_gasto_subcategoria;