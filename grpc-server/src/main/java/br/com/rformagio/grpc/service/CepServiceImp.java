package br.com.rformagio.grpc.service;

import br.com.rformagio.grpc.server.grpcserver.AddressRequest;
import br.com.rformagio.grpc.server.grpcserver.AddressResponse;
import br.com.rformagio.grpc.server.grpcserver.CepServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.HashMap;
import java.util.Map;

@GrpcService
public class CepServiceImp extends CepServiceGrpc.CepServiceImplBase {

    @Override
    public void getAddress(AddressRequest request, StreamObserver<AddressResponse> responseObserver) {
        String cep = request.getCep();
        Map<String, String> addressMap = new HashMap<>();
        addressMap.put("13960000", "Socorro");
        addressMap.put("01422001", "São Paulo");
        String cidade = addressMap.get(cep);
        AddressResponse response = AddressResponse.newBuilder()
            .setCep(cep)
                .setCidade(cidade)
            .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
