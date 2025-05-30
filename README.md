# HomeBankingCaC

## Descripción
HomeBankingCaC es una aplicación de banca en línea que permite a los usuarios realizar transferencias, gestionar cuentas y usuarios. Utiliza RabbitMQ para el procesamiento asíncrono de transferencias.

## Requisitos
- Java 21
- Maven 3.6.0 o superior
- RabbitMQ 3.12 o superior

## Tecnologías
- Spring Boot
- Spring AMQP
- H2 Database
- RabbitMQ

## Configuración RabbitMQ
1. Instala RabbitMQ:
    ```sh
    # Docker
    docker run -d --hostname my-rabbit --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:management
    ```

2. Verifica la configuración en `application.properties`:
    ```properties
    spring.rabbitmq.host=localhost
    spring.rabbitmq.port=5672
    spring.rabbitmq.username=guest
    spring.rabbitmq.password=guest
    queue.name=test-queue
    ```

## Instalación
1. Clona el repositorio:
    ```sh
    git clone <URL_DEL_REPOSITORIO>
    ```
2. Navega al directorio del proyecto:
    ```sh
    cd HomeBankingCaC
    ```
3. Compila el proyecto:
    ```sh
    mvn clean install
    ```
## Ejecución
Para ejecutar la aplicación, utiliza el siguiente comando:
```sh
mvn spring-boot:run