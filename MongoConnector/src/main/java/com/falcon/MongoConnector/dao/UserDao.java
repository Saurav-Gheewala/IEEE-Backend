package com.falcon.MongoConnector.dao;

import com.falcon.MongoConnector.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends MongoRepository<User , Integer> {
}
