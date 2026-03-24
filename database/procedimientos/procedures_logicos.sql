drop procedure if exists sp_crear_presupuesto_completo;
create procedure sp_crear_presupuesto_completo(
in p_id_usuario int,
in p_nombre varchar(100),
in p_descripcion varchar(300),
in p_anio_inicio int,
in p_mes_inicio int,
in p_anio_fin int,
in p_mes_fin int,
in p_lista_subcategorias_json json,
in p_creado_por varchar(100)
)
begin
    declare v_id_presupuesto int;
    declare v_total_ingresos decimal(20,5) default 0;
    declare v_total_gastos decimal(20,5) default 0;
    declare v_total_ahorros decimal(20,5) default 0;

    declare exit handler for sqlexception
    begin
        rollback;
        resignal;
    end;

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
        set message_text = 'Lo siento, el usuario no existe o esta inactivo';
    end if;

    if p_nombre is null or trim(p_nombre) = '' then
        signal sqlstate '45000'
        set message_text = 'El nombre del presupuesto es obligatorio escribirlo';
    end if;

    if p_mes_inicio < 1 or p_mes_inicio > 12 then
        signal sqlstate '45000'
        set message_text = 'El mes de inicio es invalido, debe ser de 1 al 12';
    end if;

    if p_mes_fin < 1 or p_mes_fin > 12 then
        signal sqlstate '45000'
        set message_text = 'El mes de fin es invalido, debe ser de 1 al 12';
    end if;

    if p_anio_fin < p_anio_inicio
    or (p_anio_fin = p_anio_inicio and p_mes_fin < p_mes_inicio) then
        signal sqlstate '45000'
        set message_text = 'El periodo de inicio no puede ser mayor que el final';
    end if;

    if p_lista_subcategorias_json is null
    or json_length(p_lista_subcategorias_json) = 0 then
        signal sqlstate '45000'
        set message_text = 'Lo siento, se debe enviar al menos una subcategoria en el json';
    end if;

    if exists(
        select 	
        	1
        from 
        	presupuesto
        where 
        	id_usuario = p_id_usuario
	        and estado_presupuesto = 1
	        and (p_anio_inicio * 100 + p_mes_inicio) <= (anio_fin * 100 + mes_fin)
	    	and (p_anio_fin * 100 + p_mes_fin) >= (anio_inicio * 100 + mes_inicio)
	)then
    	signal sqlstate '45000'
        set message_text = 'Lo siento, ya existe un presupuesto activo en ese periodo';
    end if;

    if exists(
        select 
        	1
        from 
        	json_table(p_lista_subcategorias_json, '$[*]' 
        	columns(
                id_subcategoria int path '$.id_subcategoria',
                monto_mensual decimal(20,5) path '$.monto_mensual'
            )
        )j
        left join 
        	subcategoria s
            on j.id_subcategoria = s.id_subcategoria
        where 
        	j.id_subcategoria is null
        	or j.monto_mensual is null
        	or j.monto_mensual < 0
        	or s.id_subcategoria is null
            or s.activo = 0
    )then
        signal sqlstate '45000'
        set message_text = 'Lo siento, pero hay subcategorias invalidas, inactivas o montos incorrectos en el json';
    end if;

    if exists(
    	select 
        	j.id_subcategoria
        from 
        	json_table(p_lista_subcategorias_json, '$[*]' 
            columns(
                id_subcategoria int path '$.id_subcategoria'
            )
        )j
        group by 
        	j.id_subcategoria
        having count(*) > 1
    )then
        signal sqlstate '45000'
        set message_text = 'Lo siento, pero hay subcategorias repetidas en el json';
    end if;

    start transaction;

    insert into presupuesto(
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
        creado_por,
        modificado_por
    )
    values(
        p_id_usuario,
        p_nombre,
        p_anio_inicio,
        p_mes_inicio,
        p_anio_fin,
        p_mes_fin,
        0,
        0,
        0,
        current_timestamp(),
        1,
        p_creado_por,
        null
    );

    set v_id_presupuesto = last_insert_id();

    insert into presupuesto_detalle(
        id_presupuesto,
        id_subcategoria,
        monto_mensual_asignado,
        observaciones,
        creado_por,
        modificado_por
    )
    select
        v_id_presupuesto,
        j.id_subcategoria,
        j.monto_mensual,
        p_descripcion,
        p_creado_por,
        null
    from 
    	json_table(p_lista_subcategorias_json, '$[*]' 
    	columns(
            id_subcategoria int path '$.id_subcategoria',
            monto_mensual decimal(20,5) path '$.monto_mensual'
        )
    )j;

    select
        ifnull(sum(case when lower(c.tipo_categoria) = 'ingreso' then pd.monto_mensual_asignado else 0 end), 0),
        ifnull(sum(case when lower(c.tipo_categoria) = 'gasto' then pd.monto_mensual_asignado else 0 end), 0),
        ifnull(sum(case when lower(c.tipo_categoria) = 'ahorro' then pd.monto_mensual_asignado else 0 end), 0)
    into
        v_total_ingresos,
        v_total_gastos,
        v_total_ahorros
    from 
    	presupuesto_detalle pd
    inner join subcategoria s
        on s.id_subcategoria = pd.id_subcategoria
    inner join categoria c
        on c.id_categoria = s.id_categoria
    where 
    	pd.id_presupuesto = v_id_presupuesto;

    update presupuesto set 
    	total_ingresos = v_total_ingresos,
        total_gastos = v_total_gastos,
        total_ahorros = v_total_ahorros
    where 
    	id_presupuesto = v_id_presupuesto;

    commit;

    select
        v_id_presupuesto as id_presupuesto_creado,
        v_total_ingresos as total_ingresos,
        v_total_gastos as total_gastos,
        v_total_ahorros as total_ahorros,
    	'El presupuesto completo fue creado correctamente' as mensaje_creacion;
end;
call sp_crear_presupuesto_completo(
    1,
    'Presupuesto Agosto Octubre 2026',
    'Distribucion mensual del presupuesto para el periodo agosto octubre 2026',
    2026,
    8,
    2026,
    10,
    '[
        {"id_subcategoria": 3, "monto_mensual": 18000.00000},
        {"id_subcategoria": 4, "monto_mensual": 4600.00000}
    ]',
    'admin'
);

drop procedure if exists sp_registrar_transaccion_completa;
create procedure sp_registrar_transaccion_completa(
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
	declare v_id_transaccion int;

	declare exit handler for sqlexception
    begin
        rollback;
        resignal;
    end;

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
		inner join presupuesto p
			on pd.id_presupuesto = p.id_presupuesto
		where 
			pd.id_detalle = p_id_detalle
			and ((p_anio > p.anio_inicio or (p_anio = p.anio_inicio and p_mes >= p.mes_inicio))
			and (p_anio < p.anio_fin or (p_anio = p.anio_fin and p_mes <= p.mes_fin)))
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el anio y mes de la transaccion no estan dentro del periodo de presupuesto';
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
		where 
			pd.id_detalle = p_id_detalle
			and lower(trim(c.tipo_categoria)) = lower(trim(p_tipo))
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el tipo de la transaccion no coincide con el tipo de categoria de la subcategoria';
	end if;
	
	start transaction;
	
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
		fecha_hora_registro,
		creado_por,
		modificado_por
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
		current_timestamp(),
		p_creado_por,
		null
	);
	
	set v_id_transaccion = last_insert_id();
	commit;
		
	select 
		v_id_transaccion as id_transaccion,
		'La transaccion completa fue creada correctamente' as mensaje_creacion;
end;
call sp_registrar_transaccion_completa(
6,
2026,
9,
'gasto',
'pago de energia electrica',
'2600',
'2026-09-17',
'tarjeta',
2,
'pago del recibo del mes de septiembre',
'admin'
);

drop procedure if exists sp_procesar_obligaciones_mes;
create procedure sp_procesar_obligaciones_mes(
in p_id_usuario int,
in p_anio int,
in p_mes int,
in p_id_presupuesto int 
)
begin
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
			presupuesto
		where 
			id_presupuesto = p_id_presupuesto
			and id_usuario = p_id_usuario
			and estado_presupuesto = 1
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el presupuesto no existe, o no pertenece al usuario o esta inactivo el usuario';
	end if;
	
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
			presupuesto
		where 
			id_presupuesto = p_id_presupuesto
			and ((p_anio > anio_inicio or (p_anio = anio_inicio and p_mes >= mes_inicio))
			and (p_anio < anio_fin or (p_anio = anio_fin and p_mes <= mes_fin)))
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el mes no esta dentro del periodo de presupuesto';
	end if;
	
	select distinct
		o.id_obligacion,
		o.nombre,
		o.descripcion_detallada,
		c.nombre as categoria,
		s.nombre as subcategoria,
		o.monto_fijo_mensual,
		o.dia_vencimiento,
		p_anio as anio,
		p_mes as mes,
		case  
			when o.dia_vencimiento < day(current_date())
				and year(current_date()) = p_anio and month(current_date()) = p_mes
				then 'vencida'
			when o.dia_vencimiento - day(current_date()) between 0 and 3
				and year(current_date()) = p_anio and month(current_date()) = p_mes
				then 'por vencerse'
				else 'pendiente'
		end as estado_alerta
	from 
		obligacion_fija o 
	inner join subcategoria s 
		on o.id_subcategoria = s.id_subcategoria
	inner join categoria c 
		on s.id_categoria = c.id_categoria
	inner join presupuesto_detalle pd 
		on pd.id_subcategoria = s.id_subcategoria
	inner join presupuesto p 
		on pd.id_presupuesto = p.id_presupuesto
	where 
		p.id_presupuesto = p_id_presupuesto 
		and p.id_usuario = p_id_usuario
		and o.vigente = 1
		and(year(o.fecha_inicio) < p_anio
			or(year(o.fecha_inicio) = p_anio and month(o.fecha_inicio) <= p_mes))
		and(o.fecha_fin is null
			or year(o.fecha_fin) > p_anio
			or(year(o.fecha_fin) = p_anio and month(o.fecha_fin) >= p_mes))
	order by 
		o.dia_vencimiento asc;		
end;
call sp_procesar_obligaciones_mes(1, 2026, 8, 5);

drop procedure if exists sp_calcular_balance_mensual;
create procedure sp_calcular_balance_mensual(
in p_id_usuario int,
in p_id_presupuesto int,
in p_anio int,
in p_mes int,
out p_total_ingresos decimal(20,5), 
out p_total_gastos decimal(20,5), 
out p_total_ahorros decimal(20,5), 
out p_balance_final decimal(20,5)
)
begin
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
			presupuesto
		where 
			id_presupuesto = p_id_presupuesto
			and id_usuario = p_id_usuario
			and estado_presupuesto = 1
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el presupuesto no existe, o no pertenece al usuario o esta inactivo el usuario';
	end if;
	
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

	select
        ifnull(sum(case when lower(t.tipo_transaccion) = 'ingreso' then t.monto_transaccion else 0 end), 0),
        ifnull(sum(case when lower(t.tipo_transaccion) = 'gasto' then t.monto_transaccion else 0 end), 0),
        ifnull(sum(case when lower(t.tipo_transaccion) = 'ahorro' then t.monto_transaccion else 0 end), 0)
    into
        p_total_ingresos,
        p_total_gastos,
        p_total_ahorros
    from 
    	transaccion t
    inner join presupuesto_detalle pd
        on t.id_detalle = pd.id_detalle
    inner join presupuesto p
        on pd.id_presupuesto = p.id_presupuesto
    where 
    	p.id_presupuesto = p_id_presupuesto 
    	and p.id_usuario = p_id_usuario
    	and t.anio_transaccion = p_anio
    	and t.mes_transaccion = p_mes;
	
	set p_balance_final = p_total_ingresos - p_total_gastos - p_total_ahorros;
	
	select	
		p_id_usuario as id_usuario,
		concat(
			ifnull(u.primer_nombre, ''), ' ',
			ifnull(u.primer_apellido, '')
		)as nombre_usuario,
		p_id_presupuesto as id_presupuesto,
		p.nombre as nombre_presupuesto,
		p_anio as anio,
		p_mes as mes,
		p_total_ingresos as total_ingresos,
		p_total_gastos as total_gastos,
		p_total_ahorros as total_ahorros,
		p_balance_final as balance_final
	from 
		presupuesto p
	inner join usuario u
		on p.id_usuario = u.id_usuario
	where 
		p.id_presupuesto = p_id_presupuesto
		and u.id_usuario = p_id_usuario;
end;
call sp_calcular_balance_mensual(1, 5, 2026, 9, @ingresos, @gastos, @ahorros, @balance);

drop procedure if exists sp_calcular_monto_ejecutado_mes;
create procedure sp_calcular_monto_ejecutado_mes(
in p_id_subcategoria int,
in p_id_presupuesto int,
in p_anio int,
in p_mes int,
in p_monto_ejecutado decimal(20,5)
)
begin
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
		ifnull(sum(t.monto_transaccion),0)
	into 	
		p_monto_ejecutado 
	from 
		transaccion t
	inner join presupuesto_detalle pd
		on t.id_detalle = pd.id_detalle 
	where 
		pd.id_presupuesto = p_id_presupuesto
		and pd.id_subcategoria = p_id_subcategoria
		and t.anio_transaccion = p_anio 
		and t.mes_transaccion = p_mes;
	
	select 
		p_id_subcategoria as id_subcategoria,
		s.nombre as nombre_subcategoria,
		p_id_presupuesto as id_presupuesto,
		p.nombre as nombre_presupuesto,
		p_anio as anio,
		p_mes as mes,
		p_monto_ejecutado as monto_ejecutado
	from 
		presupuesto p
	inner join presupuesto_detalle pd
		on p.id_presupuesto = pd.id_presupuesto
	inner join subcategoria s
		on pd.id_subcategoria = s.id_subcategoria
	where 
		p.id_presupuesto = p_id_presupuesto
		and s.id_subcategoria = p_id_subcategoria;
end;
call sp_calcular_monto_ejecutado_mes(1, 1, 2026, 4, @monto_ejecutado);

drop procedure if exists sp_calcular_porcentaje_ejecutado_mes;
create procedure sp_calcular_porcentaje_ejecutado_mes(
in p_id_subcategoria int,
in p_id_presupuesto int,
in p_anio int,
in p_mes int,
out p_porcentaje decimal(20,5)
)
begin
	declare v_monto_ejecutado decimal(20,5) default 0;
	declare v_monto_presupuestado decimal(20,5) default 0;	

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
	
	select 
		ifnull(sum(t.monto_transaccion),0)
	into 	
		v_monto_ejecutado 
	from 
		transaccion t
	inner join presupuesto_detalle pd
		on t.id_detalle = pd.id_detalle 
	where 
		pd.id_presupuesto = p_id_presupuesto
		and pd.id_subcategoria = p_id_subcategoria
		and t.anio_transaccion = p_anio 
		and t.mes_transaccion = p_mes;
	
	if v_monto_presupuestado = 0 then 
		set p_porcentaje = 0;
	else  
		set p_porcentaje = (v_monto_ejecutado / v_monto_presupuestado) * 100;
	end if;
		
	select
	    p_id_subcategoria as id_subcategoria,
	    s.nombre as nombre_subcategoria,
	    p_id_presupuesto as id_presupuesto,
	    p.nombre as nombre_presupuesto,
	    p_anio as anio,
	    p_mes as mes,
	    v_monto_presupuestado as monto_presupuestado,
	    v_monto_ejecutado as monto_ejecutado,
	    p_porcentaje as porcentaje
	from 
	    presupuesto p
	inner join presupuesto_detalle pd
	    on p.id_presupuesto = pd.id_presupuesto
	inner join subcategoria s
	    on pd.id_subcategoria = s.id_subcategoria
	where 
	    p.id_presupuesto = p_id_presupuesto
	    and pd.id_subcategoria = p_id_subcategoria;
end;
call sp_calcular_porcentaje_ejecutado_mes(1, 1, 2026, 4, @porcentaje);

drop procedure if exists sp_cerrar_presupuesto;
create procedure sp_cerrar_presupuesto(
in p_id_presupuesto int,
in p_modificado_por varchar(100)
)
begin
	declare v_id_usuario int;
	declare v_nombre_presupuesto varchar(100);
	declare v_anio_inicio int;
	declare v_mes_inicio int;
	declare v_anio_fin int;
	declare v_mes_fin int;
    declare v_total_ingresos decimal(20,5) default 0;
    declare v_total_gastos decimal(20,5) default 0;
    declare v_total_ahorros decimal(20,5) default 0;
	declare v_balance_final decimal(20,5) default 0;

	declare exit handler for sqlexception 
	begin 
		rollback;
		resignal;
	end;
	
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
			presupuesto
		where 
			id_presupuesto = p_id_presupuesto
			and estado_presupuesto = 1
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el presupuesto ya esta cerrado o inactivo';
	end if;
	
	select 
		id_usuario,
		nombre,
		anio_inicio,
		mes_inicio,
		anio_fin,
		mes_fin
	into 
		v_id_usuario,
		v_nombre_presupuesto,
		v_anio_inicio,
		v_mes_inicio,
		v_anio_fin,
		v_mes_fin
	from
		presupuesto
	where 
		id_presupuesto = p_id_presupuesto;
	
	if not(
		v_anio_fin < year(current_date())
		or (v_anio_fin = year(current_date()) and v_mes_fin < month(current_date()))
	)then 
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, no se puede cerrar el presupuesto porque su periodo de fin aun no ha terminado';
	end if;
	
	select
        ifnull(sum(case when lower(t.tipo_transaccion) = 'ingreso' then t.monto_transaccion else 0 end), 0),
        ifnull(sum(case when lower(t.tipo_transaccion) = 'gasto' then t.monto_transaccion else 0 end), 0),
        ifnull(sum(case when lower(t.tipo_transaccion) = 'ahorro' then t.monto_transaccion else 0 end), 0)
    into 
    	v_total_ingresos,
    	v_total_gastos,
    	v_total_ahorros
    from 
    	transaccion t 
    inner join presupuesto_detalle pd 
    	on t.id_detalle = pd.id_detalle
    where 
    	pd.id_presupuesto = p_id_presupuesto;
	
	set v_balance_final = v_total_ingresos - v_total_gastos - v_total_ahorros;
	
	start transaction;
	
	update presupuesto set 
		estado_presupuesto = 0,
		modificado_por = p_modificado_por
	where 
		id_presupuesto = p_id_presupuesto;
	
	commit;
	
	select 	
		p_id_presupuesto as id_presupuesto,
		v_nombre_presupuesto as nombre_prespuesto,
		v_id_usuario as id_usuario,
		v_anio_inicio as anio_inicio,
		v_anio_inicio as mes_inicio,
		v_anio_fin as anio_fin,
		v_mes_fin as mes_fin,
		v_total_ingresos as total_ingresos_ejecutados,
		v_total_gastos as total_gastos_ejecutados,
		v_total_ahorros as total_ahorros_ejecutados,
		v_balance_final as balance_final;
end;
call sp_cerrar_presupuesto(1, 'admin');

drop procedure if exists sp_obtener_resumen_categoria_mes;
create procedure sp_obtener_resumen_categoria_mes(
in p_id_categoria int,
in p_id_presupuesto int,
in p_anio int,
in p_mes int,
out p_monto_presupuestado decimal(20,5),
out p_monto_ejecutado decimal(20,5),
out p_porcentaje decimal(20,5)
)
begin
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
		message_text = 'Lo siento, el presupuesto ya esta cerrado o inactivo';
	end if;
	
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
		p_monto_presupuestado
	from 
		presupuesto_detalle pd 
	inner join subcategoria s 
		on pd.id_subcategoria = s.id_subcategoria
	where 
		pd.id_presupuesto = p_id_presupuesto 
		and s.id_categoria = p_id_categoria;
	
	select 
		ifnull(sum(pd.monto_mensual_asignado),0)
	into 
		p_monto_ejecutado
	from 
		transaccion t 
	inner join presupuesto_detalle pd
		on t.id_detalle = pd.id_detalle
	inner join subcategoria s
		on pd.id_subcategoria = s.id_subcategoria
	where 
		pd.id_presupuesto = p_id_presupuesto 
		and s.id_categoria = p_id_categoria
		and t.anio_transaccion = p_anio
		and t.mes_transaccion = p_mes;

	if p_monto_presupuestado = 0 then 
		set p_porcentaje = 0;
	else 
		set p_porcentaje = (p_monto_ejecutado / p_monto_presupuestado) * 100;
	end if;
	
	select 
		p_id_categoria as id_categoria,
		c.nombre as nombre_categoria,
		p_id_presupuesto as id_presupuesto,
		p.nombre as nombre_presupuesto,
		p_anio as anio,
		p_mes as mes,
		p_monto_presupuestado as monto_presupuestado,
		p_monto_ejecutado as monto_ejecutado,
		p_porcentaje as porcentaje
	from 
		categoria c	
	inner join presupuesto p 
		on p.id_presupuesto = p_id_presupuesto
	where 
		c.id_categoria = p_id_categoria;
end;
call sp_obtener_resumen_categoria_mes(2, 5, 2026, 8, @monto_presupuestado, @monto_ejecutado, @porcentaje);
