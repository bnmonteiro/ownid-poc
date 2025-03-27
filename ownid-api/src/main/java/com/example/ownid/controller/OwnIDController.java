package com.example.ownid.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.ownid.dto.*;
import com.example.ownid.model.User;
import com.example.ownid.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/ownid")
public class OwnIDController {

    private final UserRepository userRepository;

    public OwnIDController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @PostMapping("/setOwnIDDataByLoginId")
//    public ResponseEntity<Object> setOwnIdDataByLoginId(@RequestBody OwnIDData req) {
//        User user = userRepository.findByLoginId(req.getLoginId());
//        if (user == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
//        }
//        user.setOwnIdData(req.getData());
//        userRepository.save(user);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }

    @PostMapping("/setOwnIDDataByLoginId")
    public ResponseEntity <Object> setOwnIdDataByLoginId(@RequestBody OwnIDData req) {
        User user = userRepository.findByLoginId(req.getLoginId());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.OK).body("User not found");
        }
        user.setOwnIdData(req.getData());
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

//    @PostMapping("/getOwnIDDataByLoginId")
//    public ResponseEntity<Object> getOwnIdDataByLoginId(@RequestBody OwnIDSearchRequest req) {
//        User user = userRepository.findByLoginId(req.getLoginId());
//        if (user == null) {
//            return ResponseEntity.status(HttpStatus.OK)
//                    .body(new OwnIdErrorResponse(HttpStatus.NOT_FOUND, "User not found"));
//        }
//        return ResponseEntity.ok(new OwnIDDataResponse(user.getOwnIdData()));
//    }
    @PostMapping("/getOwnIDDataByLoginId")
    public ResponseEntity <Object> getOwnIdDataByLoginId(@RequestBody OwnIDSearchRequest req) {
        User user = userRepository.findByLoginId(req.getLoginId());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.OK).
                    body(new OwnIdErrorResponse(HttpStatus.NOT_FOUND, "User not found"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new OwnIDDataResponse(user.getOwnIdData()));
    }


//    public ResponseEntity<OwnIDSessionResponse> getSessionByLoginId(@RequestBody OwnIDSearchRequest req) {
//        User user = userRepository.findByLoginId(req.getLoginId());
//        if (user == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//        Algorithm alg = Algorithm.HMAC256("secret");
//        String token = JWT.create()
//                .withClaim("loginId", req.getLoginId())
//                .sign(alg);
//        return ResponseEntity.ok(new OwnIDSessionResponse(token));
//    }

    @PostMapping("/getSessionByLoginId")
    public ResponseEntity <OwnIDSessionResponse> getSessionByLoginId(@RequestBody OwnIDSearchRequest req) {
        User user = userRepository.findByLoginId(req.getLoginId());
        Algorithm alg = Algorithm.HMAC256("secret");
        String token = JWT.create().
                withClaim("loginId", req.getLoginId()).
                sign(alg);

        return ResponseEntity.status(HttpStatus.OK).body(new OwnIDSessionResponse(token));
    }

}
