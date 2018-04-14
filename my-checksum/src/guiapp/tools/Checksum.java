package guiapp.tools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Checksum {

	MessageDigest messageDigest;
	String algorithm;
	
	/*
	 * Every implementation of the Java platform is required to support the following standard MessageDigest algorithms: 
     * MD2
     * MD5
     * SHA-1    160-bit hash
     * SHA-256
     * SHA-384
     * SHA-512
	 */
	public Checksum(String algorithm) {
		try {
			this.algorithm = algorithm;
			messageDigest = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String forFile(String filename) {

	    byte[] dataBytes = new byte[1024];
	    int nread = 0;

	    FileInputStream fis = null;
		try {
			fis = new FileInputStream(filename);
		    while ((nread = fis.read(dataBytes)) != -1) {
		    	messageDigest.update(dataBytes, 0, nread);
		    };
		    
		    fis.close();
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			return "File not found";
		} catch (IOException e) {
			//e.printStackTrace();
			return "IOException while reading file";
		} finally {
			try {
				if (fis !=null ) {
					fis.close();
				}
			} catch (IOException e) {
				//e.printStackTrace();
				return "IOException while closing file";
			}
		}

	    byte[] mdbytes = messageDigest.digest();

	    //convert the byte to hex format
	    StringBuffer sb = new StringBuffer("");
	    for (int i = 0; i < mdbytes.length; i++) {
	    	/*
	    	 * Adding 0x100 to the number and converting it to String will preserve leading zeroes.
	    	 * That’s why substring(1) is needed to truncate the “hundreds"
	    	 */
	    	sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
	    }

	    return "["+ algorithm + "]: " + sb.toString();
	}
}
