package pl.pilaf.inz.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.pilaf.inz.model.User;
import pl.pilaf.inz.repository.UserRepository;
import pl.pilaf.json.UserLoginJson;

@RestController
@RequestMapping(value = "/login")
public class LoginController {

	@Autowired
	private UserRepository userRepository;

	@Inject
	private SessionBiz sessionBiz;

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> logIn(@RequestBody UserLoginJson user) {
		// if (sessionBiz.isLogedIn()){
		// return new ResponseEntity<String>(HttpStatus.OK);
		// }
		List<User> users = userRepository.findByLogin(user.getLogin());
		if (users.size() != 1) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		User valUser = users.get(0);
		boolean loged = valUser.getPassword().equals(user.getPassword());
		sessionBiz.setLogedIn(loged);
		sessionBiz.setUser(valUser);
		return (loged) ? new ResponseEntity<String>(HttpStatus.OK) : new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	}

}
