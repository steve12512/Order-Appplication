package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.exception.*;
import com.example.demo.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }


    public User createUser(int id, int age, String username, String email){
        if (id <= 0) {
            throw new InvalidIdException(id);
        }
        if (repository.findById(id).isPresent()){
            throw new UserAlreadyExistsException(id);
        }
        User user = new User(username,id,email,age);
        repository.save(user);
        return user;
    }

    public List<User> getUsers(){
        List<User> users = repository.findAll();
        return users;
    }

    public Page<User> getUsers(Pageable pageable){
        return repository.findAll(pageable);
    }



    public User updateUserEmail(int id, String new_email){
        Optional<User> userOpt = repository.findById(id);
        if (userOpt.isEmpty()) {
            throw new UserNotFoundException(id);
        }
        User user = userOpt.get();
        if (user.getEmail().equals(new_email)){
            throw new SameEmailException(id,new_email);
        }
        user.setEmail(new_email);
        repository.save(user);
        return user;
    }

    public User getUserById(int id){
        Optional<User>  userOpt = repository.findById(id);
        if (userOpt.isEmpty()){
            throw new UserNotFoundException(id);
        }
        return  userOpt.get();
    }

    public  void deleteById(int id){
        Optional<User> userOpt = repository.findById(id);
        if (userOpt.isEmpty()){
            throw  new UserNotFoundException(id);
        }
        repository.deleteById(id);
    }

    public Page<User> getUsersOlderThan(int minAge, Pageable pageable){
        return repository.findByAgeGreaterThanEqual(minAge,pageable);
    }


    public Page<User> getUsersOlderThanContains(int minAge,String name,Pageable pageable){
       return repository.findByAgeGreaterThanEqualAndUsernameContaining( minAge,name,pageable);
    }





}