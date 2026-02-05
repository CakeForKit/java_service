package ru.bellintegrator.TestService.services;
import ru.bellintegrator.TestService.models.User;
import ru.bellintegrator.TestService.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CurComsumerUserService implements ComsumerUserService {
    private final UserRepository userRepo;

    @Override
    @CacheEvict(cacheNames="getAllUsers", allEntries=true)
    public void save(User user) {
        userRepo.save(user);
    }

    @Override
    @CacheEvict(cacheNames="getAllUsers", allEntries=true)
    public void deleteById(UUID id) {
        try{
            userRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {}
    }
}
