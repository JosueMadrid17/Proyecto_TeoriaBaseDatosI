drop trigger if exists tg_crear_subcategoria_por_defecto;
create trigger tg_crear_subcategoria_por_defecto after insert on categoria 
for each row	
begin
	insert into subcategoria(
		id_categoria, 
		nombre, 
		descripcion_detallada,
		activo,
		es_por_defecto,
		creado_por,
		modificado_por
	)
	values(
		new.id_categoria,
		'general',
		concat('Subcategoria creada por defecto de la categoria', new.nombre),
		1,
		1,
		new.creado_por,
		null
	);
end;