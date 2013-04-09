package com.vteam.xml_project.util;

public final class PhoneNumberUtil {

	private static final String PHONE_NUMBER_ALLOWED_CHARS = " -(),+";
	private static final int PHONE_NUMBER_MIN_LENGTH = 5;
	private static final int PHONE_NUMBER_MAX_LENGTH = 15;

	private PhoneNumberUtil() {
	}

	public static String stripPhoneNumber(String phoneNumber) {
		try {
			StringBuilder sb = new StringBuilder();

			if (isEmpty(phoneNumber)) {
				return "";
			}
			for (int i = 0; i < phoneNumber.length(); i++) {
				String s = phoneNumber.substring(i, i + 1);

				if (!PHONE_NUMBER_ALLOWED_CHARS.contains(s)) {
					sb.append(s);
				} else if (s.equals("+") && i == 0) {
					sb.append(s);
				}
			}

			String result = sb.toString();
			if (result.isEmpty()) {
				return "";
			}

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static boolean validatePhoneNumber(String phoneNumber) {
		return validatePhoneNumber(phoneNumber, false);
	}

	public static boolean validatePhonebookContactNumber(String phoneNumber) {
		if (isEmpty(phoneNumber)) {
			return false;
		}
		if (hasInvalidChars(phoneNumber.substring(1, phoneNumber.length()))) {
			return false;
		}

		String trimPhoneNumber = phoneNumber;
		if (hasPlusSign(phoneNumber)) {
			trimPhoneNumber = phoneNumber.substring(1);
		}
		if (!validLength(trimPhoneNumber)) {
			return false;
		}

		return true;
	}

	public static boolean validatePhoneNumber(String phoneNumber,
			boolean skipLengthCheck) {
		if (isEmpty(phoneNumber)) {
			return false;
		}
		if (!hasPlusSign(phoneNumber)) {
			return false;
		}
		if (hasInvalidChars(phoneNumber.substring(1, phoneNumber.length()))) {
			return false;
		}

		if (!skipLengthCheck) {
			String trimPhoneNumber = phoneNumber.substring(1);
			if (!validLength(trimPhoneNumber)) {
				return false;
			}
		}

		return true;
	}

	public static boolean validatePhoneNumberForRates(String phoneNumber) {
		if (isEmpty(phoneNumber)) {
			return false;
		}
		if (!hasPlusSign(phoneNumber)) {
			return false;
		}
		if (hasInvalidChars(phoneNumber.substring(1, phoneNumber.length()))) {
			return false;
		}

		return true;
	}

	public static boolean validatePhoneNumberLength(String phoneNumber) {
		String trimPhoneNumber = phoneNumber;

		if (isEmpty(phoneNumber)) {
			return false;
		}

		if (trimPhoneNumber.startsWith("+")) {
			trimPhoneNumber = trimPhoneNumber.substring(1).trim();
		}

		return true;
	}

	private static boolean isEmpty(String origPhoneNum) {
		if (origPhoneNum == null || origPhoneNum.trim().equals("")) {
			return true;
		}
		return false;
	}

	private static boolean hasPlusSign(String origPhoneNum) {
		if (origPhoneNum.charAt(0) != '+') {
			return false;
		}
		return true;
	}

	public static boolean hasInvalidChars(String phoneNumber) {
		String invalidCharFound = StringUtil.findNonDigitChars(phoneNumber);
		if (invalidCharFound != null) {
			return true;
		}
		return false;
	}  

	private static boolean validLength(String phoneNumber) {
		if (phoneNumber == null) {
			return false;
		}
		return (phoneNumber.length() <= PHONE_NUMBER_MAX_LENGTH)
				&& (phoneNumber.length() >= PHONE_NUMBER_MIN_LENGTH);
	}
        
        
}
