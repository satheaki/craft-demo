package com.intuit.craftdemo.controllers;

import com.intuit.craftdemo.handler.ApiResponse;
import com.intuit.craftdemo.handler.JWTAuthenticationResponse;
import com.intuit.craftdemo.model.Login;
import com.intuit.craftdemo.model.User;
import com.intuit.craftdemo.security.jwt.JwtTokenManager;
import com.intuit.craftdemo.service.IUserService;
import com.intuit.craftdemo.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


/**
 * @author : Akshay Sathe
 * @version 1.0
 * Controller class for handling all the requests to follow or unfollow a user.
 * Also handles token generation on authenticating the user
 */
@RestController
@RequestMapping("/api.intuit.com/1.0")
public class UserController {

    @Autowired
    IUserService userService;

    @Autowired
    JwtTokenManager jwtTokenManager;

    @Autowired
    AuthenticationManager authenticationManager;


    /**
     * Method mapped for handling following a user
     *
     * @param followerUserName: Username of user to be followed
     * @return: Returns a string response in response entity object
     */
    @RequestMapping(value = "/follow/{follower_username}", method = RequestMethod.POST)
    public ResponseEntity<?> followUser(@PathVariable("follower_username") String followerUserName) {
        User user = userService.follow(followerUserName);
        if (user == null) {
            ApiResponse apiResponse = new ApiResponse(false, "User " + followerUserName + " not found", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }else if(user.getUserName().contains("existing")){
            ApiResponse apiResponse = new ApiResponse(false, "User " + followerUserName + " is already being followed", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }
        String responseMsg = user.getUserName() + " is now following " + followerUserName;
        return new ResponseEntity<>(responseMsg, HttpStatus.OK);
    }


    /**
     * Method mapped for handling unfollowing a user
     *
     * @param followerUserName: Username of user to be followed
     * @return: Returns a string response in response entity object
     */
    @RequestMapping(value = "/unfollow/{follower_username}", method = RequestMethod.POST)
    public ResponseEntity<?> unfollowUser(@PathVariable("follower_username") String followerUserName) {
        User user = userService.unfollow(followerUserName);
        if (user == null) {
            ApiResponse apiResponse = new ApiResponse(false, "User " + followerUserName + " not found", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    /**
     * Method mapped for validating user credentials and token generation
     *
     * @param login : Credentials object having username and password
     * @return: Return a JSON obejct with token in response entity
     */
    @RequestMapping(value = "/login/token", method = RequestMethod.POST)
    public ResponseEntity<?> authenticateUser(@RequestBody Login login) {
        if (login.getUsername().isEmpty() || login.getPassword().isEmpty())
            return new ResponseEntity<>("Username of password invalid", HttpStatus.BAD_REQUEST);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));

        String jwt = jwtTokenManager.generateJwtToken(authentication);

        User user = userService.checkUser(login.getUsername());
        if (user == null)
            userService.registerUser(login.getUsername());

        return new ResponseEntity<>(new JWTAuthenticationResponse(Constants.HEADER_STRING, jwt, Constants.TOKEN_PREFIX), HttpStatus.OK);
    }

}
