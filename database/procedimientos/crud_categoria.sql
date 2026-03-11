drop procedure if exists sp_insertar_categoria;
create procedure sp_insertar_categoria(
in p_nombre varchar(100),
in p_descripcion varchar(100),
in p_tipo varchar(40),
in p_creado_por varchar(100)
)
begin
	if p_nombre is null or trim(p_nombre) = '' then 
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el nombre de la categoria es obligatorio escribirlo';
	end if;

	if p_tipo is null or trim(p_tipo) = '' then 
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el tipo de categoria es obligatorio escribirlo';
	end if;

	if exists(
		select 
			1
		from 	
			categoria
		where 
			nombre = p_nombre
		and tipo_categoria = p_tipo
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, esa categoria ya existe';
	end if;
	
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
call sp_insertar_categoria('Vestimenta', 'Compra de ropa', 'gasto', 'admin');
select * from categoria;

drop procedure if exists sp_actualizar_categoria;
create procedure sp_actualizar_categoria(
in p_id_categoria int,
in p_nombre varchar(100),
in p_descripcion varchar(100),
in p_modificado_por varchar(100)
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

	if p_nombre is null or trim(p_nombre) = '' then 
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, el nombre de la categoria es obligatorio escribirlo';
	end if;
	
	if exists(
		select 
			1
		from 	
			categoria
		where 
			nombre = p_nombre
		and id_categoria != p_id_categoria
	)then
		signal sqlstate '45000'
		set 
		message_text = 'Lo siento, esa categoria ya existe';
	end if;
	
	update categoria set 
		nombre = p_nombre,
		descripcion_detallada = p_descripcion,
		modificado_por = p_modificado_por
	where id_categoria = p_id_categoria;
end;
call sp_actualizar_categoria(5, 'Vestimenta', 'Compra de ropa en Amazon', 'admin');
select * from categoria;

drop procedure if exists sp_eliminar_categoria;
create procedure sp_eliminar_categoria(
in p_id_categoria int
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
	
  	if exists(
	  	select 
	  		1
	    from 
	    	subcategoria
	    where 
	    	id_categoria = p_id_categoria
	    	and activo = 1 
	    	and es_por_defecto = 0
	    limit 1
    )then 
    	signal sqlstate '45000'
    	set message_text = 'Lo siento no se puede eliminar, ya existen subcategorias adicionales activas';
  	else
	    delete from subcategoria
	    where id_categoria = p_id_categoria;

	    delete from categoria
	    where id_categoria = p_id_categoria;
  	end if;
end;
call sp_eliminar_categoria(5);
select * from categoria;

drop procedure if exists sp_consultar_categoria;
create procedure sp_consultar_categoria(
in p_id_categoria int
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

	select 
		id_categoria,
		nombre,
		descripcion_detallada,
		tipo_categoria,
		creado_en,
		modificado_en,
		creado_por,
		modificado_por
	from 
		categoria
	where 
		id_categoria = p_id_categoria;
end;
call sp_consultar_categoria(2);

drop procedure if exists sp_listar_categorias;
create procedure sp_listar_categorias(
in p_id_usuario int,
in p_tipo varchar(40)
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
		c.id_categoria,
		c.nombre,
		c.descripcion_detallada,
		c.tipo_categoria,
		c.creado_en,
		c.modificado_en,
		c.creado_por,
		c.modificado_por
	from 
		categoria c
	inner join subcategoria s
		on c.id_categoria = s.id_categoria
	inner join presupuesto_detalle pd
		on s.id_subcategoria = pd.id_subcategoria
	inner join presupuesto p
		on pd.id_presupuesto = p.id_presupuesto
	inner join usuario u
		on p.id_usuario = u.id_usuario
	where 
		u.id_usuario = p_id_usuario
		and (p_tipo is null or c.tipo_categoria = p_tipo);
end;
call sp_listar_categorias(1, 'gasto');