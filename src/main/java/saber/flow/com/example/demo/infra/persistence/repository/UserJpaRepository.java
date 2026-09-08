package saber.flow.com.example.demo.infra.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import saber.flow.com.example.demo.infra.persistence.entities.UserEntity;

public interface UserJpaRepository extends JpaRepository<UserEntity, String> {
	Optional<UserEntity> findByEmail(String email);
}