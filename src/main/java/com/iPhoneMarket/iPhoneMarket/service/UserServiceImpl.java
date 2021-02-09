package com.iPhoneMarket.iPhoneMarket.service;

import com.iPhoneMarket.iPhoneMarket.models.Role;
import com.iPhoneMarket.iPhoneMarket.models.User;
import com.iPhoneMarket.iPhoneMarket.repository.RoleRepository;
import com.iPhoneMarket.iPhoneMarket.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void save(User user) {
        logger.debug("(save) user: "+user.toString());
        userRepository.save(user);
    }

    @Override
    public void saveNew(User user){
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.getOne(2));
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        logger.debug("(findByUsername) username: "+username);
        return userRepository.findByUsername(username);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public List<Role> findAllRoles(){return roleRepository.findAll();}

    public void remove(User user){
        userRepository.delete(user);
    }

    public void deleteUserRole(User user, Integer roleId) throws Exception{
        Role role = roleRepository.getOne(roleId);
        if(role == null || !user.getRoles().contains(role)){
            throw new Exception("User hasn't this role");
        }
        user.getRoles().remove(role);
        save(user);
    }

    public void addUserRole(User user, Integer roleId) throws Exception{
        Role role = roleRepository.getOne(roleId);
        if(role == null){
            throw new Exception("Role isn't exist");
        }
        if(user.getRoles().contains(role)){
            throw new Exception("User has this role");
        }
        user.getRoles().add(role);
        save(user);
    }
}
