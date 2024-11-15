package com.todotic.contactlistapi.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtils {

	private final static String BRAZIL_ZONE = "America/Sao_Paulo";
	
	public static LocalDateTime getHoje() {
		return LocalDateTime.now()
							.atZone(ZoneId.of("UTC"))
				            .withZoneSameInstant(ZoneId.of(BRAZIL_ZONE))
				            .toLocalDateTime();
	}
}
