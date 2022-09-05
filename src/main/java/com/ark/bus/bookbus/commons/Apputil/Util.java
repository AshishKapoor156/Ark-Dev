package com.ark.bus.bookbus.commons.Apputil;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.lang.time.FastDateFormat;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;


public class Util {

	public static String appendChar(String data, char ch, int totalLen,
			boolean appendLeft) {
		if (data == null)
			data = "";
		StringBuilder sb = new StringBuilder();
		if (!appendLeft)
			sb.append(data);
		for (int i = 1; i <= (totalLen - data.length()); i++)
			sb.append(ch);
		if (appendLeft)
			sb.append(data);
		return sb.toString();
	}

	public static String getANBFromCardNo(String cardNo) {
		int cardlen = cardNo.length();
		return (cardlen == 12) ? cardNo : cardNo.substring(cardlen - 13,
				cardlen - 1);
	}

	public static String formatAmountISO(String amount) {
		return appendChar(amount.replace(".", ""), '0', 12, true);
	}

	public static String getAmountWithDecimalPosition(String amount, int decPos) {
		long amt = Long.parseLong(amount.toString());
		if (amt == 0)
			return "0";
		String str = Long.toString(amt);
		StringBuilder ls_Amount = new StringBuilder();
		if (str.length() == decPos) {
			ls_Amount.append("0.");
			ls_Amount.append(str);
		} else if (str.length() < decPos) {
			str = appendChar(str, '0', decPos, true);
			ls_Amount.append("0.");
			ls_Amount.append(str);
		} else {
			ls_Amount.append(str);
			ls_Amount.insert(ls_Amount.length() - decPos, '.');
		}
		return ls_Amount.toString();
	}

	public static String getLength(String str, int byteLength) {
		if (str == null)
			return null;
		StringBuilder sb = new StringBuilder();
		String strLen = Integer.toString(str.length());
		for (int i = strLen.length(); i < byteLength; i++)
			sb.append("0");
		sb.append(strLen);
		return sb.toString();
	}

	public static String getCompressedLength(String str, int byteLength) {
		if (str == null)
			return null;
		StringBuilder sb = new StringBuilder();
		String strLen = Integer.toString(str.length());
		for (int i = strLen.length(); i < byteLength; i++)
			sb.append("0");
		sb.append(strLen);
		return IsoUtil.hex2AsciiChar(sb.toString());
	}

	public static boolean isNullOrEmpty(String value) {
		return (null == value || value.isEmpty());
	}

	public static boolean isAnyNullOrEmpty(String... values) {
		for (String v : values)
			if (null == v || v.isEmpty())
				return true;
		return false;
	}

//	public static String maskCardNumber(String cardNo, int prefix, int sufix) {
//		return (cardNo.length() - (sufix + prefix) <= 0) ? cardNo : StringUtils
//				.rightPad(cardNo.substring(0, prefix), cardNo.length() - sufix,
//						'X')
//				+ cardNo.substring(cardNo.length() - sufix, cardNo.length());
//	}

	public static String getCompressedData(String str, int byteLength) {
		if (str == null)
			return null;
		StringBuilder sb = new StringBuilder();
		for (int i = str.length(); i < byteLength; i++)
			sb.append("0");
		sb.append(str);
		StringBuilder sbout = new StringBuilder();
		for (int j = 0; j < byteLength; j += 2)
			sbout.append(IsoUtil.hex2AsciiChar(sb.toString()
					.substring(j, j + 2)));
		return sbout.toString();
	}

	public static String getIsoFsscUniqueId(String rrn, String stan,
			String terminalId) {
		StringBuilder buffer = new StringBuilder(rrn);
		buffer.append(stan);
		buffer.append(terminalId);
		return buffer.toString();
	}

	public static String capitalize(final String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0)
			return str;
		final char firstChar = str.charAt(0);
		final char newChar = Character.toTitleCase(firstChar);
		if (firstChar == newChar)
			return str;
		char[] newChars = new char[strLen];
		newChars[0] = newChar;
		str.getChars(1, strLen, newChars, 1);
		return String.valueOf(newChars);
	}

//	public static String getTime(String format) {
//		return FastDateFormat.getInstance(format).format(new Date());
//	}

	public static String getFormattedDateTime(final String format) {
		return new SimpleDateFormat(format).format(new Date());
	}

	public static String getDateTimeInGMT(final String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		return sdf.format(new Date());
	}

	public static boolean amIRightThread(String threadName) {
		if (threadName.equalsIgnoreCase(Thread.currentThread().getName()))
			return false;
		return true;
	}

	public static String hashSHA512(String code2Hash, String salt)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		md.update(salt.getBytes(StandardCharsets.UTF_8));
		byte[] bytes = md.digest(code2Hash.getBytes(StandardCharsets.UTF_8));
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++)
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
					.substring(1));
		return sb.toString();
	}

	public static String decToHex(int dec) {
		return Integer.toHexString(dec).toUpperCase();
	}

	public static String rightTrimChars(String data, int maxLen) {
		return data.substring(0,
				data.length() > maxLen ? maxLen : data.length());
	}

	public static String getDateTimeInUTC(String format) throws ParseException {
		// DateFormat dateFormat = new SimpleDateFormat("HHmmss");
		Calendar cal = Calendar.getInstance();
		try {
			// String hour = format.substring(4, 6);
			// // String minute = format.substring(2, 4);
			//
			// cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hour) + 2);
			// // cal.set(Calendar.MINUTE, Integer.valueOf(minute) + 30);
			//

			cal.setTimeZone(TimeZone.getTimeZone("IST"));

			// adding hours into Date
			cal.add(Calendar.HOUR_OF_DAY, 2);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// return dateFormat.format(cal.getTime());
		// return cal.getTime().toString();
		return getTime(cal);
	}

	/**
	 *
	 * @return current Date from Calendar in HH:mm:SS format
	 *
	 *         adding 1 into month because Calendar month starts from zero
	 */
	public static String getTime(Calendar cal) {
		return "" + "0" + (cal.get(Calendar.MONTH) + 1)
				+ cal.get(Calendar.DATE) + cal.get(Calendar.HOUR_OF_DAY)
				+ (cal.get(Calendar.MINUTE)) + cal.get(Calendar.SECOND);
	}

	
	public static void main(String[] args) {
		
		System.out.println(getDateTimeInGMT("YY"));
		
	}
	
	
}
