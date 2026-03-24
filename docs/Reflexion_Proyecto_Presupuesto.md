# 🧠 Reflexión sobre el proceso de desarrollo

Durante el desarrollo del sistema de presupuesto personal, se logró implementar una solución completa que abarca base de datos, backend, frontend y reportería, cumpliendo con los objetivos del proyecto.

En cuanto al uso de inteligencia artificial (IA), esta fue utilizada como herramienta de apoyo en diferentes áreas del proyecto:

- **Inserción de datos:** Se utilizó IA para generar ideas y ejemplos de datos realistas para las pruebas.  
- **Frontend:** Se apoyó en IA para estructurar la interfaz en consola y mejorar la organización del código.  
- **Reportes:** Se utilizó IA para comprender mejor el uso de librerías para generar los gráficos visualmente, utilizando herramientas como:
  - mysql-connector-j-8.4.0.jar
  - jcommon-1.0.23.jar
  - jfreechart-1.5.4.jar
  - openpdf-1.3.30.jar

En general, el uso de inteligencia artificial permitió agilizar el desarrollo en ciertas áreas, mejorar la comprensión de algunos conceptos y optimizar tiempos. 

Sin embargo, fue necesario complementar ese apoyo con mucha práctica, análisis y pruebas propias, para asegurar que todo el sistema funcionara correctamente y cumpliera con los requerimientos del proyecto.

---

# ⚠️ Desafíos enfrentados y soluciones

Uno de los principales desafíos fue comprender y aplicar correctamente la lógica de negocio dentro de la base de datos, especialmente en:

- Procedimientos almacenados complejos.  
- Validaciones de datos.  
- Relación entre tablas como presupuesto, transacciones y obligaciones con el resto de tablas.  

**Solución:**  
Se resolvió mediante práctica constante, pruebas en DBeaver y ajustes progresivos en los procedimientos.

Otro desafío importante fue:

- Integrar el backend con la base de datos, respetando el uso de procedimientos almacenados en lugar de queries directos, a que me doy a entender aca, es a usar el lenguaje como herramienta de llamado se podria decir, ya que toda la logica esta en la base de datos.

**Solución:**  
Se realizo el código en Java para adaptarlo a llamadas de procedimientos, asegurando que todo funcionara correctamente sin romper la lógica existente de la base de datos.

También se presentó dificultad en:

- Diseño de reportes.

**Solución:**  
Se investigó y se utilizó apoyo de IA para entender mejor cómo generar los gráficos visuales utilizando librerías.

---

# 📚 Aprendizajes clave

A lo largo del proyecto se adquirieron conocimientos importantes, entre ellos:

- Diseño de bases de datos relacionales aplicando buenas prácticas.  
- Uso de procedimientos almacenados, funciones y triggers como base de la lógica del sistema.  
- Integración entre Java(NetBeans) y base de datos(MySQL).  
- Comprensión de cómo estructurar un sistema completo desde cero, especialmente en una base de datos, siendo esta mi primera experiencia trabajando con este tipo de sistemas.  

Además, este proyecto representó una experiencia de mucho aprendizaje, ya que durante el desarrollo se mantuvo un alto nivel de interés en la clase. Pude asistir constantemente sin faltar a una clase, lo que permitió comprender mejor los temas y aprovechar al máximo el tiempo y  el contenido impartido. Fue una clase que resultó interesante y motivadora, lo cual influyó positivamente en el desarrollo del proyecto.

---

# 💡 Sugerencias de mejora del proyecto

Para mejorar el proyecto en futuras versiones, se proponen las siguientes mejoras:

- Desarrollar un frontend más visual e intuitivo, que permita una mejor experiencia de usuario.  
- Mejorar el diseño de los reportes, haciéndolos más atractivos visualmente.  
- Incluir aun más validaciones y mensajes amigables para el usuario(esto de manera visual se comprende mucho mejor).  

```
