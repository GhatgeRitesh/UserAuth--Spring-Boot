package com.ritesh.UserAuth.Controllers;

import com.ritesh.UserAuth.DBUtils.JDBC;
import com.ritesh.UserAuth.GMailAPI.GMailSender;
import com.ritesh.UserAuth.Model.User;
import jakarta.servlet.http.HttpSession;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Random;

@Component
@Controller
@Log
public class ForgotPassword {
    @Autowired
    private final GMailSender sender;
    @Autowired
    private final User user;
    @Autowired
    private final JDBC temp;

    public ForgotPassword(GMailSender sender, User user, JDBC temp) {
        this.sender = sender;
        this.user = user;
        this.temp = temp;
    }

    @GetMapping("/forgotpassword")
    public String forgot(){
        log.info("into the forgot controller");
        return  "forgotpassword";
    }
    @PostMapping("/Passreset")
    public String Reset(@RequestParam String Email_Id, HttpSession session){

         log.info("working on email verification and entered into the passreset");
        //validating the user exists or not
        boolean flag=temp.validateEmail(Email_Id);
        if(flag){
            user.setTo(Email_Id);
        }
        else{
            System.out.println("invalid Email");
            String error="1";
            session.setAttribute("error",error);
            return "redirect:/forgotpassword";
        }

//-----------------------generating the OTP Manually without using the Gmail OAuth2.0 ----------------------------------
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        // Generate a random 6-digit number
        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10); // Generate a digit between 0 and 9
            sb.append(digit);
        }

        long hashcode = Long.parseLong(sb.toString());
        user.setHashcode(hashcode);


//----------------------------------------------------------------------------------------------------------------------

        //if flag is true
        if(flag){
            String subject="Your One-Time Password (OTP) for Verification";
            String text="<html><body style=\"font-size:16px;color:black;\">Dear user,<br>\n" +
                    "\n" +
                    "Thank you for choosing our service. To complete your registration/authentication, please use the following One-Time Password (OTP):<br>\n" +
                    "\n" +
                    "OTP: <p style=\"font-size:24px\">"+ hashcode + "</p><br>\n" +
                    "\n" +
                    "Please enter this OTP within 2 minutes to verify your account. If you did not request this verification, please ignore this email.<br>\n" +
                    "\n" +
                    "If you have any questions or concerns, please don't hesitate to contact our support team at <a href=\"www.googlesupport.com\">Contact Support</a><br>\n" +
                    "\n" +
                    "Best regards,<br>\n" +
                    "UserAuth WebServices <br></html></body>";
            user.setText(text);
            user.setSubject(subject);

            boolean b=sender.SendEmail();
            if(b){
                System.out.println("Mail sent Successfully");
                session.setAttribute("Email_Id",user.getEmail_Id());
                session.setAttribute("name",hashcode);
                }
            else{
                System.out.println("The Error occured while sending the email");
                String error3="3";
                session.setAttribute("error2",error3);
                return "redirect:/forgotpassword";
            }

        }
        session.setAttribute("Email_Id",Email_Id);

        return "/Code Verification";
    }
}