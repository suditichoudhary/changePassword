# Change Password
It is a rest service which verifies the password and changes it, also includes multiple test cases implemented with Mockito

Java version : 1.8 Spring Boot Application Mysql version : 8.0.12 Port : 8081

Main method : com.change.password.PasswordApplication

Test class : com.change.password.PasswordApplicationTests

Sample Request For Verifying new password :

curl -X GET \
  'http://localhost:8081/v1/verify-password?password=Ghfgh6jokj,mbkkjjkh%2A'
  
Sample Request For Change password :

curl -X PUT \
  'http://localhost:8081/v1/change-password?emailAddress=suditichoudhary@gmail.com&oldPassword=Ghfgh6jokjmbkjjkh%2A&newPassword=Fhfgl6jokjubkljjkh$'
  
 
Mysql schema :

create database password;

use password; 

CREATE TABLE `user_details` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL DEFAULT '',
  `user_email_address` varchar(96) NOT NULL DEFAULT '',
  `user_password` text NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`user_email_address`)
) ENGINE=InnoDB

insert into user_details values (null,'suditi','suditichoudhary@gmail.com','Ghfgh6jokjmbkjjkh*');


TEST CASES :

1. Test to identify if old pwd matches with system - success
2. Test validity of new pwd - success
3. Test to change pwd in DB - success
4. Test to verify old pwd and change pwd - success
5. Test to identify at least 18 chars needed in new pwd
6. Test to identify at least 1 special char needed in new pwd
7. Test to identify at least 1 Upper case needed in new pwd
8. Test to identify at least 1 Lower case needed in new pwd
9. Test to identify at least 1 Number needed in new pwd
10. Test to Not more than 4 Special Char in new pwd
11. Test to Not more than 50% number in new pwd
12. Test to Not more than 4 Duplicate Chars in new pwd
13. Test to verify old pwd is NOT > 80% match - success
14. Test to verify old pwd blank checked
15. Test to verify new pwd blank checked 
16. Test to verify email blank checked
17. Test to verify email id doesn't exist
