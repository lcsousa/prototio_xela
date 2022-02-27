package br.com.samsung.wms.latam.cellowmsestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.samsung.wms.latam.cellowmsestore.model.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    public Optional<UserEntity> findByLogin(String login);
    public Optional<UserEntity> findByLoginAndPassword(String login,String password);

}
