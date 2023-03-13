package my.rest.demo.controllers;

import my.rest.demo.models.User;
import my.rest.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> enterUser(@RequestBody User user){
        if (userService.authUser(user.getUsername(), user.getPassword())) {
            return new ResponseEntity<>(user.getPassword(), HttpStatus.OK);
        } else {return new ResponseEntity<>(user.getPassword(),HttpStatus.UNAUTHORIZED);}
    }

    @PostMapping("/registration")
    public ResponseEntity<User> addNewUser(@RequestBody User user) {

        try{
            userService.createUser(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name="id") long id) {
        boolean isRemoved = userService.deleteUser(id);
        return isRemoved
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
