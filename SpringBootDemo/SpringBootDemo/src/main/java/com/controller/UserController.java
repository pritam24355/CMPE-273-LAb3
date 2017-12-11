package com.controller;

import com.entity.CreateShareFolder;
import com.entity.Sharewith;
import com.entity.User;
import com.service.ShareFolderService;
import com.service.UserService;

import antlr.collections.List;

import org.hibernate.Session;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.w3c.dom.html.HTMLParagraphElement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.File;

@Controller    

@RequestMapping(path="/user") 
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ShareFolderService sharefolderservice;

    @PostMapping(path="/add",consumes = MediaType.APPLICATION_JSON_VALUE ) 
    public  ResponseEntity<?> addNewUser (@RequestBody User user) {
        
        userService.addUser(user);
        System.out.println("Saved");
        return new ResponseEntity(null,HttpStatus.CREATED);
    }

    @GetMapping(path="/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping(path="/login",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody String user, HttpSession session)
    {

        JSONObject jsonObject = new JSONObject(user);
        session.setAttribute("name",jsonObject.getString("username"));
        return new ResponseEntity(userService.login(jsonObject.getString("username"),jsonObject.getString("password")),HttpStatus.OK);
    }
    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {

        userService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

    @GetMapping(path="/list",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody File[] getAllUsers1() {
        
        return userService.getAllUsers1();
    }

    @PostMapping(value = "/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> logout(HttpSession session) {
        System.out.println(session.getAttribute("name"));
        session.invalidate();
        return  new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping(path="/delete",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deletefile(@RequestBody String filename, HttpSession session)
    {
    	System.out.println(session.getAttribute("name"));
    		boolean x = userService.deletefile(filename,(Session) session.getAttribute("name"));
		return new ResponseEntity(HttpStatus.OK);

    }

    @PostMapping(path="/sharing",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sharefile(@RequestBody Sharewith sharewith, HttpSession session)
    {
    		boolean x = userService.sharefile(sharewith);
		return new ResponseEntity(HttpStatus.OK);

    }

    @PostMapping(path="/createsharefolder",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createsharefolder(@RequestBody CreateShareFolder createsharefolder, HttpSession session)
    {
    		boolean x = sharefolderservice.createShareFolder(createsharefolder);
		return new ResponseEntity(HttpStatus.OK);

    }

    @PostMapping("/uploadfiles") 
    public ResponseEntity<?> singleFileUpload(@RequestParam("file") MultipartFile file,
                                   HttpSession session) {
    	String userfolder = session.getAttribute("name").toString();
    	userService.uploader(file,userfolder);
     
    		return new ResponseEntity(HttpStatus.OK);
    }

}