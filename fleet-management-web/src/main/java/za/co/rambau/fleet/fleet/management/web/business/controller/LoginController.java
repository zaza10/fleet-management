package za.co.rambau.fleet.fleet.management.web.business.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.SecurityContext;
import za.co.rambau.fleet.fleet.management.data.entities.User;
import za.co.rambau.fleet.fleet.management.web.business.service.UserService;

/**
 *
 * @author fhatu
 */
@Named(value = "loginController")
@Dependent
public class LoginController {
    @Inject
    private UserService userService;
    @Resource
    private HttpSession httpSession;
    @Resource
    private SecurityContext context;
    private String  username;
    private String password;
    private final User user;
   
    public LoginController() {        
     user =userService.find(username);
     if(user!=null&&user.getPassword().equals(password)){         
         try {
             
             Subject subject=  login(httpSession,username,password);            
         } catch (LoginException ex) {
             Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
         }
     
     
     }
     
     
     
    }

    private Subject login(final HttpSession session, final String userid, final String password) throws LoginException {
        if (session == null) {
            return null;
        }
        LoginContext context = (LoginContext) session.getAttribute("securitycontext");
        if (context != null) {
            return context.getSubject();
        }
        context = new LoginContext("fleet-man", new CallbackHandler() {

            public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
                for (int i = 0; i < callbacks.length; i++) {
                    if (callbacks[i] instanceof NameCallback) {
                        ((NameCallback) callbacks[i]).setName(userid);
                    } else if (callbacks[i] instanceof PasswordCallback) {
                        ((PasswordCallback) callbacks[i]).setPassword(password.toCharArray());
                    } else {
                        throw new UnsupportedCallbackException(callbacks[i]);
                    }
                }
            }

        });
        context.login();
        Subject result = context.getSubject();        
        if (result == null) {
            return null;
        }
        session.setAttribute("securitycontext", context);
        return result;
    }

}
