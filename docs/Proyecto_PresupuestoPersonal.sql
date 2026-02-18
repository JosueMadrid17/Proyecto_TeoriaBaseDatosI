CREATE TABLE `usuario` (
  `id_usuario` int PRIMARY KEY,
  `primer_nombre` varchar(300),
  `segundo_nombre` varchar(300),
  `primer_apellido` varchar(300),
  `segundo_apellido` varchar(300),
  `correo_electronico` varchar(300),
  `salario_mensual` numeric(30,5),
  `estado_usuario` bool
);

CREATE TABLE `presupuesto` (
  `id_presupuesto` int PRIMARY KEY,
  `id_usuario` int,
  `nombre` varchar(300),
  `anio_inicio` int,
  `mes_inicio` int,
  `anio_fin` int,
  `mes_fin` int,
  `total_ingresos` numeric(30,5),
  `total_gastos` numeric(30,5),
  `total_ahorros` numeric(30,5),
  `fecha_creacion` datetime,
  `hora_creacion` time,
  `estado_presupuesto` bool
);

CREATE TABLE `categoria` (
  `id_categoria` int PRIMARY KEY,
  `nombre` varchar(300),
  `descripcion_detallada` varchar(300),
  `tipo_categoria` varchar(30)
);

CREATE TABLE `subcategoria` (
  `id_subcategoria` int PRIMARY KEY,
  `id_categoria` int,
  `nombre` varchar(300),
  `descripcion_detallada` varchar(300),
  `activo` bool,
  `es_por_defecto` int
);

CREATE TABLE `presupuesto_detalle` (
  `id_detalle` int PRIMARY KEY,
  `id_presupuesto` int,
  `id_subcategoria` int,
  `monto_mensual_asignado` numeric(30,5),
  `observaciones` varchar(300)
);

CREATE TABLE `obligacion_fija` (
  `id_obligacion` int PRIMARY KEY,
  `id_subcategoria` int,
  `nombre` varchar(300),
  `descripcion_detallada` varchar(300),
  `monto_fijo_mensual` numeric(30,5),
  `dia_vencimiento` int,
  `vigente` bool,
  `fecha_inicio` datetime,
  `fecha_fin` datetime
);

CREATE TABLE `transaccion` (
  `id_transaccion` int PRIMARY KEY,
  `id_detalle` int,
  `anio_transaccion` int,
  `mes_transaccion` int,
  `id_obligacion` int,
  `tipo_transaccion` varchar(30),
  `descripcion` varchar(300),
  `monto_transaccion` numeric(30,5),
  `fecha_transaccion` datetime,
  `metodo_pago` varchar(50),
  `num_factura` int,
  `observaciones` varchar(300),
  `fecha_registro` datetime,
  `hora_registro` time
);

CREATE TABLE `obligacionfija_transaccion` (
  `id_transaccion` int,
  `id_obligacion` int
);

ALTER TABLE `presupuesto` ADD FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`);

ALTER TABLE `presupuesto_detalle` ADD FOREIGN KEY (`id_presupuesto`) REFERENCES `presupuesto` (`id_presupuesto`);

ALTER TABLE `subcategoria` ADD FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id_categoria`);

ALTER TABLE `presupuesto_detalle` ADD FOREIGN KEY (`id_subcategoria`) REFERENCES `subcategoria` (`id_subcategoria`);

ALTER TABLE `obligacion_fija` ADD FOREIGN KEY (`id_subcategoria`) REFERENCES `subcategoria` (`id_subcategoria`);

ALTER TABLE `transaccion` ADD FOREIGN KEY (`id_detalle`) REFERENCES `presupuesto_detalle` (`id_detalle`);

ALTER TABLE `obligacionfija_transaccion` ADD FOREIGN KEY (`id_transaccion`) REFERENCES `obligacion_fija` (`id_obligacion`);

ALTER TABLE `obligacionfija_transaccion` ADD FOREIGN KEY (`id_obligacion`) REFERENCES `transaccion` (`id_transaccion`);
