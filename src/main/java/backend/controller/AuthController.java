package backend.controller;

import backend.model.*;
import backend.payload.request.SignupRequest;
import backend.payload.response.MessageResponse;
import backend.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class AuthController {



    @Autowired
    PersonneRepository userRepository;

    @Autowired
    FormateurRepository formateurRepository;

    @Autowired
    ClientRepository clpRepository;

    @Autowired
    EntrepriseRepository eseRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;


    @GetMapping("/login")
    public Principal login(Principal principal){
        System.out.println("UserLogged:"+principal);
        return  principal ;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        personne user = new personne(signUpRequest.getCin(),signUpRequest.getUsername(),signUpRequest.getPrenom(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getAdresse(),signUpRequest.getTel());



        String strRoles = signUpRequest.getRole();
        //Set<role> roles = new HashSet<>();

        if (strRoles == null) {
            /*role adminRole = roleRepository.findByNom(ERole.admin)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);*/
            user.setRole("admin");
            userRepository.save(user);
        } else {

                switch (strRoles) {
                    case "client":
                        /*role userRole = roleRepository.findByNom(ERole.client)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);*/

                            client p= new client(signUpRequest.getCin(),signUpRequest.getUsername(),signUpRequest.getPrenom(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()),
                                signUpRequest.getAdresse(),signUpRequest.getTel(),signUpRequest.getNiveau());
                            p.setRole("client");
                        clpRepository.save(p);
                        break;

                    case "entreprise":
                        //role eseRole = roleRepository.findByNom(ERole.entreprise)
                               // .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        //roles.add(eseRole);

                        entreprise e= new entreprise(signUpRequest.getRcs(),signUpRequest.getNom());
                        eseRepository.save(e);

                        break;

                    case "formateur":
                        /*role formRole = roleRepository.findByNom(ERole.formateur)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(formRole);*/
                        formateur f= new formateur(signUpRequest.getCin(),signUpRequest.getUsername(),signUpRequest.getPrenom(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()),
                                signUpRequest.getAdresse(),signUpRequest.getTel(),signUpRequest.getTypeformateur());
                        f.setRole("formateur");

                        formateurRepository.save(f);
                        break;
                    default:
                      //  role adminRole = roleRepository.findByNom(ERole.admin)
                            //    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        //roles.add(adminRole);
                    //user.setRoles(roles);
                        user.setRole("admin");
                        //System.out.println(signUpRequest.getRole());
                        userRepository.save(user);
                }

        }





        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
