package com.trnka.backend.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.trnka.backend.dto.user.PasswordModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.trnka.backend.config.Templates;
import com.trnka.backend.domain.User;
import com.trnka.backend.domain.UserType;
import com.trnka.backend.dto.UsersListDto;
import com.trnka.backend.dto.user.UserModel;
import com.trnka.backend.repository.UserRepository;

import liquibase.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {

    private static final String MSG_USR_CREATED = "Pouzivatel vytvoreny.";
    private static final String MSG_PWD_CHANGED = "Heslo uspesne zmenene.";
    private static final String MSG_USERNAME_AT_LEAST_N_CHARACTERS = "Pouzivatelske meno musi obsahovat minimalne 6 znakov a maximalne 20 znakov!";
    private static final String MSG_PWD_AT_LEAST_N_CHARACTERS = "Heslo meno musi obsahovat minimalne 6 znakov a maximalne 20 znakov!";
    private static final String MSG_PWD_EMPTY = "Heslo nesmie byt prazdne!";
    public static final String MSG_USER_UPDATED = "Pouzivatel upraveny.";
    public static final String MSG_USERNAME_EMPTY = "Pouzivatelske meno nesmie byt prazdne!";
    public static final String MSG_USER_ALREADY_EXISTS = "Pouzivatel s tymto pouzivatelskym menom uz existuje!";

    @Value("user.new.default.pwd")
    private String defaultPwd;


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ModelAndView getUsersList() {
        ModelAndView model = new ModelAndView(Templates.ADMIN_USER_LIST.getTemplateName());
        UsersListDto dto = new UsersListDto();
        dto.setUsers(userRepository.findAll());
        return model.addObject("model", dto);
    }

    public ModelAndView getCreateOrUpdateUI(final Long id) {
        if (id == null){
            return getUiForCreate();
        }

        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            String msg = String.format("User with id %s not found!", id);
            log.error(msg);
            return ErrorPage.create(msg);
        }
        return getUiForEdit(user.get(), null, Collections.emptyList(), null, Collections.emptyList());
    }

    private ModelAndView getUiForCreate() {
        UserModel model = new UserModel();
        model.setType(UserType.TEACHER); // default type

        return getUi(model, new PasswordModel());
    }

    private ModelAndView getUiForEdit(User user, String infoMsg, List<String> errors, String pwdInfoMsg, List<String> passwordErrors) {
        UserModel model = toModel(user, infoMsg, errors);
        PasswordModel pwdModel = toPwdModel(user, pwdInfoMsg, passwordErrors);
        return getUi(model, pwdModel);
    }

    public ModelAndView createOrUpdateUser(UserModel model) {
        // create
        if (model.getId() == null){
            return createUser(model);
        }
        // update
        return updateUser(model);
    }

    private ModelAndView getUi(UserModel model, PasswordModel pwdModel) {
        ModelAndView mv = new ModelAndView(Templates.ADMIN_USER_EDIT.getTemplateName());
        return mv.addObject("model", model).addObject("pwdModel", pwdModel);
    }


    private PasswordModel toPwdModel(final User user, final String infoMsg, final List<String> passwordErrors){
        PasswordModel model = new PasswordModel();
        model.setInfoMessage(infoMsg);
        model.setId(user.getId());
        model.setErrors(passwordErrors);
        return model;
    }


    private UserModel toModel(final User user, String infoMsg, final List<String> errors) {
        UserModel model = new UserModel();
        model.setUsername(user.getUsername());
        model.setId(user.getId());
        model.setType(user.getType());
        model.setErrors(errors);
        model.setInfoMessage(infoMsg);

        return model;
    }

    private User toEntity(final UserModel model, final User user) {
        user.setType(model.getType());
        user.setUsername(model.getUsername());
        // pwd is not changed via this call, therefore not set
        return user;
    }

    private ModelAndView updateUser(UserModel model) {
        model.getErrors().clear();
        Optional<User> found = userRepository.findById(model.getId());
        if (!found.isPresent()) {
            model.getErrors().add("User with id:" + model.getId() + "not found!");
            return getUi(model, new PasswordModel());
        }

        boolean hasUsernameChanged = !model.getUsername().equals(found.get().getUsername());
        if (hasUsernameChanged){
            List<String> errors = validateUsername(model.getUsername(), found.get().getUsername());
            if (!errors.isEmpty()) {
                model.setErrors(errors);
                return getUi(model, new PasswordModel());
            }
        }
        User user = toEntity(model, found.get());
        User savedUser = userRepository.save(user);
        UserModel newModel = toModel(savedUser, MSG_USER_UPDATED, Collections.emptyList());
        PasswordModel pwdModel = toPwdModel(user, null, Collections.emptyList());
        return getUi(newModel, pwdModel);
    }

    private List<String> validateUsername(String newUsername, String oldUsername){

        if (StringUtils.isEmpty(newUsername)){
            return Collections.singletonList(MSG_USERNAME_EMPTY);
        }
        if (newUsername.length() < 6 || newUsername.length() > 20){
            return Collections.singletonList(MSG_USERNAME_AT_LEAST_N_CHARACTERS);
        }

        // when username changed - check whether does not exist user with same username
        if (!newUsername.equals(oldUsername)) {
            Optional<User> foundByNewUsername = userRepository.findByUsername(newUsername);
            if (foundByNewUsername.isPresent()) {
                return Collections.singletonList(MSG_USER_ALREADY_EXISTS);
            }
        }
        return Collections.emptyList();
    }


    private ModelAndView createUser(UserModel model){
        List<String> errors = validateUsername(model.getUsername(), null);
        if (!errors.isEmpty()) {
            model.setErrors(errors);
            return getUi(model, new PasswordModel());
        }

        // set default password, this will be manual set then by admin
        User user = new User();
        user.setUsername(model.getUsername());
        user.setPassword(passwordEncoder.encode(defaultPwd));
        user.setType(model.getType());

        User savedUser = userRepository.save(user);
        return getUi(toModel(savedUser, MSG_USR_CREATED, Collections.emptyList()), toPwdModel(user, null, Collections.emptyList()));
    }


    private List<String> validatePassword(String password){
        if (StringUtils.isEmpty(password)){
            return Collections.singletonList(MSG_PWD_EMPTY);
        }
        if (password.length() < 6 || password.length() > 20){
            return Collections.singletonList(MSG_PWD_AT_LEAST_N_CHARACTERS);
        }
        return Collections.emptyList();
    }


    public ModelAndView updatePassword(final PasswordModel model) {
        Optional<User> found = userRepository.findById(model.getId());
        User user = found.get();
        if (!found.isPresent()) {
            return getUiForEdit(user, null, Collections.singletonList("User with id:" + model.getId() + "not found!"), null, Collections.emptyList());
        }
        List<String> pwdErrors = validatePassword(model.getPassword());
        if (!pwdErrors.isEmpty()){
            return getUiForEdit(user, null, Collections.emptyList(), null, pwdErrors);
        }
        user.setPassword(passwordEncoder.encode(model.getPassword()));
        return getUiForEdit(user, null, Collections.emptyList(), MSG_PWD_CHANGED, pwdErrors);
    }
}
