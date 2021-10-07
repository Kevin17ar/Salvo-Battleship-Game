package com.codeoftheweb.Salvo.confugurations;

import com.codeoftheweb.Salvo.models.Player;
import com.codeoftheweb.Salvo.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class WebAuthentication extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(inputUser ->{
            Player pl= this.playerRepository.findByEmail(inputUser);
            Player player = this.playerRepository.findByUser(inputUser);

            if (player != null){
                return new User(player.getUser(), player.getPassword(), AuthorityUtils.createAuthorityList("PLAYER"));
            }
            if (pl != null){
                return new User(pl.getUser(), pl.getPassword(), AuthorityUtils.createAuthorityList("PLAYER"));
            }
            else {
                throw new UsernameNotFoundException("Unknown player:" + inputUser);
            }
        });
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
