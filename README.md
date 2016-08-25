Google Cloud Messaging MeetMe
================================

Aplicación de mensajeria instanea desarrollada para el Proyecto de desarrollo de aplicaciones multiplataforma en el centro I.E.S. Zaidin-Vergeles promocion 2014-2016.


Introducción
------------

- [Más sobre Google Cloud Messaging](https://developers.google.com/cloud-messaging/)

Indice
---------------

 1. [Como surgio la idea](https://github.com/MeetMeCJ/gcm#como-surgio-la-idea)
 2. [Screenshots](https://github.com/MeetMeCJ/gcm#screenshots)
 3. [Conceptos basicos](https://github.com/MeetMeCJ/gcm#conceptos-basicos)
 
  1 [Codigo](https://github.com/MeetMeCJ/gcm#codigo)
 4. [Soporte](https://github.com/MeetMeCJ/gcm#soporte)
 5. [Licencia](https://github.com/MeetMeCJ/gcm#licencia)

Como surgio la idea
--------------------

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer iaculis erat orci, volutpat malesuada arcu cursus sed. Donec iaculis arcu in velit elementum, eu elementum metus euismod. Donec at urna in velit consequat ornare at vel erat. Ut lacinia quam et condimentum faucibus. Nullam lacinia fringilla accumsan. Curabitur vel nulla eu enim fringilla dignissim. Nullam eget enim quis quam consectetur hendrerit vel nec erat.

Praesent scelerisque, mauris vitae finibus euismod, urna dui condimentum tortor, sed ultricies mauris odio quis tellus. Donec rhoncus purus nec metus sagittis egestas. Sed mattis ex at enim commodo, vel pretium arcu dictum. Sed tempus sit amet leo non suscipit. Cras sed diam tristique, aliquet magna tempus, blandit dolor. Vivamus id viverra neque. Sed ut purus sagittis, porta mauris non, condimentum enim. Fusce quis urna et augue ultrices ullamcorper. Etiam ex metus, dapibus vel elementum nec, imperdiet eu risus. Ut in pellentesque mauris. Cras iaculis ut mi at malesuada. Sed elementum interdum odio et porta. Ut aliquet fermentum dapibus.

Screenshots
-----------
![Screenshot](app/src/main/intro1.jpg =270x480)
![Screenshot](app/src/main/intro3.jpg =270x480)
![Screenshot](app/src/main/main.jpg =270x480)
![Screenshot](app/src/main/setting.jpg =270x480)

Conceptos basicos
--------------------
Aplicación de mensajería instantánea basada en la nube. Utiliza el servicio GCM (Google Cloud Messaging) de Google para poder intercambiar mensajes en json, entre un dispositivo de origen (emisor) y un dispositivo de llegada (receptor).

Además, utiliza un servicio externo de Google desarrollado para almacenar información adicional (datos personales) utilizado java.

Codigo
-------
Nuestra implementacion de codigo busca la elegancia en la sencillez pero con mucha potencia.

```java
@DatabaseTable
public class Contact implements Parcelable {
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String NICK = "nick";
    public static final String TELEPHONE = "telephone";
    public static final String TOKEN = "token";
    public static final String DESCRIPTION = "description";
    public static final String LASTCONNECTION = "lastconnection";
    public static final String SEECONNECTION = "seeconnection";
    public static final String PRIVACY = "privacy";

    @DatabaseField(generatedId = true, columnName = ID)
    private Long id;

    @DatabaseField(columnName = NAME)
    private String name;

    @DatabaseField(columnName = NICK)
    private String nick;

    @DatabaseField(columnName = TELEPHONE)
    private List<String> telephone;

    @DatabaseField(columnName = TOKEN)
    private String token;

    @DatabaseField(columnName = DESCRIPTION)
    private String description;

    @DatabaseField(columnName = LASTCONNECTION)
    private String lastconnection;

    @DatabaseField(columnName = SEECONNECTION)
    private String seeconnection;

    @DatabaseField(columnName = PRIVACY)
    private String privacy;

    ...
```

Soporte
-------

- Web: No disponible de momento

Si encuentra algun error, por favor comuníquenoslo en:
https://github.com/MeetMeCJ/gcm/issues


Licencia
--------

Copyright 2015 Google, Inc.

> Licenciado a la Apache Software Foundation (ASF) en virtud de uno o más acuerdos de licencia para colaboradores. Vea el archivo de AVISO distribuido con este trabajo para obtener información adicional con respecto a la propiedad de los derechos de autor. Las licencias ASF este archivo para que bajo la licencia Apache, versión 2.0 (la "Licencia"); no se puede utilizar este archivo salvo en cumplimiento de la Licencia. Usted puede obtener una copia de la Licencia en

> http://www.apache.org/licenses/LICENSE-2.0

> A menos que lo requiera la ley aplicable o se acuerde por escrito, el software distribuido bajo la Licencia se distribuye "TAL CUAL", SIN GARANTÍAS O CONDICIONES DE CUALQUIER TIPO, ya sea de forma expresa o implícita. Consulte la licencia para los permisos de idioma específico que rige y limitaciones en la licencia.
