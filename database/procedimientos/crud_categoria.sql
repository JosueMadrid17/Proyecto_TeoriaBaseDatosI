drop procedure if exists sp_insertar_categoria;
create procedure sp_insertar_categoria(
in p_nombre varchar(100),
in p_descripcion varchar(100),
in p_tipo varchar(40),
in p_creado_por varchar(100)
)
begin
	insert into categoria(
	nombre,
	descripcion_detallada,
	tipo_categoria,
	creado_por
	)
	values(
	p_nombre,
	p_descripcion,
    p_tipo, 
    p_creado_por
	);
end;
call sp_insertar_categoria('salario', 'ingreso de salario cada mes', 'ingreso', 'admin');
select * from categoria;

drop procedure if exists sp_actualizar_categoria;
create procedure sp_actualizar_categoria(
in p_id_categoria int,
in p_nombre varchar(100),
in p_descripcion varchar(100),
in p_modificado_por varchar(100)
)
begin
	update categoria set 
		nombre = p_nombre,
		descripcion_detallada = p_descripcion,
		modificado_por = p_modificado_por
	where id_categoria = p_id_categoria;
end;
call sp_actualizar_categoria(1, 'Transporte y movilidad', 'Gastos de transporte publico y privado', 'admin');
select * from categoria;

drop procedure if exists sp_eliminar_categoria;
create procedure sp_eliminar_categoria(
in p_id_categoria int
)
begin
  if exists (
  	select 
  		1
    from 
    	subcategoria
    where 
    	id_categoria = p_id_categoria
    	and activo = 1 
    	and es_por_defecto = 0
    limit 1
    ) then signal sqlstate '45000'
    	set message_text = 'no se puede eliminar: existen subcategorias adicionales activas';
  else
    delete from subcategoria
    where id_categoria = p_id_categoria;

    delete from categoria
    where id_categoria = p_id_categoria;
  end if;
end;
call sp_eliminar_categoria(1);
select * from categoria;

drop procedure if exists sp_consultar_categoria;
create procedure sp_consultar_categoria(
in p_id_categoria int
)
begin
	select *
	from categoria
	where id_categoria = p_id_categoria;
end;
call sp_consultar_categoria(2);

drop procedure if exists sp_listar_categorias;
create procedure sp_listar_categorias(
in p_id_usuario int,
in p_tipo varchar(40)
)
begin
	select distinct c.*
	from categoria c
	inner join subcategoria s
		on c.id_categoria = s.id_categoria
	inner join presupuesto_detalle pd
		on s.id_subcategoria = pd.id_subcategoria
	inner join presupuesto p
		on pd.id_presupuesto = p.id_presupuesto
	inner join usuario u
		on p.id_usuario = u.id_usuario
	where u.id_usuario = p_id_usuario
		and (p_tipo is null or c.tipo_categoria = p_tipo);
end;
call sp_listar_categorias(1, 'gasto');

