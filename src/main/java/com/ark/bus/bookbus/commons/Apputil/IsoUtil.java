package com.ark.bus.bookbus.commons.Apputil;

import java.util.Random;

import com.ark.bus.bookbus.commons.constant.Constants;

public class IsoUtil {
	static final char PAD_CHAR = '0';

	public static char[] toGraphical(long decimal, int length) {
		char[] graphical = new char[length];
		for (int i = 0; i < graphical.length; i++) {
			length--;
			graphical[i] = (char) (decimal / Math.pow(16, 2 * length));
			decimal %= Math.pow(16, 2 * length);
		}
		return graphical;
	}

	public static long toDecimal(char[] graphical) {
		int length = graphical.length;
		long decimal = 0;
		for (int i = 0; i < graphical.length; i++) {
			length--;
			decimal += graphical[i] * (long) Math.pow(16, 2 * length);
		}
		return decimal;
	}

	/**
	 * <i>Pads the zero at the begin</i>
	 * <p>
	 * This is an utility method used for building ISO 8583 Message
	 * <p>
	 * Equivalent to Pad1
	 */
	public static String leftPadZeros(String ilosgStr, int ilonuLength) {
		StringBuilder sb = new StringBuilder(ilonuLength);
		while (sb.length() < (ilonuLength - ilosgStr.length()))
			sb.append(PAD_CHAR);
		sb.append(ilosgStr);
		return sb.toString();
	}

	/**
	 * <i>Pads the zero at the End</i>
	 * <p>
	 * This is an utility method used for building ISO 8583 Message
	 * <p>
	 */

	String padChar(String ilosgStr, char ilochPadChar, int ilonuLen,
			boolean iloboAppend) throws Exception {
		String ilosgPadStr = new String(ilosgStr);
		if (ilonuLen < ilosgStr.length()) {
			return ilosgStr.substring(ilosgStr.length() - ilonuLen);
		}
		for (int i = ilosgStr.length(); i < ilonuLen; i++) {
			ilosgPadStr = (iloboAppend) ? ilosgPadStr + ilochPadChar
					: ilochPadChar + ilosgPadStr;
		}
		return ilosgPadStr;
	}

	public static String pad(String str, char ch, int length, boolean append) {
		String padStr = new String(str);
		if (length < str.length()) {
			return str.substring(0, length);
		}
		for (int i = str.length(); i < length; i++) {
			padStr = (append) ? padStr + ch : ch + padStr;
		}
		return padStr;
	}

	public static String hex2binary(String hexString) {
		if (hexString == null)
			return null;
		if (hexString.length() % 2 != 0)
			hexString = "0" + hexString;
		StringBuilder binary = new StringBuilder();
		StringBuilder temp = new StringBuilder();
		for (int i = 0; i < hexString.length(); i++) {
			temp.append("0000");
			temp.append(Integer.toBinaryString(Character.digit(
					hexString.charAt(i), 16)));
			binary.append(temp.toString().substring(
					temp.toString().length() - 4));
		}
		return binary.toString();
	}

	public static String asciiChar2hex(String iobsgAsciiString) {
		return binary2hex(asciiChar2binary(iobsgAsciiString)).toUpperCase();
	}

	public static String asciiChar2binary(String iobsgAsciiString) {
		if (iobsgAsciiString == null)
			return null;
		StringBuilder losgBinaryString = new StringBuilder();
		StringBuilder losgTemp = new StringBuilder();
		int lonuIntValue = 0;
		for (int i = 0; i < iobsgAsciiString.length(); i++) {
			lonuIntValue = iobsgAsciiString.charAt(i);
			losgTemp.append("00000000");
			losgTemp.append(Integer.toBinaryString(lonuIntValue));
			losgBinaryString.append(losgTemp.toString().substring(
					losgTemp.toString().length() - 8));
		}
		return losgBinaryString.toString();
	}

	public static String binary2hex(String iobsgBinaryString) {
		if (iobsgBinaryString == null)
			return null;
		StringBuilder losgHexString = new StringBuilder();
		for (int i = 0; i < iobsgBinaryString.length(); i += 8) {
			StringBuilder losgTemp = new StringBuilder();
			losgTemp.append(iobsgBinaryString.substring(i, i + 8));
			int lonuIntValue = 0;
			for (int k = 0, j = losgTemp.toString().length() - 1; j >= 0; j--, k++) {
				lonuIntValue += Integer.parseInt("" + losgTemp.charAt(j))
						* Math.pow(2, k);
			}
			losgTemp.append("0").append(Integer.toHexString(lonuIntValue));
			losgHexString.append(losgTemp.toString().substring(
					losgTemp.toString().length() - 2));
		}
		return losgHexString.toString().toUpperCase();
	}

	public static int hex2decimal(String s) {
		String digits = "0123456789ABCDEF";
		s = s.toUpperCase();
		int val = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			int d = digits.indexOf(c);
			val = 16 * val + d;
		}
		return val;
	}

	public static String hex2AsciiChar(String s) {
		return binary2asciiChar(hex2binary(s));
	}

	public static String binary2asciiChar(String iobsgBinaryString) {
		StringBuilder losgCharString = new StringBuilder();
		if (iobsgBinaryString == null)
			return null;
		for (int i = 0; i < iobsgBinaryString.length(); i += 8) {
			String losgTemp = iobsgBinaryString.substring(i, i + 8);
			int lonuIntValue = 0;
			for (int k = 0, j = losgTemp.length() - 1; j >= 0; j--, k++) {
				lonuIntValue += Character.getNumericValue(losgTemp.charAt(j))
						* Math.pow(2, k);
			}
			losgCharString.append((char) lonuIntValue);
		}
		return losgCharString.toString();
	}

	public static String buildMessageType(String iobsgMsgType) {
		switch (iobsgMsgType) {
		case Constants.MSG_TYPE_TXN:
			iobsgMsgType = Constants.MSG_TYPE_RSP_TXN;
			break;
		case Constants.MSG_TYPE_0205:
			iobsgMsgType = Constants.MSG_TYPE_0215;
			break;
		case Constants.MSG_TYPE_AUTH:
			iobsgMsgType = Constants.MSG_TYPE_RSP_AUTH;
			break;
		case Constants.MSG_TYPE_0402:
			iobsgMsgType = Constants.MSG_TYPE_0412;
			break;
		case Constants.MSG_TYPE_RSP_TXN:
			iobsgMsgType = Constants.MSG_TYPE_RSP_TXN;
			break;
		case Constants.MSG_TYPE_OFFLINE:
			iobsgMsgType = Constants.MSG_TYPE_RSP_OFFLINE;
			break;
		case Constants.MSG_TYPE_0221:
			iobsgMsgType = Constants.MSG_TYPE_RSP_OFFLINE;
			break;
		case Constants.MSG_TYPE_BATCHUPLOAD:
			iobsgMsgType = Constants.MSG_TYPE_RSP_BATCHUPLOAD;
			break;
		case Constants.MSG_TYPE_REV_400:
			iobsgMsgType = Constants.MSG_TYPE_RSP_REV_410;
			break;
		case Constants.MSG_TYPE_REV_420:
			iobsgMsgType = Constants.MSG_TYPE_RSP_REV_430;
			break;
		case Constants.MSG_TYPE_REV_421:
			iobsgMsgType = Constants.MSG_TYPE_RSP_REV_430;
			break;
		case Constants.MSG_TYPE_NETWORK_MANAGEMENT:
			iobsgMsgType = Constants.MSG_TYPE_RSP_NETWORK_MANAGEMENT;
			break;
		case Constants.MSG_TYPE_SETTLEMENT:
			iobsgMsgType = Constants.MSG_TYPE_RSP_SETTLEMENT;
			break;
		case Constants.MSG_TYPE_1200:
			iobsgMsgType = Constants.MSG_TYPE_1210;
			break;
		case Constants.MSG_TYPE_1100:
			iobsgMsgType = Constants.MSG_TYPE_1110;
			break;
		case Constants.MSG_TYPE_1400:
			iobsgMsgType = Constants.MSG_TYPE_1410;
			break;
		case Constants.MSG_TYPE_1800:
			iobsgMsgType = Constants.MSG_TYPE_1810;
			break;
		case Constants.MSG_TYPE_9210:
			iobsgMsgType = Constants.MSG_TYPE_9210;
			break;
		case Constants.MSG_TYPE_9230:
			iobsgMsgType = Constants.MSG_TYPE_9230;
			break;
		case Constants.MSG_TYPE_9430:
			iobsgMsgType = Constants.MSG_TYPE_9430;
			break;
		case Constants.MSG_TYPE_9810:
			iobsgMsgType = Constants.MSG_TYPE_9810;
			break;
		}
		return iobsgMsgType;
	}

	public static String pad111(String str, final int length,
			final char bitmapPadAttribute) {
		str = (null == str) ? new String() : str;
		String padStr = new String(str + "");
		if (length <= padStr.length()) {
			return padStr.substring(0, length);
		}
		for (int i = padStr.length(); i < length; i++) {
			padStr = (bitmapPadAttribute == 'F') ? '0' + padStr : padStr + '0';
		}
		return padStr;
	}

	public static String alpha2Hex(String data) {
		char[] alpha = data.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < alpha.length; i++) {
			int count = Integer.toHexString(alpha[i]).toUpperCase().length();
			if (count <= 1) {
				sb.append("0").append(
						Integer.toHexString(alpha[i]).toUpperCase());
			} else {
				sb.append(Integer.toHexString(alpha[i]).toUpperCase());
			}
		}
		return sb.toString();
	}

	public static String padRightZero(StringBuffer s, int i) {
		StringBuffer stringbuffer = new StringBuffer(i);
		int j = i - s.length();
		for (int k = 0; k < j; k++)
			stringbuffer.append("0");

		s.append(stringbuffer.toString());
		return s.toString();
	}

	public static boolean noSplChars(char val) {
		String iChars = "~`!@#$%^&*()+=[]'{}|\"<>?";
		if (iChars.indexOf(val) != -1) {
			return true;
		}
		return false;
	}

	public static String DecimalToBinary(int no) {
		StringBuilder result = new StringBuilder();
		int i = 0;
		while (no > 0) {
			result.append(no % 2);
			i++;
			no = no / 2;
		}
		return result.reverse().toString();
	}

	public static String genRRN(int len) {
		len = len / 2;
		Random random = new Random();
		char[] digits = new char[len];
		digits[0] = (char) (random.nextInt(9) + '1');
		for (int i = 1; i < len; i++)
			digits[i] = (char) (random.nextInt(10) + '0');
		StringBuilder sb = new StringBuilder(new String(digits));
		char[] digits1 = new char[len];
		digits1[0] = (char) (random.nextInt(9) + '1');
		for (int i = 1; i < len; i++)
			digits1[i] = (char) (random.nextInt(10) + '0');
		sb.append(new String(digits1));
		return sb.toString();
	}
	
	private static final String HEX_DIGITS = "0123456789abcdef";
	
	public static String byteArrayToHexString(byte[] data, int length) {
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i != length; i++) {
			int v = data[i] & 0xff;

			buf.append(HEX_DIGITS.charAt(v >> 4));
			buf.append(HEX_DIGITS.charAt(v & 0xf));
		}

		return buf.toString();
	}

	/**
	 * Return the passed in byte array as a hex string.
	 * 
	 * @param data
	 *            the bytes to be converted.
	 * @return a hex representation of data.
	 */
	public static String byteArrayToHexString(byte[] data) {
		return byteArrayToHexString(data, data.length);
	}	
}
