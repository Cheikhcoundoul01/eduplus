package sn.ipd.eduplus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sn.ipd.eduplus.dto.AuthResponse;
import sn.ipd.eduplus.dto.LoginRequest;
import sn.ipd.eduplus.dto.RegisterRequest;
import sn.ipd.eduplus.entity.Role;
import sn.ipd.eduplus.entity.User;
import sn.ipd.eduplus.exception.DuplicateResourceException;
import sn.ipd.eduplus.repository.UserRepository;
import sn.ipd.eduplus.security.JwtUtil;
import sn.ipd.eduplus.security.UserPrincipal;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("Ce nom d'utilisateur est deja utilise");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Cet email est deja utilise");
        }

        Role role = request.getRole() != null ? request.getRole() : Role.ETUDIANT;

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .enabled(true)
                .build();

        userRepository.save(user);

        UserPrincipal principal = new UserPrincipal(user);
        String token = jwtUtil.generateToken(principal, role.name());

        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .role(role.name())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new sn.ipd.eduplus.exception.ResourceNotFoundException("Utilisateur introuvable"));

        UserPrincipal principal = new UserPrincipal(user);
        String token = jwtUtil.generateToken(principal, user.getRole().name());

        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }
}
