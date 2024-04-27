<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Expires" content="0">
        <link rel="stylesheet" href="/css/Login.css">
        <title>Vault Login</title>
    </head>
    <body>
        <div class="head">
            <div class="logo">
             <img src="/images/mylogo1.png" alt="logo">
            </div>
            <div class="links">
            <a href="#">
                <li>About</li>
            </a><a href="#">
                <li>Links</li>
            </a><a href="#">
                <li>Review</li>
            </a><a href="#">
                <li>Contact</li>
            </a>
            </div>
            <div class="button">
                <a href="register"><button>Register</button></a>
            </div>
        </div>

        <div class="template1">
            <div class="template2">
                <p id="Name">VaultDrive</p>
              <div class="text">
                <p id="top">Nice to see you again !</p><br>
               WELCOME BACK <br>
                <p id="bottom">Your trusted destination for storing and managing all your uploaded data with peace of mind.
                     Your files are safeguarded here, ensuring privacy and accessibility whenever you need them.
                     Explore the ease and security of our vault and take control of your data today!</p>
              </div>
            </div>
            <div class="formtemp">
                <div class="context">
                    Login Account
                </div>
                <form action="/submit" method="post">
                    <div class="input">
                        <img src="/images/mail (2).png" alt="i" id="icon">
                        <input type="text" name="Email_id" autocomplete="off" value="Email Id" required>
                    </div>
                    <div class="input">
                        <img src="/images/password (1).png" alt="i" id="icon">
                        <input type="text" name="password" autocomplete="off" value="Password" required>
                    </div>

                    <div class="butt">
                        <button>Login</button>
                    </div>
                </form>
            </div>
        </div>
    </body>
    </html>