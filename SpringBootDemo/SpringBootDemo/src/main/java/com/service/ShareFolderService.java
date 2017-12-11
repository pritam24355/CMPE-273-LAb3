package com.service;

import com.entity.GroupT;
import com.entity.User;
import com.repository.GroupTRepository;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.CreateShareFolder;
import org.springframework.web.multipart.MultipartFile;

import javax.jws.soap.SOAPBinding;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ShareFolderService {
    @Autowired
    private UserRepository userRepository;
    private GroupTRepository groupTRepository;


    public boolean createShareFolder(CreateShareFolder createsharefolder) {


        return false;

    }

    public boolean createGroup(String groupName, Integer user_id) {

        if (groupName.equals("") && user_id == null) {
            return false;
        } else {
            GroupT groupT = new GroupT();
            groupT.setGroup_name(groupName);
            groupT.setOwner_id(user_id);
            groupTRepository.save(groupT);
            boolean x = new File("./" + groupT.getGroup_id()).mkdir();
            if (x) return true;
            else return false;
        }
    }

    public boolean addMembersToGroup(Integer group_id, String memberEmail) {

        if (memberEmail.equals("") && group_id == null) {
            return false;
        } else {
            GroupT group = groupTRepository.findByGroup_id(group_id);
            User groupMemberUser = userRepository.findByEmail(memberEmail);
            Set<User> memberSet = group.getUser();
            memberSet.add(groupMemberUser);
            groupTRepository.save(group);


        }

        return true;

    }

    public GroupT[] listUserGroups(String userEmail) {

        if (userEmail.equals("")) {
            return new GroupT[]{};
        } else {
            User user = userRepository.findByEmail(userEmail);
            Set<GroupT> groupSet = user.getGroupt();
            return (GroupT[]) groupSet.toArray();
        }
    }

    public User[] listGroupMembers(Integer group_id) {

        if (group_id == null) {
            return new User[]{};
        } else {
            GroupT groupT = groupTRepository.findByGroup_id(group_id);
            Set<User> memberSet = groupT.getUser();
            return (User[]) memberSet.toArray();
        }
    }

    public void uploader(MultipartFile file, Integer group_id) {

        try {
            byte[] bytes = file.getBytes();

            Path path = Paths.get("./" + group_id);
            Files.write(path, bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
