package com.change.password;

import com.change.password.entity.Response;
import com.change.password.entity.UserEntity;
import com.change.password.repository.UserDetailsRepository;
import com.change.password.service.PasswordChangeService;
import com.change.password.service.PasswordVerificationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswordApplicationTests {

	@Autowired
	private PasswordChangeService passwordChangeService;
	@Autowired
	private PasswordVerificationService passwordVerificationService;

	@MockBean
	private UserDetailsRepository userDetailsRepository;

	/* 1. Test to identify if old pwd matches with system - success */
	@Test
	public void getExistingPasswordTest() {
		String emailAddress = "test@gmail.com";
		String oldPassword = "Ghfgh6jokjmbkkjjkh*";
		UserEntity testEntity = new UserEntity(5l,"test","test@gmail.com","Ghfgh6jokjmbkkjjkh*");
		when(userDetailsRepository.findByEmail(emailAddress)).thenReturn(testEntity);
		assertEquals(oldPassword,passwordChangeService.getExistingPassword(emailAddress).getData());
	}

	/* 2. Test validity of new pwd - success */
	@Test
	public void validateIncomingPasswordTest() {
		String password = "Ghfgh6jokjmbkkjjkh*";
		assertEquals("success",passwordVerificationService.validateIncomingPassword(password).getMessage());
	}

	/* 3. Test to change pwd in DB - success */
	@Test
	public void updatePasswordTest() {
		String emailAddress = "testing2@gmail.com";
		String newPassword = "yhFlok8jmbkljjkikh*";
		UserEntity testEntity = new UserEntity(5l,"test","testing2@gmail.com","Ghfgh6jokjmbkkjjkh*");
		userDetailsRepository.updateUserPassword(newPassword, emailAddress);
		verify(userDetailsRepository,times(1)).updateUserPassword(newPassword,emailAddress);
	}

	/* 4. Test to verify old pwd and change pwd - success */
	@Test
	public void changePasswordTest() {
		String emailAddress = "testing2@gmail.com";
		String oldPassword = "Ghfgh6jokjmbkkjjkh*";
		String newPassword = "yhFlok8jmbkljjkikh*";
		UserEntity testEntity = new UserEntity(5l,"test","testing2@gmail.com","Ghfgh6jokjmbkkjjkh*");
		when(userDetailsRepository.findByEmail(emailAddress)).thenReturn(testEntity);
		Response response=passwordChangeService.changePassword(emailAddress, oldPassword, newPassword);
		assertEquals("Password updated Successfuly :)",response.getMessage());
	}

	/* 5. Test to identify at least 18 chars needed in new pwd */
	@Test
	public void validateIncomingPasswordNumOfCharTest() {
		String password = "jyjghiuhj";
		assertNotEquals("success",passwordVerificationService.validateIncomingPassword(password).getMessage());
		assertEquals("Password should be atleast 18 characters",passwordVerificationService.validateIncomingPassword(password).getMessage());

	}
	/* 6. Test to identify at least 1 special char needed in new pwd */
	@Test
	public void validate1SpecialCharTest() {
		String password = "jyjghiuhjgjhkjnlljm";
		assertNotEquals("success",passwordVerificationService.validateIncomingPassword(password).getMessage());
		assertEquals("Password must contain atleast 1 Special Char !@#$&*",passwordVerificationService.validateIncomingPassword(password).getMessage());

	}

	/* 7. Test to identify at least 1 Upper case needed in new pwd */
	@Test
	public void validate1UpperCaseTest() {
		String password = "jyjghjmbiuh$gjhkljm";
		assertNotEquals("success",passwordVerificationService.validateIncomingPassword(password).getMessage());
		assertEquals("Password must contain atleast 1 Upper Case",passwordVerificationService.validateIncomingPassword(password).getMessage());

	}

	/* 8. Test to identify at least 1 Lower case needed in new pwd */
	@Test
	public void validate1LowerCaseTest() {
		String password = "KJNDAHDWSWDM$PJNDD";
		assertNotEquals("success",passwordVerificationService.validateIncomingPassword(password).getMessage());
		assertEquals("Password must contain atleast 1 Lower Case",passwordVerificationService.validateIncomingPassword(password).getMessage());

	}

	/* 9. Test to identify at least 1 Number needed in new pwd */
	@Test
	public void validate1NumberTest() {
		String password = "KJNDfWDMbjnnkl$PJNDD";
		assertNotEquals("success",passwordVerificationService.validateIncomingPassword(password).getMessage());
		assertEquals("Password must contain atleast 1 Number",passwordVerificationService.validateIncomingPassword(password).getMessage());

	}

	/* 10. Test to Not more than 4 Special Char in new pwd */
	@Test
	public void validateNotMoreThan4SpecialCharTest() {
		String password = "K3ND$AW!!!jnklM$PJNDD";
		assertNotEquals("success",passwordVerificationService.validateIncomingPassword(password).getMessage());
		assertEquals("Password CANNOT contain > 4 Special Char !@#$&*",passwordVerificationService.validateIncomingPassword(password).getMessage());

	}

	/* 11. Test to Not more than 50% number in new pwd */
	@Test
	public void validateNotMoreThan50PerNumTest() {
		String password = "1234568267890M$PjNDD";
		assertNotEquals("success",passwordVerificationService.validateIncomingPassword(password).getMessage());
		assertEquals("Password CANNOT contain > 50% Numbers",passwordVerificationService.validateIncomingPassword(password).getMessage());

	}

	/* 12. Test to Not more than 4 Duplicate Chars in new pwd */
	@Test
	public void validateNotMoreThan4DupsTest() {
		String password = "Kaaaaaa2jnklM$PJNDD";
		assertNotEquals("success",passwordVerificationService.validateIncomingPassword(password).getMessage());
		assertEquals("Password CANNOT contain > 4 Duplicate Char",passwordVerificationService.validateIncomingPassword(password).getMessage());

	}

	/* 4. Test to verify old pwd is NOT > 80% match - success */
	@Test
	public void oldPasswordNOT80PercMatchTest() {
		String emailAddress = "testing2@gmail.com";
		String oldPassword = "Ghfgh6jokjmbkkjjkh*";
		String newPassword = "Ghfgh6jokjmbuujjkh*";
		UserEntity testEntity = new UserEntity(5l,"test","testing2@gmail.com","Ghfgh6jokjmbkkjjkh*");
		when(userDetailsRepository.findByEmail(emailAddress)).thenReturn(testEntity);
		assertEquals(true,passwordChangeService.comparePasswordMatchPercentage(oldPassword,newPassword));
	}
}
