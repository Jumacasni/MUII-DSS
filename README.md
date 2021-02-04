# MUII-DSS
Asignatura de Desarrollo de Sistemas de Software basados en Componentes y Servicios (DSS) - Máster Profesional en Ingeniería Informática 2020/2021

<details open="open">
  <summary>Tabla de contenidos</summary>
  <ol>
    <li>
      <a href="#teoria">Teoría</a>
      <ul>
        <li><a href="#teoria1">Especificación de operación OCL</a></li>
        <li><a href="#teoria2">Ontología de vehículos con Protégé</a></li>
      </ul>
    </li>
    <li>
      <a href="#practicas">Prácticas</a>
      <ul>
        <li><a href="#practica1">Aplicación con JavaBeans</a></li>
          <ul>
            <li><a href="#practica1e">Enunciado</a></li>
            <li><a href="#practica1c">Código</a></li>
            <li><a href="#practica1m">Software usado</a></li>
            <li><a href="#practica1d">Demo</a></li>
          </ul>
        <li><a href="#practica2">Servicio Web CRUD</a></li>
          <ul>
            <li><a href="#practica2e">Enunciado</a></li>
            <li><a href="#practica2m">Software usado</a></li>
            <li><a href="#practica2-1">Parte 1: Aplicación Java</a></li>
              <ul>
                <li><a href="#practica2-1c">Código</a></li>
                <li><a href="#practica2-1d">Demo</a></li>
              </ul>
            <li><a href="#practica2-2">Parte 2: Aplicación Web</a></li>
              <ul>
                <li><a href="#practica2-2c">Código</a></li>
                <li><a href="#practica2-2d">Demo</a></li>
              </ul>
          </ul>
        <li><a href="#practica3">BPEL</a></li>
        <ul>
            <li><a href="#practica3e">Enunciado</a></li>
            <li><a href="#practica3m">Software usado</a></li>
            <li><a href="#practica3-1">Parte 1: Comparador de precios</a></li>
              <ul>
                <li><a href="#practica3-1c">Código</a></li>
                <li><a href="#practica3-1d">Demo</a></li>
              </ul>
            <li><a href="#practica3-2">Parte 2: Regateo de precios</a></li>
              <ul>
                <li><a href="#practica3-2c">Código</a></li>
                <li><a href="#practica3-2d">Demo</a></li>
              </ul>
          </ul>
        <li><a href="#practica4">Aplicación Android</a></li>
        <ul>
            <li><a href="#practica4e">Enunciado</a></li>
            <li><a href="#practica4c">Código</a></li>
            <li><a href="#practica4m">Software usado</a></li>
            <li><a href="#practica4d">Demo</a></li>
          </ul>
      </ul>
    </li>
  </ol>
</details>

<a name="teoria"></a>
## 1. Teoría

<a name="teoria1"></a>
### Seminario de OCL

Un sistema de una cadena hoteles permite a los clientes hacer reservas en cualquier de los hoteles de tal cadena. Este sistema de reserva permite ofrecer reservas de un tipo de habitación en diferentes hoteles.

La reserva se inicia con una petición de tipo de habitación, hotel y fechas por parte de un posible cliente; se comprueba la disponibilidad y si está disponible una habitación del tipo que quiere el cliente, se le hace la reserva al cliente. Un cliente también podría pedir que se mejore la reserva que le ofrecen y, después, se necesitaría la confirmación del cliente ya que este podría no presentarse en la fecha de entrada. De cualquier manera, el cliente siempre va a necesitar una factura.

Utilizando los operadores: exists, includes, select y asSequence->first, para especificar la operación ```IHotelMgt::makeReservation (...)``` completamente con la notación OCL. En la postcondición nos podremos referir al estado antes de realizar la operación con ```@pre``` de OCL.

* [Especificación de la operación makeReservation](teoria/OCL.pdf)

<a name="teoria2"></a>
### Ontología con Protégé

Desarrollo de una ontología de coches en Protégé.

* [vehicles.owl](teoria/protege/vehicles.owl)
* [Grafos obtenidos](teoria/protege/OWLViz%20graphs)

El diagrama de la jerarquía asertada es el siguiente:

<img src="https://github.com/Jumacasni/MUII-DSS/blob/main/teoria/protege/OWLViz%20graphs/01-asserted_hierarchy_completo.png" width="50%" height="">

El diagrama de la jerarquía inferida es el siguiente:

<img src="https://github.com/Jumacasni/MUII-DSS/blob/main/teoria/protege/OWLViz%20graphs/02-inferred_hierarchy_completo.png" width="50%" height="">

Se infiere que Mercedes es un coche caro (*costly_vehicle*).

<a name="practicas"></a>
## 2. Prácticas

<a name="practica1"></a>
### 2.1 Aplicación con JavaBeans

<a name="practica1e"></a>
#### Enunciado
*By using the JSF framework and Java EE, develop an application with JavaBeans (controller) that reacts and changes the state (color, text labels, etc.) of a Web page (View/Model) that contains more than 3 buttons, text fields and labels.*

<a name="practica1c"></a>
#### Código

* [Práctica 1](practica1)

<a name="practica1m"></a>
#### Software usado

*  [Eclipse IDE 2020-09](https://www.eclipse.org/downloads/packages/release/2020-09)
  * Dynamic Web Project
* Apache Tomcat 8.0.36

<a name="practica1d"></a>
#### Demo

<img src="https://github.com/Jumacasni/MUII-DSS/blob/main/gif/p1.gif" width="100%" height="">

<a name="practica2"></a>
### 2.2 Servicio Web CRUD

<a name="practica2e"></a>
#### Enunciado
*Complete development of an application in Java EE using REST technology with Jersey and JPA frameworks (the last one for persistence of relational data in Java applications), which allows users and administrator to search items in a catalogue of an e-commerce company. The application will easily integrate with standard e-applications on Internet (e.g.: Google Maps):*

* *Part 1) Development of a Java application that accesses the catalogue of products  by using the REST interface. The exchange of data must be preferably carried out through JSON interface. Products should be inserted and deleted in the private shopping cart of the client. Development of the REST server with Jersey, JPA and MySQL.*
* *Part 2) Development of a Web application, which will give the functionality of the Java application and be accessible to the clients from the Web. The Web application will also provide geographical information about the  location of the server that coincides with the central store of the company. Including and deleting products in the catalogue can only be done by authorized personnel (administrator).*

<a name="practica2m"></a>
#### Software usado

* [Eclipse IDE 2020-09](https://www.eclipse.org/downloads/packages/release/2020-09)
* [Jersey 2.25](https://eclipse-ee4j.github.io/jersey.github.io/download.html)
* [EclipseLink](https://www.eclipse.org/eclipselink/downloads/)
* [Apache Derby](https://db.apache.org/derby/)
* Apache Tomcat 8.0.36

<a name="practica2-1"></a>
#### Parte 1: Aplicación Java

<a name="practica2-1c"></a>
##### Código
* [Aplicación Java](practica2-1)

<a name="practica2-1d"></a>
##### Demo

<img src="https://github.com/Jumacasni/MUII-DSS/blob/main/gif/p2-1.gif" width="100%" height="">

<a name="practica2-2"></a>
#### Parte 2: Aplicación Web

<a name="practica2-2c"></a>
##### Código
* [Aplicación Web](practica2-2)

<a name="practica2-2d"></a>
##### Demo

<img src="https://github.com/Jumacasni/MUII-DSS/blob/main/gif/p2-2.gif" width="100%" height="">

<a name="practica3"></a>
### 2.3 BPEL

<a name="practica3e"></a>
##### Enunciado
* *Part 1) Client application calls the BPEL business process and gives the name of the employee, travel destination and dates (date of departure and return). The BPEL process firstly checks the employee’s status to buy the flight tickets in the class (tourist, business, private jet) according with the level of the employee in the company); we assume the existence of a WS that provides the latter information. Afterwards, the BPEL process will check tickets prices with 2 airlines: Iberia and Vueling. We assume again that both flight companies provide a WS for carrying out these consultations. The BPEL process must be able to select the cheaper price and to return a viable trip plan to the client application”.*
* *“The buyer starts by asking a price to the seller and the latter one responds by giving the product’s price or raising an exception if does not know the solicited product or it was not available in the warehouse’s stocks now. The buyer goes on asking for a price to the seller and gets into a repetitive behavior (with updates of the item’s price) up until the buyer decides to accept a price offered by the seller, which he considers as the best possible price for the product. In this assignment, we are asked to develop the complete description of the orchestration above described, which takes place between the buyer and the product’s seller”.*

<a name="practica3m"></a>
#### Software usado

* [Eclipse Photon](https://www.eclipse.org/photon/)
* Eclipse BPEL Designer 1.1.2
* [Apache Tomcat 9](https://tomcat.apache.org/download-90.cgi)
* [Apache Ode v1.3.8](https://ode.apache.org/getting-ode.html)

<a name="practica3-1"></a>
#### Parte 1: Comparador de precios

<a name="practica3-1c"></a>
##### Código
* [Comparador](practica3-1)

<a name="practica3-1d"></a>
##### Demo

<img src="https://github.com/Jumacasni/MUII-DSS/blob/main/gif/p3-1.gif" width="100%" height="">

<a name="practica3-2"></a>
#### Parte 2: Regateo de precios

<a name="practica3-2c"></a>
##### Código
* [Regateo](practica3-2)

<a name="practica3-2d"></a>
##### Demo

<img src="https://github.com/Jumacasni/MUII-DSS/blob/main/gif/p3-2.gif" width="100%" height="">

<a name="practica4"></a>
### 2.4 Aplicación Android

<a name="practica4e"></a>
#### Enunciado
*Complete the code developed in practical assignment no.2 with an app in Java/Android or C++/IOS (client part), which allows app users to search products on  the disclosed catalogue of an e-commerce site. The application will easily integrate with standard geographical location applications (e.g.: Google Maps). Finally, a complete version of the app that must be able to interact with the Web application (RESTful server) will be developed, which will allow downloading the apk from the Web site.*

<a name="practica4c"></a>
#### Código
* [Práctica 4](practica4)

<a name="practica4m"></a>
#### Software usado

* Android Studio
* [Volley](https://github.com/google/volley)
* Google Services

<a name="practica4d"></a>
#### Demo

<img src="https://github.com/Jumacasni/MUII-DSS/blob/main/gif/p4.gif" width="100%" height="">

## Licencia 📄

Este repositorio está bajo la licencia [GPLv3](LICENSE)
