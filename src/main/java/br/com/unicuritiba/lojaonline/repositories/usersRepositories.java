package br.com.unicuritiba.lojaonline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.unicuritiba.lojaonline.models.users;

@Repository
public interface usersRepositories extends JpaRepository<users, Long> {
	
}
