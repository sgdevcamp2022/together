package com.example.userservice.repository;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tbr_id")
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String email;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, unique = true)
    private String userId;
    @Column(nullable = false, unique = true)
    private String encryptedPwd;


    @OneToMany(mappedBy = "m_user",cascade = CascadeType.ALL)
    private List<FriendEntity> friendList = new ArrayList<>();

    public void deleteFriend(String email){
        this.friendList.stream()
                .filter(o->o.getEmail().equals(email))
                .collect(Collectors.toList())
                .forEach(li->{this.friendList.remove(li);
                li.setM_user(null);});
    }

    public void addFriend(FriendEntity friend){
        this.friendList.add(friend);
    }
}
