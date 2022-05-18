package com.grpcuser;

import com.grpcuser.service.UserServiceImpl;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class GrpcApplication {

    // Port
    private static final String HOST = "localhost";
    private static final int    PORT = 8080;

    // Shutdown Status
    private static final int RUNNING = 0;

    public static void main(String[] args) {

        Server server = ServerBuilder
                .forPort(PORT)
                .addService(new UserServiceImpl()
                ).build();

        try {
            server.start();
        } catch (IOException exception) {
            log.info(exception.getMessage());
            return;
        }

        // gRPC Client
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(HOST, PORT)
                .usePlaintext()
                .build();

        // channel Open
        UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);

        // 통신
        UserId setUserResult = stub.setUser(User.newBuilder()
                .setUsername("mingble")
                .setEmail("dlsqo2005@naver.com")
                .addRoles("ADMIN")
                .build());

        User getUser = stub.getUser(setUserResult);
        log.info(getUser.toString());

        channel.shutdown();
        Runtime.getRuntime().exit(RUNNING);
    }
}
