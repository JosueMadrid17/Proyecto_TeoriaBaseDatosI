# SISTEMA DE PRESUPUESTO PERSONAL

Proyecto académico desarrollado para la asignatura **Fundamentos de Sistemas de Bases de Datos**.

Este sistema permite a un usuario planificar, organizar y analizar sus finanzas personales mediante presupuestos mensuales, control detallado de transacciones y generación de reportes financieros.

---

## DESCRIPCIÓN DEL PROYECTO

El proyecto consiste en el diseño e implementación de un sistema integral de gestión presupuestal personal.

El usuario puede crear presupuestos con vigencia temporal, asignar montos planificados por categoría, registrar movimientos financieros reales y analizar el cumplimiento del presupuesto mediante indicadores y reportes.

Más allá de una simple aplicación, el proyecto está enfocado en demostrar dominio en diseño de bases de datos, implementación de lógica de negocio en el motor relacional y organización profesional del desarrollo.

---

## ENFOQUE ACADÉMICO

Este proyecto fue desarrollado aplicando principios formales de:

- Modelado Entidad–Relación (ERD)
- Transformación a Modelo Relacional
- Integridad referencial y reglas de negocio
- Procedimientos almacenados (CRUD y lógica avanzada)
- Funciones SQL para cálculos dinámicos
- Triggers para automatización y validaciones
- Arquitectura en tres capas
- Organización estructurada con Git

El objetivo principal fue trasladar la mayor parte de la lógica del sistema a la base de datos, garantizando consistencia, control y centralización de reglas.

---

## ARQUITECTURA DEL SISTEMA

El sistema fue diseñado bajo una arquitectura de tres capas claramente diferenciadas:

Capa de Presentación  
↓  
Capa de Negocio  
↓  
Capa de Datos  

### Capa de Presentación
Interfaz que permite al usuario interactuar con el sistema, registrar información y visualizar reportes.

### Capa de Negocio
Intermediario entre la interfaz y la base de datos.  
Se encarga de invocar procedimientos almacenados y controlar el flujo de la aplicación.

### Capa de Datos
Motor de base de datos donde reside la lógica principal:
- Procedimientos almacenados
- Funciones
- Triggers
- Validaciones
- Cálculos de ejecución presupuestal

---

## FUNCIONALIDADES PRINCIPALES

### Gestión de Usuarios
- Registro y actualización de usuarios
- Control de estado activo/inactivo
- Manejo de salario base como referencia presupuestal

### Gestión de Presupuestos
- Creación de presupuestos por período
- Definición de montos planificados por subcategoría
- Control de vigencia
- Cierre y análisis de ejecución

### Categorías y Subcategorías
- Clasificación en ingresos, gastos y ahorros
- Subcategoría automática por defecto mediante trigger
- Control detallado para análisis granular

### Registro de Transacciones
- Registro de ingresos, gastos y ahorros
- Asociación a subcategoría
- Imputación presupuestal por año y mes
- Validaciones de coherencia con el presupuesto activo

### Obligaciones Fijas
- Registro de compromisos financieros recurrentes
- Control de vencimientos
- Seguimiento del estado de pago

---

## ANÁLISIS Y REPORTERÍA

El sistema incorpora análisis financieros que permiten:

- Comparar ingresos vs gastos vs ahorro
- Medir el porcentaje de ejecución presupuestal
- Detectar sobre-ejecución o sub-ejecución
- Analizar tendencias de gasto en el tiempo
- Monitorear obligaciones pendientes
- Evaluar progreso de metas financieras

Estos reportes permiten transformar datos operativos en información útil para la toma de decisiones.

---

## ASPECTOS TÉCNICOS DESTACADOS

- Implementación completa de procedimientos almacenados
- Separación clara entre lógica de negocio y presentación
- Uso de triggers para garantizar reglas obligatorias
- Cálculos dinámicos mediante funciones SQL
- Integridad referencial entre entidades
- Campos de auditoría en todas las tablas
- Generación de datos de prueba realistas
- Estructura profesional del repositorio

---

## ORGANIZACIÓN DEL PROYECTO

El repositorio se encuentra estructurado de manera modular para facilitar mantenimiento y escalabilidad:

proyecto-presupuesto-personal/  
│  
├── docs/  
├── database/  
├── backend/  
├── frontend/  
└── metabase/  

Esta estructura permite separar responsabilidades y mantener orden en el desarrollo.

---

## ALCANCE DEL SISTEMA

El sistema cubre:

- Gestión completa del presupuesto mensual  
- Registro de movimientos financieros reales  
- Cálculo automático de ejecución  
- Análisis comparativo entre planificado y ejecutado  
- Seguimiento de obligaciones recurrentes  
- Generación de información financiera útil  

---

## VALOR ACADÉMICO DEL PROYECTO

Este proyecto representa la integración práctica de los conocimientos adquiridos durante el curso, demostrando:

- Capacidad de diseñar un modelo de datos coherente
- Implementar reglas de negocio complejas en SQL
- Organizar un proyecto con buenas prácticas
- Documentar y estructurar un desarrollo académico de forma profesional

---

## ESTADO DEL PROYECTO

Proyecto desarrollado como parte de evaluación académica.  
Incluye fases de diseño, implementación, pruebas y reportería.

---

## CONCLUSIÓN

El Sistema de Presupuesto Personal no solo cumple con los requerimientos académicos del curso, sino que representa una aplicación funcional que demuestra dominio técnico en diseño relacional, lógica en base de datos y organización estructurada del desarrollo.

