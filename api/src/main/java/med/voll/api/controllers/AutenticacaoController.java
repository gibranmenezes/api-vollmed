package med.voll.api.controllers;

import jakarta.validation.Valid;
import med.voll.api.controllers.DadosAutenticacao;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.infra.security.DadosTokenJwt;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired //o spring precisa que implemente o metodo de criação desse atributo. Essa classe dispara o processo.
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados){
        //converte o dto dados em o  dto usado pela classe AuthenticationManager
        var authenticationTokentoken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        //devolve o objeto que representa o usuario autenticado no banco de dados
        var authentication = manager.authenticate(authenticationTokentoken);

        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJwt(tokenJWT));
    }
}
