package dk.cphbusiness.security.encryption;

import java.util.Base64;

public class Exercise1 {

  public static final String STR1
      = // Base64 encoded
      "VGhlIGlkZW50aXR5IEkgc3RvbGUgd2FzIGZhbHNl";

  public static final String STR2
      = // ROT encoded
      "Cesar thought Cleopatra had a most charming nose";

  public static void main( String[] args ) {
    Base64.Decoder decoder = Base64.getDecoder();
    String dec = new String( decoder.decode( STR1 ) );
    System.out.println("Decoded string is: "+dec);

    int k = 17; // Exercise - which K yields the right result
    for(int i = 0; i<11;i++){
    String res = rot(STR2, k);
    System.out.println("for value "+i+" Decrypted string is: "+res);
    }
    }
  
  

  private static String rot(String in, int offset) {
    final int N = 'Z' - 'A' + 1; // also known as 26...
    StringBuilder builder = new StringBuilder();
    for (char c : in.toCharArray()) {
      if ('A' <= c && c <= 'Z') 
          builder.append((char)(((c - 'A') + offset) % N + 'A'));
      else if ('a' <= c && c <= 'z')
          builder.append((char)(((c - 'a') + offset) % N + 'a'));
      else builder.append(c);
      }
    return builder.toString();
    }

  }
