package com.example.backend_java.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

	public static String toHHmmDDMMyyyy(Date time) {
		try {
			return time == null ? null : (new SimpleDateFormat("HH:mm dd/MM/yyyy").format(time));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

	public static String toDDMMyyyy(Date time) {
		try {
			return time == null ? null : (new SimpleDateFormat("dd/MM/yyyy").format(time));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

}
