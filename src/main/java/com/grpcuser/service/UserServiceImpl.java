package com.grpcuser.service;

import com.grpcuser.User;
import com.grpcuser.UserId;
import com.grpcuser.UserServiceGrpc;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {

    private final Map<Long, User> userMap = new HashMap<>();

    private long idCount = 1;

    @Override
    public void getUser(UserId request, StreamObserver<User> responseObserver) {

        long userId = request.getId();

        if (userMap.containsKey(userId)) {
            responseObserver.onNext(userMap.get(userId));
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(new StatusException(Status.NOT_FOUND));
        }
    }

    @Override
    public void setUser(User request, StreamObserver<UserId> responseObserVer) {

        request = request.toBuilder().setId(idCount++).build();
        userMap.put(request.getId(), request);

        responseObserVer.onNext(UserId.newBuilder().setId(request.getId()).build());
        responseObserVer.onCompleted();
    }
}
