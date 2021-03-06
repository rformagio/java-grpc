# Java gRPC with Spring-Boot

gRPC is a RPC framework. It allows to call functions in a remote server as if they were local. It uses *protobuf* (protocol buffer)
to define the message format exchange between the client and the server. 
On the server side, there is a gRPC server running an application that implements the interfaces defined in a *protobuf*. 
On the client side, there is a *stub* (client) that provides the same functions and comunicates with the server.
The gRPC is independent of a language. It's possible to use diferent languages for implement the server application and
the client application. It's only necessary that every application knows the *protobuf*.

![gRPC](/docs/grpc_01.png)


## What will be developed

Let's develop a simple example of a gRPC API using Spring Boot.
We have two applications:

#### Client

The client is an API where you can get a address (only the city) by a *CEP* (Postal Address Code). 
There is an endpoint:

* http://localhost:8080/address/{cep}    GET

The client receives the request and calls a method **getAddressByCep**. This method is in a local *stub* that 
sends the request to a gRPC Server

#### Server

The server is running a gRPC Server that receives the request and find an address in a map. We are using a
map to facilitate the development. But it is simple to implement this using a database if you prefer.
 
![Client x Server](/docs/grpc_02.png)

## Defining: service, request and response

Now we need to write the *.proto* file. Protocol Buffer is used for serializing structured data. 
You can read more about [here](https://developers.google.com/protocol-buffers/docs/overview). 

 ```protobuf
syntax = "proto3";
option java_multiple_files = true;
package br.com.rformagio.grpc.server.grpcserver;

message AddressRequest {
  string cep = 1;
}

message AddressResponse {
  string cep = 1;
  string cidade = 2;
}

service CepService {
  rpc getAddress(AddressRequest) returns (AddressResponse);
}
```

The *.proto* file is used for the client to generate the *stub* and for the server to generate the *skeleton*. How gRPC is language agnostic,
you can use the *.proto* file for generate a client in Java and a server in Go, for example. In this tutorial, we are using Spring, so both, cliente and server, will be genarate in Java.

## Generating: client and server

Each language has a **protoc** implementation that allows generate your *stub* and *skeleton*. In our case, we are using a *maven* plugin.

Server depencencies :
``` xml
		<dependency>
			<groupId>net.devh</groupId>
			<artifactId>grpc-spring-boot-starter</artifactId>
			<version>2.5.1.RELEASE</version>
			<exclusions>
				<exclusion>
					<groupId>io.grpc</groupId>
					<artifactId>grpc-netty-shaded</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
		<dependency>
			<groupId>net.devh</groupId>
			<artifactId>grpc-server-spring-boot-starter</artifactId>
			<version>2.5.1.RELEASE</version>
			<exclusions>
				<exclusion>
					<groupId>io.grpc</groupId>
					<artifactId>grpc-netty-shaded</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
		<dependency>
			<groupId>net.devh</groupId>
			<artifactId>grpc-client-spring-boot-autoconfigure</artifactId>
			<version>2.5.1.RELEASE</version>
			<type>pom</type>
		</dependency>

```

Client dependencies:
``` xml
		<dependency>
			<groupId>net.devh</groupId>
			<artifactId>grpc-spring-boot-starter</artifactId>
			<version>2.5.1.RELEASE</version>
			<exclusions>
				<exclusion>
					<groupId>io.grpc</groupId>
					<artifactId>grpc-netty-shaded</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
		<dependency>
			<groupId>net.devh</groupId>
			<artifactId>grpc-client-spring-boot-starter</artifactId>
			<version>2.5.1.RELEASE</version>
			<exclusions>
				<exclusion>
					<groupId>io.grpc</groupId>
					<artifactId>grpc-netty-shaded</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
        <dependency>
			<groupId>net.devh</groupId>
			<artifactId>grpc-client-spring-boot-autoconfigure</artifactId>
			<version>2.5.1.RELEASE</version>
			<type>pom</type>
		</dependency>
```

Buil for both:

``` xml
	<build>
		<extensions>
			<extension>
				<groupId>kr.motd.maven</groupId>
				<artifactId>os-maven-plugin</artifactId>
				<version>1.6.1</version>
			</extension>
		</extensions>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
			<plugin>
				<groupId>org.xolstice.maven.plugins</groupId>
				<artifactId>protobuf-maven-plugin</artifactId>
				<version>0.6.1</version>
				<configuration>
					<protocArtifact>
						com.google.protobuf:protoc:3.3.0:exe:${os.detected.classifier}
					</protocArtifact>
					<pluginId>grpc-java</pluginId>
					<pluginArtifact>
						io.grpc:protoc-gen-grpc-java:1.4.0:exe:${os.detected.classifier}
					</pluginArtifact>
					<protoSourceRoot>src/main/proto</protoSourceRoot>
				</configuration>
				<executions>
					<execution>
						<goals>
							<goal>compile</goal>
							<goal>compile-custom</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
		</plugins>
	</build>
```

## Building and Running

Go to the grpc-client directory:

``` shell script
 mvn spring-boot:run
```

Go to the grpc-server directory:

``` shell script
mvn spring-boot:run
```

## Testing

Open your browser and:

*http://localhost:8080/address/{cep)*

{cep} : you can use 13960000 or 01422001. 

You can get the full code in my [Github](https://github.com/rformagio/java-grpc)

That's it!

**Rodrigo Formagio**

*Enginer and biker*
  