package com.service;

import com.entity.GroupT;
import com.entity.Sharewith;
import com.entity.User;
import com.repository.UserRepository;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public File[] getAllUsers1() {
        String x12 = "Test";
        File folder = new File("./" + x12);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }

            
        }
     
        boolean x = new File("./succedd").mkdir();
        boolean x1 = new File("user1").mkdir();
        return listOfFiles;
        

    }


    public void addUser(User user) {
        userRepository.save(user);
        String folder = user.getEmail();
        new File("./" + folder).mkdir();


    }

    public List<User> login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public void store(MultipartFile file) {
      

    }

    public boolean deletefile(String filename, Session attribute) {
        String userFolder = "Test";
        Path deleter = Paths.get("./" + userFolder + "/" + filename);

        try {
            if (Files.deleteIfExists(deleter)) {
                System.out.println("File deleted successfully");
                return true;
            }

        } catch (IOException e) {
            
            System.out.println("Internal Server Error");
            e.printStackTrace();
        }

        return false;
    }

    public boolean sharefile(String filename, String emails) {
     
        Set<GroupT> aaj = userRepository.findById(1).get(0).getGroupt();

        System.out.println(aaj.size());
        System.out.println("aaj");
        String userFolder = "Test";//take from session email
        Path src = Paths.get("./user1");
        String Test = "Test";
        Path linkinto = Paths.get("./" + Test + "/" + src);
        String[] shareinfo = emails.split(",");
        for (String i : shareinfo) {
            //     Files.createLink(target, link);
            Path dest = Paths.get("./" + i + "/" + filename);
            try {
                Files.createSymbolicLink(src, linkinto);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try {
            Files.createSymbolicLink(src, linkinto);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public boolean sharefile(Sharewith sharewith) {
        // TODO Auto-generated method stub
        Set<GroupT> aaj = userRepository.findById(1).get(0).getGroupt();

        System.out.println(aaj.size());
        System.out.println("aaj");
        System.out.println(sharewith.getEmails());
        String userFolder = sharewith.getOwner();//take from session email
        Path src = Paths.get("./user1");
        String Test = "Test";
        Path linkinto = Paths.get("./" + Test + "/" + sharewith.getFilename());
        String[] shareinfo = sharewith.getEmails().split(",");
        for (String i : shareinfo) {
            //     Files.createLink(target, link);
            Path dest = Paths.get("./" + i + "/" + sharewith.getFilename());
            System.out.println("In for loop");
            try {
                Files.createSymbolicLink(dest, src);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return false;
    }
//
//	public boolean deletefile(String filename, ) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//	

    public void uploader(MultipartFile file, String userfolder) {
       

        try {

            
            byte[] bytes = file.getBytes();

            Path path = Paths.get("./" + userfolder + "/" + file.getOriginalFilename());
            Files.write(path, bytes);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}


