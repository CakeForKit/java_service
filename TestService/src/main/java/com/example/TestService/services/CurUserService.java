package com.example.TestService.services;

import com.example.TestService.dto.ActionUser;
import com.example.TestService.dto.MessageDto;
import com.example.TestService.dto.UserSearchRequest;
import com.example.TestService.dto.AddUserRequest;
import com.example.TestService.mappers.UserMapper;
import com.example.TestService.messaging.MessagingService;
import com.example.TestService.models.UserModel;
import com.example.TestService.repositories.UserRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CurUserService implements UserService {
    private final UserRepository userRepo;
    private final MessagingService messagingService;
    private final UserMapper userMapper;
//    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Override
    public Optional<UserModel> getById(UUID id) {
        return userRepo.findById(id);
    }

    @Override
    @Cacheable(cacheNames = "getAllUsers")
    public Page<UserModel> getAll(UserSearchRequest searchRequest, Pageable pageable) {
        Specification<UserModel> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (! searchRequest.getFirstname().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("firstname")), "%" + searchRequest.getFirstname().toLowerCase() + "%"));
            }
            if (! searchRequest.getLastname().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("lastname")), "%" + searchRequest.getLastname().toLowerCase() + "%"));
            }
            if (searchRequest.getAge() != null) {
                predicates.add(cb.equal(root.get("age"), searchRequest.getAge()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        Page<UserModel> pum = userRepo.findAll(spec, pageable);
        System.out.println(pum);
        return userRepo.findAll(spec, pageable);
    }

    @Override
    public UserModel create(AddUserRequest request) {
        UserModel newUser = new UserModel(
                UUID.randomUUID(),
                request.firstname,
                request.lastname,
                request.age,
                false
        );
        var m = new MessageDto(ActionUser.CREATE, newUser);
        messagingService.send(m);
        return  newUser;
    }

    @Override
    public  UserModel update(UUID id,  UserModel user) {
        user.setId(id);
        var m = new MessageDto(ActionUser.UPDATE, user);
        messagingService.send(m);
        return  user;
    }

    @Override
    public void deleteById(UUID id) {
        UserModel user = new UserModel();
        user.setId(id);
        var m = new MessageDto(ActionUser.DELETE_BY_ID, user);
        messagingService.send(m);
    }
}
