package com.project.auction.service;

import java.util.List;
import com.project.auction.model.User;

public interface UserService {
    public List<User> listPersons();
    public void save(User user);
    public void delete(User user);
    public User getPerson(User user);
}
