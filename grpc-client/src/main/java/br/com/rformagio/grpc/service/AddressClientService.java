package br.com.rformagio.grpc.service;

import br.com.rformagio.grpc.server.grpcserver.AddressRequest;
import br.com.rformagio.grpc.server.grpcserver.AddressResponse;
import br.com.rformagio.grpc.server.grpcserver.CepServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

@Service
public class AddressClientService {
    public String getAddress(String cep) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        CepServiceGrpc.CepServiceBlockingStub stub = CepServiceGrpc.newBlockingStub(channel);
        AddressResponse addressResponse = stub.getAddress(AddressRequest.newBuilder()
                .setCep(cep)
                .build());
        channel.shutdown();
        return addressResponse.getCidade();
    }
}
