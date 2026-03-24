use presupuesto_personal;

-- =========================================================
-- DATOS DE PRUEBA MANUALES - DOS MESES COMPLETOS
-- Usuario de prueba: Jorge Leonel Paz Morales
-- Periodo: Marzo 2026 - Abril 2026
-- "Hecho con ai, autorizado por el inge esta parte para, 
-- mejor control y no generar errores humanos al ingresarlos 
-- uno mismo."
-- =========================================================


-- =========================================================
-- 1. USUARIO
-- =========================================================
call sp_insertar_usuario(
    'Jorge',
    'Leonel',
    'Paz',
    'Morales',
    'jorge.prueba2026@gmail.com',
    'jorge_1234',
    20000.00000,
    'admin'
);

set @id_usuario = (
    select id_usuario
    from usuario
    where correo_electronico = 'jorge.prueba2026@gmail.com'
    limit 1
);


-- =========================================================
-- 2. CATEGORIAS EXISTENTES
-- =========================================================
set @cat_servicios_publicos = (
    select id_categoria
    from categoria
    where nombre = 'Servicios Publicos'
      and tipo_categoria = 'gasto'
    limit 1
);

set @cat_comida = (
    select id_categoria
    from categoria
    where nombre = 'comida'
      and tipo_categoria = 'gasto'
    limit 1
);

set @cat_salario = (
    select id_categoria
    from categoria
    where nombre = 'salario'
      and tipo_categoria = 'ingreso'
    limit 1
);

set @cat_internet = (
    select id_categoria
    from categoria
    where nombre = 'internet'
      and tipo_categoria = 'gasto'
    limit 1
);

set @cat_transporte = (
    select id_categoria
    from categoria
    where nombre = 'Transporte'
      and tipo_categoria = 'gasto'
    limit 1
);

set @cat_ahorro = (
    select id_categoria
    from categoria
    where nombre = 'Ahorro'
      and tipo_categoria = 'ahorro'
    limit 1
);


-- =========================================================
-- 3. SUBCATEGORIAS EXISTENTES
-- Se reutilizan las ya creadas para no chocar con validaciones
-- =========================================================
set @sub_salario = (
    select id_subcategoria
    from subcategoria
    where id_categoria = @cat_salario
      and nombre = 'Salario Mensual'
    limit 1
);

set @sub_agua = (
    select id_subcategoria
    from subcategoria
    where id_categoria = @cat_servicios_publicos
      and nombre = 'Agua Potable'
    limit 1
);

set @sub_luz = (
    select id_subcategoria
    from subcategoria
    where id_categoria = @cat_servicios_publicos
      and nombre = 'Energia Electrica'
    limit 1
);

set @sub_internet = (
    select id_subcategoria
    from subcategoria
    where id_categoria = @cat_internet
      and nombre = 'general'
    limit 1
);

set @sub_supermercado = (
    select id_subcategoria
    from subcategoria
    where id_categoria = @cat_comida
      and nombre = 'Supermercado'
    limit 1
);

set @sub_transporte = (
    select id_subcategoria
    from subcategoria
    where id_categoria = @cat_transporte
      and nombre = 'Transporte Publico'
    limit 1
);

set @sub_ahorro = (
    select id_subcategoria
    from subcategoria
    where id_categoria = @cat_ahorro
      and nombre = 'general'
    limit 1
);


-- =========================================================
-- 4. PRESUPUESTO PARA DOS MESES
-- =========================================================
call sp_insertar_presupuesto(
    @id_usuario,
    'Presupuesto Marzo-Abril 2026',
    2026,
    3,
    2026,
    4,
    'admin'
);

set @id_presupuesto = (
    select id_presupuesto
    from presupuesto
    where id_usuario = @id_usuario
      and nombre = 'Presupuesto Marzo-Abril 2026'
    limit 1
);


-- =========================================================
-- 5. DETALLE DEL PRESUPUESTO
-- =========================================================
call sp_insertar_presupuesto_detalle(
    @id_presupuesto,
    @sub_salario,
    20000.00000,
    'Ingreso fijo mensual',
    'admin'
);

call sp_insertar_presupuesto_detalle(
    @id_presupuesto,
    @sub_agua,
    350.00000,
    'Pago mensual estimado de agua',
    'admin'
);

call sp_insertar_presupuesto_detalle(
    @id_presupuesto,
    @sub_luz,
    900.00000,
    'Pago mensual estimado de energia',
    'admin'
);

call sp_insertar_presupuesto_detalle(
    @id_presupuesto,
    @sub_internet,
    700.00000,
    'Pago mensual estimado de internet',
    'admin'
);

call sp_insertar_presupuesto_detalle(
    @id_presupuesto,
    @sub_supermercado,
    4500.00000,
    'Compras de supermercado del mes',
    'admin'
);

call sp_insertar_presupuesto_detalle(
    @id_presupuesto,
    @sub_transporte,
    1800.00000,
    'Gasto estimado de transporte',
    'admin'
);

call sp_insertar_presupuesto_detalle(
    @id_presupuesto,
    @sub_ahorro,
    3500.00000,
    'Meta de ahorro mensual',
    'admin'
);


-- =========================================================
-- 6. IDS DE DETALLE
-- =========================================================
set @det_salario = (
    select id_detalle
    from presupuesto_detalle
    where id_presupuesto = @id_presupuesto
      and id_subcategoria = @sub_salario
    limit 1
);

set @det_agua = (
    select id_detalle
    from presupuesto_detalle
    where id_presupuesto = @id_presupuesto
      and id_subcategoria = @sub_agua
    limit 1
);

set @det_luz = (
    select id_detalle
    from presupuesto_detalle
    where id_presupuesto = @id_presupuesto
      and id_subcategoria = @sub_luz
    limit 1
);

set @det_internet = (
    select id_detalle
    from presupuesto_detalle
    where id_presupuesto = @id_presupuesto
      and id_subcategoria = @sub_internet
    limit 1
);

set @det_supermercado = (
    select id_detalle
    from presupuesto_detalle
    where id_presupuesto = @id_presupuesto
      and id_subcategoria = @sub_supermercado
    limit 1
);

set @det_transporte = (
    select id_detalle
    from presupuesto_detalle
    where id_presupuesto = @id_presupuesto
      and id_subcategoria = @sub_transporte
    limit 1
);

set @det_ahorro = (
    select id_detalle
    from presupuesto_detalle
    where id_presupuesto = @id_presupuesto
      and id_subcategoria = @sub_ahorro
    limit 1
);

-- =========================================================
-- 7. OBLIGACIONES FIJAS
-- =========================================================

-- Reutilizar obligacion existente de agua
set @obl_agua = (
    select id_obligacion
    from obligacion_fija
    where id_subcategoria = @sub_agua
      and nombre = 'Pago Agua'
    limit 1
);

-- Crear obligacion para energia electrica porque la existente
-- esta ligada a la subcategoria "Enee" y no a "Energia Electrica"
call sp_insertar_obligacion_fija(
    @sub_luz,
    'Pago Energia Electrica',
    'Pago mensual del servicio de energia electrica',
    900.00000,
    12,
    '2026-03-01',
    '2026-04-30',
    'admin'
);

set @obl_luz = (
    select id_obligacion
    from obligacion_fija
    where id_subcategoria = @sub_luz
      and nombre = 'Pago Energia Electrica'
    limit 1
);

-- Crear obligacion para internet
call sp_insertar_obligacion_fija(
    @sub_internet,
    'Pago Internet',
    'Pago mensual del servicio de internet residencial',
    700.00000,
    15,
    '2026-03-01',
    '2026-04-30',
    'admin'
);

set @obl_internet = (
    select id_obligacion
    from obligacion_fija
    where id_subcategoria = @sub_internet
      and nombre = 'Pago Internet'
    limit 1
);

-- =========================================================
-- 8. TRANSACCIONES MARZO 2026
-- =========================================================
call sp_insertar_transaccion(
    @det_salario,
    2026,
    3,
    'ingreso',
    'Pago de salario correspondiente a marzo',
    20000.00000,
    '2026-03-01 08:00:00',
    'transferencia',
    1001,
    'Ingreso principal del mes',
    'admin'
);

call sp_insertar_transaccion(
    @det_agua,
    2026,
    3,
    'gasto',
    'Pago de recibo de agua de marzo',
    318.50000,
    '2026-03-07 10:15:00',
    'transferencia',
    2001,
    'Pago realizado un dia antes del vencimiento',
    'admin'
);
set @trx_agua_marzo = last_insert_id();

insert into obligacionfija_transaccion(
    id_transaccion,
    id_obligacion
)
values(
    @trx_agua_marzo,
    @obl_agua
);

call sp_insertar_transaccion(
    @det_luz,
    2026,
    3,
    'gasto',
    'Pago de recibo de energia de marzo',
    905.75000,
    '2026-03-11 09:30:00',
    'transferencia',
    2002,
    'Pago cercano al vencimiento',
    'admin'
);
set @trx_luz_marzo = last_insert_id();

insert into obligacionfija_transaccion(
    id_transaccion,
    id_obligacion
)
values(
    @trx_luz_marzo,
    @obl_luz
);

call sp_insertar_transaccion(
    @det_internet,
    2026,
    3,
    'gasto',
    'Pago de internet residencial de marzo',
    700.00000,
    '2026-03-15 18:20:00',
    'tarjeta',
    2003,
    'Pago exacto del servicio mensual',
    'admin'
);
set @trx_internet_marzo = last_insert_id();

insert into obligacionfija_transaccion(
    id_transaccion,
    id_obligacion
)
values(
    @trx_internet_marzo,
    @obl_internet
);

call sp_insertar_transaccion(
    @det_supermercado,
    2026,
    3,
    'gasto',
    'Compra quincenal de supermercado',
    1650.25000,
    '2026-03-05 17:40:00',
    'efectivo',
    3001,
    'Compra de alimentos y productos de limpieza',
    'admin'
);

call sp_insertar_transaccion(
    @det_supermercado,
    2026,
    3,
    'gasto',
    'Segunda compra de supermercado',
    1425.80000,
    '2026-03-18 19:10:00',
    'tarjeta',
    3002,
    'Reposicion de alimentos',
    'admin'
);

call sp_insertar_transaccion(
    @det_supermercado,
    2026,
    3,
    'gasto',
    'Compra menor de fin de mes',
    680.40000,
    '2026-03-27 16:55:00',
    'efectivo',
    3003,
    'Compra complementaria',
    'admin'
);

call sp_insertar_transaccion(
    @det_transporte,
    2026,
    3,
    'gasto',
    'Recargas y transporte de primera quincena',
    720.00000,
    '2026-03-08 07:30:00',
    'efectivo',
    4001,
    'Gastos acumulados de transporte',
    'admin'
);

call sp_insertar_transaccion(
    @det_transporte,
    2026,
    3,
    'gasto',
    'Transporte de segunda quincena',
    810.00000,
    '2026-03-23 07:45:00',
    'efectivo',
    4002,
    'Mayor uso de taxi por lluvia',
    'admin'
);

call sp_insertar_transaccion(
    @det_ahorro,
    2026,
    3,
    'ahorro',
    'Transferencia a ahorro de marzo',
    3400.00000,
    '2026-03-30 20:00:00',
    'transferencia',
    6001,
    'Ahorro del mes, ligeramente menor al presupuestado',
    'admin'
);


-- =========================================================
-- 9. TRANSACCIONES ABRIL 2026
-- =========================================================
call sp_insertar_transaccion(
    @det_salario,
    2026,
    4,
    'ingreso',
    'Pago de salario correspondiente a abril',
    20000.00000,
    '2026-04-01 08:05:00',
    'transferencia',
    1002,
    'Ingreso principal del mes',
    'admin'
);

call sp_insertar_transaccion(
    @det_agua,
    2026,
    4,
    'gasto',
    'Pago de recibo de agua de abril',
    325.00000,
    '2026-04-08 09:40:00',
    'transferencia',
    2004,
    'Pago en fecha de vencimiento',
    'admin'
);
set @trx_agua_abril = last_insert_id();

insert into obligacionfija_transaccion(
    id_transaccion,
    id_obligacion
)
values(
    @trx_agua_abril,
    @obl_agua
);

call sp_insertar_transaccion(
    @det_luz,
    2026,
    4,
    'gasto',
    'Pago de recibo de energia de abril',
    860.90000,
    '2026-04-12 11:20:00',
    'transferencia',
    2005,
    'Consumo un poco menor que marzo',
    'admin'
);
set @trx_luz_abril = last_insert_id();

insert into obligacionfija_transaccion(
    id_transaccion,
    id_obligacion
)
values(
    @trx_luz_abril,
    @obl_luz
);

call sp_insertar_transaccion(
    @det_internet,
    2026,
    4,
    'gasto',
    'Pago de internet residencial de abril',
    700.00000,
    '2026-04-14 17:30:00',
    'tarjeta',
    2006,
    'Pago un dia antes del vencimiento',
    'admin'
);
set @trx_internet_abril = last_insert_id();

insert into obligacionfija_transaccion(
    id_transaccion,
    id_obligacion
)
values(
    @trx_internet_abril,
    @obl_internet
);

call sp_insertar_transaccion(
    @det_supermercado,
    2026,
    4,
    'gasto',
    'Compra fuerte de inicio de mes',
    1880.30000,
    '2026-04-04 18:10:00',
    'tarjeta',
    3004,
    'Compra mas alta por abastecimiento',
    'admin'
);

call sp_insertar_transaccion(
    @det_supermercado,
    2026,
    4,
    'gasto',
    'Compra intermedia de supermercado',
    1210.75000,
    '2026-04-16 19:40:00',
    'efectivo',
    3005,
    'Compra de alimentos de media quincena',
    'admin'
);

call sp_insertar_transaccion(
    @det_supermercado,
    2026,
    4,
    'gasto',
    'Compra adicional de cierre de mes',
    910.60000,
    '2026-04-26 12:25:00',
    'tarjeta',
    3006,
    'Se compraron productos extra para el hogar',
    'admin'
);

call sp_insertar_transaccion(
    @det_transporte,
    2026,
    4,
    'gasto',
    'Transporte de primera quincena',
    650.00000,
    '2026-04-09 07:20:00',
    'efectivo',
    4003,
    'Menor uso de taxi',
    'admin'
);

call sp_insertar_transaccion(
    @det_transporte,
    2026,
    4,
    'gasto',
    'Transporte de segunda quincena',
    790.00000,
    '2026-04-22 08:00:00',
    'efectivo',
    4004,
    'Traslados rutinarios',
    'admin'
);

call sp_insertar_transaccion(
    @det_ahorro,
    2026,
    4,
    'ahorro',
    'Transferencia a ahorro de abril',
    3600.00000,
    '2026-04-29 20:10:00',
    'transferencia',
    6002,
    'Ahorro del mes, ligeramente mayor al de marzo',
    'admin'
);
