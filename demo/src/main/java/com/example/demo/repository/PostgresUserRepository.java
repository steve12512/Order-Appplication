package com.example.demo.repository;

import com.example.demo.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PostgresUserRepository implements  UserRepository{
    public PostgresUserRepository() {
    }

    @Override
    public Optional<User> findUserById(int id){
        return null;
    }

    @Override
    public List<User> findAll(){
        return null;
    }


    @Override
    public boolean saveUser(User user){
        return  true;
    }

    @Override
    public boolean deleteUser(int id){
        return true;
    }

    @Override
    public void updateUserEmail(int id, String new_email){
    }

}