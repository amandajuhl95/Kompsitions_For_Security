/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.cphbusiness.security.encryption;

/**
 *
 * @author benja
 */
public class Main {
    
     public static void main(String[] args) {
        Encryption_with_vector encryptor = new Encryption_with_vector();
         
        String message = "secret message that no one knows";
        
        String encryptedMessage = encryptor.encrypt(message);
        System.out.println("Encrypted test: " + encryptedMessage );
         
        String decryptedMessage = encryptor.decrypt(encryptedMessage);
        System.out.println("Decrypted test: " + decryptedMessage );
        
        String encryptedM = encryptor.encrypt(message);
        System.out.println("Encrypted test: " + encryptedM );
        
        String decryptedM = encryptor.decrypt(encryptedMessage);
        System.out.println("Decrypted test: " + decryptedM );
      
        
    }
    
}
