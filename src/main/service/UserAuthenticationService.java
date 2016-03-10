package service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


public class UserAuthenticationService {

	 public boolean authenticate(String attemptedPassword, byte[] encryptedPassword, byte[] salt)
	    throws NoSuchAlgorithmException, InvalidKeySpecException {
	   byte[] encryptedAttemptedPassword = getEncryptedPassword(attemptedPassword, salt);
	  
	   return Arrays.equals(encryptedPassword, encryptedAttemptedPassword);
	  }
	  
	  public byte[] getEncryptedPassword(String password, byte[] salt)
	    throws NoSuchAlgorithmException, InvalidKeySpecException {
	   String algorithm = "PBKDF2WithHmacSHA1";
	   int derivedKeyLength = 160;
	   int iterations = 20000;
	  
	   KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength);
	  
	   SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
	  
	   return f.generateSecret(spec).getEncoded();
	  }
	  
	  public byte[] generateSalt() throws NoSuchAlgorithmException {
	   SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
	  
	   // Generate a 8 byte (64 bit) salt as recommended by RSA PKCS5
	   byte[] salt = new byte[8];
	   random.nextBytes(salt);
	   return salt;
	 
	  }
	  
	  public static void main(String[] args) {
		UserAuthenticationService service = new UserAuthenticationService();
		try{
			byte[] salt = service.generateSalt();
			byte[] encryptedPassword = service.getEncryptedPassword("test", salt);
			
			System.out.println(service.authenticate("Test", encryptedPassword, salt));
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

}
