package com.ritesh.UserAuth.Controllers;

import com.ritesh.UserAuth.DBUtils.JDBC;
import com.ritesh.UserAuth.GMailAPI.GMailSender;
import com.ritesh.UserAuth.Hashing.GetHash_ID;
import com.ritesh.UserAuth.Regex_Validation.Password_Validation;
import jakarta.mail.Message;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.ritesh.UserAuth.Model.User;
@Controller
@Service
@Log
public class LoginController {
    @Autowired
    private final Password_Validation regex;
    @Autowired
    private JDBC b;
    @Autowired
    private final User user;
    @Autowired
    private final GetHash_ID hash;

    @Autowired
    private final GMailSender sender;

    public LoginController(Password_Validation regex, User user, GetHash_ID hash, GMailSender sender) {
        this.regex = regex;
        this.user = user;
        this.hash = hash;
        this.sender = sender;
    }

    // this is the Login Page Controller used to handel the Login transactions
    @GetMapping("Login")
    public String login() {
        return "Login";
    }
    @PostMapping("submit")
    public String login_Check(@RequestParam("Email_id")String email_ID,
                              @RequestParam("password")String Password,
                              HttpSession session
                              ){

 //-------------------------------function for the valid password length ---------------------------------------------
   if(!regex.validate(Password)){
       log.info("Password length invalid");
       String error="1";
       session.setAttribute("PassError",error);
       return "redirect:/Login";
   }
// ------------------------------function for the password check ----------------------------------------------------
       Password=hash.Hash_Id(Password);
        user.setEmail_Id(email_ID);
        user.setPassword(Password);
        Boolean flag=b.verify();
        if(!flag){
            log.info("error verification");
            String error="2";
            session.setAttribute("email_invalid",error);
            return "redirect:/Login";
        }

//-----------------------------code for the login email ----------------------------------------------------------------
       String Text="<html lang=\"en\">\n" +
               "<body>\n" +
               "Dear User <br>\n" +
               "\n" +
               "You have Just Logged in into UserAuth Web Application \n "+
               "If you have any questions or concerns, please don't hesitate to contact our support team at <a href=\"www.GoogleSupport.com\">Support</a>" +
               " <br>\n" +
               "\n" +
               "Best regards,\n" +
               "UserAuth Web Services\n" +
               "</body>\n" +
               "</html>";
        String Text2="Logged in UserAuth";
        user.setTo(email_ID);
        user.setText(Text);
        user.setSubject(Text2);
        boolean b= sender.SendEmail();
        session.setAttribute("UserName",user.getEmail_Id());
//----------------------------------------------------------------------------------------------------------------------
        return "Welcome";
    }
}
