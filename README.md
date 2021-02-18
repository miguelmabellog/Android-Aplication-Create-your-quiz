# Crea tu quiz

Esta es una aplicación que permite de forma divertida solucionar preguntar de respuestas multiple para repasar temas que sean importantes para ti, se pueden crear las propias preguntas y cuenta con un tiempo limite para responder estar preguntas.

## Tecnologia aplicada

MVVM: arquitectura. <br />
Room: Base de datos local. <br />
Corutines: corutinas para manejar ejecucion de codigo asincrono.<br />
Paging: Paginacion al mostrar preguntas en RecyclerView.<br />
Navigation: navegacion entre fragments. <br />
DataBinding: Union entre componentes layout y controladores. <br />
Notification: Notifica cuando el tiempo termina. <br />
BroadCast: Se suscribe al evento de que el tiempo se termine. <br />
Test: UI test Espresso. <br />

## Aplicación

Acceso al menu y navigation drawer que permite ver las reglas del juego y de que se trata.

<img src="acercade.gif" width="230" height="370"/>

Comienza a jugar, resuleve tods las reguntas dentro del tiempo y ganas.

<img src="anwer.gif" width="230" height="350"/>

Despues de los 10 segundos asignados sin responder la preguntas perderas y se mostrara una notificacion.

<img src="timeup.gif" width="230" height="350"/>

Crea una nueva pregunta para este proceso se implemente un UI test con espresso.

<img src="newadd.gif" width="130" height="250"/>

Elimina una o todas las preguntas que has creado, las puedes observar todas en una lista

<img src="delete.gif" width="230" height="350"/>

## License

Application developed by Miguel Angel Bello Garcia, github @miguelmabellog



