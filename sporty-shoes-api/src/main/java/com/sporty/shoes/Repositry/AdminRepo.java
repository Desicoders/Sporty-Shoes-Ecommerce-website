package com.sporty.shoes.Repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sporty.shoes.enteties.Admins;
@Repository
public interface AdminRepo extends JpaRepository<Admins, Integer> {

}
