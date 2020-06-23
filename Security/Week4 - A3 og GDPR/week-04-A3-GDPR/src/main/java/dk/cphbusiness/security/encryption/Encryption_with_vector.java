/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.cphbusiness.security.encryption;

import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author benja
 */
public class Encryption_with_vector {
    private final SecureRandom secureRandom;
    private final byte[] key;
    private final SecretKey secretKey;

    public Encryption_with_vector() {
        
        this.key = new byte[16];
        this.secureRandom = new SecureRandom();
        secureRandom.nextBytes(key);
        this.secretKey = new SecretKeySpec(key,"AES");
        
    }
     

    
    public String encrypt(String plainText) {
        
        String encryptedText = null;
        
        try{
        
        byte[] vector = new byte[12];
        secureRandom.nextBytes(vector);
        
        final Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec parameter = new GCMParameterSpec(128, vector);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameter);
        final byte[] cipherText = cipher.doFinal(plainText.getBytes());
        
        ByteBuffer buffer = ByteBuffer.allocate(4 + vector.length + cipherText.length);
        buffer.putInt(vector.length);
        buffer.put(vector);
        buffer.put(cipherText);
        byte[] cipherMsg = buffer.array();
        
        encryptedText = new String(Base64.getEncoder().encode(cipherMsg));
        
        
        }catch(InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e){
            System.out.println("Exception: " + e);
        }
        
        return encryptedText;
    }
    
      public String decrypt(String encryptedText){
         String decryptedText = null;
         
         try{
             
             final byte[] decorText = Base64.getDecoder().decode(encryptedText);
             ByteBuffer buffer = ByteBuffer.wrap(decorText);
             int vectorLength = buffer.getInt();
             
             byte[] vector = new byte[vectorLength];
             buffer.get(vector);
             
             byte[] cipherText = new byte[buffer.remaining()];
             buffer.get(cipherText);
             
             final Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
             cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(128, vector));
             byte[] plainText = cipher.doFinal(cipherText);
             decryptedText = new String(plainText);

             
         }catch(InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e){
              System.out.println("Exception: " + e);
         }
         
         return decryptedText;     
        
    }
    
}
