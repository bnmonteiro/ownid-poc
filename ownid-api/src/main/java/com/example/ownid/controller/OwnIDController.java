package com.example.ownid.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.ownid.dto.OwnIDSessionResponse;
import com.example.ownid.model.User;
import com.example.ownid.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
public class OwnIDController {

    private final UserRepository userRepository;

    public OwnIDController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/setOwnIDDataByLoginId")
    public ResponseEntity<Object> setOwnIdDataByLoginId(
            @RequestBody Map<String, String> body,
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request
    ) {

        logRequest(body, headers, request);

        String loginId = body.get("loginId");
        if (loginId == null) {
            log.warn("⚠️ Missing loginId in request body");
            return ResponseEntity.badRequest().body("loginId is required");
        }

        Optional<User> opUser = userRepository.findByLoginId(loginId);
        if (opUser.isEmpty()) {
            log.warn("❌ User not found with loginId: {}", loginId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        User user = opUser.get();
        String ownIdData = body.get("ownIdData");
        user.setOwnIdData(ownIdData);
        userRepository.save(user);

        log.info("✅ Successfully saved OwnID data for user: {}, ownIdData: {}", loginId, ownIdData);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PostMapping("/getOwnIDDataByLoginId")
    public ResponseEntity<Map<String, String>> getOwnIDDataByLoginId(
            @RequestBody Map<String, String> body,
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request
    ) {

        logRequest(body, headers, request);

        String loginId = body.get("loginId");
        if (loginId == null) {
            log.warn("⚠️ Missing loginId in request body");
            return ResponseEntity.badRequest().build();
        }

        Optional<User> userOptional = userRepository.findByLoginId(loginId);
        if (userOptional.isEmpty()) {
            log.warn("❌ User not found with loginId: {}", loginId);
            return ResponseEntity.notFound().build();
        }

        User user = userOptional.get();
        if (user.getOwnIdData() != null && !user.getOwnIdData().isEmpty()) {
            log.info("✅ Returning OwnID data for user: {}, data: {}", loginId, user.getOwnIdData());
            Map<String, String> response = new HashMap<>();
            response.put("ownIdData", user.getOwnIdData());
            return ResponseEntity.ok(response);
        } else {
            log.info("ℹ️ No OwnID data found for user: {}", loginId);
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/getSessionByLoginId")
    public ResponseEntity<OwnIDSessionResponse> getSessionByLoginId(
            @RequestBody Map<Object, Object> body,
            @RequestHeader HttpHeaders headers,
            HttpServletRequest request
            ) {

        logRequest(body, headers, request);

        String loginId = (String) body.get("loginId");
        if (loginId == null) {
            log.warn("⚠️ Missing loginId in request body");
            return ResponseEntity.badRequest().build();
        }

        Optional<User> opUser = userRepository.findByLoginId(loginId);
        if (opUser.isEmpty()) {
            log.warn("❌ User not found with loginId: {}", loginId);
            return ResponseEntity.notFound().build();
        }

        Algorithm alg = Algorithm.HMAC256("secret");
        String token = JWT.create()
                .withClaim("loginId", loginId)
                .sign(alg);

        log.info("✅ Generated token for loginId {}: {}", loginId, token);
        return ResponseEntity.status(HttpStatus.OK).body(new OwnIDSessionResponse(token));
    }

    private static void logRequest(Map<?, ?> body, HttpHeaders headers, HttpServletRequest request) {

        // Optional: log raw request info (method, URI, etc.)
        log.info("#####################################################################");
        log.info("Received 🌐 Method: {}, URI: {}", request.getMethod(), request.getRequestURI());

        // Log headers
//        headers.forEach((key, value) -> log.info("📨 Header: {} = {}", key, value));
        log.info("📨 Request Headers: {}", headers.asSingleValueMap());

        // Log request body
        log.info("📝 Request body: {}", body);


    }
}
