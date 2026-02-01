package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.dto.requests.user_requests.*;
import com.example.demo.exception.*;
import com.example.demo.exception.SameEmailException;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.exception.UserisAlreadyInactiveException;
import com.example.demo.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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


    public User createUser( int age, String username, String email){

        if (repository.findByUsername(username).isPresent()){
            throw new UserAlreadyExistsException(username);
        }
        User user = new User(username,email,age,true);
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


    @Transactional
    public User updateUserEmail(Long id, String new_email){
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

    public User getUserById(Long id){
        Optional<User>  userOpt = repository.findById(id);
        if (userOpt.isEmpty()){
            throw new UserNotFoundException(id);
        }
        return  userOpt.get();
    }

    @Transactional
    public  void deleteById(Long id){
        Optional<User> userOpt = repository.findById(id);
        if (userOpt.isEmpty()){
            throw  new UserNotFoundException(id);
        }
        repository.deleteById(id);
    }

    public Page<User> getUsersAgeGreaterThanEqual(@Valid  UserSearchRequest request, Pageable pageable){
        return  (request.username().isEmpty())
                ?this.getUsersOlderThan(request.minAge(), pageable)
                :this.getUsersAgeGreaterThanEqualAndUsernameContaining(request.minAge(),request.username(),pageable);
    }




    public Page<User> getUsersOlderThan(int minAge, Pageable pageable){
        return repository.findByAgeGreaterThanEqual(minAge,pageable);
    }


    public Page<User> getUsersAgeGreaterThanEqualAndUsernameContaining(int minAge, String name, Pageable pageable){
        System.out.println(name);
       return repository.findByAgeGreaterThanEqualAndUsernameContaining( minAge,name,pageable);
    }

    @Transactional
    public User setUserStatusToInactive(Long id){
        Optional userOpt = repository.findById(id);
        if (userOpt.isEmpty()) throw new UserNotFoundException(id);
        User user = (User) userOpt.get();
        if (user.getIsActive() == false) throw new UserisAlreadyInactiveException(id);
        user.setActive(false);
        repository.save(user);
        return  user;
    }

    public Page<User> getRankedUsers(UserSummaryRequest request){
        Pageable pageable = (request.pageable() != null )? request.pageable(): PageRequest.of(0,10);
        int minAge = (request.minAge() != null)?request.minAge():18;
        int maxAge = (request.maxAge() != null)?request.maxAge():100;
        String usernameContains = (request.usernameContains() != null)?request.usernameContains():"";
        Boolean isActive = request.isActive();
        return repository.findRankedUsers(minAge,maxAge,usernameContains,isActive,pageable);
    }



}