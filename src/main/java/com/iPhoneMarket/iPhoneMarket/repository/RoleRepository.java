package com.iPhoneMarket.iPhoneMarket.repository;

import com.iPhoneMarket.iPhoneMarket.models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {


}
