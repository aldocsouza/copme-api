package br.gov.pa.iasep.copme_api.model.services;

import br.gov.pa.iasep.copme_api.infra.security.TokenService;
import br.gov.pa.iasep.copme_api.model.entities.DTOs.LoginRequestDTO;
import br.gov.pa.iasep.copme_api.model.entities.DTOs.LoginResponseDTO;
import br.gov.pa.iasep.copme_api.model.entities.DTOs.RequestUserDTO;
import br.gov.pa.iasep.copme_api.model.entities.User;
import br.gov.pa.iasep.copme_api.model.interfaces.mappers.UserMapper;
import br.gov.pa.iasep.copme_api.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public UserService(
            UserRepository userRepository, UserMapper userMapper,
            TokenService tokenService
    ){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
    }

    public String createAccountService(RequestUserDTO userDto){
        if(userRepository.findByUsername(userDto.username()) != null) return "Já existe um usuário com o login informado";
        if(userRepository.findByCpf(userDto.cpf()) != null) return "Já existe um usuário com o CPF informado";
        if(userRepository.findByEmail(userDto.email()) != null) return "Já existe um usuário com o e-mail informado";
        if(userRepository.findByRegistration(userDto.registration()) != null) return "Já existe um usuário com a matrícula informada";

        String encryptedPassword = new BCryptPasswordEncoder().encode(userDto.password());
        User user = new User(
                userDto.fullName(),
                userDto.cpf(),
                userDto.registration(),
                userDto.email(),
                userDto.phone(),
                userDto.username(),
                encryptedPassword,
                userDto.situation(),
                userDto.role()
        );
        userRepository.save(user);

        return "Usuário cadastrado com sucesso!";
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO){

        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequestDTO.username(), loginRequestDTO.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return new LoginResponseDTO(token);
    }

}
