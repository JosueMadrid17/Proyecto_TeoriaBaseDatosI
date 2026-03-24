drop procedure if exists sp_reporte_resumen_mensual;
create procedure sp_reporte_resumen_mensual(
in p_id_usuario int,
in p_anio_inicio int,
in p_mes_inicio int,
in p_anio_fin int,
in p_mes_fin int
)
begin
    if p_id_usuario is null or p_id_usuario <= 0 then
        signal sqlstate '45000'
        set 
		message_text = 'Lo siento, el id del usuario es obligatorio y debe ser valido';
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
    	message_text = 'Lo siento, el usuario no existe o no esta activo';
    end if;

    if p_anio_inicio is null or p_anio_inicio <= 0 then
        signal sqlstate '45000'
        set 
    	message_text = 'Lo siento, el anio de inicio es obligatorio y debe ser valido';
    end if;

    if p_anio_fin is null or p_anio_fin <= 0 then
        signal sqlstate '45000'
        set 
    	message_text = 'Lo siento, el anio de fin es obligatorio y debe ser valido';
    end if;

    if p_mes_inicio is null or p_mes_inicio < 1 or p_mes_inicio > 12 then
        signal sqlstate '45000'
        set 
    	message_text = 'Lo siento, el mes de inicio es invalido, debe ser de 1 al 12';
    end if;

    if p_mes_fin is null or p_mes_fin < 1 or p_mes_fin > 12 then
        signal sqlstate '45000'
        set 
    	message_text = 'Lo siento, el mes de fin es invalido, debe ser de 1 al 12';
    end if;

    if (p_anio_inicio > p_anio_fin) or (p_anio_inicio = p_anio_fin and p_mes_inicio > p_mes_fin) then
        signal sqlstate '45000'
        set 
    	message_text = 'Lo siento, el periodo de inicio no puede ser mayor que el final';
    end if;

    select
        t.anio_transaccion as anio,
        t.mes_transaccion as mes,
        ifnull(sum(case when t.tipo_transaccion = 'ingreso' then t.monto_transaccion else 0 end), 0) as total_ingresos,
        ifnull(sum(case when t.tipo_transaccion = 'gasto' then t.monto_transaccion else 0 end), 0) as total_gastos,
        ifnull(sum(case when t.tipo_transaccion = 'ahorro' then t.monto_transaccion else 0 end), 0) as total_ahorros,
        
        (ifnull(sum(case when t.tipo_transaccion = 'ingreso' then t.monto_transaccion else 0 end), 0) -
         ifnull(sum(case when t.tipo_transaccion = 'gasto' then t.monto_transaccion else 0 end), 0) -
         ifnull(sum(case when t.tipo_transaccion = 'ahorro' then t.monto_transaccion else 0 end), 0)) as balance_final
    from 
    	transaccion t
    inner join presupuesto_detalle pd
        on t.id_detalle = pd.id_detalle
    inner join presupuesto p
        on pd.id_presupuesto = p.id_presupuesto
    where 
    	p.id_usuario = p_id_usuario
    	and ((t.anio_transaccion > p_anio_inicio or 
      		(t.anio_transaccion = p_anio_inicio and t.mes_transaccion >= p_mes_inicio))
      	and (t.anio_transaccion < p_anio_fin or 
      		(t.anio_transaccion = p_anio_fin and t.mes_transaccion <= p_mes_fin)))
    group by
        t.anio_transaccion,
        t.mes_transaccion
    order by
        t.anio_transaccion,
        t.mes_transaccion;
end;


drop procedure if exists sp_reporte_distribucion_gastos_categoria;
create procedure sp_reporte_distribucion_gastos_categoria(
in p_id_usuario int,
in p_anio int,
in p_mes int
)
begin
    declare v_total_gastos decimal(20,5) default 0;

    if p_id_usuario is null or p_id_usuario <= 0 then
        signal sqlstate '45000'
        set 
    	message_text = 'Lo siento, el id del usuario es obligatorio y debe ser valido';
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
    	message_text = 'Lo siento, el usuario no existe o no esta activo';
    end if;

    if p_anio is null or p_anio <= 0 then
        signal sqlstate '45000'
        set 
    	message_text = 'Lo siento, el anio es obligatorio y debe ser valido';
    end if;

    if p_mes is null or p_mes < 1 or p_mes > 12 then
        signal sqlstate '45000'
        set 
    	message_text = 'Lo siento, el mes es invalido, debe ser de 1 al 12';
    end if;

    select 
    	ifnull(sum(t.monto_transaccion), 0)
    into 
    	v_total_gastos
    from 
    	transaccion t
    inner join presupuesto_detalle pd
        on t.id_detalle = pd.id_detalle
    inner join presupuesto p
        on pd.id_presupuesto = p.id_presupuesto
    inner join subcategoria s
        on pd.id_subcategoria = s.id_subcategoria
    inner join categoria c
        on s.id_categoria = c.id_categoria
    where 
    	p.id_usuario = p_id_usuario
      	and t.anio_transaccion = p_anio
      	and t.mes_transaccion = p_mes
      	and t.tipo_transaccion = 'gasto'
      	and c.tipo_categoria = 'gasto';

    select
        c.id_categoria,
        c.nombre as nombre_categoria,
        ifnull(sum(t.monto_transaccion), 0) as monto_total_gastado,
        case
            when 
            	v_total_gastos = 0 then 0
            else 
            	round((ifnull(sum(t.monto_transaccion), 0) / v_total_gastos) * 100, 2)
        end as porcentaje_total_gastos,
        count(t.id_transaccion) as cantidad_transacciones
    from 
    	transaccion t
    inner join presupuesto_detalle pd
        on t.id_detalle = pd.id_detalle
    inner join presupuesto p
        on pd.id_presupuesto = p.id_presupuesto
    inner join subcategoria s
        on pd.id_subcategoria = s.id_subcategoria
    inner join categoria c
        on s.id_categoria = c.id_categoria
    where 
    	p.id_usuario = p_id_usuario
      	and t.anio_transaccion = p_anio
      	and t.mes_transaccion = p_mes
      	and t.tipo_transaccion = 'gasto'
      	and c.tipo_categoria = 'gasto'
    group by
        c.id_categoria,
        c.nombre
    order by
        monto_total_gastado desc;
end;


drop procedure if exists sp_reporte_cumplimiento_presupuesto;
create procedure sp_reporte_cumplimiento_presupuesto(
in p_id_usuario int,
in p_anio int,
in p_mes int,
in p_tipo_categoria varchar(40)
)
begin
    if p_id_usuario is null or p_id_usuario <= 0 then
        signal sqlstate '45000'
        set 
		message_text = 'Lo siento, el id del usuario es obligatorio y debe ser valido';
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
    	message_text = 'Lo siento, el usuario no existe o no esta activo';
    end if;

    if p_anio is null or p_anio <= 0 then
        signal sqlstate '45000'
        set 
    	message_text = 'Lo siento, el anio es obligatorio y debe ser valido';
    end if;

    if p_mes is null or p_mes < 1 or p_mes > 12 then
        signal sqlstate '45000'
        set 
    	message_text = 'Lo siento, el mes es invalido, debe ser de 1 al 12';
    end if;

    if p_tipo_categoria is null or trim(p_tipo_categoria) = '' then
        signal sqlstate '45000'
        set 
    	message_text = 'Lo siento, el tipo de categoria es obligatorio escribirlo';
    end if;

    select
        c.id_categoria,
        c.nombre as nombre_categoria,
        s.id_subcategoria,
        s.nombre as nombre_subcategoria,
        pd.monto_mensual_asignado as monto_presupuestado,
        ifnull(sum(t.monto_transaccion), 0) as monto_ejecutado,
        (pd.monto_mensual_asignado - ifnull(sum(t.monto_transaccion), 0)) as diferencia,
        case
            when 
            	pd.monto_mensual_asignado = 0 then 0
            else 
            	round((ifnull(sum(t.monto_transaccion), 0) / pd.monto_mensual_asignado) * 100, 2)
        end as porcentaje_ejecucion,
        case
            when 
            	pd.monto_mensual_asignado = 0 then 'SIN PRESUPUESTO'
            when
            	(ifnull(sum(t.monto_transaccion), 0) / pd.monto_mensual_asignado) < 0.80 then 'VERDE'
            when 
            	(ifnull(sum(t.monto_transaccion), 0) / pd.monto_mensual_asignado) <= 1.00 then 'AMARILLO'
            else 'ROJO'
        end as indicador_visual
    from 
    	presupuesto p
    inner join presupuesto_detalle pd
        on p.id_presupuesto = pd.id_presupuesto
    inner join subcategoria s
        on pd.id_subcategoria = s.id_subcategoria
    inner join categoria c
        on s.id_categoria = c.id_categoria
    left join transaccion t
        on pd.id_detalle = t.id_detalle
        and t.anio_transaccion = p_anio
        and t.mes_transaccion = p_mes
    where 
    	p.id_usuario = p_id_usuario
      and c.tipo_categoria = p_tipo_categoria
      and ((p_anio > p.anio_inicio or (p_anio = p.anio_inicio and p_mes >= p.mes_inicio))
      and (p_anio < p.anio_fin or (p_anio = p.anio_fin and p_mes <= p.mes_fin)))
    group by
        c.id_categoria,
        c.nombre,
        s.id_subcategoria,
        s.nombre,
        pd.monto_mensual_asignado
    order by
        c.nombre,
        s.nombre;
end;


drop procedure if exists sp_reporte_tendencia_gastos_categoria_tiempo;
create procedure sp_reporte_tendencia_gastos_categoria_tiempo(
in p_id_usuario int,
in p_anio_inicio int,
in p_mes_inicio int,
in p_anio_fin int,
in p_mes_fin int,
in p_categorias varchar(500)
)
begin
    if p_id_usuario is null or p_id_usuario <= 0 then
        signal sqlstate '45000'
        set 
		message_text = 'Lo siento, el id del usuario es obligatorio y debe ser valido';
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
    	message_text = 'Lo siento, el usuario no existe o no esta activo';
    end if;

    if p_anio_inicio is null or p_anio_inicio <= 0 then
        signal sqlstate '45000'
        set 
    	message_text = 'Lo siento, el anio de inicio es obligatorio y debe ser valido';
    end if;

    if p_anio_fin is null or p_anio_fin <= 0 then
        signal sqlstate '45000'
        set 
    	message_text = 'Lo siento, el anio de fin es obligatorio y debe ser valido';
    end if;

    if p_mes_inicio is null or p_mes_inicio < 1 or p_mes_inicio > 12 then
        signal sqlstate '45000'
        set 
    	message_text = 'Lo siento, el mes de inicio es invalido, debe ser de 1 al 12';
    end if;

    if p_mes_fin is null or p_mes_fin < 1 or p_mes_fin > 12 then
        signal sqlstate '45000'
        set 
    	message_text = 'Lo siento, el mes de fin es invalido, debe ser de 1 al 12';
    end if;

    if (p_anio_inicio > p_anio_fin) or (p_anio_inicio = p_anio_fin and p_mes_inicio > p_mes_fin) then
        signal sqlstate '45000'
        set 
    	message_text = 'Lo siento, el periodo de inicio no puede ser mayor que el final';
    end if;

    if p_categorias is null or trim(p_categorias) = '' then
        signal sqlstate '45000'
        set 
    	message_text = 'Lo siento, debe escribir al menos una categoria';
    end if;

    select
        t.anio_transaccion as anio,
        t.mes_transaccion as mes,
        c.id_categoria,
        c.nombre as nombre_categoria,
        ifnull(sum(t.monto_transaccion), 0) as monto_gastado
    from 
    	transaccion t
    inner join presupuesto_detalle pd
        on t.id_detalle = pd.id_detalle
    inner join presupuesto p
        on pd.id_presupuesto = p.id_presupuesto
    inner join subcategoria s
        on pd.id_subcategoria = s.id_subcategoria
    inner join categoria c
        on s.id_categoria = c.id_categoria
    where 
    	p.id_usuario = p_id_usuario
      	and t.tipo_transaccion = 'gasto'
      	and c.tipo_categoria = 'gasto'
      	and ((t.anio_transaccion > p_anio_inicio or (t.anio_transaccion = p_anio_inicio and t.mes_transaccion >= p_mes_inicio))
        and (t.anio_transaccion < p_anio_fin or (t.anio_transaccion = p_anio_fin and t.mes_transaccion <= p_mes_fin)))
      	and find_in_set(c.nombre, p_categorias) > 0
    group by
        t.anio_transaccion,
        t.mes_transaccion,
        c.id_categoria,
        c.nombre
    order by
        t.anio_transaccion,
        t.mes_transaccion,
        c.nombre;
end;


drop procedure if exists sp_reporte_estado_obligaciones_fijas;
create procedure sp_reporte_estado_obligaciones_fijas(
in p_id_usuario int,
in p_anio int,
in p_mes int,
in p_estado varchar(40)
)
begin
    if p_id_usuario is null or p_id_usuario <= 0 then
        signal sqlstate '45000'
        set 
		message_text = 'Lo siento, el id del usuario es obligatorio y debe ser valido';
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
    	message_text = 'Lo siento, el usuario no existe o no esta activo';
    end if;

    if p_anio is null or p_anio <= 0 then
        signal sqlstate '45000'
        set 
    	message_text = 'Lo siento, el anio es obligatorio y debe ser valido';
    end if;

    if p_mes is null or p_mes < 1 or p_mes > 12 then
        signal sqlstate '45000'
        set 
    	message_text = 'Lo siento, el mes es invalido, debe ser de 1 al 12';
    end if;

    if p_estado is null or trim(p_estado) = '' then
        signal sqlstate '45000'
        set 
    	message_text = 'Lo siento, el estado es obligatorio escribirlo';
    end if;

    with obligaciones_usuario as (
    	select distinct
            o.id_obligacion,
            o.nombre as nombre_obligacion,
            c.nombre as nombre_categoria,
            o.monto_fijo_mensual,
            o.dia_vencimiento,
            str_to_date(
                concat(p_anio, '-',
                    lpad(p_mes, 2, '0'), '-',
                    lpad(least(o.dia_vencimiento, day(last_day(str_to_date(concat(p_anio, '-', lpad(p_mes, 2, '0'), '-01'), '%Y-%m-%d')))), 2, '0')),
                '%Y-%m-%d'
            ) as fecha_vencimiento_mes,
            max(t.fecha_transaccion) as fecha_ultimo_pago
        from 
        	obligacion_fija o
        inner join subcategoria s
            on o.id_subcategoria = s.id_subcategoria
        inner join categoria c
            on s.id_categoria = c.id_categoria
        inner join presupuesto_detalle pd
            on s.id_subcategoria = pd.id_subcategoria
        inner join presupuesto p
            on pd.id_presupuesto = p.id_presupuesto
        left join obligacionfija_transaccion ot
            on o.id_obligacion = ot.id_obligacion
        left join transaccion t
            on ot.id_transaccion = t.id_transaccion
            and t.anio_transaccion = p_anio
            and t.mes_transaccion = p_mes
        where 
        	p.id_usuario = p_id_usuario
          	and o.vigente = 1
          	and c.tipo_categoria = 'gasto'
          	and (year(o.fecha_inicio) < p_anio or (year(o.fecha_inicio) = p_anio and month(o.fecha_inicio) <= p_mes))
          	and (o.fecha_fin is null or year(o.fecha_fin) > p_anio or (year(o.fecha_fin) = p_anio and month(o.fecha_fin) >= p_mes))
        group by
            o.id_obligacion,
            o.nombre,
            c.nombre,
            o.monto_fijo_mensual,
            o.dia_vencimiento
    )
    select
        id_obligacion,
        nombre_obligacion,
        nombre_categoria,
        monto_fijo_mensual,
        fecha_vencimiento_mes,
        case
            when 
            	fecha_ultimo_pago is not null then 'PAGADA'
            when 
            	curdate() > fecha_vencimiento_mes then 'VENCIDA'
            when 
            	datediff(fecha_vencimiento_mes, curdate()) < 3 then 'POR VENCER'
            else 'PENDIENTE'
        end as estado_pago,
        case
            when 
            	fecha_ultimo_pago is not null then 0
            when 
            	curdate() > fecha_vencimiento_mes then datediff(curdate(), fecha_vencimiento_mes)
            else 
            	datediff(fecha_vencimiento_mes, curdate())
        end as dias_referencia,
        fecha_ultimo_pago
    from 
    	obligaciones_usuario
    where
        upper(trim(p_estado)) = 'TODOS'
        or (
            upper(trim(p_estado)) = 'PAGADA'
            and fecha_ultimo_pago is not null
        )
        or (
            upper(trim(p_estado)) = 'VENCIDA'
            and fecha_ultimo_pago is null
            and curdate() > fecha_vencimiento_mes
        )
        or (
            upper(trim(p_estado)) = 'POR VENCER'
            and fecha_ultimo_pago is null
            and curdate() <= fecha_vencimiento_mes
            and datediff(fecha_vencimiento_mes, curdate()) < 3
        )
        or (
            upper(trim(p_estado)) = 'PENDIENTE'
            and fecha_ultimo_pago is null
            and datediff(fecha_vencimiento_mes, curdate()) >= 3
        )
    order by fecha_vencimiento_mes, nombre_obligacion;
end;