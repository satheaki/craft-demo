package com.intuit.craftdemo.service;

import com.intuit.craftdemo.model.User;
import com.intuit.craftdemo.repositories.UserRepository;
import com.intuit.craftdemo.security.jwt.JwtTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImplementation implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenManager jwtTokenManager;


    /**
     * Method for following a user
     *
     * @param followerUserName: username of user to be followed
     * @return : Returns a user object in response entity
     */
    @Override
    public User follow(String followerUserName) {
        String userName = jwtTokenManager.getUserNameFromContext();
        User user = userRepository.findUserByUserName(userName);
        User follower = userRepository.findUserByUserName(followerUserName);
        if (user == null || follower == null)
            return null;
        else {
            ArrayList<String> existingFollowers = user.getFollowing();
            if(existingFollowers.contains(follower.getUserName())){
                User dummy=new User();
                dummy.setUserName("existing");
                return dummy;
            }
            else{
                existingFollowers.add(followerUserName);
                return userRepository.save(user);
            }

        }

    }


    /**
     * Method for fetching followers of a user
     *
     * @param userName: current logged in user's username
     * @return: Returns a list of users, the current user is following
     */
    @Override
    public ArrayList<String> getFollowing(String userName) {
        User user = userRepository.findUserByUserName(userName);
        if (user != null)
            return user.getFollowing();
        else
            return null;
    }

    /**
     * Method for unfollowing a user
     *
     * @param followerUserName: username of user to unfollow
     * @return: Returns a user object in response entity
     */
    @Override
    public User unfollow(String followerUserName) {
        String userName = jwtTokenManager.getUserNameFromContext();
        User user = userRepository.findUserByUserName(userName);
        User follower = userRepository.findUserByUserName(followerUserName);
        if (user == null || follower == null)
            return null;
        else {
            ArrayList<String> existingFollowers = user.getFollowing();
            existingFollowers.remove(followerUserName);
            return userRepository.save(user);
        }
    }


    /**
     * Method to check if user already exists
     *
     * @param userName: username of user
     * @return: Returns a User object
     */
    @Override
    public User checkUser(String userName) {
        User user = userRepository.findUserByUserName(userName);
        if (user == null)
            return null;
        else
            return user;
    }


    /**
     * Method for registering a valid LDAP user
     *
     * @param userName: username of user
     * @return: Returna User object
     */
    @Override
    public User registerUser(String userName) {
        User newUser = new User();
        newUser.setUserName(userName);
        return userRepository.save(newUser);
    }
}
