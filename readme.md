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

The client is an API where you can get a address by a *CEP* (Postal Address Code). 
There is an endpoint:

* http://localhost:8080/address/{cep}    GET

The client receives the request and calls a method **getAddressByCep**. This method is in a local *stub* that 
sends the request to a gRPC Server

#### Server

The server is running a gRPC Server that receives the request and find an address in a map. We are using a
map to facilitate the development. But it is simple to implement this using a database if you prefer.
 


## Defining: service, request and response

Now we need to write the *.proto* file. Protocol Buffer is used for serializing structured data. 
You can read more about [here](https://developers.google.com/protocol-buffers/docs/overview). 


  