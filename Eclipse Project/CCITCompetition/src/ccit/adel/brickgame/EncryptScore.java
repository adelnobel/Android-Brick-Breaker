package ccit.adel.brickgame;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptScore {

	String skey = "QsThgYjhGhGkIoPl";
	String iv = "abcdefghijklmnop";

	public String encrypt(String score) throws Exception {
		Key key = new SecretKeySpec(skey.getBytes(), "AES/CBC/NoPadding");
		IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
		Cipher c = Cipher.getInstance("AES/CBC/NoPadding");
		c.init(Cipher.ENCRYPT_MODE, key, ivSpec);
		score = padString(score);
		byte[] encValue = c.doFinal(score.getBytes());

		return byteArrayToHex(encValue);
	}

	public String decrypt(String encryptedValue) throws Exception {
		Key key = new SecretKeySpec(skey.getBytes(), "AES");
		Cipher c = Cipher.getInstance("AES");
		c.init(Cipher.DECRYPT_MODE, key);
		byte[] decordedValue = hexStringToByteArray(encryptedValue);
		byte[] decValue = c.doFinal(decordedValue);
		String decryptedValue = new String(decValue);
		return decryptedValue;
	}

	public byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
					.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	public String byteArrayToHex(byte[] array) {
		StringBuilder hex = new StringBuilder(array.length * 2);
		for (byte b : array)
			hex.append(String.format("%02x", b));
		return hex.toString();
	}

	private String padString(String source) {
		char paddingChar = ' ';
		int size = 16;
		int padLength = size - source.length() % size;

		for (int i = 0; i < padLength; i++) {
			source += paddingChar;
		}

		return source;
	}

}
