drop procedure if exists sp_insertar_subcategoria;
create procedure sp_insertar_subcategoria(
in p_id_categoria int,
in p_nombre varchar(100),
in p_descripcion varchar(100),
in p_es_defecto tinyint,
in p_creado_por varchar(100)
)
begin
	insert into subcategoria(
	id_categoria,
	nombre,
	descripcion_detallada,
	activo,
	es_por_defecto,
	creado_por
	)
	values(
	p_id_categoria,
	p_nombre,
	p_descripcion,
	1,
    p_es_defecto, 
    p_creado_por
	);
end;
call sp_insertar_subcategoria(2, 'Energia Electrica', 'Pago de recibo de energia electrica', 0, 'admin');
select * from subcategoria;

drop procedure if exists sp_actualizar_subcategoria;
create procedure sp_actualizar_subcategoria(
in p_id_subcategoria int,
in p_nombre varchar(100),
in p_descripcion varchar(100),
in p_modificado_por varchar(100)
)
begin
	update subcategoria set 
		nombre = p_nombre,
		descripcion_detallada = p_descripcion,
		modificado_por = p_modificado_por
	where id_subcategoria = p_id_subcategoria;
end;
call sp_actualizar_subcategoria(2, 'Enee', 'Pago de energia', 'admin');
select * from subcategoria;

drop procedure if exists sp_eliminar_subcategoria;
create procedure sp_eliminar_subcategoria(
in p_id_subcategoria int
)
begin
   if exists (
	    select 
	    	1
	    from 
	    	subcategoria s
	    left join presupuesto_detalle pd
	      on pd.id_subcategoria = s.id_subcategoria
	    left join transaccion t
	      on t.id_detalle = pd.id_detalle
	    where s.id_subcategoria = p_id_subcategoria
	      and (pd.id_detalle is not null or t.id_transaccion is not null)
	    limit 1
	  	) then signal sqlstate '45000'
      		set message_text = 'no se puede eliminar: subcategoria esta en uso en presupuestos o transacciones';
   else
    	delete from subcategoria
    	where id_subcategoria = p_id_subcategoria;
   end if;
end;
call sp_eliminar_subcategoria(2);
select * from subcategoria;

drop procedure if exists sp_consultar_subcategoria;
create procedure sp_consultar_subcategoria(
in p_id_subcategoria int
)
begin
	select
		s.id_subcategoria,
		s.id_categoria,
		c.nombre as nombre_categoria,
		c.tipo_categoria,
		s.nombre as nombre_subcategoria,
		s.descripcion_detallada,
		s.activo,
		s.es_por_defecto,
		s.creado_en,
		s.modificado_en,
		s.creado_por,
		s.modificado_por
	from subcategoria s
	inner join categoria c
		on c.id_categoria = s.id_categoria
	where s.id_subcategoria = p_id_subcategoria;
end;
call sp_consultar_subcategoria(1);

drop procedure if exists sp_listar_subcategorias_por_categoria;
create procedure sp_listar_subcategorias_por_categoria(
in p_id_categoria int
)
begin
	select *
	from subcategoria
	where id_categoria = p_id_categoria;
end;
call sp_listar_subcategorias_por_categoria(3);
