package ezraspring.restful.service;

import ezraspring.restful.entity.User;

import ezraspring.restful.model.RegisterUserRequest;
import ezraspring.restful.repository.UserRepository;
import ezraspring.restful.security.BCrypt;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    Validator validator;

    @Override
    public void register(RegisterUserRequest request) {
        Set<ConstraintViolation<RegisterUserRequest>> constraintViolations= validator.validate(request);

        if(!constraintViolations.isEmpty()){
            throw new ConstraintViolationException(constraintViolations);
        }
        if(userRepository.existsById(request.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Username already registered ");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(BCrypt.hashpw(request.getPassword(),BCrypt.gensalt()));
        user.setName(request.getName());

        userRepository.save(user);
    }


}
