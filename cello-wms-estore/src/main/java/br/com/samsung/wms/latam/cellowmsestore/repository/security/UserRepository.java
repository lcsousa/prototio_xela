package br.com.samsung.wms.latam.cellowmsestore.repository.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.samsung.wms.latam.cellowmsestore.entity.security.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    public Optional<UserEntity> findByLogin(String login);
    public Optional<UserEntity> findByLoginAndPassword(String login,String password);

}
